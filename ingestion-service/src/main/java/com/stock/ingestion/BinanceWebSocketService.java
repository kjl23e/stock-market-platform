package com.stock.ingestion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;

@Service
public class BinanceWebSocketService implements WebSocket.Listener {

    private static final Logger log = LoggerFactory.getLogger(BinanceWebSocketService.class);
    private static final String WS_URL = "wss://stream.binance.com:9443/ws/btcusdt@trade";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BinanceWebSocketService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void init() {
        log.info("Initiating connection to Binance WebSocket...");
        HttpClient client = HttpClient.newHttpClient();
        client.newWebSocketBuilder()
                .buildAsync(URI.create(WS_URL), this)
                .exceptionally(ex -> {
                    log.error("Failed to establish WebSocket connection", ex);
                    return null;
                });
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        log.info("Connected to live Binance trade stream!");
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        try {
            JsonNode node = objectMapper.readTree(data.toString());
            if (node.has("p") && node.has("q")) {
                double price = node.get("p").asDouble();
                double volume = node.get("q").asDouble();
                long timeMs = node.get("T").asLong();

                Map<String, Object> tick = new HashMap<>();
                tick.put("symbol", "BINANCE:BTCUSDT");
                tick.put("price", price);
                tick.put("volume", volume);
                tick.put("timestamp", Instant.ofEpochMilli(timeMs).toString());

                kafkaTemplate.send("stock-ticks", "BINANCE:BTCUSDT", tick);
            }
        } catch (Exception e) {
            log.error("Error processing Binance tick payload", e);
        }

        webSocket.request(1);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        log.error("Binance WebSocket error", error);
    }
}
