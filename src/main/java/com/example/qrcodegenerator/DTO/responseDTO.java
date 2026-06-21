package com.example.qrcodegenerator.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class responseDTO {


        private Long id;
        private String qrUrl;

}
