package com.efs.backend.Service;


import com.efs.backend.DAO.DAOCustomer;
import com.efs.backend.DAO.DAOProduct;
import com.efs.backend.DAO.DAOUser;
import com.efs.backend.Enums.LogType;
import com.efs.backend.Models.Customer;
import com.efs.backend.Models.Log;
import com.efs.backend.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private DAOUser daoUser;

    @Autowired
    private DAOProduct daoProduct;

    @Autowired
    private DAOCustomer daoCustomer;

    @Autowired
    private LogService logService;

    public boolean login(String username,String password){

        Optional<User> result = daoUser.findUserByUsername(username);

        User user = null;

        if (result.isPresent()){
            user = result.get();

            if (password.equals(user.getPassword())){
                Log log = new Log(user,null, LocalDateTime.now(), LogType.INFORMATION,username+" logged in");
                logService.saveLog(log);
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    public long getUserIdByUsername(String username){
        Optional<User> result = daoUser.findUserByUsername(username);

        User user = null;

        if (result.isPresent()){
            user = result.get();
        }else{
        return 0;
        }
        return user.getUserId();
    }

    public Customer getUserById(Long id){
        return daoCustomer.findById(id).get();
    }


    public Customer saveCustomer(Customer customer){
        return daoCustomer.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return daoCustomer.findAll();
    }



}
