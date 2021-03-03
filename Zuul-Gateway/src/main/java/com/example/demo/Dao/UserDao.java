package com.example.demo.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.Model.Users;


@Component
@EnableTransactionManagement
public interface UserDao extends JpaRepository<Users, String> {
	public Users findByUsername(String username);
	

}
