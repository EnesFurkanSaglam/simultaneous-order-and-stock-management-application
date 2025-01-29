package com.efs.backend.DAO;

import com.efs.backend.Models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DAOCustomer extends JpaRepository< Customer,Long> {
}
