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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class Controller {

    @Autowired
    public MusicDao musicdao;

    @Autowired
    public Calling calling;

    @Autowired
    public Followdao followdao;
    
    @Autowired
    public ArtistCalling artistCalling;


    @RequestMapping(value = "/AddSongs", method = RequestMethod.POST)
    public ResponseEntity<Songresponse> addSongs(@RequestHeader (name="Authorization") String token,
    		@Valid  @RequestBody IncommingMusicdata data)
    {
        Songresponse songresponse = new Songresponse();
        HttpStatus status = HttpStatus.CREATED;

        try{
            Music music = new Music();

            music.setSongname(data.getSongname());
            music.setCoverurl(data.getCoverurl());
            music.setFileurl(data.getFileurl());
            music.setLanguage(data.getLanguage());
            Date date=new SimpleDateFormat("dd/MM/yyyy").parse(data.getReleasedata());
            music.setReleasedata(date);
            Music temp =musicdao.saveAndFlush(music);

            List<Artists> lst_art = new ArrayList<>();
            for(Artists i:data.getArtists())
            {
                Artists aa = new Artists();
                aa.setArtistid(i.getArtistid());
                lst_art.add(aa);
            }
//            calling artist 
            ResponseEntity<ArtistApiCallResponse> ar = artistCalling.CheckArtistApiCall(token, lst_art);
            lst_art.clear();
            System.gc();
            List<Artists> receive_lst = Objects.requireNonNull(ar.getBody()).getLst_of_artist();
            for(Artists i:receive_lst)
            {
                Artists aa = new Artists();
                aa.setMusic(temp);
                System.out.println(i.getArtistname());
                aa.setArtistid(i.getArtistid());
                aa.setArtistname(i.getArtistname());
                lst_art.add(aa);
            }
            
            music.setArtists(lst_art);
            musicdao.saveAndFlush(music);
            songresponse.setMgs("Song added Successfully");
            songresponse.setStaus(HttpStatus.CREATED.value());
        }
        catch (Exception e)
        {
            songresponse.setMgs(e.toString());
        }
        return new ResponseEntity<>(songresponse, status);
    }


    @RequestMapping(value = "/LikeSong", method = RequestMethod.POST)
    public ResponseEntity<Songresponse> LikeSongs(@Valid  @RequestHeader(name = "Authorization") String token,
                                                 @RequestBody IncommingMusicdata data)
    {
        Songresponse songresponse = new Songresponse();
        HttpStatus status = HttpStatus.CREATED;

        try{
//            get music
            Music music =musicdao.findMusicByMusicid(data.getMusicid());
//          call user
            Map<String, String> userinfo = calling.checktokenzuul(token);
            Map.Entry<String, String> user = userinfo.entrySet().iterator().next();
//            check if user has already followed
            System.out.println(user.getKey());
            System.out.println(music);
            SongFollowers check = followdao.findByUsernameAndMusic(user.getKey(), music);
            System.out.println(check);
//            if already followed then unfollow
            if(check != null)
            {
                followdao.deleteById(check.Followid);
                songresponse.setMgs("UnFollowed successfully");
                songresponse.setStaus(HttpStatus.CREATED.value());
            }
            else
            {
                List<SongFollowers> ar = new ArrayList<>();

                SongFollowers sf = new SongFollowers();
                sf.setMusic(music);
                sf.setUsername(user.getKey());
                ar.add(sf);

                music.setFollowers(ar);
                musicdao.saveAndFlush(music);
                songresponse.setMgs("Followed successfully");
                songresponse.setStaus(HttpStatus.CREATED.value());
            }

        }
        catch (Exception e)
        {
            songresponse.setMgs(e.toString());
        }
        return new ResponseEntity<>(songresponse, status);
    }
    

    
    @RequestMapping(value = "/checksongs", method = RequestMethod.POST)
    public ResponseEntity<SongInternalcall> checksongs(  @RequestHeader(name = "Authorization") String token,
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
