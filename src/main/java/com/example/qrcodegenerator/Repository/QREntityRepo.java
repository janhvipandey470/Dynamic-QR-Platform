package com.example.qrcodegenerator.Repository;

import com.example.qrcodegenerator.Entity.QREntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QREntityRepo extends JpaRepository<QREntity,Long> {
}
