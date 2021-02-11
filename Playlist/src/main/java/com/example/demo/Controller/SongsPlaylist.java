package com.example.demo.Controller;

import com.example.demo.Apicalling.SongsCalling;
import com.example.demo.Apicalling.UserCalling;

import com.example.demo.Dao.PlaylistSongsDao;
import com.example.demo.Dao.Playlistdao;
import com.example.demo.Incommingdata.PlaylistSongsIncommingdata;
import com.example.demo.Incommingdata.SongInternalcall;
import com.example.demo.Model.Playlist;

import com.example.demo.Model.PlaylistSongs;
import com.example.demo.Response.PlaylistResponse;
import com.example.demo.Service.Playlistservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/PlaylistSongs")
public class SongsPlaylist {

    @Autowired
    public Playlistdao PLdao;

    @Autowired
    public PlaylistSongsDao PLSdao;

    @Autowired
    public SongsCalling songcalling;

    @Autowired
    public UserCalling usercalling;

    @Autowired
    public usercalldata usercalldata;
    
    @Autowired
    public Playlistservice playlistservice;

    @PostMapping("/addSongs")
    public ResponseEntity<PlaylistResponse> addSongtoPlaylist(@RequestHeader(name = "Authentication") String token, @RequestBody PlaylistSongsIncommingdata data) {
        PlaylistResponse response = new PlaylistResponse();
        HttpStatus status = HttpStatus.OK;
        try {
//        	Calling user
            Map.Entry<String, String> user_authority = playlistservice.usercall(token);

//           Find Playlist
            Optional<Playlist> checkplst = PLdao.findById(data.getPlaylistid()); // checking playlist is valid
            Playlist plst = checkplst.orElse(null);

//            Check if user is the creater of playlist
            assert plst != null;
            if (!user_authority.getKey().equals(plst.getPlaylistcreater())) {
                throw new SecurityException("Unauthorized user");
            }

            try {
//              Api calling and checking songs id
                SongInternalcall song_info = playlistservice.song_calling_Service(token, data.getSongid());

                if (song_info.getSongid() == 0)
                    throw new NoSuchElementException("Invalid Song");


                PlaylistSongs songs = new PlaylistSongs();
                songs.setSongid(song_info.getSongid()); // adding song id

                    // checking if song is already preset in playlist id;
                try {
                    PlaylistSongs pls = PLSdao.findByPlaylistAndSongid(plst, song_info.getSongid());
                    if (pls==null)
                    {
                        songs.setPlaylist(plst); // adding playlist id
                        List<PlaylistSongs> lst = new ArrayList<>();
                        lst.add(songs); // adding song to list


                        plst.setPlaylistSongs(lst);  // adding Song list to playlist

                        PLdao.saveAndFlush(plst); // playlist

                        response.setMgs("Added Successfully");
                    }
                    else {
                        response.setMgs("Song already present in playlist");
                        response.setStatus(HttpStatus.CONFLICT.value());
                        status = HttpStatus.CONFLICT;
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage().toString());
                    songs.setPlaylist(plst); // adding playlist id
                    List<PlaylistSongs> lst = new ArrayList<>();
                    lst.add(songs); // adding song to list

                    Playlist pl = new Playlist();
                    pl.setPlaylistSongs(lst);  // adding Song list to playlist
                    System.out.println(pl.getPlaylistSongs());
                    PLdao.saveAndFlush(pl); // playlist

                    response.setMgs("Added Successfully");
                }

            } catch (Exception e) {
                response.setMgs(e.getMessage().toString());
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            response.setMgs(e.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }


        return new ResponseEntity<>(response, status);
    }


    @DeleteMapping("/RemoveSongs")
    public ResponseEntity<PlaylistResponse> RemoveSongtoPlaylist(@RequestHeader(name = "Authentication") String token,
                                                                 @RequestBody PlaylistSongsIncommingdata data) {
        PlaylistResponse response = new PlaylistResponse();
        HttpStatus status = HttpStatus.OK;
        try {
            Map.Entry<String, String> user_authority = playlistservice.usercall(token);
            Optional<Playlist> checkplst = PLdao.findById(data.getPlaylistid()); // checking playlist is valid

            Playlist plst = checkplst.get();
            if (!user_authority.getKey().equals(plst.getPlaylistcreater()))
                throw new SecurityException("Unauthorized user");
            try {
                    PlaylistSongs dlt = PLSdao.findByPlaylistAndSongid(plst, data.getSongid());
                    PLSdao.deleteById(dlt.getId());
                    response.setMgs("Removed Successfully");

            } catch (Exception e) {
                response.setMgs(e.toString());
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            response.setMgs("You cant perform this operations");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(response, status);
    }


    @PostMapping("/Songs")
    public ResponseEntity<PlaylistResponse> PlaylistSongs(@RequestHeader(name = "Authentication") String token,
                                                                 @RequestBody PlaylistSongsIncommingdata data) {
        PlaylistResponse response = new PlaylistResponse();
        HttpStatus status = HttpStatus.OK;
        try {
            Map.Entry<String, String> user_authority = playlistservice.usercall(token);
            Optional<Playlist> checkplst = PLdao.findById(data.getPlaylistid()); // checking playlist is valid
            Playlist plst = checkplst.orElse(null);
            try {
            	
                if (checkplst.isPresent()) {
                    List<PlaylistSongs> lstsongs = PLSdao.findPlaylistSongsByPlaylist_Playlistid(plst.getPlaylistid());
                    
                    response.setLstsongs(lstsongs);
                }
            } catch (Exception e) {
                response.setMgs(e.toString());
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            response.setMgs("You can't perform this operations");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(response, status);
    }
}
