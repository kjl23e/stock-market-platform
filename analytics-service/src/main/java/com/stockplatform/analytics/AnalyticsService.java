package com.stockplatform.analytics;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnalyticsService {

    private final Map<String, List<StockTick>> historyMap = new ConcurrentHashMap<>();

    public synchronized void processTick(StockTick tick) {
        if (tick == null || tick.getSymbol() == null) return;
        historyMap.computeIfAbsent(tick.getSymbol(), k -> new ArrayList<>()).add(tick);
        List<StockTick> ticks = historyMap.get(tick.getSymbol());
        if (ticks.size() > 100) {
            ticks.remove(0);
        }
    }

    public synchronized AnalyticsResult getAnalytics(String symbol) {
        List<StockTick> ticks = historyMap.getOrDefault(symbol, Collections.emptyList());
        if (ticks.isEmpty()) {
            return new AnalyticsResult(0, 0, 0, 0);
        }

        double sumPrice = 0;
        double sumPV = 0;
        double sumV = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        int count = Math.min(ticks.size(), 20);
        List<StockTick> recent = ticks.subList(ticks.size() - count, ticks.size());

        for (StockTick t : recent) {
            sumPrice += t.getPrice();
        }
        double sma = sumPrice / count;

        for (StockTick t : ticks) {
            double vol = t.getVolume() > 0 ? t.getVolume() : 1.0;
            sumPV += (t.getPrice() * vol);
            sumV += vol;
            if (t.getPrice() < min) min = t.getPrice();
            if (t.getPrice() > max) max = t.getPrice();
        }

        double vwap = sumV > 0 ? sumPV / sumV : sma;

        return new AnalyticsResult(sma, vwap, min == Double.MAX_VALUE ? 0 : min, max == Double.MIN_VALUE ? 0 : max);
    }
}
