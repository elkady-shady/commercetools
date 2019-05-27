package com.commercetools.stockhandling.controller;

import com.commercetools.stockhandling.dto.ProductSellingDTO;
import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.dto.StockStatisticsResponse;
import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.mapper.StockMapper;
import com.commercetools.stockhandling.service.StockService;
import com.commercetools.stockhandling.service.StockTransactionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

    private StockTransactionLogService stockTransactionLogService;

    private StockService stockService;

    private StockMapper stockMapper;

    @Autowired
    public StatisticsController(StockTransactionLogService stockTransactionLogService,StockService stockService,
                                StockMapper stockMapper) {
        this.stockTransactionLogService = stockTransactionLogService;
        this.stockService = stockService;
        this.stockMapper =stockMapper;
    }

    @GetMapping
    public ResponseEntity<?> getStockStatistics(@RequestParam("time") String time) throws ParseException {
        if(!time.equals("today") && !time.equals("lastMonth")) {
            return new ResponseEntity<>(null,null,HttpStatus.BAD_REQUEST);
        }

        List<Object[]> topSellingProducts = stockTransactionLogService.getTopSellingProducts(time);
        List<Object[]> highAvailableProducts = stockTransactionLogService.getHighAvailableStocks(time);

        StockStatisticsResponse stockStatisticsResponse = new StockStatisticsResponse();
        LocalDateTime requestTime = LocalDateTime.now();

        stockStatisticsResponse.setRequestTimestamp(requestTime);
        stockStatisticsResponse.setTime(time);

        topSellingProducts.stream().forEach(obj ->{
            ProductSellingDTO productSellingDTO = new ProductSellingDTO();
            productSellingDTO.setProductId((String) obj[0]);
            productSellingDTO.setItemsSold(Math.abs((Long)obj[1]));

            stockStatisticsResponse.getTopSellingProducts().add(productSellingDTO);
        });

        highAvailableProducts.stream().forEach(obj -> {
            Stock stock = stockService.getStockByProductId((String) obj[0]);
            StockDTO stockDTO = stockMapper.convertToDTO(stock);

            stockStatisticsResponse.getTopAvailableProducts().add(stockDTO);
        });

        return new ResponseEntity<>(stockStatisticsResponse,null,HttpStatus.OK);
    }
}
