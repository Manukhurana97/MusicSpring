package com.example.demo.Apicalling;

import com.example.demo.Incommingdata.SongInternalcall;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("Music")
public interface SongsCalling {

	@PostMapping("/checksongs")
	@LoadBalanced
	public ResponseEntity<SongInternalcall> checksongs(@RequestHeader(name = "Authorization") String token,
													   @RequestHeader(name="songid") int songid);
}
