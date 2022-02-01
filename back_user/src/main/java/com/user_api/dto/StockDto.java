package com.user_api.dto;

import com.user_api.model.User;
import com.user_api.model.UserOrders;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
public class StockDto {
    private Long id;
    private String stock_symbol;
    private String stock_name;

    public StockDto(Long id, String stock_symbol, String stock_name) {
        this.id = id;
        this.stock_symbol = stock_symbol;
        this.stock_name = stock_name;
    }

    public StockDto tranformaParaObjeto1() {
        return new StockDto(id, stock_symbol, stock_name);
    }
}
