package com.efs.backend.Controller;

import com.efs.backend.DTO.DTOLogin;
import com.efs.backend.Models.Customer;
import com.efs.backend.Models.Log;
import com.efs.backend.Models.Order;
import com.efs.backend.Models.Product;
import com.efs.backend.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LogService logService;


    @Autowired
    private CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody DTOLogin login) {
        String username = login.getUsername();
        String password = login.getPassword();
        Boolean result = adminService.login(username, password);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Admin logged out");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> productList = productService.getProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/product-by/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete-product-by/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @PutMapping("/update-product")
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @PostMapping("/save-product")
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/reject-order-by/{id}")
    public ResponseEntity<Order> rejectOrderById(@PathVariable("id") Long orderId) {
        Order order = orderService.rejectOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PutMapping("confirm-all-pending-orders")
    public ResponseEntity<List<Order>> confirmAllPendingOrders() {
        List<Order> orders = orderService.confirmAllPendingOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<Log>> getAllLogs() {
        List<Log> logs = logService.getAllLogs();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
