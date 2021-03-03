package com.example.demo.Model;

import javax.persistence.*;

@Entity
public class Users {
	
	@Id
	public String username;
	
	public String password;
	
	public boolean enabled;
	
	public boolean accountNonExpired;
	
	public boolean accountNonLocked;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "users")
	private Authorities authorities;



	public String getUsername() {
		return username;
	}

	
	public String getPassword() {
		return password;
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


	public Authorities getAuthorities() {
		return authorities;
	}	

	
}
