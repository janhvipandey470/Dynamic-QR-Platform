package com.example.qrcodegenerator.service;

import com.example.qrcodegenerator.Entity.users;
import com.example.qrcodegenerator.Repository.usersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUserDetails;

@Service
public class userDetailService implements UserDetailsService {
    @Autowired
    private usersRepo repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        users user=repo.findByEmail(email).orElseThrow();
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("User")
                .build();
    }
}
