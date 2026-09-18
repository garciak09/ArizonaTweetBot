package com.example.arizonatweetbot.service.posting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepostService {

    private final TwitterClient twitterClient;

    public void repostWinsAndHype() {
        var posts = twitterClient.searchRecent(
                "Arizona Wildcats OR BearDown OR UofA (win OR final OR beat OR upset)",
                20
        );

        posts.stream()
                .limit(5)
                .forEach(p -> twitterClient.repost((String) p.get("id")));
    }

    public void repostRecruiting() {
        var posts = twitterClient.searchRecent(
                "Arizona commit OR Wildcats commit OR Arizona recruiting",
                20
        );

        posts.stream()
                .limit(3)
                .forEach(p -> twitterClient.repost((String) p.get("id")));
    }
}

