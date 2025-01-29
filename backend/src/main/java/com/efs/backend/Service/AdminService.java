package com.efs.backend.Service;


import com.efs.backend.DAO.DAOAdmin;
import com.efs.backend.DAO.DAOUser;
import com.efs.backend.Enums.LogType;
import com.efs.backend.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private DAOAdmin daoAdmin;

    @Autowired
    private DAOUser daoUser;

    @Autowired
    private LogService logService;


    public boolean login(String username,String password){

        Optional<User> result = daoUser.findUserByUsername(username);

        User user = null;

        if (result.isPresent()){
            user = result.get();

            if (password.equals(user.getPassword())){
                Log log = new Log(user,null, LocalDateTime.now(), LogType.INFORMATION,"Admin logged in");
                logService.saveLog(log);
                return true;
            }
        }else{
            return false;
        }
        return false;
    }

    public Admin saveAdmin(Admin admin){
        return daoAdmin.save(admin);
    }





}
