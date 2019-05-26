package com.commercetools.stockhandling.service;

import com.commercetools.stockhandling.dto.StockStatisticsResponse;
import com.commercetools.stockhandling.entity.StockTransactionLog;

import java.text.ParseException;


public interface StockTransactionLogService {

    void createStockTransactionLog(StockTransactionLog stockTransactionLog);

    StockStatisticsResponse getStatistics(String time) throws ParseException;
}
