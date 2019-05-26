package com.commercetools.stockhandling.controller;

import com.commercetools.stockhandling.dto.StockStatisticsResponse;
import com.commercetools.stockhandling.service.StockTransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    private StockTransactionLogService stockTransactionLogService;

    @Autowired
    public StatisticsController(StockTransactionLogService stockTransactionLogService) {
        this.stockTransactionLogService = stockTransactionLogService;
    }

    @GetMapping
    public ResponseEntity<?> getStockStatistics(@RequestParam("time") String time) throws ParseException {
        if(!time.equals("today") && !time.equals("lastMonth")) {
            return new ResponseEntity<>(null,null,HttpStatus.BAD_REQUEST);
        }
        
        StockStatisticsResponse stockStatisticsResponse = stockTransactionLogService.getStatistics(time);

        return new ResponseEntity<>(stockStatisticsResponse,null,HttpStatus.OK);
    }
}
