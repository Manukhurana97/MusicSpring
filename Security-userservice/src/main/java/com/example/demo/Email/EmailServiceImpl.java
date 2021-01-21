package com.example.demo.Email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl  {
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	public void sendmail(String to, String subject , int time, String body) 
	{
		System.out.println("Sending email");
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("test@gmail.com");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		if (time!=0)
			simpleMailMessage.setSentDate(new Date(System.currentTimeMillis() +time));
		simpleMailMessage.setText(body);
		System.out.println(simpleMailMessage);
		
		mailSender.send(simpleMailMessage);
		
	}

}
