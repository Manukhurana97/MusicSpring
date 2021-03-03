package com.example.demo.Controller;

import com.example.demo.ApiCall.UserArtistenablecalling;
import com.example.demo.Service.Artistservice;
import com.example.demo.dao.ArtistRequestDao;
import com.example.demo.model.Artist_Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.ApiCall.Calling;
import com.example.demo.Response.ArtistApiCallResponse;
import com.example.demo.Response.ArtistResponse;
import com.example.demo.Response.ArtistResponseRequest;
import com.example.demo.dao.ArtistDao;
import com.example.demo.dao.hibernate.ArtistQueryImpl;
import com.example.demo.model.Artist;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.naming.AuthenticationException;

@RequestMapping("/Artist")
@RestController
public class Controller {
	
	@Autowired
	public ArtistDao dao;
	
	@Autowired
	ArtistQueryImpl impl;
	
	 @Autowired
	 Calling calling;

	 @Autowired
	 UserArtistenablecalling callingUserArtistenable;

	@Autowired
	ArtistRequestDao artistRequestDao;

	@Autowired
	Artistservice artistservice;

	
	@PostMapping("/AddArtist/{Requestid}")
	 public ResponseEntity<ArtistResponse> AddArtist(@RequestHeader (name="Authentication") String token, @PathVariable int Requestid)
	 {
		 ArtistResponse response = new ArtistResponse();
		 HttpStatus status=HttpStatus.CREATED;
		 try {
//	         Api calling
			 UserCall(token);

//			 request data
			 Artist_Request data = artistRequestDao.findByArtistrequestid(Requestid);
			 
			 System.out.println(dao.existsDistinctByArtistemail(data.getUserid()));
			 if(!dao.existsDistinctByArtistemail(data.getUserid()))
			 {
			 
				 if(data.getArtistname() != null)
				 {
					 Artist artist = new Artist();
					 artist.setArtistname(data.getArtistname());  // username
					 artist.setArtistemail(data.getUserid());
					 
					 if(data.getImage() != null) 
					 {
						 artist.setImage(data.getImage()); // image
					 } 
					 if(data.getDescription() != null) 
					 {
						 artist.setDescription(data.getDescription()); // description
					 }
					 
					 artist = dao.saveAndFlush(artist);
	
	//				 user call set artist = true
					 artistservice.callingUserArtistenable(token, artist.getArtistid(), data.getUserId());
	
	//				 delete from request
					 artistservice.deleterequest(data.getArtistrequestid());
	
					 response.setMessage("Saved Successfully");
				 }
				 else 
				 {
					 response.setMessage("Name can't be null");
					 status = HttpStatus.BAD_REQUEST;
				 }
			 }
			 else 
			 {
//				 delete from request
				 artistRequestDao.deleteById(data.getArtistrequestid());
				 response.setMessage("Your account is already an artist account");
				 status = HttpStatus.BAD_REQUEST;
			 }
			 
		 }
		 catch(Exception e)
		 {
		 	status = HttpStatus.UNAUTHORIZED;
			 response.setMessage(e.toString());
			 
		 }
		 return new ResponseEntity<>(response, status);
	 }

	private void UserCall(@RequestHeader(name = "Authentication") String token) throws AuthenticationException {
		Map<String, String> map = calling.checktokenzuul(token);
		Map.Entry<String, String> user_authority = map.entrySet().iterator().next();
		System.out.println(user_authority.getValue());
		if(user_authority.getValue().equals("ROLE_USER") || user_authority.getValue().equals("ROLE_ARTIST"))
		{
			throw new AuthenticationException("Permission Denied");
		}
	}

	
	@GetMapping("/AllArtistRequest")
	public ResponseEntity<ArtistResponseRequest> AllArtistRequest(@RequestHeader (name="Authentication") String token)
	{
		ArtistResponseRequest response = new ArtistResponseRequest();
		HttpStatus status=HttpStatus.OK;
		try{
//			Api calling
			UserCall(token);

			 List<Artist_Request> lst = artistRequestDao.findAll();
			response.setAllRequest(lst);
		}
		catch(Exception e)
		{
			status = HttpStatus.UNAUTHORIZED;
			response.setMessage(e.toString());
		}

		return new ResponseEntity<>(response, status);
	}

	@GetMapping("/AllArtist")
	public ResponseEntity<ArtistResponse> AllArtist(@RequestHeader (name="Authentication") String token)
	{
		ArtistResponse response = new ArtistResponse();
		HttpStatus status=HttpStatus.OK;
		try{
//			Api calling
			UserCall(token);

			List<Artist> lst = dao.findAll();
			response.setList(lst);
		}
		catch(Exception e)
		{
			status = HttpStatus.UNAUTHORIZED;
			response.setMessage(e.toString());
		}

		return new ResponseEntity<>(response, status);
	}
	

	
	@PostMapping("/checkArtistsApiCall")
	@Cacheable(value="Artistlist")
	public ResponseEntity<ArtistApiCallResponse> CheckArtistApiCall(@RequestHeader (name="Authentication") String token, @RequestBody List<Artist> artistList)
	{
		ArtistApiCallResponse response = new ArtistApiCallResponse();
		HttpStatus status=HttpStatus.OK;
		try {
			
			List<Artist> ret_Art_list = impl.findAllArtistsWithCriteriaQuery(artistList);
			response.setLst_of_artist(ret_Art_list);
		}
		catch(Exception e) 
		{
			System.out.println(e.toString());}
		
		return new ResponseEntity<>(response, status);
	}


}
