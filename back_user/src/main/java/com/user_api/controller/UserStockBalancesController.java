package com.user_api.controller;

import com.user_api.dto.StockDto;
import com.user_api.dto.UserStockDto;
import com.user_api.model.User;
import com.user_api.model.UserStockBalances;
import com.user_api.repository.UserStockBalancesRepository;
import com.user_api.repository.UsersRepository;
import com.user_api.service.StockService;
import com.user_api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
public class UserStockBalancesController {

    @Autowired
    private UserStockBalancesRepository userStockBalancesRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private WebClient webClient;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private StockService userStockService;

    @GetMapping("/teste")
    public List<UserStockBalances> listar() {
        return userStockBalancesRepository.findAll();
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<UserStockDto> obterPorCodigo(@PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        UserStockDto userStockBalances = this.stockService.obterPorCodigo(id, token);

        return ResponseEntity.ok(userStockBalances);
    }

    @GetMapping("/teste/{id}")
    public ResponseEntity<StockDto> obterPorCodigo2(@PathVariable Long id,
            @RequestHeader("Authorization") String token) {

        StockDto userStockBalances = this.stockService.obterPorCodigo2(id, token);

        return ResponseEntity.ok(userStockBalances);
    }

    @PostMapping("/")
    public ResponseEntity<UserStockBalances> salvar(@RequestBody UserStockDto dto) {
        User user = usersRepository.findById(dto.getId_user()).orElseThrow();
        UserStockBalances userStockBalances = userStockService.salvar(dto.tranformaParaObjeto(user));
        return new ResponseEntity<>(userStockBalances, HttpStatus.CREATED);
        // UserStockBalances userStockBalances = dto.tranformaParaObjeto(user);

    }

}