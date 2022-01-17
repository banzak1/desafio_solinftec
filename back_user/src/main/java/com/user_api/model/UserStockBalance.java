package com.user_api.model;

import java.io.Serializable;
import java.util.Objects;

public class UserStockBalance implements Serializable {

    private Long id_stock;
    private User user;

    public UserStockBalance(Long id_stock, User user) {
        this.id_stock = id_stock;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserStockBalance that = (UserStockBalance) o;
        return Objects.equals(id_stock, that.id_stock) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_stock, user);
    }
}