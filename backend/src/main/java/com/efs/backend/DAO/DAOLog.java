package com.efs.backend.DAO;


import com.efs.backend.Models.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DAOLog extends JpaRepository<Log,Long> {

    @Query(value = "SELECT * FROM log WHERE order_id = ?1", nativeQuery = true)
    Optional<List<Log>> getLogsByOrderId(Long orderId);
}
