package com.stockplatform.analytics.model;

public class SymbolAnalytics {
    private String symbol;
    private double currentPrice;
    private double sma;
    private double minPrice;
    private double maxPrice;
    private double vwap;
    private int windowSize;

    public SymbolAnalytics(String symbol, double currentPrice, double sma, double minPrice, double maxPrice, double vwap, int windowSize) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.sma = sma;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.vwap = vwap;
        this.windowSize = windowSize;
    }

    public String getSymbol() { return symbol; }
    public double getCurrentPrice() { return currentPrice; }
    public double getSma() { return sma; }
    public double getMinPrice() { return minPrice; }
    public double getMaxPrice() { return maxPrice; }
    public double getVwap() { return vwap; }
    public int getWindowSize() { return windowSize; }
}
