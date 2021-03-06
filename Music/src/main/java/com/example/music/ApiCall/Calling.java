package com.example.music.ApiCall;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;


@FeignClient("UserService")
public interface Calling {

    @PostMapping("/Account/CheckToken")
    Map<String, String> checktokenzuul(@RequestHeader(name = "Authentication") String token);
}
