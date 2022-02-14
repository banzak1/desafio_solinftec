package com.user_api.controller;

import com.user_api.dto.StockDto;
import com.user_api.dto.UserOrdersDto;
import com.user_api.model.User;
import com.user_api.model.UserOrders;
import com.user_api.repository.BuyRepository;
import com.user_api.repository.UserOrdersRepository;
import com.user_api.repository.UsersRepository;
import com.user_api.service.StockService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.*;
import java.util.List;

@RestController
public class UserOrdersController {

    @Autowired
    private UserOrdersRepository userOrdersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private WebClient webClient;
    @Autowired
    private StockService stockService;

    @GetMapping("/orders")
    public List<UserOrders> listar() {
        return userOrdersRepository.findAll();
    }

    @PostMapping("/orders")
    public ResponseEntity<UserOrders> salvar(@RequestBody UserOrdersDto dto,
            @RequestHeader("Authorization") String token) {
        User user = usersRepository.findById(dto.getId_user()).orElseThrow();
        Double dollar = user.getDollar_balance();
        Double mult = dto.getPrice() * dto.getVolume();
        if (dollar >= mult) {// verifica se o usuario tem dinheiro na carteira pra criar uma ordem de compra
            UserOrders userOrders = userOrdersRepository.save(dto.tranformaParaObjeto1(user));
            stockService.teste1(userOrders.getId_stock(), token);
            return new ResponseEntity<>(userOrders, HttpStatus.CREATED);
        } else {
            System.out.println("Ordem não criada, valor insuficiente");
        }
        return null;
    }

    @PostMapping("/venda")
    public UserOrders vender(@RequestBody UserOrdersDto dto) throws SQLException {

        if (dto.getType() == 0) {
            List<UserOrders> userOrders = userOrdersRepository.findByTypeStock(dto.getId_stock());
            List<UserOrders> teste1 = userOrdersRepository.testando1();
            List<UserOrders> userteste = buyRepository.fyndteste();
            List<UserOrders> userteste1 = buyRepository.findtTeste1();
            List<UserOrders> userFind = userOrdersRepository.findByCalculo();
            if (userOrders.isEmpty()) {
                System.out.println(dto.getStatus());
                if (!userteste1.isEmpty()) {
                    System.out.println("buy negativa");
                    for (UserOrders cont : userteste1) {
                        System.out.println(dto.getId());
                        buyRepository.updateDollarBalanceNE(cont, cont.getUser());
                        buyRepository.RemainingNE(cont);
                        buyRepository.atualizarBalanceNE(cont.getId(), cont.getUser(), cont.getId_stock());
                    }
                    // userOrdersRepository.updateStatus2();
                }
                if (!userFind.isEmpty()) {
                    System.out.println("venda positiva");
                    for (UserOrders cont : userFind) {
                        userOrdersRepository.updateDollarBalance(cont.getUser());
                        userOrdersRepository.updateRemainingValue(cont.getId_stock(), cont);
                        userOrdersRepository.atualizarBalance(cont.getUser(), cont.getId_stock());
                    }
                }
                if (!userteste.isEmpty()) {
                    System.out.println("compra positiva");
                    for (UserOrders cont : userteste) {
                        buyRepository.updateDollarBalancePO(cont.getUser(), cont);
                        buyRepository.RemainigPO(cont, cont.getUser());
                        buyRepository.atualizarBalancePO(cont.getId(), cont.getUser(), cont.getId_stock());
                    }
                    userOrdersRepository.updateStatus();
                }
                if (!teste1.isEmpty()) {
                    System.out.println("venda negativa");
                    for (UserOrders cont : teste1) {
                        userOrdersRepository.RemainingNE(cont.getUser(), cont.getId_stock());
                        userOrdersRepository.updateDollarBalanceNE(cont, cont.getUser());
                        userOrdersRepository.updateRemainingValue2(cont);
                    }
                }
                userOrdersRepository.updateStatus2();
            }
        }
        return null;
    }

    @PostMapping("/teste/{id}")
    public ResponseEntity<StockDto> teste(@PathVariable Long id, @RequestHeader("Authorization") String token)
            throws Exception {
        StockDto stockDto1 = this.stockService.teste1(id, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockDto1);
    }

}