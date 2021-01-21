package com.example.demo.model;

public class LoginForm {
	
	public String Username;
	public String password;

    public LoginForm() {

    }


    public String getUsername() {
		return Username;
	}
	public void setUsername(String Username) {
		this.Username = Username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginForm(String Username, String password) {
		super();
		this.Username = Username;
		this.password = password;
	}
	
	
	
	
	

}
