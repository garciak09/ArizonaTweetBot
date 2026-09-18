package com.example.arizonatweetbot.domain;

import java.time.LocalDateTime;

public class EmptyContextFactory {

    public static GameContext create() {
        return new GameContext(
                "Generic",
                "general",
                LocalDateTime.now(),
                null,
                null,
                "Wildcats"
        );
    }
}
