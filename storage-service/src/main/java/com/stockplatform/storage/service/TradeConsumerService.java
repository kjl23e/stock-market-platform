package com.stockplatform.storage.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockplatform.storage.model.TradeDocument;
import com.stockplatform.storage.repository.TradeRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TradeConsumerService {

    private final TradeRepository tradeRepository;
    private final ObjectMapper objectMapper;

    public TradeConsumerService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "stock-trades", groupId = "storage-group")
    public void consume(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            if (root.has("data") && root.get("data").isArray()) {
                for (JsonNode node : root.get("data")) {
                    String symbol = node.get("s").asText();
                    Double price = node.get("p").asDouble();
                    Double volume = node.get("v").asDouble();
                    Long timestamp = node.get("t").asLong();

                    TradeDocument trade = new TradeDocument(symbol, price, volume, timestamp);
                    tradeRepository.save(trade);

                    System.out.println(">>> Indexed to Elasticsearch: " + symbol + " @ $" + price);
                }
            }
        } catch (Exception e) {
            System.err.println(">>> Error processing message: " + e.getMessage());
        }
    }
}
