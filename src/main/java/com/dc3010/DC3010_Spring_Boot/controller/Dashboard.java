package com.dc3010.DC3010_Spring_Boot.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dc3010.DC3010_Spring_Boot.Service.EmailService;
import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.ResponseWrapper;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

@Controller    
public class Dashboard {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmailService emailService;
	
		/**
		 * Returns the view dashboard.html to the user when /dashboard is called
		 * @param model the spring model used to add attributes to the session
		 * @param userDetails custom implementation of springs securities user details which gets the current logged in users information
		 * @return modelAndView the model attributes and dashboard.html used by spring to generate the webpage 
		 */
		
		@GetMapping("/dashboard")
		protected ModelAndView doGet(Model model, @AuthenticationPrincipal SecUserDetails userDetails){
			
			User user = userService.getUserByLogin(userDetails.getUsername());
			
			model.addAttribute("user", user);
			
			ModelAndView modelAndView = new ModelAndView("dashboard.html");
			
			
			List<Project> allProjects = projectService.findAll();
			
			model.addAttribute("projectRecords", allProjects);
			
			return modelAndView;
		}
		
		@GetMapping("/findOne/{id}")
		protected ResponseEntity<ResponseWrapper> findOneProject(@PathVariable("id") Integer projectId){
			
			
			Project foundProject = projectService.findOne(projectId);
			
			ResponseWrapper wrapper = new ResponseWrapper(foundProject);
			wrapper.setTools();
			
			return new ResponseEntity<ResponseWrapper>(wrapper, HttpStatus.OK);
		}
		
		@GetMapping("/show-interest/{userId}/{projectId}")
		protected ResponseEntity<Void> sendInterestEmail(@PathVariable("userId") Integer resourceManagerId, @PathVariable("projectId") Integer projectId,@AuthenticationPrincipal SecUserDetails userDetails)
		{
			Project projectInterestedIn = projectService.findOne(projectId);
			User resourceManager = userService.getUserById(resourceManagerId);
			User loggedInUser = userService.getUserByLogin(userDetails.getUsername());
			
	
			//Create the message to put in email
			String message = String.format("%s %s is interested in the following role:\n\nClient: %s\nProject: %s\nStart Date: %s\nEnd Date: %s\nGrade Required: %s\n\nPlease reach them via email at: %s",
				    loggedInUser.getFirstName(),
				    loggedInUser.getLastName(),
				    projectInterestedIn.getCompanyName(),
				    projectInterestedIn.getProjectName(),
				    projectInterestedIn.getStartDate(),
				    projectInterestedIn.getEndDate(),
				    projectInterestedIn.getGrade(),
				    loggedInUser.getEmail()
				);
			
			emailService.sendEmail(resourceManager.getEmail(), "Interested in Project role", message);
	
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	
}
