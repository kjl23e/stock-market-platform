package com.stockplatform.ingestion.service;

import com.stockplatform.ingestion.model.StockTick;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockProducerService {

    private static final Logger log = LoggerFactory.getLogger(StockProducerService.class);
    private static final String TOPIC = "stock-ticks";

    private final KafkaTemplate<String, StockTick> kafkaTemplate;

    public StockProducerService(KafkaTemplate<String, StockTick> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStockTick(StockTick stockTick) {
        log.info("Publishing tick to Kafka: {} -> ${}", stockTick.symbol(), stockTick.price());
        kafkaTemplate.send(TOPIC, stockTick.symbol(), stockTick);
    }
}
