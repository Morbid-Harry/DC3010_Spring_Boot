package com.dc3010.DC3010_Spring_Boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

@Controller
public class FavouritesPage {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ToolService toolService;
	
	@GetMapping("/favourites")
	protected ModelAndView doGet(Model model, @AuthenticationPrincipal SecUserDetails userDetails){
		
		User user = userService.getUserByLogin(userDetails.getUsername());
		
		model.addAttribute("user", user);
		
		ModelAndView modelAndView = new ModelAndView("favourites.html");
		
		List<Project> favouritedProjects = user.getFavourtiedProjects();
		
		//Favourited projects to display to the user
		model.addAttribute("projectRecords", favouritedProjects);
		
		//All the tools that are potentially used by a project used for the tools filter
		model.addAttribute("allTools", toolService.findAll());
		
		return modelAndView;
	}
	
	
	@PostMapping("/favourite/remove/{projectId}")
	protected ResponseEntity<Void> removefavouriteProject(@PathVariable("projectId") Integer projectId, @AuthenticationPrincipal SecUserDetails userDetails)
	{
		//Get the user
		User loggedInUser = userService.getUserByLogin(userDetails.getUsername());
		
		Project projectToRemove = projectService.findOne(projectId);
		
		projectToRemove.getFavourtiedBy().remove(loggedInUser);
		loggedInUser.getFavourtiedProjects().remove(projectToRemove);

		userService.addUser(loggedInUser);
		projectService.addProject(projectToRemove);
	
		
		return new ResponseEntity<Void>(HttpStatus.OK);		
	}
	
	
}
