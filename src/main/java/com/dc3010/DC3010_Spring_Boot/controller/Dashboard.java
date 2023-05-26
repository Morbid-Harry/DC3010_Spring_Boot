package com.dc3010.DC3010_Spring_Boot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

@RequestMapping("/dashboard")
@Controller    
public class Dashboard {
		
	@Autowired
	private UserService service;

		@GetMapping
		protected ModelAndView doGet(Model model, @AuthenticationPrincipal SecUserDetails userDetails){
			
			User user = service.getUserByLogin(userDetails.getUsername());
			
			model.addAttribute("user", user);
			
			ModelAndView modelAndView = new ModelAndView("dashboard.html");
			
			return modelAndView;
		}
	
}
