package com.example.demo.Service;

import com.example.demo.Dao.AuthorityDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Dao.userdetailsDao;
import com.example.demo.Email.EmailServiceImpl;
import com.example.demo.model.Authorities;
import com.example.demo.model.UserDetails;
import com.example.demo.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    public userdetailsDao userdetailsdao;

    @Autowired
    public UserDao userdao;

    @Autowired
    public AuthorityDao authorityDao;

    @Autowired
    public EmailServiceImpl emailServiceImpl;

    @Async
    public CompletableFuture<UserDetails> findUserdetailsbyusername(String username, Integer artistid) {
        UserDetails userDetails = userdetailsdao.findByusers_Username(username);

        userDetails.setArtistId(artistid);
        userDetails.setArtist(true);
        userdetailsdao.saveAndFlush(userDetails);

//    	send email
        String body = "you Artist account has been successfully created, You can now upload your own songs ";
        ;
        emailServiceImpl.sendmail(userDetails.getUsers().getUsername(), "Artist account created successfully", 0, body);

        return CompletableFuture.completedFuture(userDetails);
    }

    @Async
    public CompletableFuture<Users> findUserbyusername(String username) {
        Users users = userdao.findByUsername(username);
        Authorities authorities = authorityDao.findAuthoritiesByUsers(users);
        authorities.setAuthority("ROLE_ARTIST");
        authorityDao.saveAndFlush(authorities);
        return CompletableFuture.completedFuture(users);
    }


}
