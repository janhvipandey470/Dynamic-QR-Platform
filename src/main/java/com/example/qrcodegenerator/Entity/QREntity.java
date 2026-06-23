package com.example.qrcodegenerator.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QREntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String text;
    private String imageURL;
    private String publicId;
    private String QR_URL;
    private String redirectURL;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private users user;


}
