package com.incapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailSevice {
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean simpleMail(String email,String subject, String message) {
		try {
			SimpleMailMessage mailMessage=new SimpleMailMessage();
			mailMessage.setTo(email);
			mailMessage.setSubject(subject);
			mailMessage.setText(message);
		
			mailSender.send(mailMessage);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	public boolean htmlMail(String email,String subject, String message) {
		try {
			MimeMessage mailMessage=mailSender.createMimeMessage();
			boolean multiPart=true;
			MimeMessageHelper helper=new MimeMessageHelper(mailMessage,multiPart);
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText("text/html",message);
		
			mailSender.send(mailMessage);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
}
