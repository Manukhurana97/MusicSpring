package com.example.demo.Validator;


import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.debatty.java.stringsimilarity.Cosine;


public class PasswordValidator {
	
	private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    
//    passsword similarity
//    public static float passwordsimilarity(String old_password, String new_password) {
//	    Cosine cosine = new Cosine(2);
//	    System.out.println(cosine);
//	    Map<String, Integer> pass1 = cosine.getProfile(old_password);
//	    Map<String, Integer> pass2 = cosine.getProfile(new_password);
	    
//	    return (float) cosine.similarity(old_password, new_password);
//    }

}
