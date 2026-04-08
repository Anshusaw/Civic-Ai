package com.incapp.service;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.incapp.entity.Officer;
import com.incapp.entity.Role;
import com.incapp.entity.User;
import com.incapp.repo.OfficerRepository;
import com.incapp.repo.UserRepository;

@Service
public class OfficerService {
	@Autowired
	private OfficerRepository officerRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailSevice mailSevice;

	
	public boolean save(Officer officer) {
		Officer o=officerRepository.findByEmail(officer.getEmail());
		if(o==null) {
			User user = new User();
	        user.setEmail(officer.getEmail());
	        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ@abcdefghijklmnopqrstuvwxyz#0123456789";
	        StringBuilder pwd = new StringBuilder();
	        Random rnd = new Random();
	        for (int i = 0; i < 5; i++)
	            pwd.append(chars.charAt(rnd.nextInt(chars.length())));

			BCryptPasswordEncoder b=new BCryptPasswordEncoder();
			user.setPassword(b.encode(pwd));
	        user.setProvider("LOCAL");
	        user.setRoles(Set.of(Role.ROLE_OFFICER));
			
	        userRepository.save(user);
			officerRepository.save(officer);
			System.out.println(pwd);
			String subject="Congrats! Registered Successfully.";
			String message="<div style='background-color:yellow;padding:20px;'>"
					+ "<h2>Hello <b>"+officer.getName()+"</b>, </h2>"
					+ "<p> You have been registered with CivicAi Application.</p>"
					+ "<p> Your password is <b>"+pwd+"</b>.</p>"
					+ "</div>";
//			mailSevice.simpleMail(officer.getEmail(), subject, message);
			mailSevice.htmlMail(officer.getEmail(), subject, message);
			return true;
		}
		else {
			return false;
		}
	}
	public Officer getByEmail(String email) {
		return officerRepository.findByEmail(email);
	}
	public List<Officer> getAll() {
//		return officerRepository.findAll();
		return officerRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
}
