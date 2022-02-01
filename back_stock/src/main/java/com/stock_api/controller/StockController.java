package com.stock_api.controller;

import java.util.List;
import java.util.Optional;

import com.stock_api.model.Stock;
import com.stock_api.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import org.springframework.web.bind.annotation.controller;

@RestController

public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/stocks/{id}")
    public Optional<Stock> obterStock(@PathVariable(value = "id") Long id) {
        return stockRepository.findById(id);
    }

    @GetMapping("/stocks")
    public List<Stock> listar() {
        return stockRepository.findAll();
    }

    @PostMapping("/stocks")
    public Stock adicionar(@RequestBody Stock stock) {
        return stockRepository.save(stock);

    }
}