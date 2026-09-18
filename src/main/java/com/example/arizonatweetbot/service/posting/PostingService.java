package com.example.arizonatweetbot.service.posting;

import com.example.arizonatweetbot.model.TweetResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostingService {

    private final TwitterClient twitterClient;

    public String sendTweet(String text) {
        if (text == null || text.isBlank()) return "";
        return twitterClient.postTweet(text);
    }

    public void repost(String tweetId) {
        twitterClient.repost(tweetId);
    }

    public void quoteTweet(String tweetId, String text) {
        twitterClient.quoteTweet(tweetId, text);
    }
}

