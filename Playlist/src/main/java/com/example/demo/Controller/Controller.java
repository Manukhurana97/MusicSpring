package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


import com.example.demo.Dao.FollowPlaylistdao;
import com.example.demo.Dao.PlaylistSongsDao;

import com.example.demo.Incommingdata.PlaylistIncommingdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Apicalling.UserCalling;
import com.example.demo.Dao.Playlistdao;
import com.example.demo.Model.Playlist;

import com.example.demo.Response.PlaylistResponse;
import com.example.demo.Service.Playlistservice;

@RestController
@RequestMapping("/Playlist")
public class Controller {

	@Autowired
	public Playlistdao PLdao;

	@Autowired
	public FollowPlaylistdao followersdao;


	@Autowired
	public PlaylistSongsDao PLSdao;

	@Autowired
	public usercalldata usercalldata;
	
	@Autowired
	public Playlistservice playlistservice;

	@PostMapping("/CreatePlaylist")
	public ResponseEntity<PlaylistResponse> createPlaylist(@RequestHeader(name="Authentication") String token, @RequestBody PlaylistIncommingdata data){
		PlaylistResponse response = new PlaylistResponse();
		HttpStatus status = HttpStatus.CREATED;
		try
		{

			Map.Entry<String, String> user_authority = playlistservice.usercall(token);

			System.out.println(user_authority.getKey());
			Playlist plst = new Playlist();
			plst.setPlaylistcreater(user_authority.getKey());

//			playlist name
			plst.setPlaylistname(data.getPlaylistname());

//			check if no playlist url is given use default url for playlist;
			if (data.getPlaylistCoverUrl()!=null)
			{
				plst.setPlaylistCoverUrl(data.getPlaylistCoverUrl());
			}

//			check if description is empty
			if(data.getPlaylistdescription()!=null)
			{
				plst.setPlaylistdescription(data.getPlaylistdescription());
			}
//			Add data
			LocalDate localdate = LocalDate.now();
			plst.setCreateon(localdate);

//			public private
			plst.setPublicprivate(false);

			PLdao.saveAndFlush(plst);
			response.setMgs("added Successfully");

		}
		catch(Exception e)
		{
			response.setMgs(e.getMessage().toString());
			status = HttpStatus.NOT_FOUND;
		}

		return new ResponseEntity<>(response, status);
	}
	
	@PostMapping("/usersPlaylist")
	public ResponseEntity<PlaylistResponse> usersPlaylist(@RequestHeader(name="Authentication") String token) {
		PlaylistResponse response = new PlaylistResponse();
		HttpStatus status = HttpStatus.CREATED;
		try {
			//		api calling
			Map.Entry<String, String> user_authority = playlistservice.usercall(token);
			System.out.println(user_authority);
			try {

				List<Playlist> plst = PLdao.findAllByPlaylistcreater( user_authority.getKey());
				System.out.println(plst);
				response.setLstplst(plst);

			} catch (Exception e) {
				response.setMgs(e.getMessage().toString());
				status = HttpStatus.NOT_FOUND;
			}
		}
		catch (Exception e) {
			response.setMgs(e.toString());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<>(response, status);
	}


	@PostMapping("/PublicprivatePlaylist")
	public ResponseEntity<PlaylistResponse> publicPlaylist(@RequestHeader(name="Authentication") String token, @RequestBody PlaylistIncommingdata data) {
		PlaylistResponse response = new PlaylistResponse();
		HttpStatus status = HttpStatus.CREATED;
		try {
			//		api calling

			Map.Entry<String, String> user_authority = playlistservice.usercall(token);
			System.out.println(user_authority);
			try {

				Playlist plst = PLdao.findByPlaylistidAndPlaylistcreater(data.playlistid, user_authority.getKey());
//			updating to public
				if (!plst.getPublicprivate()) {
					plst.setPublicprivate(true);
					response.setMgs(" public Playlist ");
				} else {
					plst.setPublicprivate(false);
					response.setMgs(" private Playlist ");
				}
				PLdao.saveAndFlush(plst);

			} catch (Exception e) {
				response.setMgs(e.toString());
			}
		}
		catch (Exception e) {
			response.setMgs(e.toString());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<>(response, status);
	}



	@DeleteMapping("/DeletePlaylist")
	public ResponseEntity<PlaylistResponse> DeletePlaylist(@RequestHeader(name="Authentication") String token, @RequestBody PlaylistIncommingdata data) {
		PlaylistResponse response = new PlaylistResponse();
		HttpStatus status = HttpStatus.CREATED;

		try {
			//	api calling
			Map.Entry<String, String> user_authority = playlistservice.usercall(token);

			try {
				Playlist plst = PLdao.findByPlaylistidAndPlaylistcreater(data.getPlaylistid(), user_authority.getKey());
				if(!plst.getPlaylistcreater().equals(user_authority.getKey()))
				{
					throw new SecurityException("You can't perform this operations");
				}
			//	delete playlist
				PLdao.deleteById(plst.playlistid);
				response.setMgs("Playlist deleted successfully");
			} catch (Exception e) {
				status = HttpStatus.UNAUTHORIZED;
				response.setMgs(e.toString());
			}
		}
		catch (Exception e) {
			response.setMgs("You can't perform this operations");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			status = HttpStatus.UNAUTHORIZED;
		}

		return new ResponseEntity<>(response, status);
	}


}
