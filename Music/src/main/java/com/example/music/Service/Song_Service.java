package com.example.music.Service;

import com.example.music.ApiCall.ArtistCalling;
import com.example.music.ApiCall.Calling;
import com.example.music.ApiCall.incomming.ArtistApiCallResponse;
import com.example.music.Model.Artists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class Song_Service {

    @Autowired
    public Calling calling;

    @Autowired
    public ArtistCalling artistCalling;

    @Async
    public CompletableFuture<Map<String, String>> user_Call(String token) {
        return  CompletableFuture.supplyAsync(() -> calling.checktokenzuul(token));
    }

    @Async
    public CompletableFuture<ResponseEntity<ArtistApiCallResponse>> artist_info(String token, List<Artists> lst_art)
    {
        return CompletableFuture.supplyAsync(() -> artistCalling.CheckArtistApiCall(token, lst_art));
    }

    public Map.Entry<String, String> UserCall(@RequestHeader(name = "Authentication") String token) throws AuthenticationException, ExecutionException, InterruptedException {
        CompletableFuture<Map<String, String>> c_f = user_Call(token);
        Map<String, String> map = c_f.get();
        Map.Entry<String, String> user_authority = map.entrySet().iterator().next();
        if (user_authority.getValue().equals("ROLE_USER")) {
            throw new AuthenticationException("Permission Denied, The requester us not authorized to perform this operations");
        }
        return user_authority;
    }

    public ResponseEntity<ArtistApiCallResponse> artistcalling(String token, List<Artists> lst_art) throws ExecutionException, InterruptedException {
        CompletableFuture<ResponseEntity<ArtistApiCallResponse>> c_f = artist_info(token, lst_art);
        return c_f.get();
    }

}
