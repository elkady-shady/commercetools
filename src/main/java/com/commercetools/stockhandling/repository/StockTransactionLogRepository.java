package com.commercetools.stockhandling.repository;

import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.entity.StockTransactionLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockTransactionLogRepository extends JpaRepository<StockTransactionLog,Long> {

    @Query("SELECT tl.productId,SUM(tl.transactionValue) as itemsSold from StockTransactionLog tl WHERE tl.transactionValue < 0 AND " +
            " tl.transactionDate between :startOfDay AND :endOfDay GROUP BY tl.productId ORDER BY itemsSold ASC")
    List<Object[]> getTopSelling(@Param("startOfDay") LocalDateTime startOfDay,
                                 @Param("endOfDay") LocalDateTime endOfDay ,Pageable pageable);

    @Query("SELECT tl.productId,SUM(tl.transactionValue) as itemsSold from StockTransactionLog tl WHERE " +
            "tl.transactionDate between :startOfDay AND :endOfDay GROUP BY tl.productId ORDER BY itemsSold ASC ")
    List<Object[]> getHighestAvailable(@Param("startOfDay") LocalDateTime startOfDay,
                                    @Param("endOfDay") LocalDateTime endOfDay,Pageable pageable);
}
