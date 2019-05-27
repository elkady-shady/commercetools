package com.commercetools.stockhandling.controller;

import com.commercetools.stockhandling.dto.StockDTO;
import com.commercetools.stockhandling.entity.Stock;
import com.commercetools.stockhandling.repository.StockRepository;
import com.commercetools.stockhandling.service.StockService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/stockdb")
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private StockService stockService;

    @MockBean
    private StockRepository stockRepository;

    private Stock stock;

    @Before
    public void setup() {
        stock = new Stock();
        stock.setId("00001");
        stock.setProductId("veg-123");
        stock.setQuantity(50);
        stock.setTimeStamp(LocalDateTime.now());

        StockDTO stockDTO = new StockDTO();
        stockDTO.setId("00001");
        stockDTO.setProductId("veg-123");
        stockDTO.setQuantity(50);
        stockDTO.setTimeStamp(LocalDateTime.now());

        when(modelMapper.map(stock,StockDTO.class)).thenReturn(stockDTO);
        when(stockService.getStockByProductId("veg-123")).thenReturn(stock);
        when(stockRepository.findByIdAndProductId("00001","veg-123")).thenReturn(Optional.of(stock));
    }

    @Test
    public void testGetStock() throws Exception {
        this.mockMvc.perform(get("/stock?productId=veg-123")).andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("veg-123"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock.id").value("00001"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock.quantity").value("50"));
    }

    @Test
    public void testUpdateStock() throws Exception {
        StockDTO stockDTO = new StockDTO();
        stockDTO.setId("00001");
        stockDTO.setProductId("veg-123");
        stockDTO.setQuantity(30);

        String jsonString = new ObjectMapper().writeValueAsString(stockDTO);

        this.mockMvc.perform(post("/stock/update").contentType(MediaType.APPLICATION_JSON).content(jsonString)).andExpect(status().isCreated());

    }
}
