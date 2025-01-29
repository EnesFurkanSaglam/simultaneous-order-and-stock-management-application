package com.efs.backend.Controller;


import com.efs.backend.Service.StarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/starter")
public class StarterController {

    @Autowired
    private StarterService starterService;


    @GetMapping("/start")
    public ResponseEntity<Boolean> starter(){
        Boolean result = starterService.populateRandomData();
        return ResponseEntity.ok(result);
    }


}
