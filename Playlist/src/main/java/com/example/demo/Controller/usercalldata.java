package com.example.demo.Controller;

import com.example.demo.Apicalling.UserCalling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class usercalldata {

    @Autowired
    public UserCalling calling;

    public Map.Entry<String, String> usercall(String token) {
        System.out.println(token);
        Map<String, String> map = calling.checktokenzuul(token);
        System.out.println(map);
        Map.Entry<String, String> userinfo = map.entrySet().iterator().next();
        System.out.println(userinfo);
        // find playlist and check if user is creater of playlist

        if (userinfo.getKey() == null) {
            throw new SecurityException("Unauthorized user");
        }
        return userinfo;

    }
}
