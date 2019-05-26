package com.commercetools.stockhandling.controller;

import com.commercetools.stockhandling.dto.StockAvailabilityResponse;
import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.service.StockService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("stock")
public class StockController {

    private StockService stockService;

    private ModelMapper modelMapper;

    @Autowired
    public  StockController(StockService stockService,ModelMapper modelMapper) {
        this.stockService = stockService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateStock(@RequestBody StockDTO stockDto) {
        stockService.updateStock(stockDto);
        return new ResponseEntity<>(null,null, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getStock(@RequestParam("productId") String productId){
        Stock stock = stockService.getStockByProductId(productId);
        LocalDateTime requestTime = LocalDateTime.now();
        StockDTO stockDTO = convertToDTO(stock);
        StockAvailabilityResponse stockAvailabilityResponse = new StockAvailabilityResponse(requestTime,stockDTO);

        return new ResponseEntity<>(stockAvailabilityResponse,null,HttpStatus.OK);
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
