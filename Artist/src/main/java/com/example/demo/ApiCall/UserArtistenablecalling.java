package com.example.demo.ApiCall;

import com.example.demo.ApiCall.IncommingResponse.Response;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;


@FeignClient("UserService")
public interface UserArtistenablecalling {

    @PostMapping("/Userdetails/Createartist")
    @LoadBalanced
    public  ResponseEntity<Response> CreateArtist(@RequestHeader(name = "Authentication") String token, Map<Integer, String> map) ;
}
