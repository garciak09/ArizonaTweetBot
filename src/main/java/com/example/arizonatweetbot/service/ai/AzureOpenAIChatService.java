package com.example.arizonatweetbot.service.ai;

import com.example.arizonatweetbot.config.AzureOpenAIProperties;
import com.example.arizonatweetbot.model.ChatCompletionRequest;
import com.example.arizonatweetbot.model.ChatCompletionResponse;
import com.example.arizonatweetbot.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AzureOpenAIChatService {
    private static final Logger log = LoggerFactory.getLogger(AzureOpenAIChatService.class);
    private final WebClient webClient;
    private final AzureOpenAIProperties properties;

    public AzureOpenAIChatService(WebClient azureOpenAIWebClient, AzureOpenAIProperties properties) {
        this.webClient = azureOpenAIWebClient;
        this.properties = properties;
    }

    public String generateCompletion(String systemPrompt, String userPrompt, Double temperature, Integer maxTokens) {
        ChatCompletionRequest request = new ChatCompletionRequest(
                List.of(
                        ChatMessage.system(systemPrompt),
                        ChatMessage.user(userPrompt)
                ),
                temperature != null ? temperature : 0.7,
                maxTokens != null ? maxTokens : 256
        );

        String path = String.format(
                "/openai/deployments/%s/chat/completions?api-version=%s",
                properties.getDeploymentName(),
                properties.getApiVersion()
        );

        try {
            ChatCompletionResponse response = webClient.post()
                    .uri(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatCompletionResponse.class)
                    .onErrorResume(WebClientResponseException.class, ex -> {
                        log.error("Azure OpenAI error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
                        return Mono.error(ex);
                    })
                    .block();

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                log.warn("Empty response from Azure OpenAI");
                return "";
            }

            ChatMessage msg = response.getChoices().get(0).getMessage();
            return msg != null ? msg.getContent() : "";

        } catch (WebClientResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error calling Azure OpenAI", ex);
            throw ex;
        }
    }

    public String generateWildcatsTweet(String context) {
        String systemPrompt = """
                You are an AI that writes concise, engaging Arizona Wildcats sports tweets.
                Constraints:
                - Max 250 characters
                - No hashtags unless explicitly requested
                - No emojis unless explicitly requested
                - Sound like a sharp, informed, gen z fan, not a marketer.
                """;

        String userPrompt = "Generate a tweet based on this context: " + context;

        return generateCompletion(systemPrompt, userPrompt, 0.8, 128);
    }
}
