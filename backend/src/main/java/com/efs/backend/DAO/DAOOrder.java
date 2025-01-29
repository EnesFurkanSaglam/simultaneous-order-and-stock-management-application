package com.efs.backend.DAO;

import com.efs.backend.Models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface  DAOOrder extends JpaRepository<Order,Long > {

    @Query(value = "SELECT * FROM orders WHERE customer_id = ?1", nativeQuery = true)
    Optional<List<Order>> getOrdersByUserId(Long userId);

    @Query(value = "SELECT * FROM orders WHERE product_id = ?1", nativeQuery = true)
    Optional<List<Order>> getOrdersByProductId(Long productId);
}
