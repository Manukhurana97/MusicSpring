package com.example.demo.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang.IncompleteArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.UserDao;
import com.example.demo.Dao.passwordresetDao;
import com.example.demo.Email.EmailServiceImpl;
import com.example.demo.Jwt.Util;
import com.example.demo.Request.RegisterForm;
import com.example.demo.Response.RegisterResponse;
import com.example.demo.Validator.EmailValidator;
import com.example.demo.Validator.PasswordValidator;
import com.example.demo.model.PasswordResetToken;
import com.example.demo.model.UserDetails;
import com.example.demo.model.Users;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/password")
@RestController
public class PasswordController {
	
//	logging
	Logger loging  = LoggerFactory.getLogger("password");
	
	 @Autowired
	 public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public JdbcUserDetailsManager detailsManager;
    
    @Autowired
    public UserDao dao;
    
    @Autowired
    public passwordresetDao passworddao;
    
    @Autowired
    public  Util util;
    
    @Autowired
    public EmailServiceImpl emailServiceImpl;
    
    @Autowired
    public com.example.demo.Service.UserService UserService;
	
    
    @PostMapping("/change-password")
	 public ResponseEntity<RegisterResponse> changepassword(@RequestHeader(value = "Authentication") String token,@RequestBody RegisterForm form) 
	 {
		 RegisterResponse response = new RegisterResponse();
	     HttpStatus status = HttpStatus.CREATED;
//	     password format validation
	     try {
	    	 
	     if (PasswordValidator.isValid(form.getPassword())) {
		     String newencodedPassword = passwordEncoder.encode(form.getPassword());
		     Claims claims = util.checkToken(token) ;
		    
	         Users user = dao.findByUsername(claims.get("SESS_USERNAME").toString());
	         
//	         password similarity
		     if(passwordEncoder.matches(form.getPassword(), user.getPassword())) {
		    	status = HttpStatus.CONFLICT;
		     	throw new IllegalArgumentException("Old and New Password can't be same");
		     }
		     else {
		    	 user.setPassword(newencodedPassword);
		    	 dao.saveAndFlush(user);
		    	 String body = "This email is to informed you that your password has been changed successfully";
	    		 emailServiceImpl.sendmail(form.getUsername(), "Password Reset", 1000*30, body);
		    	 response.setMessage(" Password change successfully");
		     }
	     }
	     }
	     catch(Exception e) 
	     {
	    	 response.setMessage(e.getMessage());
	     }
		 return new ResponseEntity<>(response ,status);
	 }
    
    
     @PostMapping("/forgot-password")
	 public ResponseEntity<RegisterResponse> forgotpassword(@RequestBody RegisterForm form)
	 {
		 RegisterResponse response = new RegisterResponse();
	     HttpStatus status = HttpStatus.OK;
//	     password format validation
	     System.out.println("email"+ form.getUsername());
	     if(form.getUsername().isEmpty() || form.getUsername().equals("")) {
	    	 response.setMessage("email cant be blank");
	     }
	     else 
	     {
	    	 try {
	    		
//	    		 Users user = dao.findByUsername(form.getUsername());
	    		 CompletableFuture<Users> CF_userDetails = UserService.findUserbyusername(form.getUsername());
	    		 Users user = CF_userDetails.get();
	    		 if (user.getUsername()==null) 
	    		 {
	    			 throw new UsernameNotFoundException("invalid Email");
	    		 }
	    		 else {
		    		 String token = UUID.randomUUID().toString();
		    		 PasswordResetToken pass = new PasswordResetToken();
		    		 pass.setUser(user);
		    		 pass.setToken(token);
		    		 pass.setIs_used(false);
		    		 pass.setExpiryDate(new Date(System.currentTimeMillis() +1000*60*31));
		    		 passworddao.saveAndFlush(pass);
	//	    		 send email
		    		 String body = "This mail is in response to a request to recover a forgotten password"+
								"\nhttp://localhost:4200/reset-password/"+token+
								"\n This link is valid for 30 minutes"+
								"\n Please do not reply on this mail";
		    		 emailServiceImpl.sendmail(form.getUsername(), "Password Reset", 1000*30, body);
		    		 System.out.println("Send successfully");
		    		 response.setMessage("Mail send successfully");
	    		 }
	    	 }
	    		

	    	 catch(Exception e) 
	    	 {
	    		 status = HttpStatus.NOT_FOUND;
	    		 response.setMessage("Invalid Email"); 
	    	 }
	     }
		 return new ResponseEntity<>(response ,status);
	 }

     
     
