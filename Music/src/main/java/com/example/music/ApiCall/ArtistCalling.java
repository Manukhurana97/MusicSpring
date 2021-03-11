package com.example.music.ApiCall;

import com.example.music.Model.Artists;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@FeignClient("Artist")
public interface ArtistCalling {

	@PostMapping("/Artist/checkArtistsApiCall")
    @LoadBalanced
	public ResponseEntity<com.example.music.ApiCall.incomming.ArtistApiCallResponse> CheckArtistApiCall(@RequestHeader (name="Authentication") String token, @RequestBody List<Artists> artistList);

}
