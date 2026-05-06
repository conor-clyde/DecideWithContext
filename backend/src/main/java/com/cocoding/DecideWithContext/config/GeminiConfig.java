package com.cocoding.DecideWithContext.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    @ConditionalOnProperty(prefix = "gemini.api", name = "key")
    public Client geminiClient(@Value("${gemini.api.key}") String apiKey) {
        return Client.builder().apiKey(apiKey).build();
    }
}