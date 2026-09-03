package com.stockplatform.ingestion.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

@Service
public class FinnhubWebSocketService implements WebSocket.Listener {

    @Value("${finnhub.api.key}")
    private String apiKey;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public FinnhubWebSocketService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void connect() {
        String url = "wss://ws.finnhub.io?token=" + apiKey;
        HttpClient client = HttpClient.newHttpClient();
        client.newWebSocketBuilder()
                .buildAsync(URI.create(url), this)
                .thenAccept(webSocket -> {
                    System.out.println(">>> Connected to Finnhub WebSocket!");
                    // Chain send operations to prevent concurrent send collisions
                    webSocket.sendText("{\"type\":\"subscribe\",\"symbol\":\"BINANCE:BTCUSDT\"}", true)
                            .thenRun(() -> webSocket.sendText("{\"type\":\"subscribe\",\"symbol\":\"AAPL\"}", true));
                });
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        String message = data.toString();
        kafkaTemplate.send("stock-trades", message);
        System.out.println(">>> Streamed to Kafka: " + message);
        webSocket.request(1);
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println(">>> WebSocket Error: " + error.getMessage());
    }
}
