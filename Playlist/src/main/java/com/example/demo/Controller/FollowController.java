package com.example.demo.Controller;

import com.example.demo.Apicalling.UserCalling;
import com.example.demo.Dao.FollowPlaylistdao;
import com.example.demo.Dao.PlaylistSongsDao;
import com.example.demo.Dao.Playlistdao;
import com.example.demo.Incommingdata.PlaylistIncommingdata;
import com.example.demo.Model.Playlist;
import com.example.demo.Model.PlaylistFollowers;
import com.example.demo.Response.PlaylistResponse;
import com.example.demo.Service.Playlistservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/PlaylistFollow")
public class FollowController {

//	logging
	Logger loging  = LoggerFactory.getLogger("followercontroller");
    @Autowired
    public Playlistdao PLdao;

    @Autowired
    public FollowPlaylistdao followersdao;

    @Autowired
    public PlaylistSongsDao PLSdao;

    @Autowired
    public Playlistservice playlistservice;

    @PostMapping("/FollowPlaylist")
    public ResponseEntity<PlaylistResponse> followPlaylist(@RequestHeader(name = "Authentication") String token, @RequestBody PlaylistIncommingdata data) {
        PlaylistResponse response = new PlaylistResponse();
        HttpStatus status = HttpStatus.OK;
        Controller controller = new Controller();

        try {
//            user calling
            Map.Entry<String, String> user_authority = playlistservice.usercall(token);

            try {
                System.out.println(data.getPlaylistid());
                Optional<Playlist> checkplst = PLdao.findById(data.getPlaylistid()); // finding playlist by id

                Playlist plst = checkplst.get();
                System.out.println(plst);
//				check if Playlist creater is trying to follow the playlist
                if (plst.getPlaylistcreater().equals(user_authority.getKey())) {
                    response.setStatus(HttpStatus.CONFLICT.value());
                    response.setMgs("You cant follow your own Playlist");
                } else {
//						check if user has already followed
                    PlaylistFollowers follow = followersdao.findByUsernameAndPlaylist(user_authority.getKey(), plst);
                    if (follow == null) {
                        List<PlaylistFollowers> lst = new ArrayList<>();
                        PlaylistFollowers followers = new PlaylistFollowers();
                        followers.setUsername(user_authority.getKey());
                        followers.setPlaylist(plst);
                        lst.add(followers);
                        plst.setFollowers(lst);
                        PLdao.saveAndFlush(plst);
                        response.setMgs("Followed successfully");
                    } else {
                        followersdao.deleteById(follow.getId());
                        response.setMgs("UnFollowed successfully");
                    }
                }

            } catch (Exception e) {
                response.setMgs(e.toString());
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            response.setMgs(e.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(response, status);
    }

}
