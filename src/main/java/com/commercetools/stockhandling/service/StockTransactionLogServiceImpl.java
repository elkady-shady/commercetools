package com.commercetools.stockhandling.service;

import com.commercetools.stockhandling.dto.ProductSellingDTO;
import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.dto.StockStatisticsResponse;
import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.entity.StockTransactionLog;
import com.commercetools.stockhandling.mapper.StockMapper;
import com.commercetools.stockhandling.repository.StockRepository;
import com.commercetools.stockhandling.repository.StockTransactionLogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class StockTransactionLogServiceImpl implements StockTransactionLogService {

    private StockTransactionLogRepository stockTransactionLogRepository;

    private StockRepository stockRepository;

    private StockMapper stockMapper;

    @Autowired
    public StockTransactionLogServiceImpl(StockTransactionLogRepository stockTransactionLogRepository,
                                          StockRepository stockRepository,StockMapper stockMapper) {
        this.stockTransactionLogRepository = stockTransactionLogRepository;
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    @Override
    public void createStockTransactionLog(StockTransactionLog stockTransactionLog) {
        stockTransactionLogRepository.save(stockTransactionLog);
    }

    @Override
    public List<Object[]> getTopSellingProducts(String time) {
        LocalDateTime startDate;
        LocalDateTime endDate;
        if(time.equals("today")) {
            startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            endDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        } else {
            startDate = LocalDateTime.of(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalTime.MIN);
            endDate = LocalDateTime.of(LocalDate.now().minusMonths(1)
                    .withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()), LocalTime.MAX);
        }

        List<Object[]> topSellingProducts = stockTransactionLogRepository.getTopSelling(startDate,endDate ,PageRequest.of(0,3));

        return topSellingProducts;
    }

    @Override
    public List<Object[]> getHighAvailableStocks(String time) {
        LocalDateTime startDate;
        LocalDateTime endDate;
        if(time.equals("today")) {
            startDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            endDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        } else {
            startDate = LocalDateTime.of(LocalDate.now().minusMonths(1).withDayOfMonth(1), LocalTime.MIN);
            endDate = LocalDateTime.of(LocalDate.now().minusMonths(1)
                    .withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()), LocalTime.MAX);
        }

        List<Object[]> highAvailableProducts = stockTransactionLogRepository.getHighestAvailable(startDate,endDate,PageRequest.of(0,3));

        return highAvailableProducts;
    }


}
