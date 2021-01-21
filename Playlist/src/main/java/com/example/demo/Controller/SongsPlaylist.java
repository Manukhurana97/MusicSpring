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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/addSongs")
    public ResponseEntity<PlaylistResponse> addSongtoPlaylist(@RequestHeader(name = "Authorization") String token, @RequestBody PlaylistSongsIncommingdata data) {
        PlaylistResponse response = new PlaylistResponse();
        HttpStatus status = HttpStatus.OK;
        try {

            Map.Entry<String, String> userinfo = usercalldata.usercall(token);
            Optional<Playlist> checkplst = PLdao.findById(data.getPlaylistid()); // checking playlist is valid
            Playlist plst = checkplst.get();

            if (!userinfo.getKey().equals(plst.getPlaylistcreater())) {
                throw new SecurityException("Unauthorized user");
            }
            try {

//          Api calling and checking songs id
                ResponseEntity<SongInternalcall> out = songcalling.checksongs(token, data.getSongid());

                PlaylistSongs songs = new PlaylistSongs();

                songs.setSongid(out.getBody().getSongid()); // adding song id

                if (checkplst.isPresent()) {
                    // checking if song is already preset in playlist id;
                    PlaylistSongs pls = PLSdao.findByPlaylistAndSongid(plst, out.getBody().getSongid());
                    if (pls == null) {
                        songs.setPlaylist(plst); // adding playlist id
                        List<PlaylistSongs> lst = new ArrayList<>();
                        lst.add(songs); // adding song to list

                        Playlist pl = new Playlist();
                        pl.setPlaylistSongs(lst);  // adding Song list to playlist
                        PLdao.saveAndFlush(pl); // playlist

                        response.setMgs("Added Successfully");
                    } else {
                        response.setMgs("Song already present in playlist");
                        response.setStatus(HttpStatus.CONFLICT.value());
                        status = HttpStatus.CONFLICT;
                    }

                }

            } catch (Exception e) {
                response.setMgs(e.toString());
            }
        } catch (Exception e) {
            response.setMgs("You cant perform this operations");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }


        return new ResponseEntity<>(response, status);
    }


    @DeleteMapping("/RemoveSongs")
    public ResponseEntity<PlaylistResponse> RemoveSongtoPlaylist(@RequestHeader(name = "Authorization") String token,
                                                                 @RequestBody PlaylistSongsIncommingdata data) {
        PlaylistResponse response = new PlaylistResponse();
        HttpStatus status = HttpStatus.OK;
        try {

            Map.Entry<String, String> userinfo = usercalldata.usercall(token);
            Optional<Playlist> checkplst = PLdao.findById(data.getPlaylistid()); // checking playlist is valid
            Playlist plst = checkplst.get();
            if (!userinfo.getKey().equals(plst.getPlaylistcreater())) {
                throw new SecurityException("Unauthorized user");
            }

            try {

                if (checkplst.isPresent()) {
                    PlaylistSongs dlt = PLSdao.findByPlaylistAndSongid(plst, data.getSongid());
                    PLSdao.deleteById(dlt.getId());
                    response.setMgs("Removed Successfully");
                }
            } catch (Exception e) {
                response.setMgs(e.toString());
            }
        } catch (Exception e) {
            response.setMgs("You cant perform this operations");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(response, status);
    }
}
