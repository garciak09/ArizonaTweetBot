package com.example.arizonatweetbot.controller;

import com.example.arizonatweetbot.domain.CategoryPicker;
import com.example.arizonatweetbot.domain.EmptyContextFactory;
import com.example.arizonatweetbot.domain.GameContext;
import com.example.arizonatweetbot.domain.TweetCategory;
import com.example.arizonatweetbot.service.ai.AIContentService;
import com.example.arizonatweetbot.service.posting.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {

    private final PostingService postingService;
    private final AIContentService aiContentService;

    @PostMapping("/tweet")
    public String tweet(@RequestParam String text) {
        return postingService.sendTweet(text);
    }

    @PostMapping("/ai-tweet")
    public String aiTweet() {
        TweetCategory category = CategoryPicker.pick();
        GameContext ctx = EmptyContextFactory.create();
        String tweet = aiContentService.generate(category, ctx);
        return postingService.sendTweet(tweet);
    }
}
