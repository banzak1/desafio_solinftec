package com.user_api.repository;

import com.user_api.model.User;
import com.user_api.model.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BuyRepository extends JpaRepository<UserOrders, Long> {

    @Query(value = "SELECT * FROM user_orders WHERE type = 0 and id_stock = ?1 and status = 1", nativeQuery = true)
    List<UserOrders> findByTypeStock(Long id_stock);
    // Procura compras

    @Query(value = "SELECT * FROM user_orders a, user_orders b  where a.type <> b.type  and a.id_stock = b.id_stock and a.status = b.status ", nativeQuery = true)
    List<UserOrders> findByCalculo();

    @Modifying
    @Query(value = "update user_orders  set remaining_value = (SELECT MAX(b.remaining_value) - MIN(b.volume)AS ID FROM user_orders a, user_orders b  WHERE a.type <> b.type  and a.id_stock = b.id_stock and a.id=?1) where type =  0 AND id=?1", nativeQuery = true)
    int updateRemainingValue(UserOrders id);
    // Ele atualiza o raimaning value conforme compras são realizadas

    @Modifying
    @Query(value = "UPDATE user_orders SET status = 2 WHERE id = ?1 and remaining_value = 0", nativeQuery = true)
    int updateStatus(UserOrders id);

    // @Modifying
    // @Query(value = "UPDATE users_orders SET status = 3 WHERE id_order = ?1 and
    // remaining_value = 0 ", nativeQuery = true)
    // int updateStatus2(UserOrder id_order);

    @Modifying
    @Query(value = "update users set dollar_balance = (select  u.dollar_balance - a.remaining_value  * uo.price  "
            + " FROM user_orders a, user_orders uo " +
            "inner join users u on uo.id_user = u.id " +
            "where a.status = 1  and a.id_stock = uo.id_stock and a.type = 1 and a.status = uo.status and  u.id = ?1) where id = ?1", nativeQuery = true)
    int updateDollarBalance(User user);

    @Modifying
    @Query(value = " update users set dollar_balance = dollar_balance  - ( " +
            " select  a.remaining_value * a.price    FROM user_orders a, user_orders uo " +
            " inner join users u on uo.id_user = u.id " +
            " where a.status = 1  and a.id_user = ?1 and a.type <> uo.type and a.id_stock = uo.id_stock) where id = ?1", nativeQuery = true)
    int updateDollarBalance2(User user);

    // atauliza dinheiro do usuario quando comrpas

    @Query(value = "SELECT * from user_orders where status =1 and type = 0 ", nativeQuery = true)
    List<UserOrders> findByStatus();
    // seleciona compras abertas

    @Modifying
    @Query(value = "update user_stock_balances set volume = (select a.volume - a.remaining_value " +
            " AS ID FROM user_orders a, user_orders uo " +
            " inner join users u on uo.id_user = u.id " +
            " inner join user_stock_balances usb on u.id = usb.id_user " +
            " WHERE a.type <> uo.type  and a.id_stock = uo.id_stock and a.id= ?1 ) where id_user = ?2 and id_stock = ?3", nativeQuery = true)
    int atualizarBalance(Long id, User id_user, Long id_stock);
    // calaculo do Stock balance quando realizada a compra

    @Query(value = " select * from " +
            " user_orders a, user_orders b where a.remaining_value >= b.volume and a.type = 0 and a.id_stock = b.id_stock and a.id <> b.id", nativeQuery = true)
    List<UserOrders> fyndteste();

    @Modifying
    @Query(value = " update user_orders  set remaining_value = (SELECT MAX(a.remaining_value) - MIN(b.volume)AS ID FROM user_orders a, user_orders b  WHERE a.type <> b.type  and a.id_stock = b.id_stock and a.id= ?1) where id= ?1 and type = 0 ", nativeQuery = true)
    int atualizarvalue(UserOrders id_order);

    @Query(value = "select * from " +
            " user_orders a, user_orders b where  a.remaining_value <= b.volume and a.type = 0 and a.status = 1 and a.id_stock = b.id_stock and a.id <> b.id", nativeQuery = true)
    List<UserOrders> findtTeste1();

    @Modifying
    @Query(value = " update user_orders  set remaining_value = (  select a.remaining_value  - b.remaining_value from " +
            " user_orders a, user_orders b where  a.type =0 and a.id_stock = b.id_stock and a.id_stock = ?1 and a.id <> b.id) where id=?2 and type = 0", nativeQuery = true)
    int AtuaalizarValue(Long id_stock, UserOrders id);

    @Modifying
    @Query(value = " update user_orders  set remaining_value = 0 where id=?1 and type = 0", nativeQuery = true)
    int AtuaalizarValue2(UserOrders id);

}
