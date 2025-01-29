package com.efs.backend.DAO;

import com.efs.backend.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DAOProduct extends JpaRepository<Product,Long> {
}
