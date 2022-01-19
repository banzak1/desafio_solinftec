package com.user_api.service;

import com.user_api.model.UserStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController

public class UserServices {

    @Autowired
    private WebClient webClienStock;

    @GetMapping
    public UserStock obterPorCodigo(Long id, @RequestHeader("Authorization") String token) {
        Mono<UserStock> monoStock = this.webClienStock
                .method(HttpMethod.GET)
                .uri("/stocks/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(UserStock.class);

        monoStock.subscribe(s -> {
            System.out.println("acabou");
        });
        UserStock stock = monoStock.block();
        return stock;
    }

    // @GetMapping("/stock")
    // public List<UserStock> listar3(Long id) {
    //
    // Mono<UserStock[]> monoStock = this.webClienStock
    // .method(HttpMethod.GET)
    // .uri("/stock/{id}", id)
    // .retrieve()
    // .bodyToMono(UserStock[].class);
    // return Arrays.stream(monoStock.block()).toList();
    // }

    @GetMapping("/stocks")
    public List<UserStock> listar2(@RequestHeader("Authorization") String token) {

        Mono<UserStock[]> monoStock = this.webClienStock
                .method(HttpMethod.GET)
                .uri("/stocks")
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(UserStock[].class);
        return Arrays.stream(monoStock.block()).toList();
    }

}
