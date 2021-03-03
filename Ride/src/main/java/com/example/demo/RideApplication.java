package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.dao.Repository;
import com.example.demo.model.ThemeparkRide;

@SpringBootApplication
public class RideApplication {

	public static void main(String[] args) {
		SpringApplication.run(RideApplication.class, args);
		
	}
	
//	@Bean
//	public CommandLineRunner commandLineRunner(Repository repository) 
//	{
//		return (agrs) -> {
//		repository.save( new ThemeparkRide("Rollercoaster", "Train ride that speeds you along.", 5, 3));
//		repository.save( new ThemeparkRide("Log flume", "Boat ride with plenty of splashes.", 3, 2));
//		repository.save( new ThemeparkRide("Teacups", "Spinning ride in a giant tea-cup.", 2, 4));
//				
//		};
//				
//	}

}
