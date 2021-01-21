package com.example.demo.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.mapping.OneToMany;

public class labelteam {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int labelid;

	public String labelname;
	
}
