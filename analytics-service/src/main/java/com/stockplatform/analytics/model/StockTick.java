package com.stockplatform.analytics.model;

public class StockTick {
    private String symbol;
    private Double price;
    private Double volume;
    private String timestamp;

    public StockTick() {}

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
