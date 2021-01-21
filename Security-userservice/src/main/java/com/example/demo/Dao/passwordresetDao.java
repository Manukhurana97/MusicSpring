package com.example.demo.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.model.PasswordResetToken;

@Repository
@EnableTransactionManagement
public interface passwordresetDao extends JpaRepository<PasswordResetToken, Integer> {
	public PasswordResetToken findByToken(String token);

}
