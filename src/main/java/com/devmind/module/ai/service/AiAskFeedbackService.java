package com.devmind.module.ai.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.devmind.common.api.PageResult;
import com.devmind.common.api.ResultCode;
import com.devmind.common.exception.BizException;
import com.devmind.module.ai.dto.AskFeedbackRequest;
import com.devmind.module.ai.entity.AiAskFeedback;
import com.devmind.module.ai.entity.AiAskLog;
import com.devmind.module.ai.mapper.AiAskFeedbackMapper;
import com.devmind.module.ai.mapper.AiAskLogMapper;
import com.devmind.module.ai.vo.AskFeedbackResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiAskFeedbackService {

    private static final int STATUS_ACTIVE = 1;
    private static final long MAX_PAGE_SIZE = 50;

    private final AiAskFeedbackMapper feedbackMapper;
    private final AiAskLogMapper askLogMapper;

    public AiAskFeedbackService(AiAskFeedbackMapper feedbackMapper,
                                AiAskLogMapper askLogMapper) {
        this.feedbackMapper = feedbackMapper;
        this.askLogMapper = askLogMapper;
    }

    @Transactional
    public AskFeedbackResponse saveFeedback(Long userId, Long askLogId, AskFeedbackRequest request) {
        AiAskLog askLog = askLogMapper.selectOne(new LambdaQueryWrapper<AiAskLog>()
                .eq(AiAskLog::getId, askLogId)
                .eq(AiAskLog::getUserId, userId));
        if (askLog == null) {
            throw new BizException(ResultCode.NOT_FOUND, "ask log not found");
        }

        AiAskFeedback feedback = new AiAskFeedback();
        feedback.setUserId(userId);
        feedback.setAskLogId(askLogId);
        feedback.setHelpful(request.getHelpful());
        feedback.setReason(normalize(request.getReason()));
        feedback.setExpectedAnswer(normalize(request.getExpectedAnswer()));
        feedback.setStatus(STATUS_ACTIVE);
        feedback.setCreatedAt(LocalDateTime.now());
        feedbackMapper.insert(feedback);
        return toResponse(feedback);
    }

    public PageResult<AskFeedbackResponse> page(Long userId, Boolean helpful, long pageNo, long pageSize) {
        long safePageNo = Math.max(pageNo, 1);
        long safePageSize = Math.min(Math.max(pageSize, 1), MAX_PAGE_SIZE);

        LambdaQueryWrapper<AiAskFeedback> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AiAskFeedback::getUserId, userId)
                .eq(AiAskFeedback::getStatus, STATUS_ACTIVE);
        if (helpful != null) {
            queryWrapper.eq(AiAskFeedback::getHelpful, helpful);
        }
        queryWrapper.orderByDesc(AiAskFeedback::getCreatedAt)
                .orderByDesc(AiAskFeedback::getId);

        Page<AiAskFeedback> page = feedbackMapper.selectPage(new Page<>(safePageNo, safePageSize), queryWrapper);
        List<AskFeedbackResponse> records = page.getRecords().stream()
                .map(this::toResponse)
                .toList();

        return new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private AskFeedbackResponse toResponse(AiAskFeedback feedback) {
        return new AskFeedbackResponse(
                feedback.getId(),
                feedback.getAskLogId(),
                feedback.getHelpful(),
                feedback.getReason(),
                feedback.getExpectedAnswer(),
                feedback.getStatus(),
                feedback.getCreatedAt()
        );
    }
}
