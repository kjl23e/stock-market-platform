package com.stockplatform.storage.repository;

import com.stockplatform.storage.model.StockTickEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockTickRepository extends JpaRepository<StockTickEntity, Long> {
    List<StockTickEntity> findBySymbol(String symbol);
}
