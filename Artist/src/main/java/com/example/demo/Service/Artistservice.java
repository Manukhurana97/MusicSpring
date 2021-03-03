package com.example.demo.Service;

import com.example.demo.ApiCall.UserArtistenablecalling;
import com.example.demo.dao.ArtistRequestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class Artistservice {

    @Autowired
    UserArtistenablecalling callingUserArtistenable;

    @Autowired
    ArtistRequestDao artistRequestDao;

    @Async
    public void callingUserArtistenable(String token, Integer Artistid, String Userid)
    {
        try {
            Map<Integer, String> map = new HashMap<>();
            map.put(Artistid, Userid);
            callingUserArtistenable.CreateArtist(token, map);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    @Async
    public void deleterequest(int requestid)
    {
        try {
            artistRequestDao.deleteById(requestid);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
