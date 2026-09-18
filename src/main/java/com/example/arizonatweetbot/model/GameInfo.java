package com.example.arizonatweetbot.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class GameInfo {
    @Id
    private String id;
    private String opponent;
    private LocalDate date;
    private String sport;
}
