package com.example.qrcodegenerator.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class CloudConfig {

        @Value("${cloud.name}")
        private String cloudName;

        @Value("${cloud.key}")
        private String apiKey;

        @Value("${cloud.secret}")
        private String apiSecret;

        @Bean
        public Cloudinary cloudinary() {
            Map<String, String> config = new HashMap<>();

            config.put("cloud_name", cloudName);
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);

            return new Cloudinary(config);
        }
    }

