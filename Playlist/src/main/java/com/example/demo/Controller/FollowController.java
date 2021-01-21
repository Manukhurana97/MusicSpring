package com.example.demo.Controller;

import com.example.demo.Apicalling.UserCalling;
import com.example.demo.Dao.FollowPlaylistdao;
import com.example.demo.Dao.PlaylistSongsDao;
import com.example.demo.Dao.Playlistdao;
import com.example.demo.Incommingdata.PlaylistIncommingdata;
import com.example.demo.Model.Playlist;
import com.example.demo.Model.PlaylistFollowers;
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
@RequestMapping("/PlaylistFollow")
public class FollowController {

    @Autowired
    public Playlistdao PLdao;

    @Autowired
    public FollowPlaylistdao followersdao;

    @Autowired
    public PlaylistSongsDao PLSdao;

    @Autowired
    public usercalldata usercalldata;

    @PostMapping("/FollowPlaylist")
    public ResponseEntity<PlaylistResponse> followPlaylist(@RequestHeader(name = "Authorization") String token, @RequestBody PlaylistIncommingdata data) {
        PlaylistResponse response = new PlaylistResponse();
        HttpStatus status = HttpStatus.OK;
        Controller controller = new Controller();

        try {
//            user calling
            Map.Entry<String, String> userinfo = usercalldata.usercall(token);

            try {
                System.out.println(data.getPlaylistid());
                Optional<Playlist> checkplst = PLdao.findById(data.getPlaylistid()); // finding playlist by id
                Playlist plst = checkplst.get();
                System.out.println(plst);
//				check if Playlist creater is trying to follow the playlist
                if (plst.getPlaylistcreater().equals(userinfo.getKey())) {
                    response.setStatus(HttpStatus.CONFLICT.value());
                    response.setMgs("You cant follow your own Playlist");
                } else {
//						check if user has already followed
                    PlaylistFollowers follow = followersdao.findByUsernameAndPlaylist(userinfo.getKey(), plst);
                    if (follow == null) {
                        List<PlaylistFollowers> lst = new ArrayList<>();
                        PlaylistFollowers followers = new PlaylistFollowers();
                        followers.setUsername(userinfo.getKey());
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
            }
        } catch (Exception e) {
            response.setMgs(e.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            status = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(response, status);
    }

}
