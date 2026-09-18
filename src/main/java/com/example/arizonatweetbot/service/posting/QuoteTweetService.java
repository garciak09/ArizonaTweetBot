package com.example.arizonatweetbot.service.posting;

import com.example.arizonatweetbot.service.ai.AzureOpenAIChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuoteTweetService {

    private final TwitterClient twitterClient;
    private final AzureOpenAIChatService ai;

    public void quoteHypePosts() {
        var posts = twitterClient.searchRecent(
                "Arizona Wildcats OR UofA OR BearDown OR ASU",
                20
        );

        posts.stream()
                .limit(3)
                .forEach(this::react);
    }

    private void react(Map<String, Object> post) {
        String text = (String) post.get("text");
        String id = (String) post.get("id");

        String system = """
            You are a Gen Z Arizona Wildcats fan.
            Write a short quote tweet reaction.
            Max 10 words. Casual, funny, confident. No speeches.
            """;

        String user = "React to this post: " + text;

        String reaction = ai.generateCompletion(system, user, 0.8, 32);

        if (reaction != null && !reaction.isBlank()) {
            twitterClient.quoteTweet(id, reaction.trim());
        }
    }
}

