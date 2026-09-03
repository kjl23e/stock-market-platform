package com.stockplatform.analytics.controller;

import com.stockplatform.analytics.model.SymbolAnalytics;
import com.stockplatform.analytics.service.AnalyticsEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsEngine analyticsEngine;

    public AnalyticsController(AnalyticsEngine analyticsEngine) {
        this.analyticsEngine = analyticsEngine;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<SymbolAnalytics> getAnalytics(@PathVariable String symbol) {
        SymbolAnalytics stats = analyticsEngine.getAnalytics(symbol);
        if (stats == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(stats);
    }
}
