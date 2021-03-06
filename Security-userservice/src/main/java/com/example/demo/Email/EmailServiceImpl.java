package com.example.demo.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendmail(String to, String subject, int time, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("test@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        if (time != 0)
            simpleMailMessage.setSentDate(new Date(System.currentTimeMillis() + time));
        simpleMailMessage.setText(body);
        System.out.println(simpleMailMessage);

        mailSender.send(simpleMailMessage);

    }

}
