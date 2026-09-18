package com.example.arizonatweetbot.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class AzureOpenAIClientConfig {
    private final AzureOpenAIProperties properties;

    @Autowired
    public AzureOpenAIClientConfig(AzureOpenAIProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WebClient azureOpenAIWebClient() {
        HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5_000).responseTimeout(Duration.ofSeconds(30)).doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS)).addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)));
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).baseUrl(properties.getEndpoint()).defaultHeader("api-key", properties.getApiKey()).exchangeStrategies(ExchangeStrategies.builder().codecs(cfg -> cfg.defaultCodecs().maxInMemorySize(8 * 1024 * 1024)).build()).build();
    }
}
