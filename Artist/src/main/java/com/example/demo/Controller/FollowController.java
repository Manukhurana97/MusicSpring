package com.example.demo.Controller;


import com.example.demo.ApiCall.Calling;

import com.example.demo.Response.ArtistResponse;
import com.example.demo.dao.ArtistDao;
import com.example.demo.dao.ArtistFollowDao;
import com.example.demo.dao.hibernate.ArtistQueryImpl;
import com.example.demo.model.Artist;
import com.example.demo.model.Followers;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RequestMapping("/Follow")
@RestController
public class FollowController {

    @Autowired
    public ArtistDao dao;

    @Autowired
    public ArtistFollowDao followDao;

    @Autowired
    Calling calling;
    
    @Autowired
    ArtistQueryImpl artistqueryimpl;


    @PostMapping("/FollowArtist/{artistid}")
     public ResponseEntity<ArtistResponse> AddArtist(@RequestHeader(name="Authorization") String token, @PathVariable(value = "artistid") int artistid)
    {
        ArtistResponse response = new ArtistResponse();
        HttpStatus status=HttpStatus.OK;


//       Api calling
        Map<String, String> map = calling.checktokenzuul(token);
        Map.Entry<String, String> user = map.entrySet().iterator().next();
//       find the artist
        Artist artist = dao.findArtistByArtistid(artistid);
        try {
                if (artist == null)
                {
                    throw new IllegalArgumentException();
                }
            try {
                Followers check_exists = followDao.findByUsernameAndAndArtist_artistid(user.getKey(), artist.getArtistid());
                if (check_exists == null) {
//                    follow
                    List<Followers> lst_follower = new ArrayList<>();
                    Followers followers = new Followers();

                    Artist artist1 = new Artist();
                    followers.setUsername(user.getKey());
                    followers.setArtist(artist);
                    lst_follower.add(followers);
                    artist.setFollowing(lst_follower);
                    dao.saveAndFlush(artist);
                    response.setMessage("follow successfully");
                }
                else {
//                unfollow
                    System.out.println(check_exists.getId());
//                    followDao.deleteByArtist_ArtistidAndAndUsername(artistid, user.getKey());
                    followDao.deleteById(check_exists.getId());
                    artistqueryimpl.deleteByFollower(check_exists.getId());
                    response.setMessage("Unfollow successfully");
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        catch (Exception e)
        {
            response.setMessage("No artist found");
        }

        return new ResponseEntity<>(response, status);
    }
}
