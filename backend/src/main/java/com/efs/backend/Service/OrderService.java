package com.efs.backend.Service;

import com.efs.backend.DAO.DAOCustomer;
import com.efs.backend.DAO.DAOOrder;
import com.efs.backend.DAO.DAOProduct;
import com.efs.backend.DTO.DTOSaveOrder;
import com.efs.backend.Enums.CustomerType;
import com.efs.backend.Enums.LogType;
import com.efs.backend.Enums.OrderStatus;
import com.efs.backend.Models.Customer;
import com.efs.backend.Models.Log;
import com.efs.backend.Models.Order;
import com.efs.backend.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
public class OrderService {

    private final Semaphore semaphore = new Semaphore(5);
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final ConcurrentHashMap<Long, Thread> threadMap = new ConcurrentHashMap<>();

    @Autowired
    DAOOrder daoOrder;

    @Autowired
    DAOProduct daoProduct;

    @Autowired
    DAOCustomer daoCustomer;

    @Autowired
    LogService logService;

    public boolean saveOrder(List<DTOSaveOrder> dtoSaveOrderList) {
        double sumPrice = 0.0;

        for (DTOSaveOrder dtoSaveOrder : dtoSaveOrderList) {
            double totalPrice = calculateOrderPrice(dtoSaveOrder.getQuantity(), dtoSaveOrder.getProductId());
            sumPrice += totalPrice;
        }

        for (DTOSaveOrder dtoSaveOrder : dtoSaveOrderList) {
            Customer customer = daoCustomer.findById(dtoSaveOrder.getCustomerId()).get();
            Product product = daoProduct.findById(dtoSaveOrder.getProductId()).get();
            if (customer.getBudget() < sumPrice) {
                Log log = new Log(customer.getUser(), null, LocalDateTime.now(), LogType.ERROR,
                        customer.getUser().getUsername()+" ("+ customer.getCustomerType() +") "+ product.getProductName()+" " + dtoSaveOrder.getQuantity()+" quantity order was not approved due to insufficient balance.");
                logService.saveLog(log);
                return false;
            }
        }

        for (DTOSaveOrder dtoSaveOrder : dtoSaveOrderList) {
            Long productId = dtoSaveOrder.getProductId();
            Product product = daoProduct.findById(productId).get();
            Integer quantity = dtoSaveOrder.getQuantity();
            if (product.getStock() < quantity) {
                Customer customer = daoCustomer.findById(dtoSaveOrder.getCustomerId()).get();
                Log log = new Log(customer.getUser(), null, LocalDateTime.now(), LogType.ERROR,
                        customer.getUser().getUsername()+" ("+ customer.getCustomerType() +") "+ product.getProductName()  +" "  + dtoSaveOrder.getQuantity()+" quantity order was not approved due to insufficient stock.");
                logService.saveLog(log);
                return false;
            }
        }

        for (DTOSaveOrder dtoSaveOrder : dtoSaveOrderList) {
            Customer customer = daoCustomer.findById(dtoSaveOrder.getCustomerId()).get();
            Long productId = dtoSaveOrder.getProductId();
            Product product = daoProduct.findById(productId).get();
            Integer quantity = dtoSaveOrder.getQuantity();
            double totalPrice = calculateOrderPrice(quantity, productId);

            Order order = new Order(customer, product, quantity, totalPrice, OrderStatus.PENDING, LocalDateTime.now(), 0.0,0);
            daoOrder.save(order);

            customer.setTotalSpent(customer.getTotalSpent() + totalPrice);
            customer.setBudget(customer.getBudget() - totalPrice);

            daoCustomer.save(customer);
            product.setStock(product.getStock() - quantity);
            daoProduct.save(product);

            Log log = new Log(customer.getUser(), order, LocalDateTime.now(), LogType.INFORMATION,
                    customer.getUser().getUsername()+" ("+ customer.getCustomerType() +") "+ product.getProductName() +" " + dtoSaveOrder.getQuantity()+" quantity order has been put on hold.");
            logService.saveLog(log);


            OrderThread orderThread = new OrderThread(order, semaphore, logService, daoOrder,daoProduct,daoCustomer);
            Thread thread = new Thread(orderThread);
            threadMap.put(order.getOrderId(), thread);
            thread.start();
        }

        return true;
    }

    public double calculateOrderPrice(Integer quantity, Long productId) {
        Optional<Product> result = daoProduct.findById(productId);
        Product product = result.get();
        Double productPrice = product.getPrice();

        return productPrice * quantity;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return daoOrder.getOrdersByUserId(userId).get();
    }

    public List<Order> getOrders() {
        return daoOrder.findAll();
    }

    @Transactional
    public Order rejectOrder(Long orderId) {
        Order order = daoOrder.findById(orderId).get();

        Customer customer = daoCustomer.findById(order.getCustomer().getId()).get();


        double totalPrice = calculateOrderPrice(order.getQuantity(), order.getProduct().getProductId());
        customer.setTotalSpent(customer.getTotalSpent() - totalPrice);
        customer.setBudget(customer.getBudget() + totalPrice);

        Product product = daoProduct.findById(order.getProduct().getProductId()).get();
        product.setStock(product.getStock() + order.getQuantity());
        order.setOrderStatus(OrderStatus.CANCELLED);

        daoCustomer.save(customer);
        daoProduct.save(product);


        Thread thread = threadMap.get(order.getOrderId());
        if (thread != null) {
            thread.interrupt();
            threadMap.remove(order.getOrderId());
        }

        Log log = new Log(order.getCustomer().getUser(), order, LocalDateTime.now(), LogType.WARNING,
                customer.getUser().getUsername()+" ("+ customer.getCustomerType() +") "+ product.getProductName()  +" "  + order.getQuantity()+" quantity order was rejected by the admin.");
        logService.saveLog(log);

        return daoOrder.save(order);
    }

    @Transactional
    public List<Order> confirmAllPendingOrders() {
        List<Order> orders = daoOrder.findAll();

        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.PENDING)) {

                order.setOrderStatus(OrderStatus.COMPLETED);
                daoOrder.save(order);


                Thread thread = threadMap.get(order.getOrderId());
                if (thread != null) {
                    thread.interrupt();
                    threadMap.remove(order.getOrderId());
                }


                Customer customer = order.getCustomer();

                if (customer.getCustomerType().equals(CustomerType.STANDARD) && customer.getTotalSpent() > 2000) {
                    customer.setCustomerType(CustomerType.PREMIUM);
                    daoCustomer.save(customer);


                    Log log = new Log(
                            customer.getUser(),
                            order,
                            LocalDateTime.now(),
                            LogType.INFORMATION,
                            customer.getUser().getUsername() + "  user was upgraded from Standard to Premium."
                    );
                    logService.saveLog(log);
                }
            }
        }


        Log log = new Log(
                null,
                null,
                LocalDateTime.now(),
                LogType.INFORMATION,
                " All orders were approved by the admin."
        );
        logService.saveLog(log);

        return daoOrder.findAll();
    }

}
