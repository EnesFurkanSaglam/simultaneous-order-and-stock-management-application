package com.efs.backend.Controller;

import com.efs.backend.DTO.DTOLogin;
import com.efs.backend.DTO.DTOSaveOrder;
import com.efs.backend.Models.Customer;
import com.efs.backend.Models.Order;
import com.efs.backend.Models.Product;
import com.efs.backend.Service.CustomerService;
import com.efs.backend.Service.OrderService;
import com.efs.backend.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody DTOLogin login) {

        String username = login.getUsername();
        String password = login.getPassword();
        Boolean result = customerService.login(username, password);

        if (result) {
            return ResponseEntity.ok("Customer logged in");
        } else {
            return ResponseEntity.status(401).body("Login failed.");
        }
    }

    @GetMapping("/get-userId-by/{username}")
    public ResponseEntity<Long> getUserIdByUsername(@PathVariable("username") String username) {
        Long userId = customerService.getUserIdByUsername(username);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/get-user-by/{id}")
    public ResponseEntity<Customer> getUserIdByUsername(@PathVariable("id") Long userId) {
        Customer customer = customerService.getUserById(userId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> productList = productService.getProducts();
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/save-order")
    public ResponseEntity<Boolean> saveOrder(@RequestBody List<DTOSaveOrder> dtoSaveOrder) {
        Boolean result = orderService.saveOrder(dtoSaveOrder);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/orders-by-user/{id}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }
}
