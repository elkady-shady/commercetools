package com.commercetools.stockhandling.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class StockAvailabilityResponse {

    public StockAvailabilityResponse(LocalDateTime requestTimestamp, StockDTO stockDTO) {
        this.requestTimestamp = requestTimestamp;
        fillStockData(stockDTO);
        this.productId = stockDTO.getProductId();
    }

    private String productId;

    private LocalDateTime requestTimestamp;

    @JsonProperty("stock")
    private Map<String,String> stockDataMap = new HashMap<>();

    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Fill StockData
     * @param stockDTO
     */
    private void fillStockData(StockDTO stockDTO) {
        stockDataMap.put("id",stockDTO.getId());
        stockDataMap.put("timestamp",stockDTO.getTimeStamp().toString());
        stockDataMap.put("quantity",String.valueOf(stockDTO.getQuantity()));
    }
}
