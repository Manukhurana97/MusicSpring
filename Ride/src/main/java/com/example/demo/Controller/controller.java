package com.example.demo.Controller;




import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.dao.Repository;
import com.example.demo.model.ThemeparkRide;

@RestController
public class controller {
	
	
	@Autowired
	public Repository repository;
	

	@GetMapping(value="/ride")
	public List<ThemeparkRide> getallRides()
	{
		return repository.findAll();
	}
	
	@GetMapping(value="/ride/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ThemeparkRide getRidesbyId(@PathVariable long id)
	{	
		return repository.findById(id).orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Invalid ride id %s", id)));
	}
	
	
	@PostMapping(value="/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ThemeparkRide createride(@Valid @RequestBody ThemeparkRide themeparkRide) 
	{
		System.out.println(themeparkRide.toString());
		return repository.saveAndFlush(themeparkRide);
	}

}
