package com.efs.backend.Models;

import com.efs.backend.Enums.CustomerType;
import jakarta.persistence.*;


@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Double budget;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerType customerType = CustomerType.STANDARD;

    @Column(name = "total_spent", nullable = false)
    private Double totalSpent = 0.0;

    public Customer() {
    }

    public Customer(User user, String customerName, Double budget, CustomerType customerType, Double totalSpent) {
        this.user = user;
        this.customerName = customerName;
        this.budget = budget;
        this.customerType = customerType;
        this.totalSpent = totalSpent;
    }

    public Customer(Long id, User user, String customerName, Double budget, CustomerType customerType, Double totalSpent) {
        this.id = id;
        this.user = user;
        this.customerName = customerName;
        this.budget = budget;
        this.customerType = customerType;
        this.totalSpent = totalSpent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }


}