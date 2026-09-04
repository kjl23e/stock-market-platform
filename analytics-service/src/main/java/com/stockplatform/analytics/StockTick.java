package com.stockplatform.analytics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockTick {
    private String symbol;
    private double price;
    private double volume;
    private String timestamp;

    public StockTick() {}

    public StockTick(String symbol, double price, double volume, String timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