     @PostMapping("/reset-password/{resettoken}")
	 public ResponseEntity<RegisterResponse> resetpasswordtokenverify(@PathVariable(name="resettoken") String resettoken) 
	 {
		 RegisterResponse response = new RegisterResponse();
	     HttpStatus status = HttpStatus.OK;
//	     password format validation
	     

	    	 try {
	    		
	    		 PasswordResetToken token = passworddao.findByToken(resettoken);
	    		 
	    		 if (!token.getExpiryDate().after(new Date(System.currentTimeMillis()))  )
	    		 {
	    			 throw new UsernameNotFoundException("Link is invalid");
	    		 }
	    		 else if (token.getIs_used()) {
	    			 throw new UsernameNotFoundException("Link already used");
				}
	    		 else {
	    			 response.setMessage("token is valid ");
		    		 
	    		 }
	    	 }
	    	 catch(Exception e) 
	    	 {
	    		 
	    		 status = HttpStatus.UNPROCESSABLE_ENTITY;
	    		 response.setMessage(e.getMessage());
	    	 }

		 return new ResponseEntity<>(response ,status);
	 }
     
     
     @PostMapping("/reset-password-confirm/{resettoken}")
	 public ResponseEntity<RegisterResponse> resetpasswordyconfirm(@PathVariable(name="resettoken") String resettoken, @RequestBody RegisterForm form) 
	 {
    	 
		 RegisterResponse response = new RegisterResponse();
	     HttpStatus status = HttpStatus.OK;
	     Date date = new Date();
//	     password format validation
	     
	     if(resettoken.isEmpty()) {
	    	 response.setMessage("password token can't be blank");
	     }
	     else 
	     {
	    	 try {
	    		
	    		 PasswordResetToken tokendetails = passworddao.findByToken(resettoken);
	    		 if (!tokendetails.getExpiryDate().after(new Date(System.currentTimeMillis())))
	    		 {
	    			 throw new UsernameNotFoundException("Link is expired");
	    		 }
	    		 else if ( tokendetails.getIs_used()) {
	    			 throw new UsernameNotFoundException("Link is already used");
				}
	    		 else {
	    			 if (PasswordValidator.isValid(form.getPassword())) {
	    				 String newencodedPassword = passwordEncoder.encode(form.getPassword()); // encode password
		    			 Users user = dao.findByUsername(tokendetails.getUser().getUsername()); // find the user
		    			 user.setPassword(newencodedPassword); // set updated password
				    	 dao.saveAndFlush(user);  // save updated password
				    	 
				    	 tokendetails.setIs_used(true);  // test token to used
				    	 passworddao.saveAndFlush(tokendetails); // change password status 

			    		 
//			    		 send email
			    		 String body = "Your password for the " +user.getUsername()+"  has been changed"+
			    				 	"\nwe are sending you this fot your protection"+
			    				 	"\nDate of password change "+date.getDate();
			    				 	;			
			    		 emailServiceImpl.sendmail(form.getUsername(), "Confirmation of passsword change", 0, body);
			    		 response.setMessage("Password change successsfully");
	    			 }
	    			 else 
	    			 {
	    				 response.setMessage("Invalid password format");
	    			 }
	    		 }
	    	 }
	    	 catch(Exception e) 
	    	 {
	    		 status = HttpStatus.NOT_FOUND;
	    		 response.setMessage(e.getMessage());
	    	 }
	     }
		 return new ResponseEntity<>(response ,status);
	 }
     
     
     
     @PostMapping("/CheckTokenpasswordencoder")
     public Map<String, String> getuserfromtoken(@RequestHeader(name="Authentication") String token, @RequestBody String password) {
     	Map<String, String> map = new HashMap<>();
     	Claims claims;
         try {
             claims = util.checkToken(token) ;
        
             Users user = dao.findByUsername(claims.get("SESS_USERNAME").toString()); // user
             String newencodedPassword = passwordEncoder.encode(password); // encoded password
             if(passwordEncoder.matches(password, user.getPassword())) 
             {
            	 throw new IllegalArgumentException(" pasword can't be same  ");
             }
             map.put(user.getUsername(), newencodedPassword);  
             return map ;
          } catch (Exception e) {
         	 map.put(e.toString(), e.toString());
             return map;
         }
        
     }

}
