package com.example.qrcodegenerator.Controller;

import com.example.qrcodegenerator.DTO.loginDTO;
import com.example.qrcodegenerator.DTO.registerDTO;
import com.example.qrcodegenerator.DTO.responseDTO;
import com.example.qrcodegenerator.Entity.QREntity;
import com.example.qrcodegenerator.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class userController {
    @Autowired
    private userService service;
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody loginDTO loginDTO){

        String token = service.login(loginDTO);

        return ResponseEntity.ok(token);
    }
    @PostMapping("/sign_up")
    public String handleAccount(@RequestBody registerDTO registerDTO){
        service.register(registerDTO);
        return "login Again";
    }
    @GetMapping("/dashboard")
    public List<responseDTO> dashboard(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        return service.dashboard(email);
    }
}
