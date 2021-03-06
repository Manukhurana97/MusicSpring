package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activity.InvalidActivityException;
import javax.validation.Valid;

import com.example.demo.Dao.UserDao;
import com.example.demo.Dao.userdetailsDao;
import com.example.demo.Jwt.Util;
import com.example.demo.Request.LoginForm;
import com.example.demo.Request.RegisterForm;
import com.example.demo.Response.RegisterResponse;
import com.example.demo.Thread.SpringAsyncConfig;
import com.example.demo.Validator.EmailValidator;
import com.example.demo.Validator.PasswordValidator;

import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserDetails;
import com.example.demo.Extension.ExtendedUser;
import com.example.demo.model.Users;

import io.jsonwebtoken.Claims;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Account")
@RestController
public class Controller {
	
//	logging
	Logger loging  = LoggerFactory.getLogger("user");

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public JdbcUserDetailsManager detailsManager;

//    @Autowired
//    public ExtendedJdbcUserDetailsManager E_detailsManager;

    @Autowired
    public AuthenticationManager authentication;

    @Autowired
    public  Util util;
    
    @Autowired
    public UserDao userdao;
    
    @Autowired
    public userdetailsDao userdetailsdao;
    
    @Autowired
    public SpringAsyncConfig springAsyncConfig;

    @PostMapping("/Register")
    public ResponseEntity<RegisterResponse> Register(@Valid @RequestBody RegisterForm form) {
    	RegisterResponse response = new RegisterResponse();
    	HttpStatus status = HttpStatus.OK;
    	UserDetails u_details  = new UserDetails();
    	try {
	        List<GrantedAuthority> authority = new ArrayList<>();
	        authority.add(new SimpleGrantedAuthority("ROLE_USER"));
//	        password format validation
	        
	        if(EmailValidator.isValid(form.getUsername())) {

                if (PasswordValidator.isValid(form.getPassword())) {
                    String encodedPassword = passwordEncoder.encode(form.getPassword());
                    //User detail = new User(form.getUsername(), encodedPassword, true,true, true, true, authority);
                    ExtendedUser detail = new ExtendedUser(form.getUsername(), encodedPassword, true, true, true, true, authority);
                    detailsManager.createUser(detail);
                    
//                    user details
                    u_details.setUserCountry(form.country);
                    u_details.setUsers(userdao.findByUsername(form.getUsername()));
                    userdetailsdao.saveAndFlush(u_details);
                    
                    response.setMessage("User created successfully");
                }
		        else {
	                status = HttpStatus.BAD_REQUEST;
		        	response.setMessage("password format is invalid, It must have one Capital letter, small letters and one special char");
		        }
	        }
	        else 
	        {
	        	status = HttpStatus.BAD_REQUEST;
	        	response.setMessage("Email format is invalid, it must follow stand email format");
	        }
    	}
    	catch(Exception e) {
            status = HttpStatus.CONFLICT;
    		response.setMessage("email associated with different account");
    	}

    	 return new ResponseEntity<RegisterResponse>(response ,status);
    }
    
    

    @PostMapping("/AuthorityRegister")
    public ResponseEntity<RegisterResponse> AuthorityRegister(@Valid @RequestBody RegisterForm form) {
    	RegisterResponse response = new RegisterResponse();
    	HttpStatus status = HttpStatus.OK;
    	UserDetails u_details  = new UserDetails();
    	try {
	        List<GrantedAuthority> authority = new ArrayList<>();
	        authority.add(new SimpleGrantedAuthority(form.getRole()));
	        
//	        password format validation

                if (PasswordValidator.isValid(form.getPassword())) {
                    String encodedPassword = passwordEncoder.encode(form.getPassword());
                    //User detail = new User(form.getUsername(), encodedPassword, true,true, true, true, authority);
                    ExtendedUser detail = new ExtendedUser(form.getUsername(), encodedPassword, true, true, true, true, authority);
                    detailsManager.createUser(detail);
                    
//                    user details
                    u_details.setUserCountry(form.country);
                    u_details.setUsers(userdao.findByUsername(form.getUsername()));
                    userdetailsdao.saveAndFlush(u_details);
                    
                    response.setMessage("User created successfully");
                }
	        else {
                status = HttpStatus.BAD_REQUEST;
	        	response.setMessage("password format is invalid");
	        }
    	}
    	catch(Exception e) {
            status = HttpStatus.CONFLICT;
    		response.setMessage("email associated with different account");
    	}

    	 return new ResponseEntity<RegisterResponse>(response ,status);
    }
    
    

    
    @PostMapping("/Login")
    public ResponseEntity<RegisterResponse> Login(@RequestBody LoginForm form) {
    	RegisterResponse response = new RegisterResponse();
    	HttpStatus status = HttpStatus.OK;
    	
        Authentication details = null;
        String token = null;
        try {
        	 if(EmailValidator.isValid(form.getUsername())) {
	            details = authentication.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));
	            token = util.create_Token(details);
	            
	            response.setToken(token);
	            response.setStatus(HttpStatus.OK.value());
	            response.setUsername(details.getName());
	            response.setMessage("Login successfully");
        	 }
        	 else
        	 {
        		 throw new InvalidActivityException("Invalid email format");
        	 }
        } catch (Exception e) {
            status = HttpStatus.UNAUTHORIZED;
            response.setMessage(e.getMessage());
        }
        
   	 return new ResponseEntity<RegisterResponse>(response ,status);

    }

    
    @PostMapping("/CheckToken")
    public Map<String, String> getuserfromtoken(@RequestHeader(name="Authentication") String token) {
    	Map<String, String> map = new HashMap<>();
    	Claims claims;
        try {
        	
            claims = util.checkToken(token);
         
            Users user = userdao.findByUsername(claims.get("SESS_USERNAME").toString());
            map.put(user.getUsername().toString(), user.getAuthorities().getAuthority().toString());
           
         } catch (Exception e) {
        	 System.out.println(e.toString());
        	map.put("error", e.toString());
        }
        return map;
    }
    
    @PostMapping("/CheckTokenWeb")
    public ResponseEntity<RegisterResponse> getuserfromtokenforweb(@RequestHeader(name="Authentication") String token) throws AuthenticationException {
    	RegisterResponse response = new RegisterResponse();
    	HttpStatus status = HttpStatus.OK;
    	
    	Claims claims;
        try {
        	
            claims = util.checkToken(token);
         
            Users user = userdao.findByUsername(claims.get("SESS_USERNAME").toString());
            response.setMessage("true");
       	 	response.setToken(token);
       	 	response.setStatus(HttpStatus.OK.value());
       	 	response.setUsername(user.getUsername());
           
         } catch (Exception e) {
        	 response.setStatus(HttpStatus.UNAUTHORIZED.value());
        	 throw new AuthenticationException("Invalid token");
        }
        return new ResponseEntity<RegisterResponse>(response ,status);
    }

    

    

}
