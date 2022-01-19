package com.user_api.controller;

import com.user_api.model.UserStock;
import com.user_api.repository.UserStockRepository;
import com.user_api.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
public class UserStockController {

    @Autowired
    private UserStockRepository userStockBalancesRepository;
    @Autowired
    private UserServices stockService;
    @Autowired
    private WebClient webClient;

    public List<UserStock> listar() {
        return userStockBalancesRepository.findAll();
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<UserStock> obterPorCodigo(@PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        UserStock userStockBalances = this.stockService.obterPorCodigo(id, token);

        return ResponseEntity.ok(userStockBalances);
    }
}