package com.commercetools.stockhandling.controller;

import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.entity.StockTransactionLog;
import com.commercetools.stockhandling.repository.StockRepository;
import com.commercetools.stockhandling.repository.StockTransactionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("initialization")
public class InitializationController {

    private StockRepository stockRepository;

    private StockTransactionLogRepository stockTransactionLogRepository;

    @Autowired
    public InitializationController(StockRepository stockRepository,StockTransactionLogRepository stockTransactionLogRepository) {
        this.stockRepository = stockRepository;
        this.stockTransactionLogRepository = stockTransactionLogRepository;
    }

    @PostMapping
    public ResponseEntity<?> initializeDB() {
        Stock stock = new Stock();
        stock.setQuantity(50);
        stock.setProductId("veg-123");
        stock.setId("00001");
        stock.setTimeStamp(LocalDateTime.now());

        stockRepository.save(stock);

        StockTransactionLog stockTransactionLog = new StockTransactionLog();
        stockTransactionLog.setProductId("veg-123");
        stockTransactionLog.setTransactionValue(50);
        stockTransactionLog.setTransactionDate(LocalDateTime.now());

        stockTransactionLogRepository.save(stockTransactionLog);

        return new ResponseEntity<>(null,null,HttpStatus.CREATED);
    }
}
