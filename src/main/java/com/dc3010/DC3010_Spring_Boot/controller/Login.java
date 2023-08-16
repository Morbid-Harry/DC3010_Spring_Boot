package com.dc3010.DC3010_Spring_Boot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;



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
	
	/**
	 * 
	 * @return the main page is displayed when the /login endpoint is requested 
	 */
	@GetMapping
	String login()
	{		
		return "mainPage.html";	
	}

}
