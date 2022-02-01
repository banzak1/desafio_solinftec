package com.user_api.controller;

import com.user_api.dto.UserOrdersDto;
import com.user_api.dto.UserStockDto;
import com.user_api.model.User;
import com.user_api.model.UserOrders;
import com.user_api.repository.BuyRepository;
import com.user_api.repository.UserOrdersRepository;
import com.user_api.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;

@RestController
public class UserOrdersController {

    @Autowired
    private UserOrdersRepository userOrdersRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BuyRepository BuyRepository;

    @GetMapping("/orders")
    public List<UserOrders> listar() {
        return userOrdersRepository.findAll();
    }

    @PostMapping("/orders")
    public ResponseEntity<UserOrders> salvar(@RequestBody UserOrdersDto dto) {
        User user = usersRepository.findById(dto.getId_user()).orElseThrow();
        Double dollar = user.getDollar_balance();
        Double mult = dto.getPrice() * dto.getVolume();
        if (dollar >= mult) {// verifica se o usuario tem dinheiro na carteira pra criar uma ordem de Buy
            UserOrders userOrders = userOrdersRepository.save(dto.tranformaParaObjeto1(user));
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
            List<UserOrders> userStatus = userOrdersRepository.findByStatus();
            List<UserOrders> userStock = userOrdersRepository.findStockExists();
            List<UserOrders> novoTeste = userOrdersRepository.novoTeste();
            List<UserOrders> teste1 = userOrdersRepository.testando1();
            List<UserOrders> userOrders1 = BuyRepository.findByTypeStock(dto.getId_stock());
            List<UserOrders> userStatus1 = BuyRepository.findByStatus();
            List<UserOrders> userteste = BuyRepository.fyndteste();
            List<UserOrders> userteste1 = BuyRepository.findtTeste1();
            if (userOrders.isEmpty()) {
                List<UserOrders> userFind = userOrdersRepository.findByCalculo();
                System.out.println(dto.getStatus());
                if (!userteste1.isEmpty()) {
                    System.out.println("Buy negativa");
                    for (UserOrders cont : userteste1) {
                        BuyRepository.updateDollarBalance2(cont.getUser());
                        BuyRepository.AtuaalizarValue2(cont);
                        // BuyRepository.updateStatus(cont);
                        BuyRepository.atualizarBalance(cont.getId(), cont.getUser(), cont.getId_stock());
                    }
                }

                if (!userFind.isEmpty()) {
                    System.out.println("venda positiva");
                    for (UserOrders cont : userFind) {// Esse for lista os matches
                        userOrdersRepository.updateDollarBalance(cont.getUser());
                        userOrdersRepository.updateRemainingValue(cont.getId_stock(), cont);
                        userOrdersRepository.atualizarBalance(cont.getUser(), cont.getId_stock());
                        // userOrdersRepository.updateStatus(cont);

                    }
                }

                if (!userteste.isEmpty()) {
                    System.out.println("Buy positiva");
                    for (UserOrders cont : userteste) {
                        BuyRepository.updateDollarBalance(cont.getUser());
                        BuyRepository.AtuaalizarValue(cont.getId_stock(), cont);
                        BuyRepository.updateStatus(cont);
                        BuyRepository.atualizarBalance(cont.getId(), cont.getUser(), cont.getId_stock());
                    }
                }
                if (!teste1.isEmpty()) {
                    System.out.println("venda negativa");
                    for (UserOrders cont : teste1) {
                        userOrdersRepository.atualizarBalance2(cont.getUser(), cont.getId_stock());
                        userOrdersRepository.updateDollarBalance1(cont, cont.getUser());
                        userOrdersRepository.updateRemainingValue2(cont);
                        userOrdersRepository.updateStatus(cont);
                        System.out.println(cont + "valorrrrrrrr do cont");

                    }
                }

            }

        }
        return null;
    }
}
