package com.efs.backend.DAO;

import com.efs.backend.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DAOUser extends JpaRepository< User,Long> {

    @Query(value = "SELECT * FROM user WHERE username = ?1", nativeQuery = true)
    Optional<User> findUserByUsername(String username);


}
