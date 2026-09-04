package com.stockplatform.analytics;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/{symbol}")
    public AnalyticsResult getAnalytics(@PathVariable String symbol) {
        return analyticsService.getAnalytics(symbol);
    }
}
