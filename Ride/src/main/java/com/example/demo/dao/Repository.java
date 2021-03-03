package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.demo.model.ThemeparkRide;


@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<ThemeparkRide, Long> {
	List<ThemeparkRide> findByName(String Name);
	
}

