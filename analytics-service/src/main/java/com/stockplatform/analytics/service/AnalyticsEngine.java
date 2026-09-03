package com.stockplatform.analytics.service;

import com.stockplatform.analytics.model.StockTick;
import com.stockplatform.analytics.model.SymbolAnalytics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnalyticsEngine {

    private final int WINDOW_SIZE = 20;
    private final Map<String, Deque<StockTick>> tickWindows = new ConcurrentHashMap<>();

    @KafkaListener(topics = "stock-ticks", groupId = "analytics-group")
    public void consume(StockTick tick) {
        if (tick == null || tick.getSymbol() == null || tick.getPrice() == null) return;

        tickWindows.compute(tick.getSymbol(), (symbol, window) -> {
            if (window == null) window = new ArrayDeque<>();
            if (window.size() >= WINDOW_SIZE) window.pollFirst();
            window.addLast(tick);
            return window;
        });
    }

    public SymbolAnalytics getAnalytics(String symbol) {
        Deque<StockTick> window = tickWindows.get(symbol);
        if (window == null || window.isEmpty()) return null;

        List<StockTick> snapshot;
        synchronized (window) {
            snapshot = new ArrayList<>(window);
        }

        double latestPrice = snapshot.get(snapshot.size() - 1).getPrice();
        double sumPrice = 0.0;
        double sumPriceVolume = 0.0;
        double sumVolume = 0.0;
        double minPrice = Double.MAX_VALUE;
        double maxPrice = Double.MIN_VALUE;

        for (StockTick t : snapshot) {
            double p = t.getPrice();
            double v = t.getVolume() != null ? t.getVolume() : 1.0;

            sumPrice += p;
            sumPriceVolume += p * v;
            sumVolume += v;

            if (p < minPrice) minPrice = p;
            if (p > maxPrice) maxPrice = p;
        }

        double sma = sumPrice / snapshot.size();
        double vwap = sumVolume > 0 ? (sumPriceVolume / sumVolume) : sma;

        return new SymbolAnalytics(symbol, latestPrice, sma, minPrice, maxPrice, vwap, snapshot.size());
    }
}
