package com.example.demo.Dao;

import com.example.demo.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public interface passwordresetDao extends JpaRepository<PasswordResetToken, Integer> {
    public PasswordResetToken findByToken(String token);

}
