package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Set;


@Entity
public class Users {
	
	@Id
	@Email
	public String username;
	
	@JsonIgnore
	public String password;
	
	@Column(columnDefinition = "boolean default true")
	public boolean enabled;
	
	@Column(columnDefinition = "boolean default true")
	public boolean accountNonExpired;
	
	@Column(columnDefinition = "boolean default true")
	public boolean accountNonLocked;



	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnable() {
		return enabled;
	}




	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}




	
}
