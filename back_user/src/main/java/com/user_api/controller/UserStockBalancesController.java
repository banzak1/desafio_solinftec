package com.user_api.controller;

import com.user_api.model.UserStockBalances;
import com.user_api.repository.UserStockBalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserStockBalancesController {

    @Autowired
    private UserStockBalancesRepository userStockBalancesRepository;

    public List<UserStockBalances> listar() {
        return userStockBalancesRepository.findAll();
    }
}
