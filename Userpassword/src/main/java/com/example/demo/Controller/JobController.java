package com.example.demo.Controller;

import com.example.demo.Dao.hibernate.passwordresetimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/job")
@RestController
public class JobController {

    @Autowired
    passwordresetimpl passwordimpl;

    @PostMapping("/clearpasswordtoken")
    @Scheduled(cron = "* */5 * * * * ")
    public void deletepasswordtoken()
    {
        System.out.println("Clean batch start in few seconds");
        passwordimpl.deleteAllByExpiredtoken();
        System.out.println("completed");
    }

}