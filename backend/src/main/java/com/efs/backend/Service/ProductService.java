package com.efs.backend.Service;

import com.efs.backend.DAO.DAOLog;
import com.efs.backend.DAO.DAOOrder;
import com.efs.backend.DAO.DAOProduct;
import com.efs.backend.Enums.LogType;
import com.efs.backend.Models.Log;
import com.efs.backend.Models.Order;
import com.efs.backend.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private DAOProduct daoProduct;

    @Autowired
    private LogService logService;

    @Autowired
    private DAOOrder daoOrder;

    @Autowired
    private DAOLog daoLog;

    public List<Product> getProducts(){
        return daoProduct.findAll();
    }

    public Product getProductById(Long id){
        Optional<Product> result = daoProduct.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

    public void deleteProductById(Long id){
        Product product = daoProduct.findById(id).get();
        List<Order> orders = daoOrder.getOrdersByProductId(id).get();

        for (Order order : orders){
            List<Log> logs = daoLog.getLogsByOrderId(order.getOrderId()).get();
            for (Log log : logs){
                daoLog.delete(log);
            }
        }
        for (Order order : orders){
            daoOrder.delete(order);
        }

        daoProduct.deleteById(id);
        Log log = new Log(null,null, LocalDateTime.now(), LogType.INFORMATION,product.getProductName()+" deleted ");
        logService.saveLog(log);

    }
    public void updateProduct(Product product){
        Product product1 = daoProduct.save(product);
        Log log = new Log(null,null, LocalDateTime.now(), LogType.INFORMATION,product1.getProductName()+" ' updated ");
        logService.saveLog(log);
    }

    public void saveProduct(Product product){
        Product product1 = daoProduct.save(product);
        Log log = new Log(null,null, LocalDateTime.now(), LogType.INFORMATION,product1.getProductName()+" added ");
        logService.saveLog(log);
    }




}

