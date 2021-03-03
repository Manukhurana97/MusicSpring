package com.example.demo.ApiCall;

import java.util.Map;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient("UserService")
public interface Calling {

    @PostMapping("/Account/CheckToken")
    @LoadBalanced
    Map<String, String> checktokenzuul(@RequestHeader(name = "Authentication") String token);
}
