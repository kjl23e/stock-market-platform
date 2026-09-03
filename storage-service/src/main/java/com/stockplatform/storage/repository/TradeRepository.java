package com.stockplatform.storage.repository;

import com.stockplatform.storage.model.TradeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends ElasticsearchRepository<TradeDocument, String> {
}
