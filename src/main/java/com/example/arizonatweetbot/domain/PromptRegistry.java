package com.example.arizonatweetbot.domain;

public enum PromptRegistry {

    DAILY_VIBES("""
        Write a short Arizona Wildcats daily vibes tweet.
        Max 12 words. Gen Z tone.
        """),

    LIVE_GAME("""
        Write a short Arizona Wildcats live-game tweet.
        Max 12 words. Gen Z tone. No speeches.
        Arizona score and context: %s
        """),

    PREGAME("""
        Write a short Arizona Wildcats pregame hype tweet.
        Max 12 words. Gen Z trash talk.
        Opponent: %s. Sport: %s.
        """),

    POSTGAME("""
        Write a short Arizona Wildcats postgame reaction tweet.
        Max 12 words. Gen Z tone.
        Opponent: %s. Sport: %s. Context: %s
        """),

    RIVALRY_MEME("""
        Write a short petty ASU rivalry tweet.
        Max 10 words. Gen Z jokes only.
        """);

    private final String template;

    PromptRegistry(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        return template.formatted(args);
    }
}
