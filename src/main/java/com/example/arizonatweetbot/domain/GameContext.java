package com.example.arizonatweetbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GameContext {
    private String title;          // "Live game", "Pregame", "Daily vibes"
    private String sport;          // "basketball", "football", "general"
    private LocalDateTime gameTime;
    private String opponent;       // "USC", "ASU", etc. or null
    private String status;         // "live", "pregame", "postgame", or null
    private String extraContext;   // score, notes, etc.
}
