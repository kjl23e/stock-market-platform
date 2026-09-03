package com.stockplatform.ingestion.scheduler;

import com.stockplatform.ingestion.model.StockTick;
import com.stockplatform.ingestion.service.StockProducerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Component
public class MockMarketDataGenerator {

    private final StockProducerService producerService;
    private final Random random = new Random();
    private final List<String> symbols = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA");

    public MockMarketDataGenerator(StockProducerService producerService) {
        this.producerService = producerService;
    }

    @Scheduled(fixedRate = 1000)
    public void generateTick() {
        String symbol = symbols.get(random.nextInt(symbols.size()));
        double price = 100 + (500 - 100) * random.nextDouble();
        long volume = random.nextLong(100, 5000);

        StockTick tick = new StockTick(symbol, Math.round(price * 100.0) / 100.0, volume, Instant.now());
        producerService.sendStockTick(tick);
    }
}
