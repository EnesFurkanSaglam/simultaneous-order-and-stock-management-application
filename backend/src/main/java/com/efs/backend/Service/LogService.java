package com.efs.backend.Service;

import com.efs.backend.DAO.DAOLog;
import com.efs.backend.Models.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private DAOLog daoLog;

    public void saveLog(Log log){
        daoLog.save(log);
    }
    public List<Log> getAllLogs(){
        return daoLog.findAll();
    }

}
