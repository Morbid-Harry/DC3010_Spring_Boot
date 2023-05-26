package com.dc3010.DC3010_Spring_Boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.PasswordUtils;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;


/**
 * Spring boot implementation class login
 */
@RequestMapping("/login")
@Controller
@SessionAttributes({"user", "userPreferences"})
public class Login{
	@Autowired
	UserService userService;

	@ModelAttribute("user")
	public User setSession() {
		return new User();
	}
	
	
	@GetMapping
	String login()
	{		
		return "mainPage.html";	
	}

}
