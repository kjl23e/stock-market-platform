package com.stockplatform.websocket;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaWebSocketListener {

    private final SimpMessagingTemplate messagingTemplate;

    public KafkaWebSocketListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "stock-ticks", groupId = "websocket-group")
    public void consumeAndBroadcast(String tickJson) {
        messagingTemplate.convertAndSend("/topic/ticks", tickJson);
    }
}
