package com.example.demo.Dao;

import com.example.demo.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
@EnableTransactionManagement
public interface userdetailsDao extends JpaRepository<UserDetails, Integer> {
    UserDetails findByusers_Username(String username);
}