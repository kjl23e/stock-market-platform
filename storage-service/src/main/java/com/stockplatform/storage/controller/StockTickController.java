package com.stockplatform.storage.controller;

import com.stockplatform.storage.model.StockTickEntity;
import com.stockplatform.storage.repository.StockTickRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticks")
public class StockTickController {

    private final StockTickRepository repository;

    public StockTickController(StockTickRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{symbol}")
    public List<StockTickEntity> getTicksBySymbol(@PathVariable String symbol) {
        return repository.findBySymbol(symbol);
    }
}
