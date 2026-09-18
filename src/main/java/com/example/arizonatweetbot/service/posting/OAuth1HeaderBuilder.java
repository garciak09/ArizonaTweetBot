package com.example.arizonatweetbot.service.posting;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.http.HttpHeaders;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class OAuth1HeaderBuilder {
    public static HttpHeaders build(String apiKey, String apiSecret, String accessToken, String accessTokenSecret, String method, String url) {
        try {
            String nonce = UUID.randomUUID().toString().replaceAll("-", "");
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            Map<String, String> params = new TreeMap<>();
            params.put("oauth_consumer_key", apiKey);
            params.put("oauth_nonce", nonce);
            params.put("oauth_signature_method", "HMAC-SHA1");
            params.put("oauth_timestamp", timestamp);
            params.put("oauth_token", accessToken);
            params.put("oauth_version", "1.0");
            String paramString = normalizeParams(params);
            String baseString = method.toUpperCase() + "&" + encode(url) + "&" + encode(paramString);
            String signingKey = encode(apiSecret) + "&" + encode(accessTokenSecret);
            String signature = Base64.encodeBase64String(HmacUtils.hmacSha1(signingKey, baseString));
            params.put("oauth_signature", signature);
            String header = "OAuth " + buildAuthHeader(params);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", header);
            headers.add("Content-Type", "application/json");
            return headers;
        } catch (Exception e) {
            throw new RuntimeException("OAuth signing failed", e);
        }
    }

    private static String normalizeParams(Map<String, String> params) {
        List<String> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairs.add(encode(entry.getKey()) + "=" + encode(entry.getValue()));
        }
        return String.join("&", pairs);
    }

    private static String buildAuthHeader(Map<String, String> params) {
        List<String> pairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairs.add(encode(entry.getKey()) + "=\"" + encode(entry.getValue()) + "\"");
        }
        return String.join(", ", pairs);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8)
                .replace("+", "%20")
                .replace("*", "%2A")
                .replace("%7E", "~");
    }
}