package com.dc3010.DC3010_Spring_Boot.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dc3010.DC3010_Spring_Boot.Service.EmailService;
import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.ResponseWrapper;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;
/**
 * Controller which handles HTTP response and requests for the Dashboard page
 * @author Harry
 *
 */
@Controller    
public class Dashboard {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ToolService toolService;
	
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
			
			//All projects to display to the user
			model.addAttribute("projectRecords", allProjects);
			
			//All the tools that are potentially used by a project used for the tools filter
			model.addAttribute("allTools", toolService.findAll());
			
			return modelAndView;
		}
		
		/**
		 * 
		 * @param projectId the integer value of the project selected when clicking view details on the dashboard or favourites page
		 * @return a json response holding a response wrapper object containing the project details and its associated tools to be displayed as a modal
		 */
		@GetMapping("/findOne/{id}")
		protected ResponseEntity<ResponseWrapper> findOneProject(@PathVariable("id") Integer projectId){
			
			
			Project foundProject = projectService.findOne(projectId);
			
			ResponseWrapper wrapper = new ResponseWrapper(foundProject);
			wrapper.setTools();
			
			
			return new ResponseEntity<ResponseWrapper>(wrapper, HttpStatus.OK);
		}
		
		/**
		 * Handles the request of when a user click the send email button when viewing the details of a project
		 * @param projectId the id of the project the user is viewing the details of used to get the project info for the email text
		 * @param userDetails the logged in users information
		 * @return a json response of Ok if the email sent correctly 
		 */
		@GetMapping("/show-interest/{projectId}")
		protected ResponseEntity<Void> sendInterestEmail(@PathVariable("projectId") Integer projectId,@AuthenticationPrincipal SecUserDetails userDetails)
		{
			Project projectInterestedIn = projectService.findOne(projectId);
			User resourceManager = projectInterestedIn.getCreatedBy();
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
		
		/**
		 * Handles the request of when a use clicks to add a project to their favourites on the dashboard page
		 * @param projectId projectId the id of the project the user is viewing the details of to then add that project to that users favourites
		 * @param userDetails the details of the logged in user
		 * @return returns either an OK or Bad_Request if either the user already has the project in their favourites or not
		 */
		@PostMapping("/favourite/{projectId}")
		protected ResponseEntity<Void> favouriteProject(@PathVariable("projectId") Integer projectId, @AuthenticationPrincipal SecUserDetails userDetails)
		{
			//Get the user
			User loggedInUser = userService.getUserByLogin(userDetails.getUsername());
			
			

			//Get the project favourited
			Project projectToAdd = projectService.findOne(projectId);
				
							
				//Check the project isn't already favourtied by the user
				//if the user in favourited by has the same Id as the logged in user
				if(projectToAdd.getFavourtiedBy().contains(loggedInUser))
				{
					//Already in the users favourites so return bad request
					return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
				}
				else {
					// Add the user to the project's favoritedBy list
					projectToAdd.getFavourtiedBy().add(loggedInUser);
					//Update favourites table
					projectService.addProject(projectToAdd);

					return new ResponseEntity<Void>(HttpStatus.OK);
				}
			

		}
		

		
	
}
