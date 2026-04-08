package com.incapp.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.incapp.entity.Citizen;
import com.incapp.entity.Department;
import com.incapp.entity.Officer;
import com.incapp.repo.CitizenRepository;
import com.incapp.service.CitizenService;
import com.incapp.service.DepartmentService;
import com.incapp.service.OfficerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
	@Value("${admin.name}")
    private String adminName;

	@Autowired
	private CitizenService citizenService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private OfficerService officerService;

	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
    @GetMapping("/civic_login")
    public String civicLogin() {
        return "civic_login";
    }

    @GetMapping("/citizen/complaint-submit")
    public String citizenComplaintSubmit(HttpSession session, Principal principal) {
        
        String email = principal.getName(); // logged-in email
        
        Citizen citizen = citizenService.getByEmail(email);
        session.setAttribute("name", citizen.getName());
        session.setAttribute("email", email);
        return "citizen/complaint-submit";
    }
    @GetMapping("/citizen/home")
    public String citizenHome(HttpSession session, Principal principal) {
        
        String email = principal.getName(); // logged-in email
        
        Citizen citizen = citizenService.getByEmail(email);
        session.setAttribute("name", citizen.getName());
        session.setAttribute("email", email);
        return "citizen/home";
    }

    @GetMapping("/officer/home")
    public String officerHome(HttpSession session, Principal principal) {
        
        String email = principal.getName(); // logged-in email
        
        Officer officer = officerService.getByEmail(email);
        session.setAttribute("name", officer.getName());
        session.setAttribute("email", email);
        return "officer/home";
    }

    @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Principal principal) {
        
        String email = principal.getName(); // logged-in email
        session.setAttribute("name", adminName);
        session.setAttribute("email", email);
        return "admin/home";
    }
	@GetMapping("/admin/officers")
	public String adminOfficers(Model model) {
	    model.addAttribute("officer", new Officer());
	    model.addAttribute("departments", departmentService.getAll());
	    model.addAttribute("officers", officerService.getAll());
		return "admin/officers";
	}
	@GetMapping("/admin/departments")
	public String adminDepartments(Model model) {
	    model.addAttribute("department", new Department());
	    model.addAttribute("departments", departmentService.getAll());
		return "admin/departments";
	}
}