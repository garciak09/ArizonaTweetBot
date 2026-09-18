package com.example.arizonatweetbot.service.score;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiveScoreInfo {
    private int arizonaScore;
    private int opponentScore;
    private String status; // "1st Half", "Final", "3rd Quarter", etc.
}
