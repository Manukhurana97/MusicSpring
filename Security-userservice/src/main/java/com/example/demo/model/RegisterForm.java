package com.example.demo.model;

public class RegisterForm {
	
	public String Username;
    public String password;
    public String role;
    public String country;

    public RegisterForm() {

    }

    public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public RegisterForm(String username, String password, String role) {
		super();
		Username = username;
		this.password = password;
		this.role = role;
	}
    
	
    
	
      
    

}
