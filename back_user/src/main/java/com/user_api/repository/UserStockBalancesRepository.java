package com.user_api.repository;

import com.user_api.model.UserStockBalances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockBalancesRepository extends JpaRepository<UserStockBalances, Long> {
}
