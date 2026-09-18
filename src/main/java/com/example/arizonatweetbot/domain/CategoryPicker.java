package com.example.arizonatweetbot.domain;

import java.util.Random;

public class CategoryPicker {

    private static final TweetCategory[] VALUES = TweetCategory.values();
    private static final Random RANDOM = new Random();

    public static TweetCategory pick() {
        return VALUES[RANDOM.nextInt(VALUES.length)];
    }
}
