package com.example.arizonatweetbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GameInfo {
    private String sport;
    private String opponent;
    private LocalDateTime startTime;
}
