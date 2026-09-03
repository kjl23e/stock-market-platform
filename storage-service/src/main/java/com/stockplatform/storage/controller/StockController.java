package com.stockplatform.storage.controller;

import com.stockplatform.storage.model.StockTickDocument;
import com.stockplatform.storage.repository.StockRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping("/{symbol}")
    public List<StockTickDocument> getTicksBySymbol(@PathVariable String symbol) {
        return stockRepository.findBySymbol(symbol.toUpperCase());
    }
}
