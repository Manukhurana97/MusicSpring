package com.example.music.Controller;


import com.example.music.ApiCall.ArtistCalling;
import com.example.music.ApiCall.Calling;
import com.example.music.ApiCall.incomming.ArtistApiCallResponse;
import com.example.music.Dao.Followdao;
import com.example.music.Dao.MusicDao;
import com.example.music.Model.Artists;
import com.example.music.Model.IncommingMusicdata;
import com.example.music.Model.Music;
import com.example.music.Model.SongFollowers;
import com.example.music.Response.SongInternalcall;
import com.example.music.Response.Songresponse;
import com.example.music.Service.Song_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class Controller {
	
	public static final Logger loging  = LoggerFactory.getLogger(Controller.class); 

    @Autowired
    public MusicDao musicdao;

    @Autowired
    public Calling calling;

    @Autowired
    public Song_Service songService;

    @Autowired
    public Followdao followdao;
    
    @Autowired
    public ArtistCalling artistCalling;


    @RequestMapping(value = "/AddSongs", method = RequestMethod.POST)
    public ResponseEntity<Songresponse> addSongs(@RequestHeader (name="Authentication") String token,
    		@Valid  @RequestBody IncommingMusicdata data)
    {
        Songresponse songresponse = new Songresponse();
        HttpStatus status = HttpStatus.CREATED;
        
        try {
    //      User call
        	Map.Entry<String, String> user = songService.UserCall(token);
        	try {
	            Music music = new Music();
	
	            music.setSongname(data.getSongname());
	            music.setCoverurl(data.getCoverurl());
	            music.setFileurl(data.getFileurl());
	            music.setLanguage(data.getLanguage());
	            Date date=new SimpleDateFormat("dd/MM/yyyy").parse(data.getReleasedata());
	            music.setReleasedata(date);
	            music.setRequestor(user.getKey());
	            Music temp =musicdao.saveAndFlush(music);
	
	            List<Artists> lst_art = new ArrayList<>();
	            for(Artists i:data.getArtists()) {
	                Artists aa = new Artists();
	                aa.setArtistid(i.getArtistid());
	                lst_art.add(aa);
	            }

	//            calling artist 
	            ResponseEntity<ArtistApiCallResponse> ar = songService.artistcalling(token, lst_art);
	            lst_art.clear();
	            
	            List<Artists> receive_lst = Objects.requireNonNull(ar.getBody()).getLst_of_artist();
	            for(Artists i:receive_lst) {
	                Artists aa = new Artists();
	                aa.setMusic(temp);
	                aa.setArtistid(i.getArtistid());
	                aa.setArtistname(i.getArtistname());
	                lst_art.add(aa);
	            }
	            music.setArtists(lst_art);
	            musicdao.saveAndFlush(music);
	            songresponse.setMgs("Song added Successfully");
	            songresponse.setStaus(HttpStatus.CREATED.value());
	        }
	        catch (Exception e) {
	            songresponse.setMgs(e.getMessage());
	        }
        }
        catch (Exception e) {
            songresponse.setMgs(e.getMessage());
        }
        return new ResponseEntity<>(songresponse, status);
    }


    @RequestMapping(value = "/LikeSong", method = RequestMethod.POST)
    public ResponseEntity<Songresponse> LikeSongs(@Valid  @RequestHeader(name = "Authentication") String token,
                                                 @RequestBody IncommingMusicdata data)
    {
        Songresponse songresponse = new Songresponse();
        HttpStatus status = HttpStatus.CREATED;

        try{
//            get music
            Music music =musicdao.findMusicByMusicid(data.getMusicid());
//          call user
            Map.Entry<String, String> user = songService.UserCall(token);
            
//            check if user has already followed
            SongFollowers check = followdao.findByUsernameAndMusic(user.getKey(), music);
            System.out.println(check);
//            if already followed then unfollow
            if(check != null) {
                followdao.deleteById(check.Followid);
                songresponse.setMgs("UnFollowed successfully");
            }
            else {
                List<SongFollowers> ar = new ArrayList<>();

                SongFollowers sf = new SongFollowers();
                sf.setMusic(music);
                sf.setUsername(user.getKey());
                ar.add(sf);

                music.setFollowers(ar);
                musicdao.saveAndFlush(music);
                songresponse.setMgs("Followed successfully");
            }
            songresponse.setStaus(HttpStatus.CREATED.value());
        }
        catch (Exception e) {
            songresponse.setMgs(e.getMessage());
        }
        return new ResponseEntity<>(songresponse, status);
    }
    

    @RequestMapping(value = "/DeleteSong", method = RequestMethod.DELETE)
    public ResponseEntity<Songresponse> DeleteSongs(@RequestHeader (name="Authentication") String token,
    		@Valid  @RequestBody IncommingMusicdata data)
    {
        Songresponse songresponse = new Songresponse();
        HttpStatus status = HttpStatus.CREATED;
        
        try {
//        	call user
            Map<String, String> userinfo = calling.checktokenzuul(token);
            Map.Entry<String, String> user = userinfo.entrySet().iterator().next();
	        try{
	        	Music music = musicdao.findMusicByMusicid(data.getMusicid());
	        	System.out.println(music.getRequestor()+" "+user.getKey()+" "+user.getValue());
	        	if (user.getValue().equals("ROLE_STAFF") || user.getValue().equals("ROLE_ADMIN")) {
	        		
	        		musicdao.deleteById(music.getMusicid());
	        		songresponse.setMgs(music.getSongname()+" deleted successfully");
	        	}
	        	else if (user.getValue().equals(music.getRequestor())) {
	        		musicdao.deleteById(music.getMusicid());
	        		songresponse.setMgs(music.getSongname()+" deleted successfully");
				}
	        	else {
	        		songresponse.setMgs(" You don't have the permission's");
	        	}
	        }
	        catch (Exception e) {
	            songresponse.setMgs(e.getMessage());
	        }
        }
        catch (Exception e) {
            songresponse.setMgs(e.getMessage());
        }
        return new ResponseEntity<>(songresponse, status);
    }

    
    @RequestMapping(value = "/checksongs", method = RequestMethod.POST)
    public ResponseEntity<SongInternalcall> checksongs(  @RequestHeader(name = "Authentication") String token,
                                                 @RequestHeader(name="songid") int songid)
    {
    	SongInternalcall songresponse = new SongInternalcall();
        HttpStatus status = HttpStatus.OK;

        try{
//            get music
            Music music=musicdao.findMusicByMusicid(songid);
            songresponse.setSongid(music.getMusicid());
        }
        catch (Exception e)
        {
        	System.out.println(e.toString());
        }
        return new ResponseEntity<>(songresponse, status);
    }
}
