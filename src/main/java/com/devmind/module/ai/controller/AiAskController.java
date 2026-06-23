package com.devmind.module.ai.controller;

import com.devmind.common.api.Result;
import com.devmind.common.api.PageResult;
import com.devmind.common.security.AuthenticatedUser;
import com.devmind.module.ai.dto.AskRequest;
import com.devmind.module.ai.service.AiAskLogService;
import com.devmind.module.ai.service.AiAskService;
import com.devmind.module.ai.vo.AskLogResponse;
import com.devmind.module.ai.vo.AskResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
public class AiAskController {

    private final AiAskService aiAskService;
    private final AiAskLogService askLogService;

    public AiAskController(AiAskService aiAskService,
                           AiAskLogService askLogService) {
        this.aiAskService = aiAskService;
        this.askLogService = askLogService;
    }

    @PostMapping("/ask")
    public Result<AskResponse> ask(@AuthenticationPrincipal AuthenticatedUser user,
                                   @Valid @RequestBody AskRequest request) {
        return Result.success(aiAskService.ask(user.userId(), request));
    }

    @GetMapping("/ask-logs")
    public Result<PageResult<AskLogResponse>> pageLogs(@AuthenticationPrincipal AuthenticatedUser user,
                                                       @RequestParam(defaultValue = "1") long pageNo,
                                                       @RequestParam(defaultValue = "10") long pageSize) {
        return Result.success(askLogService.page(user.userId(), pageNo, pageSize));
    }
}
