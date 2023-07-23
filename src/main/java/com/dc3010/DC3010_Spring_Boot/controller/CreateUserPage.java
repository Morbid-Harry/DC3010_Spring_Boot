package com.dc3010.DC3010_Spring_Boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.PasswordUtils;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;


@Controller  
public class CreateUserPage {
		
	@Autowired
	private UserService userService;

	@GetMapping("/create")
	protected ModelAndView doGet(Model model, @AuthenticationPrincipal SecUserDetails userDetails){
				
		ModelAndView modelAndView = new ModelAndView("createuser.html");
				
		return modelAndView;
	}
	
	@PostMapping("/register")
	protected ModelAndView doPost(Model model, @RequestParam("register-email") String email, @RequestParam("register-first-name") String firstName, @RequestParam("register-last-name") String lastName, @RequestParam("register-user-name") String username,  @RequestParam("register-password") String password, @RequestParam(value = "roles", required = false) String[] selectedOptions, @RequestParam("register-user-grade") String grade)
	{
		//To encode given password
		PasswordUtils util = new PasswordUtils();
		
		//Create new user object with details given
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setLogin(username);
		newUser.setPassword(util.encode(password));
		newUser.setGrade(grade);
		
		//Add user role based on checkboxes 
		if(selectedOptions != null)
		{
			//if admin is selected
			if(selectedOptions[0].equalsIgnoreCase("Admin"))
			{
				newUser.setRole("ROLE_USER,ROLE_RM,ROLE_ADMIN");
			}
			else
			{
				newUser.setRole("ROLE_USER,ROLE_RM");
			}

			
		}
		else
		{
			newUser.setRole("ROLE_USER");
		}
		
		ModelAndView modelAndView = new ModelAndView("createuser.html");
		
		//Check if user does not exist
		if(userService.getUserByEmail(email) == null && userService.getUserByLogin(username) == null)
		{
			userService.addUser(newUser);
			model.addAttribute("success", "User created!");
			return modelAndView;
		}
		
		
		model.addAttribute("error", "User already exists");
		return modelAndView;
	}
	
}
