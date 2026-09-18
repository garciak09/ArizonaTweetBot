package com.example.arizonatweetbot.service.posting;

import com.example.arizonatweetbot.service.ai.AzureOpenAIChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemeService {

    private final AzureOpenAIChatService ai;

    public String generateRivalryMeme(String opponent) {
        String system = """
            You are a petty, funny Arizona Wildcats fan.
            Write a short smack talk line about %s.
            Max 10 words. No slurs. No NSFW. Just rivalry jokes.
            """.formatted(opponent);

        return ai.generateCompletion(system, "Give me one line.", 0.9, 32);
    }
}

