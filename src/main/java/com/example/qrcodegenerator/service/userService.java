package com.example.qrcodegenerator.service;

import com.example.qrcodegenerator.Config.Security.JwtService;
import com.example.qrcodegenerator.DTO.loginDTO;
import com.example.qrcodegenerator.DTO.registerDTO;
import com.example.qrcodegenerator.DTO.responseDTO;
import com.example.qrcodegenerator.Entity.QREntity;
import com.example.qrcodegenerator.Entity.users;
import com.example.qrcodegenerator.Repository.usersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {
    @Autowired
    private usersRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwt;
    public String login(loginDTO loginDTO) {
        users user = repo
                .findByEmail(loginDTO.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));
        boolean match= passwordEncoder.matches(loginDTO.getPassword(),user.getPassword());
        System.out.println(loginDTO.getEmail());
        System.out.println(loginDTO.getPassword());
        System.out.println(user.getPassword());
        if(!match){
            throw new RuntimeException("Invalid email or password");
        }
        return jwt.generateToken(user.getEmail());
    }

    public Boolean register(registerDTO registerDTO) {
        boolean exists=repo.findByEmail(registerDTO.getEmail()).isPresent();
        if(exists){
            return Boolean.FALSE;
        }
        else{
            users user=new users();
            user.setEmail(registerDTO.getEmail());
            user.setName(registerDTO.getName());
            user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
            repo.save(user);
            return Boolean.TRUE;
        }
    }

    public List<responseDTO> dashboard(String email) {
        users user=repo.findByEmail(email).orElseThrow();
        return user.getQR().stream().map(qr->new responseDTO(qr.getId(), qr.getQR_URL(), qr.getType())).toList();

    }
}
