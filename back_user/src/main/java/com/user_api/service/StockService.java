package com.user_api.service;

import com.user_api.dto.StockDto;
import com.user_api.dto.UserStockDto;
import com.user_api.model.UserStockBalances;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@RestController
public class StockService {
    @Autowired
    private WebClient webClient;

    @GetMapping
    public UserStockDto obterPorCodigo(Long id, @RequestHeader("Authorization") String token) {
        Mono<UserStockDto> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stocks/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(UserStockDto.class);

        monoStock.subscribe(s -> {
            System.out.println("acabou");
        });
        UserStockDto stock = monoStock.block();
        return stock;
    }

    public StockDto obterPorCodigo2(Long id, @RequestHeader("Authorization") String token) {
        Mono<StockDto> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stocks/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(StockDto.class);

        monoStock.subscribe(s -> {
            System.out.println("acabou");
        });
        StockDto stock = monoStock.block();
        return stock;
    }

    @GetMapping("/stocks")
    public List<UserStockBalances> listar2(@RequestHeader("Authorization") String token) {
        Mono<UserStockBalances[]> monoStock = this.webClient
                .method(HttpMethod.GET)
                .uri("/stocks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(UserStockBalances[].class);
        return Arrays.stream(monoStock.block()).toList();

    }
}