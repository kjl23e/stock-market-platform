package com.stockplatform.storage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stock_ticks")
public class StockTickEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private Double price;
    private Double volume;
    private String timestamp;

    public StockTickEntity() {}

    public StockTickEntity(String symbol, Double price, Double volume, String timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
