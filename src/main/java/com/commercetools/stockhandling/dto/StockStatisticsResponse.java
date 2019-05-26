package com.commercetools.stockhandling.dto;

import com.commercetools.stockhandling.entity.Stock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StockStatisticsResponse {

    private LocalDateTime requestTimestamp;

    private String time;

    private List<StockDTO> topAvailableProducts = new ArrayList<>();

    private List<ProductSellingDTO> topSellingProducts = new ArrayList<>();


    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<StockDTO> getTopAvailableProducts() {
        return topAvailableProducts;
    }

    public void setTopAvailableProducts(List<StockDTO> topAvailableProducts) {
        this.topAvailableProducts = topAvailableProducts;
    }

    public List<ProductSellingDTO> getTopSellingProducts() {
        return topSellingProducts;
    }

    public void setTopSellingProducts(List<ProductSellingDTO> topSellingProducts) {
        this.topSellingProducts = topSellingProducts;
    }
}
