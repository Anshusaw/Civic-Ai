package com.incapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.incapp.entity.Citizen;
import com.incapp.entity.Department;
import com.incapp.entity.Officer;
import com.incapp.service.AIResponse;
import com.incapp.service.AIService;
import com.incapp.service.CitizenService;
import com.incapp.service.DepartmentService;
import com.incapp.service.OfficerService;


@Controller
public class CivicAiController {

	@Autowired
	private CitizenService citizenService;
	@Autowired
	private OfficerService officerService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private AIService aiService;


	@GetMapping("/")
	public String home() {
		return "index";
	}
	@GetMapping("/citizen_register")
	public String citizenRegister() {
		return "citizen_register";
	}
    
	@PostMapping("/register")
	public String register(@ModelAttribute Citizen citizen,@RequestParam String fname,@RequestParam String lname,@RequestParam String password,RedirectAttributes ra) {
		citizen.setName(fname+" "+lname);
		if(citizenService.save(citizen,password)) {
			ra.addFlashAttribute("success", "Registration successful");
			return "redirect:/civic_login";
		}else {
			ra.addFlashAttribute("msg", "Citizen Already Exist!");
			return "redirect:/citizen_register";
		}
	} 
	@PostMapping("/admin/officers/save")
	public String saveOfficer(@ModelAttribute Officer officer,RedirectAttributes ra) {
		if(officerService.save(officer)) {
			ra.addFlashAttribute("success", "Registration successful");
			return "redirect:/admin/officers";
		}else {
			ra.addFlashAttribute("error", "Officer Already Exist!");
			return "redirect:/admin/officers";
		}
	}  
	@PostMapping("/admin/departments/save")
	public String saveDepartment(@ModelAttribute Department department,RedirectAttributes ra) {
		if(departmentService.save(department.getName())) {
			ra.addFlashAttribute("success", "Added successful");
		}else {
			ra.addFlashAttribute("error", "Department Already Exist!");
		}
		return "redirect:/admin/departments";
	} 
	@GetMapping("/reset_password")
    public String resetPassword(@RequestParam String token, RedirectAttributes ra) {
    		String email=citizenService.validateToken(token);
		if(email==null) {
			ra.addFlashAttribute("error", "Invalid or Expired token.");
			return "redirect:/civic_login";
		}
        return "reset_password";
    }
	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam String email,RedirectAttributes ra) {
		if(citizenService.resetPassword(email)) {
			ra.addFlashAttribute("success", "Reset Link Mail Sent Success");
		}else {
			ra.addFlashAttribute("error", "Email ID Not Registered!");
		}
		return "redirect:/civic_login";
	} 
	@PostMapping("/updatePassword")
	public String updatePassword(@RequestParam String password,@RequestParam String token, RedirectAttributes ra) {
		String email=citizenService.validateToken(token);
		if(email==null) {
			ra.addFlashAttribute("error", "Invalid or Expired token.");
			return "redirect:/civic_login";
		}
		if(citizenService.updatePassword(email, password)) {
			ra.addFlashAttribute("success","Password updated!");
		}else {
			ra.addFlashAttribute("error","User Not Found!");
		}
		return "redirect:/civic_login";
	} 
	@PostMapping("/citizen/complaints/ai_analysis")
	public String complaintsAnalysis(@RequestParam String description,RedirectAttributes ra) {
		AIResponse aiResponse= aiService.classifyComplaint(description);
		System.out.println(aiResponse);
		ra.addFlashAttribute("aiResponse",aiResponse);
		return "redirect:/citizen/complaint-submit";
	} 
	
}
