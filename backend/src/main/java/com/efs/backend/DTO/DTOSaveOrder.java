package com.efs.backend.DTO;


import java.time.LocalDateTime;

public class DTOSaveOrder {

    private Long productId;
    private Integer quantity;
    private Long CustomerId;
    private LocalDateTime orderCreationTime = LocalDateTime.now();

    public DTOSaveOrder() {
    }

    public DTOSaveOrder(Long productId, Integer quantity, Long customerId, LocalDateTime orderCreationTime) {
        this.productId = productId;
        this.quantity = quantity;
        CustomerId = customerId;
        this.orderCreationTime = orderCreationTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(Long customerId) {
        CustomerId = customerId;
    }

    public LocalDateTime getOrderCreationTime() {
        return orderCreationTime;
    }

    public void setOrderCreationTime(LocalDateTime orderCreationTime) {
        this.orderCreationTime = orderCreationTime;
    }
}
