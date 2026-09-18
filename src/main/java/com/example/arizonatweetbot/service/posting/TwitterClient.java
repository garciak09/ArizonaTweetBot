package com.example.arizonatweetbot.service.posting;

import com.example.arizonatweetbot.config.TwitterApiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TwitterClient {

    private final TwitterApiConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    // -----------------------------
    // 1. POST TWEET
    // -----------------------------
    public String postTweet(String text) {
        String url = config.getBaseUrl() + "/tweets";

        Map<String, Object> body = Map.of("text", text);

        HttpHeaders headers = OAuth1HeaderBuilder.build(
                config.getApiKey(),
                config.getApiSecret(),
                config.getAccessToken(),
                config.getAccessTokenSecret(),
                "POST",
                url
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        return response.getBody();
    }

    // -----------------------------
    // 2. REPOST (RETWEET)
    // -----------------------------
    public void repost(String tweetId) {
        String url = config.getBaseUrl() + "/users/" + config.getUserId() + "/retweets";

        Map<String, Object> body = Map.of("tweet_id", tweetId);

        HttpHeaders headers = OAuth1HeaderBuilder.build(
                config.getApiKey(),
                config.getApiSecret(),
                config.getAccessToken(),
                config.getAccessTokenSecret(),
                "POST",
                url
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    // -----------------------------
    // 3. QUOTE TWEET
    // -----------------------------
    public void quoteTweet(String tweetId, String text) {
        String url = config.getBaseUrl() + "/tweets";

        String quoteUrl = "https://twitter.com/i/web/status/" + tweetId;

        Map<String, Object> body = Map.of(
                "text", text + " " + quoteUrl
        );

        HttpHeaders headers = OAuth1HeaderBuilder.build(
                config.getApiKey(),
                config.getApiSecret(),
                config.getAccessToken(),
                config.getAccessTokenSecret(),
                "POST",
                url
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.POST, request, String.class);
    }

    // -----------------------------
    // 4. SEARCH RECENT POSTS
    // -----------------------------
    public List<Map<String, Object>> searchRecent(String query, int limit) {
        String url = config.getBaseUrl() + "/tweets/search/recent?query=" + query + "&max_results=" + limit;

        HttpHeaders headers = OAuth1HeaderBuilder.build(
                config.getApiKey(),
                config.getApiSecret(),
                config.getAccessToken(),
                config.getAccessTokenSecret(),
                "GET",
                url
        );

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        Map<String, Object> body = response.getBody();
        if (body == null || !body.containsKey("data")) return List.of();

        return (List<Map<String, Object>>) body.get("data");
    }
}
