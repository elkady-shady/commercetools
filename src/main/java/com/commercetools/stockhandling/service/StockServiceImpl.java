package com.commercetools.stockhandling.service;

import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.entity.StockTransactionLog;
import com.commercetools.stockhandling.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;

    private StockTransactionLogService stockTransactionLogService;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository,StockTransactionLogService stockTransactionLogService) {
        this.stockRepository = stockRepository;
        this.stockTransactionLogService = stockTransactionLogService;
    }

    @Override
    @Transactional
    public void updateStock(StockDTO stockDTO) {
        Stock stock = stockRepository.findByIdAndProductId(stockDTO.getId(),stockDTO.getProductId()).get();
        long changeInQuantity = stockDTO.getQuantity() - stock.getQuantity();
        stock.setQuantity(stockDTO.getQuantity());
        stock.setTimeStamp(stockDTO.getTimeStamp());

        StockTransactionLog stockTransactionLog = new StockTransactionLog();
        stockTransactionLog.setProductId(stockDTO.getProductId());
        stockTransactionLog.setTransactionDate(LocalDateTime.now());
        stockTransactionLog.setTransactionValue(changeInQuantity);

        stockTransactionLogService.createStockTransactionLog(stockTransactionLog);
    }

    @Override
    public Stock getStockByProductId(String productId) {
        return stockRepository.findByProductId(productId).get();
    }



}
