package com.dc3010.DC3010_Spring_Boot.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Servlet implementation class signOutServlet
 */
@RequestMapping("/signOut")
@Controller
public class signOut{
       
	@GetMapping
	protected ModelAndView doGet(Model model){
		//Ends the session and destroys all session attributes
		model.asMap().clear();
		
		return new ModelAndView("mainPage.html");
	}
}
