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

/**
 * Controller which handles HTTP response and requests for the Favourites page
 * @author Harry
 *
 */
@Controller
public class FavouritesPage {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ToolService toolService;
	
	/**
	 * When a user clicks to go to the favourites page this function is called and will return favourites.html page along with that users favourite projects
	 * @param model contains data of the associated page such as projectRecords and allTools
	 * @param userDetails the currently logged in user
	 * @return favourites.html page view along with that users favourite project and list of all tools to be used in filtering
	 */
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
	
	/**
	 * When a user removes a project from their favourites this controller is called 
	 * @param projectId the id of the project the user is currently viewing the details of and will be removed from the favourites table
	 * @param userDetails the details of the logged in user
	 * @return an Ok status message done Asynchronously so does not return a view
	 */
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
