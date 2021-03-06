package com.example.demo.Controller;


import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.UserDao;
import com.example.demo.Dao.userdetailsDao;
import com.example.demo.Response.Response;
import com.example.demo.model.UserDetails;
import com.example.demo.model.Users;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Userdetails")
public class UserDetailsController {
	
//	logging
	Logger loging  = LoggerFactory.getLogger("details");
	
	@Autowired
    public UserDao userdao;
    
    @Autowired
    public userdetailsDao userdetailsdao;
    
   

    @Autowired
    public com.example.demo.Service.UserService UserService;
    
    @Autowired
    public JdbcUserDetailsManager detailsManager;
    
    
    
    @PostMapping("/Createartist")
    public ResponseEntity<Response> CreateArtist(@RequestHeader(name = "Authentication") String token, @RequestBody Map<Integer, String> map) {
    	Response response = new Response();
    	HttpStatus status = HttpStatus.OK;
    	Map.Entry<Integer, String> user = map.entrySet().iterator().next();
    	
//    	UserDetails userDetails = userdetailsdao.findByusers_Username(user.getValue());
    	CompletableFuture<UserDetails> CF_userDetails = UserService.findUserdetailsbyusername(user.getValue(), user.getKey());
    	CompletableFuture<Users>  CF_user = UserService.findUserbyusername(user.getValue()); 
    	UserDetails userDetails = null;
    	Users users = null;
		try {
			userDetails = CF_userDetails.get();
			users = CF_user.get();
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    	
    	
		return new ResponseEntity<>(response ,status);
    }
}
