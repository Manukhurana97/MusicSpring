package com.example.demo.Apicalling;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient("UserService")
public interface UserCalling {

    @PostMapping("/Account/CheckToken")

    Map<String, String> checktokenzuul(@RequestHeader(name = "Authentication") String token);
}
