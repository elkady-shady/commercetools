package com.commercetools.stockhandling.service;

import com.commercetools.stockhandling.entity.StockTransactionLog;

import java.util.List;


public interface StockTransactionLogService {

    void createStockTransactionLog(StockTransactionLog stockTransactionLog);

    List<Object[]> getTopSellingProducts(String time);

    List<Object[]> getHighAvailableStocks(String time);
}
