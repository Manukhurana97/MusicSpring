package com.example.demo.Validator;


import org.springframework.scheduling.annotation.Async;

public class EmailValidator {
	
	
	static String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	public static boolean isValid(final String email) {
        return email.matches(regex);
    }
	

}
