package com.example.demo.Dao;

import com.example.demo.model.Authorities;
import com.example.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Component
@EnableTransactionManagement
public interface AuthorityDao extends JpaRepository<Authorities, Integer> {
    public Authorities findAuthoritiesByUsers(Users username);
}
