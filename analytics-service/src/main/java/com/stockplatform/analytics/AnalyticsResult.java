package com.stockplatform.analytics;

public class AnalyticsResult {
    private double sma;
    private double vwap;
    private double minPrice;
    private double maxPrice;

    public AnalyticsResult() {}

    public AnalyticsResult(double sma, double vwap, double minPrice, double maxPrice) {
        this.sma = sma;
        this.vwap = vwap;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public double getSma() { return sma; }
    public void setSma(double sma) { this.sma = sma; }

    public double getVwap() { return vwap; }
    public void setVwap(double vwap) { this.vwap = vwap; }

    public double getMinPrice() { return minPrice; }
    public void setMinPrice(double minPrice) { this.minPrice = minPrice; }

    public double getMaxPrice() { return maxPrice; }
    public void setMaxPrice(double maxPrice) { this.maxPrice = maxPrice; }
}
