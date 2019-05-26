package com.commercetools.stockhandling.service;

import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.entity.Stock;

public interface StockService {

    void updateStock(StockDTO stockDTO);

    Stock getStockByProductId(String productId);
}
