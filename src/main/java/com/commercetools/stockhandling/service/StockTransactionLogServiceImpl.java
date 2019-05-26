package com.commercetools.stockhandling.service;

import com.commercetools.stockhandling.dto.ProductSellingDTO;
import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.dto.StockStatisticsResponse;
import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.entity.StockTransactionLog;
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

    private ModelMapper modelMapper;

    @Autowired
    public StockTransactionLogServiceImpl(StockTransactionLogRepository stockTransactionLogRepository,
                                          StockRepository stockRepository,ModelMapper modelMapper) {
        this.stockTransactionLogRepository = stockTransactionLogRepository;
        this.stockRepository = stockRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createStockTransactionLog(StockTransactionLog stockTransactionLog) {
        stockTransactionLogRepository.save(stockTransactionLog);
    }

    /**
     *
     * @param time
     * @return
     * @throws ParseException
     */
    @Override
    public StockStatisticsResponse getStatistics(String time) {
        LocalDateTime requestTime = LocalDateTime.now();
        StockStatisticsResponse stockStatisticsResponse = new StockStatisticsResponse();

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
        List<Object[]> highAvailableProducts = stockTransactionLogRepository.getHighestAvailable(startDate,endDate,PageRequest.of(0,3));

        stockStatisticsResponse.setRequestTimestamp(requestTime);
        stockStatisticsResponse.setTime(time);

        topSellingProducts.stream().forEach(obj ->{
            ProductSellingDTO productSellingDTO = new ProductSellingDTO();
            productSellingDTO.setProductId((String) obj[0]);
            productSellingDTO.setItemsSold(Math.abs((Long)obj[1]));

            stockStatisticsResponse.getTopSellingProducts().add(productSellingDTO);
        });

        highAvailableProducts.stream().forEach(obj -> {
            Stock stock = stockRepository.findByProductId((String) obj[0]).get();
            StockDTO stockDTO = convertToDTO(stock);

            stockStatisticsResponse.getTopAvailableProducts().add(stockDTO);
        });

        return stockStatisticsResponse;
    }

    /**
     *
     * @param stock
     * @return
     */
    private StockDTO convertToDTO(Stock stock) {
        return modelMapper.map(stock,StockDTO.class);
    }
}
