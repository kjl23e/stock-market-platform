package com.stockplatform.storage.consumer;

import com.stockplatform.storage.model.StockTickEntity;
import com.stockplatform.storage.repository.StockTickRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StockTickConsumer {

    private final StockTickRepository repository;

    public StockTickConsumer(StockTickRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "stock-ticks", groupId = "storage-group")
    public void consume(StockTickEntity tick) {
        StockTickEntity saved = repository.save(tick);
        System.out.println("Persisted Kafka Tick -> ID: " + saved.getId() + ", Symbol: " + saved.getSymbol() + ", Price: $" + saved.getPrice());
    }
}
