package com.example.arizonatweetbot.model;

import lombok.Data;

@Data
public class TweetResponse {
    private DataNode data;

    @Data
    public static class DataNode {
        private String id;
        private String text;
    }
}