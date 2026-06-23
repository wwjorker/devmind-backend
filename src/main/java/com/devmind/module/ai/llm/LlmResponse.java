package com.devmind.module.ai.llm;

public class LlmResponse {

    private final String answer;
    private final String modelProvider;
    private final boolean mock;

    public LlmResponse(String answer, String modelProvider, boolean mock) {
        this.answer = answer;
        this.modelProvider = modelProvider;
        this.mock = mock;
    }

    public String getAnswer() {
        return answer;
    }

    public String getModelProvider() {
        return modelProvider;
    }

    public boolean isMock() {
        return mock;
    }
}
