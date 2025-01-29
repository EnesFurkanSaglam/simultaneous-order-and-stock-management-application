package com.efs.backend.Service;

import com.efs.backend.DAO.DAOUser;
import com.efs.backend.Models.Admin;
import com.efs.backend.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private DAOUser daoUser;


    public User saveUser(User user){
        return daoUser.save(user);
    }
}
