package com.example.qrcodegenerator.Repository;

import com.example.qrcodegenerator.DTO.loginDTO;
import com.example.qrcodegenerator.Entity.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface usersRepo extends JpaRepository<users,Long> {
   Optional<users> findByEmail(String email);

}
