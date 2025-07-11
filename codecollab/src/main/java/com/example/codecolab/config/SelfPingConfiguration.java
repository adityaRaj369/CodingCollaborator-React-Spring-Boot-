package com.example.codecolab.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class SelfPingConfiguration {

    @Value("${self.ping.url:https://coding-collaborator-ai-compiler.onrender.com/health}")
    private String selfPingUrl;

    @Scheduled(fixedRate = 10 * 60 * 1000) // Every 10 minutes
    public void pingSelf() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(selfPingUrl, String.class);
            System.out.println("Self-ping successful: " + response);
        } catch (Exception e) {
            System.err.println("Self-ping failed: " + e.getMessage());
        }
    }
}