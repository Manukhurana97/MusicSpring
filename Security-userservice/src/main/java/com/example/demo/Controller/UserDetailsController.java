package com.example.demo.Controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dao.UserDao;
import com.example.demo.Dao.userdetailsDao;
import com.example.demo.Email.EmailServiceImpl;
import com.example.demo.Response.Response;
import com.example.demo.model.UserDetails;

@RestController
@RequestMapping("/Userdetails")
public class UserDetailsController {
	
	@Autowired
    public UserDao userdao;
    
    @Autowired
    public userdetailsDao userdetailsdao;
    
    @Autowired
    public EmailServiceImpl emailServiceImpl;

    
    @PostMapping("/Createartist")
    public ResponseEntity<Response> CreateArtist(@RequestHeader(name = "Authentication") String token, @RequestBody Map<Integer, String> map) {
    	Response response = new Response();
    	HttpStatus status = HttpStatus.OK;
    	Map.Entry<Integer, String> user = map.entrySet().iterator().next();
    	UserDetails userDetails = userdetailsdao.findByusers_Username(user.getValue());
    	userDetails.setArtistId(user.getKey());
    	userDetails.setArtist(true);
    	System.out.println(userDetails.getArtistId() +" : "+userDetails.getUsers());
    	userdetailsdao.saveAndFlush(userDetails);
    	
//    	send email
		 String body = "you Artist account has been successfully ";
				 	;			
		 emailServiceImpl.sendmail(userDetails.getUsers().getUsername(), "Confirmation of passsword change", 0, body);
    	return new ResponseEntity<>(response ,status);
    }
}
