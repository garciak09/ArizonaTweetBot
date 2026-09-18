package com.example.arizonatweetbot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class TweetLog {
    @Id
    private String id;
    private String text;
    private LocalDateTime timestamp;
    private String category;
    private boolean posted;
}
