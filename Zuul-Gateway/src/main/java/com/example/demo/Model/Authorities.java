package com.example.demo.Model;

import javax.persistence.*;

@Entity
public class Authorities {
	
	@Id
	public int id;

	public String authority;
	
	@OneToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="username", nullable = false)
	public Users users;


	public int getId() {
		return id;
	}

	public String getAuthority() {
		return authority;
	}

	public Users getUsers() {
		return users;
	}	

}
