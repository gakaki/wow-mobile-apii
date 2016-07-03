package com.wow.stock;

import com.wow.stock.service.StockService;
import com.wow.stock.vo.AvailableStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StockApplication {

    private static final Logger logger = LoggerFactory.getLogger(StockApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Autowired
    private StockService stockService;

    @RequestMapping(value = "/products/{productId}/stocks/available", method = RequestMethod.GET)
    public AvailableStock getAvailableStock(@PathVariable Integer productId) {
        return stockService.getAvailableStock(productId);
    }
}