package com.example.demo.ApiCall;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient("/UserService")
public interface PasswordCall {
    @PostMapping("/password/CheckTokenpasswwordencoder")
    @LoadBalanced
    public Map<String, String> getuserfromtoken(@RequestHeader(name="Authentication") String token, String password);
}