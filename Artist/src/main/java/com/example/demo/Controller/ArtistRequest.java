package com.example.demo.Controller;

import com.example.demo.ApiCall.Calling;
import com.example.demo.Response.ArtistResponse;
import com.example.demo.dao.ArtistRequestDao;
import com.example.demo.model.Artist_Request;
import com.example.demo.model.Incommingdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Map;

@RequestMapping("/Request-Artist")
@RestController
public class ArtistRequest {

    @Autowired
    ArtistRequestDao artistRequestDao;

    @Autowired
    Calling calling;


    private String UserCall(@RequestHeader(name = "Authorization") String token) throws AuthenticationException {
        Map<String, String> map = calling.checktokenzuul(token);
        Map.Entry<String, String> user_authority = map.entrySet().iterator().next();
        if(!user_authority.getValue().equals("ROLE_USER"))
        {
            throw new AuthenticationException("Permission Denied");
        }

        return user_authority.getKey();
    }

    @PostMapping("/AddArtist")
    public ResponseEntity<ArtistResponse> AddArtist(@RequestHeader(name="Authorization") String token, @RequestBody Incommingdata data) throws AuthenticationException {
        ArtistResponse response = new ArtistResponse();
        HttpStatus status=HttpStatus.CREATED;

//        user calling
        String userid = UserCall(token);

        Artist_Request request = new Artist_Request();
        request.setArtistname(data.getArtistname());  // username
        request.setUserid(userid);

        if(data.getImage() != null)
        {
            request.setImage(data.getImage()); // image
        }
        if(data.getDescription() != null)
        {
            request.setDescription(data.getDescription()); // description
        }

        artistRequestDao.saveAndFlush(request);
        response.setMessage("Request Raise successfully");

        return new ResponseEntity<>(response, status);
    }
}
