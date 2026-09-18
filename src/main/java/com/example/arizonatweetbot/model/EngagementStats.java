package com.example.arizonatweetbot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class EngagementStats {
    @Id
    private String id;
    private String tweetId;
    private int likes;
    private int retweets;
    private int replies;
}
