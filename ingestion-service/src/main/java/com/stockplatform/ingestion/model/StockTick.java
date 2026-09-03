package com.stockplatform.ingestion.model;

import java.time.Instant;

public record StockTick(
    String symbol,
    double price,
    long volume,
    Instant timestamp
) {}
