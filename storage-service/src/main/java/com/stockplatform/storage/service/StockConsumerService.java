package com.stockplatform.storage.service;

import com.stockplatform.storage.model.StockTickDocument;
import com.stockplatform.storage.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StockConsumerService {

    private static final Logger log = LoggerFactory.getLogger(StockConsumerService.class);
    private final StockRepository stockRepository;

    public StockConsumerService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @KafkaListener(topics = "stock-ticks", groupId = "storage-group")
    public void consume(StockTickDocument document) {
        log.info("Consuming from Kafka -> Indexing to Elasticsearch: {} @ ${}", document.getSymbol(), document.getPrice());
        stockRepository.save(document);
    }
}
