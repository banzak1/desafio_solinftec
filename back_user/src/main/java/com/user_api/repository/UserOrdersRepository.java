package com.user_api.repository;

import com.user_api.model.User;
import com.user_api.model.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {
    @Query(value = "SELECT * FROM user_orders WHERE type = 1 and id_stock = ?1 and status = 1", nativeQuery = true)
    List<UserOrders> findByTypeStock(Long id_stock);// procurar ordens de venda abertas

    @Query(value = "SELECT * FROM user_orders a, user_orders b  where a.type <> b.type  and a.remaining_value > b.volume and a.type = 1  and a.id_stock = b.id_stock and a.status = b.status", nativeQuery = true)
    List<UserOrders> findByCalculo();// Pegando matches

    @Modifying
    @Query(value = "update user_orders  set remaining_value = (SELECT  a.remaining_value - b.volume AS ID FROM user_orders a, user_orders b  WHERE a.type =1  and a.id_stock = b.id_stock and a.id_stock = ?1 and a.id <> b.id) where type =  1 AND id=?2", nativeQuery = true)
    int updateRemainingValue(Long id_stock, UserOrders id);// Ele atualiza remaining value quando há match

    @Modifying
    @Query(value = "UPDATE user_orders SET status = 2 WHERE id = ?1 and remaining_value = 0", nativeQuery = true)
    int updateStatus(UserOrders id);

    @Modifying
    @Query(value = "UPDATE user_orders SET status = 3 WHERE id = ?1 and remaining_value = 0 ", nativeQuery = true)
    int updateStatus2(UserOrders id);

    @Modifying
    @Query(value = "update users set dollar_balance = (select a.volume  * uo.price + u.dollar_balance " +
            "FROM user_orders a, user_orders uo " +
            "inner join users u on uo.id_user = u.id " +
            "where a.status = 1  and a.id_stock = uo.id_stock and a.type = 0  and a.status = uo.status and uo.id <> a.id and u.id = ?1) where id = ?1", nativeQuery = true)
    int updateDollarBalance(User user);

    @Modifying
    @Query(value = "update users set dollar_balance =  dollar_balance +( " +
            "select a.remaining_value  * a.price " +
            "fROM user_orders a, user_orders uo " +
            "where  a.id_stock = uo.id_stock and a.type = 1  and uo.id <> a.id and a.id = ?1 " +
            ") where id = ?2", nativeQuery = true)
    int updateDollarBalance1(UserOrders id, User user);

    @Query(value = "SELECT * from user_orders where status =1 and type = 1 ", nativeQuery = true)
    List<UserOrders> findByStatus();

    @Modifying
    @Query(value = "update user_stock_balances set volume = (SELECT MAX(usb.volume) - MIN(uo.remaining_value)AS ID FROM user_orders uo inner join users u on uo.id_user = u.id inner join user_stock_balances usb on u.id = usb.id_user where uo.id_user = 1 and uo.id_stock = 5) where id_user =1 and id_stock=5", nativeQuery = true)
    int selectLinha();

    @Query(value = "select * from user_stock_balances usb inner join users u on usb.id_user = u.id inner join user_orders uo  on u.id = uo.id_user where uo.id_stock = usb.id_stock ", nativeQuery = true)
    List<UserOrders> findStockExists();

    @Modifying
    @Query(value = "insert into user_stock_balances (id_user, id_stock,  stock_symbol, stock_name, volume)(select id_user, id_stock, stock_symbol, stock_name, volume from user_orders uo)", nativeQuery = true)
    @Transactional
    void inserirStock();

    @Modifying
    @Query(value = "update user_stock_balances set volume = volume -(" +
            "select a.volume - a.remaining_value" +
            " AS ID FROM user_orders a" +
            " Inner join users u on a.id_user = u.id " +
            "  inner join user_stock_balances usb on u.id = usb.id_user " +
            "  WHERE  a.id_stock = usb.id_stock and a.id_user = ?1 and a.id_stock = ?2 " +
            "  ) where id_user = ?1 and id_stock = ?2", nativeQuery = true)
    int atualizarBalance(User id_user, Long id_stock);

    @Query(value = "SELECT MAX(a.remaining_value) - MIN(b.volume) AS ID FROM user_orders a, user_orders b where a.status = 1 and a.id_user = b.id_user   and a.id_stock = b.id_stock", nativeQuery = true)
    int selecionarSub();

    @Query(value = "SELECT * FROM user_orders a, user_orders b  where a.type =1  and a.id_stock = b.id_stock  and a.volume >= b.volume  ", nativeQuery = true)
    List<UserOrders> novoTeste();

    @Query(value = " select * from " +
            " user_orders a, user_orders b where a.remaining_value < b.volume and a.type <>b.type and a.id_stock = b.id_stock and a.id <> b.id", nativeQuery = true)
    List<UserOrders> testando1();

    @Modifying
    @Query(value = "update user_orders  set remaining_value = 0 where id=?1 and type = 1", nativeQuery = true)
    int updateRemainingValue2(UserOrders id);// Ele atualiza remaining value quando há match

    @Modifying
    @Query(value = "update user_stock_balances set volume = ( " +
            " select  usb.volume - a.remaining_value " +
            " AS ID FROM user_orders a " +
            " Inner join users u on a.id_user = u.id " +
            " inner join user_stock_balances usb on u.id = usb.id_user " +
            " WHERE  a.id_stock = usb.id_stock and a.id_user = ?1 and a.id_stock = ?2 " +
            " ) where id_user = ?1 and id_stock = ?2", nativeQuery = true)
    int atualizarBalance2(User id_user, Long id_stock);

}
