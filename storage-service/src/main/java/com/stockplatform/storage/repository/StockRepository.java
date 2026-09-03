package com.stockplatform.storage.repository;

import com.stockplatform.storage.model.StockTickDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface StockRepository extends ElasticsearchRepository<StockTickDocument, String> {
    List<StockTickDocument> findBySymbol(String symbol);
}
