package com.example.arizonatweetbot.service.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PromptLoaderService {
    private final Map<String, String> prompts = new HashMap<>();

    public void loadPrompts() throws Exception {
        // TODO: Load prompts from resources/prompts/
        prompts.put("daily_vibes", "Daily vibes prompt...");
    }

    public String getPrompt(String category) {
        return prompts.get(category);
    }
}