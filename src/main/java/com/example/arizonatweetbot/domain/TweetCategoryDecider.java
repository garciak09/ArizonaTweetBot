package com.example.arizonatweetbot.domain;

import com.example.arizonatweetbot.domain.GameContext;
import com.example.arizonatweetbot.domain.TweetCategory;

public class TweetCategoryDecider {

    public static TweetCategory decide(GameContext ctx) {

        if ("live".equals(ctx.getStatus())) {
            return TweetCategory.LIVE_GAME;
        }

        if ("pregame".equals(ctx.getStatus())) {
            return TweetCategory.PREGAME;
        }

        if ("postgame".equals(ctx.getStatus())) {
            return TweetCategory.POSTGAME;
        }

        if (ctx.getOpponent() != null &&
                ctx.getOpponent().toLowerCase().contains("asu")) {
            return TweetCategory.RIVALRY_MEME;
        }

        return TweetCategory.DAILY_VIBES;
    }
}
