package com.example.arizonatweetbot.service.ai;

import com.example.arizonatweetbot.config.AzureOpenAIProperties;
import com.example.arizonatweetbot.domain.GameContext;
import com.example.arizonatweetbot.domain.PromptRegistry;
import com.example.arizonatweetbot.domain.TweetCategory;
import com.example.arizonatweetbot.model.ChatMessage;
import com.example.arizonatweetbot.model.ChatCompletionRequest;
import com.example.arizonatweetbot.model.ChatCompletionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIContentService {

    private final WebClient azureOpenAIWebClient;
    private final AzureOpenAIProperties properties;

    public String generate(TweetCategory category, GameContext ctx) {
        String prompt = buildPrompt(category, ctx);
        ChatCompletionRequest request = new ChatCompletionRequest(
                List.of(
                        new ChatMessage("system", "You are an AI that writes concise, engaging Arizona Wildcats tweets for Football and Men's Basketball."),
                        new ChatMessage("user", prompt)
                ),
                0.8,
                256
        );

        String path = String.format(
                "/openai/deployments/%s/chat/completions?api-version=%s",
                properties.getDeploymentName(),
                properties.getApiVersion()
        );

        ChatCompletionResponse response = azureOpenAIWebClient.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatCompletionResponse.class)
                .block();

        return response.getChoices().get(0).getMessage().getContent();
    }

    private String buildPrompt(TweetCategory category, GameContext ctx) {
        return switch (category) {
            case DAILY_VIBES -> PromptRegistry.DAILY_VIBES.format();

            case LIVE_GAME -> {
                // extraContext can hold score/status summary like "Arizona 42, USC 38 - 2nd half"
                String context = ctx.getExtraContext() != null ? ctx.getExtraContext() : "";
                yield PromptRegistry.LIVE_GAME.format(context);
            }

            case PREGAME -> {
                String opponent = ctx.getOpponent() != null ? ctx.getOpponent() : "their opponent";
                String sport = ctx.getSport() != null ? ctx.getSport() : "basketball";
                yield PromptRegistry.PREGAME.format(opponent, sport);
            }

            case POSTGAME -> {
                String opponent = ctx.getOpponent() != null ? ctx.getOpponent() : "their opponent";
                String sport = ctx.getSport() != null ? ctx.getSport() : "basketball";
                String context = ctx.getExtraContext() != null ? ctx.getExtraContext() : "";
                yield PromptRegistry.POSTGAME.format(opponent, sport, context);
            }

            case RIVALRY_MEME -> PromptRegistry.RIVALRY_MEME.format();
        };
    }

}
