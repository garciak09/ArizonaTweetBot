package com.example.arizonatweetbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ChatCompletionRequest {

    private List<ChatMessage> messages;
    private double temperature;

    @JsonProperty("max_tokens")
    private int maxTokens;

    public ChatCompletionRequest(List<ChatMessage> messages, double temperature, int maxTokens) {
        this.messages = messages;
        this.temperature = temperature;
        this.maxTokens = maxTokens;
    }

    public List<ChatMessage> getMessages() { return messages; }
    public double getTemperature() { return temperature; }
    public int getMaxTokens() { return maxTokens; }
}
