package com.incapp.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.incapp.entity.Citizen;
import com.incapp.entity.Role;
import com.incapp.entity.User;
import com.incapp.jwt.JwtUtil;
import com.incapp.repo.CitizenRepository;
import com.incapp.repo.UserRepository;

@Service
public class CitizenService {
	@Autowired
	private CitizenRepository citizenRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private MailSevice mailSevice;
	@Autowired
	private JwtUtil jwtUtil;
	
	public boolean save(Citizen citizen, String password) {
		User u=userRepo.findByEmail(citizen.getEmail());
		if(u==null) {
			User user = new User();
	        user.setEmail(citizen.getEmail());
			BCryptPasswordEncoder b=new BCryptPasswordEncoder();
			user.setPassword(b.encode(password));
	        user.setProvider("LOCAL");
	        user.setRoles(Set.of(Role.ROLE_USER));
			
			userRepo.save(user);
			citizenRepo.save(citizen);
			return true;
		}
		else {
			return false;
		}
	}

	public Citizen getByEmail(String email) {
		return citizenRepo.findById(email).orElse(null);
	}

	public boolean resetPassword(String email) {
		User u=userRepo.findByEmail(email);
		if(u==null) 
			return false;
		else {
			String token = jwtUtil.generateToken(email);
			String resetLink = "http://localhost:9090/reset_password?token=" + token;
			String subject="Password Reset Link";
			String message="<div style='background-color:yellow;padding:20px;'>"
					+ "<p> Your password reset link:  <b>"+resetLink+"</b>.</p>"
					+ "</div>";
			mailSevice.htmlMail(email, subject, message);
			return true;
		}
	}

	public String validateToken(String token) {
		return jwtUtil.validateToken(token);
	}

	public boolean updatePassword(String email, String password) {
		User u=userRepo.findByEmail(email);
		if(u==null) 
			return false;
		else {
			BCryptPasswordEncoder b=new BCryptPasswordEncoder();
			u.setPassword(b.encode(password));
			userRepo.save(u);
			return true;
		}
	}
	
	
	
}
