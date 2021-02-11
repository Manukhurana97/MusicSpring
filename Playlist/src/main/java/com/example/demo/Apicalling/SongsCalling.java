package com.example.demo.Apicalling;

import com.example.demo.Incommingdata.SongInternalcall;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("Music")
public interface SongsCalling {

	@PostMapping("/checksongs")
	ResponseEntity<SongInternalcall> checksongs(@RequestHeader(name = "Authentication") String token,
													   @RequestHeader(name="songid") int songid);
}
