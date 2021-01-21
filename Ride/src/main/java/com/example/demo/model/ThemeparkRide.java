package com.example.demo.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotEmpty;


import javax.persistence.Id;

@Entity
public class ThemeparkRide {
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long id;
	  @NotEmpty
	  private String name;
	  @NotEmpty
	  private String description;
	  private int thrillFactor;
	  private int vomitFactor;
	  
	  
	  
	public ThemeparkRide() {
		super();
	}


	public ThemeparkRide(String name, String description, int thrillFactor, int vomitFactor) {
		super();
		this.name = name;
		this.description = description;
		this.thrillFactor = thrillFactor;
		this.vomitFactor = vomitFactor;
	}


	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getThrillFactor() {
		return thrillFactor;
	}


	public void setThrillFactor(int thrillFactor) {
		this.thrillFactor = thrillFactor;
	}


	public int getVomitFactor() {
		return vomitFactor;
	}


	public void setVomitFactor(int vomitFactor) {
		this.vomitFactor = vomitFactor;
	}


	@Override
	public String toString() {
		return "ThemeparkRide [id=" + id + ", name=" + name + ", description=" + description + ", thrillFactor="
				+ thrillFactor + ", vomitFactor=" + vomitFactor + "]";
	}
}
	

