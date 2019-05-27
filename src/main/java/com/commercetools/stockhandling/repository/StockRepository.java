package com.commercetools.stockhandling.repository;

import com.commercetools.stockhandling.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock,String> {

    Optional<Stock> findByIdAndProductId(String id, String productId);

    Optional<Stock> findByProductId(String productId);
}
