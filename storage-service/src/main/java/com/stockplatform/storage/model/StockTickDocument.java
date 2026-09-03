package com.stockplatform.storage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "stock-ticks")
public class StockTickDocument {

    @Id
    private String id;
    private String symbol;
    private double price;
    private long volume;

    @Field(type = FieldType.Date)
    private Instant timestamp;

    public StockTickDocument() {}

    public StockTickDocument(String symbol, double price, long volume, Instant timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public long getVolume() { return volume; }
    public void setVolume(long volume) { this.volume = volume; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
