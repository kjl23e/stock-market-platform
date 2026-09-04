package com.stockplatform.analytics;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaAnalyticsListener {

    private final AnalyticsService analyticsService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaAnalyticsListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @KafkaListener(topics = "stock-ticks", groupId = "analytics-group")
    public void consume(String tickJson) {
        try {
            StockTick tick = objectMapper.readValue(tickJson, StockTick.class);
            analyticsService.processTick(tick);
            System.out.println("Processed tick for " + tick.getSymbol() + " -> $" + tick.getPrice());
        } catch (Exception e) {
            System.err.println("Analytics deserialization error: " + e.getMessage());
        }
    }
}
