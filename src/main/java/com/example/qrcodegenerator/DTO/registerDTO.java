package com.example.qrcodegenerator.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class registerDTO {
        private String email;
        private String name;
        private String password;
    }

