package com.commercetools.stockhandling.mapper;

import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.entity.Stock;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {


    private ModelMapper modelMapper;

    @Autowired
    public StockMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     *
     * @param stock
     * @return
     */
    public StockDTO convertToDTO(Stock stock) {
        return modelMapper.map(stock,StockDTO.class);
    }
}
