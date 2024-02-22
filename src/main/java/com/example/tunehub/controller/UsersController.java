package com.example.tunehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tunehub.entities.Users;
import com.example.tunehub.services.UsersService;

import jakarta.servlet.http.HttpSession;


@Controller
public class UsersController 
{
	@Autowired
	UsersService userv;
	
	
	@PostMapping("/register")
	public String addUser(@ModelAttribute Users user) {
		
		boolean userstatus = userv.emailExits(user.getEmail());
		if(userstatus==false) {
			userv.addUser(user);
			return "registersuccess";
		}else {
			return "registerfail";
		}
		
	}
	@PostMapping("/login")
	public String validateUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
		boolean loginstatus = userv.validateUser(email, password);
		if(loginstatus == true) {
			
			session.setAttribute("email", email);
			if(userv.getRole(email).equals("admin")) {
				return "adminhome";
			}else {
				return "customerhome";
			}
			
		}else {
			return "loginfail.html";
		}
		
	}
	
	@GetMapping("/exploresongs")
	public String exploreSongs(HttpSession session) {
		String email = (String) session.getAttribute("email");
		Users user = userv.getUser(email);
		
		boolean userStatus = user.isPremium();
		if(userStatus == true) {
			return "PermiumCustomer";
		}else {
			return "payment";
		}	
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return "login";
	}

}
