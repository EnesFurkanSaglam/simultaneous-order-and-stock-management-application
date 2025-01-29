package com.efs.backend.Service;

import com.efs.backend.DAO.DAOCustomer;
import com.efs.backend.DAO.DAOOrder;
import com.efs.backend.DAO.DAOProduct;
import com.efs.backend.Enums.CustomerType;
import com.efs.backend.Enums.LogType;
import com.efs.backend.Enums.OrderStatus;
import com.efs.backend.Models.Customer;
import com.efs.backend.Models.Log;
import com.efs.backend.Models.Order;
import com.efs.backend.Models.Product;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

public class OrderThread implements Runnable {

    private final Order order;
    private final Semaphore semaphore;
    private final LogService logService;
    private final DAOOrder daoOrder;
    private final DAOCustomer daoCustomer;
    private final DAOProduct daoProduct;

    public OrderThread(Order order, Semaphore semaphore, LogService logService, DAOOrder daoOrder,DAOProduct daoProduct,DAOCustomer daoCustomer) {
        this.order = order;
        this.semaphore = semaphore;
        this.logService = logService;
        this.daoOrder = daoOrder;
        this.daoCustomer = daoCustomer;
        this.daoProduct = daoProduct;
    }



    @Override
    public void run() {
        try {
            semaphore.acquire();
            while (!Thread.currentThread().isInterrupted() && order.getOrderStatus().equals(OrderStatus.PENDING)) {
                updatePriorityScore();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
            System.out.println("Thread for Order ID " + order.getOrderId() + " has stopped.");
        }
    }

    private void updatePriorityScore() {
        long waitingTime = java.time.Duration.between(order.getOrderDate(), LocalDateTime.now()).getSeconds();
        double newPriorityScore = (order.getCustomer().getCustomerType().equals(CustomerType.PREMIUM) ? 15 : 10)
                + (waitingTime * 0.5);
        order.setPriorityScore(newPriorityScore);
        order.setWaitingTime(waitingTime);
        daoOrder.save(order);


        if (newPriorityScore > 100) {
            rejectOrder();
        }
    }

    private void rejectOrder() {
        try {

            order.setOrderStatus(OrderStatus.CANCELLED);
            daoOrder.save(order);


            Customer customer = order.getCustomer();
            Product product = order.getProduct();
            double totalPrice = order.getTotalPrice();

            customer.setTotalSpent(customer.getTotalSpent() - totalPrice);
            customer.setBudget(customer.getBudget() + totalPrice);
            daoCustomer.save(customer);

            product.setStock(product.getStock() + order.getQuantity());
            daoProduct.save(product);


            String logMessage = customer.getUser().getUsername() + " (" + customer.getCustomerType() + ") " +
                    product.getProductName() + " " + order.getQuantity() + " quantity order was automatically canceled.";
            Log log = new Log(customer.getUser(), order, LocalDateTime.now(), LogType.WARNING, logMessage);
            logService.saveLog(log);


            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("Error during automatic rejection: " + e.getMessage());
        }
    }

}
