package com.example.arizonatweetbot.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TwitterApiConfig {

    @Value("${TWITTER_API_KEY}")
    private String apiKey;

    @Value("${TWITTER_API_SECRET}")
    private String apiSecret;

    @Value("${TWITTER_ACCESS_TOKEN}")
    private String accessToken;

    @Value("${TWITTER_ACCESS_SECRET}")
    private String accessTokenSecret;

    @Value("${TWITTER_BASE_URL:https://api.twitter.com}")
    private String baseUrl;

    @Value("${TWITTER_USER_ID}")
    private String userId;   // <-- ADD THIS
}
