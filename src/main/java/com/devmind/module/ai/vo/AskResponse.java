package com.devmind.module.ai.vo;

import com.devmind.module.search.vo.ChunkSearchResponse;

import java.util.List;

public class AskResponse {

    private Long logId;
    private String question;
    private String retrievalKeyword;
    private String promptPreview;
    private String answer;
    private String modelProvider;
    private boolean mock;
    private List<ChunkSearchResponse> retrievedChunks;
    private List<CitationResponse> citations;

    public AskResponse() {
    }

    public AskResponse(Long logId,
                       String question,
                       String retrievalKeyword,
                       String promptPreview,
                       String answer,
                       String modelProvider,
                       boolean mock,
                       List<ChunkSearchResponse> retrievedChunks,
                       List<CitationResponse> citations) {
        this.logId = logId;
        this.question = question;
        this.retrievalKeyword = retrievalKeyword;
        this.promptPreview = promptPreview;
        this.answer = answer;
        this.modelProvider = modelProvider;
        this.mock = mock;
        this.retrievedChunks = retrievedChunks;
        this.citations = citations;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRetrievalKeyword() {
        return retrievalKeyword;
    }

    public void setRetrievalKeyword(String retrievalKeyword) {
        this.retrievalKeyword = retrievalKeyword;
    }

    public String getPromptPreview() {
        return promptPreview;
    }

    public void setPromptPreview(String promptPreview) {
        this.promptPreview = promptPreview;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getModelProvider() {
        return modelProvider;
    }

    public void setModelProvider(String modelProvider) {
        this.modelProvider = modelProvider;
    }

    public boolean isMock() {
        return mock;
    }

    public void setMock(boolean mock) {
        this.mock = mock;
    }

    public List<ChunkSearchResponse> getRetrievedChunks() {
        return retrievedChunks;
    }

    public void setRetrievedChunks(List<ChunkSearchResponse> retrievedChunks) {
        this.retrievedChunks = retrievedChunks;
    }

    public List<CitationResponse> getCitations() {
        return citations;
    }

    public void setCitations(List<CitationResponse> citations) {
        this.citations = citations;
    }
}
