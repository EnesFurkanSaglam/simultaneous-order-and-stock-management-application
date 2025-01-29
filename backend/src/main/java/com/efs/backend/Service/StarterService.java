package com.efs.backend.Service;

import com.efs.backend.DAO.DAOAdmin;
import com.efs.backend.DAO.DAOCustomer;
import com.efs.backend.DAO.DAOProduct;
import com.efs.backend.DAO.DAOUser;
import com.efs.backend.Enums.CustomerType;
import com.efs.backend.Models.Admin;
import com.efs.backend.Models.Customer;
import com.efs.backend.Models.Product;
import com.efs.backend.Models.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;


@Service
public class StarterService {

    @Autowired
    private DAOUser daoUser;

    @Autowired
    private DAOAdmin daoAdmin;

    @Autowired
    private DAOCustomer daoCustomer;

    @Autowired
    private DAOProduct daoProduct;

    @PostConstruct
    public void onApplicationStart() {
        populateRandomData();
    }

    public Boolean populateRandomData() {
        Random random = new Random();

        createProducts();

        int customerCount = random.nextInt(6) + 5;
        for (int i = 1; i <= customerCount; i++) {

            User user = createRandomUser("customer" + i, "CUSTOMER");

            int budget = 500 + random.nextInt(3000 - 500 + 1);

            CustomerType type = (i <= 3) ? CustomerType.PREMIUM : CustomerType.STANDARD;

            Customer customer = new Customer();
            customer.setCustomerName("Customer " + i);
            customer.setBudget((double)budget);
            customer.setCustomerType(type);
            customer.setTotalSpent(0.0);
            customer.setUser(user);

            daoCustomer.save(customer);
        }

        createAdminUser();



        return true;
    }

    private void createProducts() {
        Product product1 = new Product("Product1", 500, 100.0);
        Product product2 = new Product("Product2", 10, 50.0);
        Product product3 = new Product("Product3", 200, 45.0);
        Product product4 = new Product("Product4", 75, 75.0);
        Product product5 = new Product("Product5", 0, 500.0);
        daoProduct.save(product1);
        daoProduct.save(product2);
        daoProduct.save(product3);
        daoProduct.save(product4);
        daoProduct.save(product5);
    }

    private void createAdminUser() {
        User adminUser = createRandomUser("admin", "ADMIN");

        Admin admin = new Admin();
        admin.setAdminName("Super Admin");
        admin.setUser(adminUser);

        daoAdmin.save(admin);
    }

    private User createRandomUser(String username, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("123");
        user.setEmail(username + "@example.com");
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());

        return daoUser.save(user);
    }
}
