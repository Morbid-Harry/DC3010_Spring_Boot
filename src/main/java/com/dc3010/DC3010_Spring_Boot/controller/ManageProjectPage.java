package com.dc3010.DC3010_Spring_Boot.controller;

import java.sql.Date;
import java.util.*;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.beans.WorkLocation;
import com.dc3010.DC3010_Spring_Boot.util.ResponseWrapper;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

/**
 * Controller which handles HTTP response and requests for the Manage Project Role page
 * @author Harry
 *
 */
@Controller
public class ManageProjectPage {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ToolService toolService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * When a user navigates to the manage project roles page this function is called 
	 * all tools in the DB are sent to the page to display as tags when creating a project
	 * all project in the DB are also sent to the page and displayed in a table so they can be deleted if needed
	 * @param model contains data of the associated page such toolRecords and projectRecords
	 * @return the view manageproject.html and the 2 model attributes toolRecords and projecrRecords
	 */
	@GetMapping("/manage")
	ModelAndView doGet(Model model)
	{
		
		//Get the list of tools that is in the tool table
		List<Tool> toolRecords = toolService.findAll();
		
		//add to model to be displayed as tags in manage projects page
		model.addAttribute("toolRecords", toolRecords);
		
		model.addAttribute("projectRecords", projectService.findAll());
		
		ModelAndView modelAndView = new ModelAndView("manageproject.html");
		
		return modelAndView;
		
	}
	
	/**
	 * On success or failure of creating a project records this function is called. It will redirect back to the page with either a success or failure flash attribute to display a messsage to the user
	 * @param userDetails the logged in user
	 * @param redirectAttributes to set a success or failure message on the page
	 * @param clientName client that requires the project role 
	 * @param projectName the name of the project for the project role
	 * @param grade the required grade of the project from A1 to A10 
	 * @param startDate the start date of the project 
	 * @param endDate the end date of the project not required but if given it must be further in the future than the start date
	 * @param workLocation can either be Remote, Client_Site or Hybrid if it is Hybrid or Client Site then an address must also be given
	 * @param address if client site or hybrid was select for work location then this must be specified
	 * @param generalDescription the job description of the project role
	 * @param toolsUsed the selected tags given in the create project form and their names to search for them in the database
	 * @return redirects back to the manage project roles page with either a success or failure flash attribute which determines the bootstrap alert they receive
	 */
	@PostMapping("/create/project")
	RedirectView createProject(@AuthenticationPrincipal SecUserDetails userDetails, RedirectAttributes redirectAttributes, @RequestParam("client-name") String clientName, @RequestParam("project-name") String projectName, @RequestParam("project-grade") String grade, 
			@RequestParam(value = "start-date", required = false) String startDate, @RequestParam(value = "end-date", required = false) String endDate,
			@RequestParam("work-location") String workLocation, @RequestParam(value = "address", required = false) String address, @RequestParam("general-description") String generalDescription, @RequestParam(value = "tools-used", required = false) String[] toolsUsed)
	{
		Project newProject = new Project();
		
		//Add values give to project
		newProject.setCompanyName(clientName);
		newProject.setProjectName(projectName);
		newProject.setGrade(grade);
		
		//If user does not select a value to dates then form sends an empty string check this before trying to add to bean
		if(!startDate.equalsIgnoreCase("")) {
		newProject.setStartDate(Date.valueOf(startDate)); }
		  
		if(!endDate.equalsIgnoreCase("")) {
		newProject.setEndDate(Date.valueOf(endDate)); }
		
		
	    // Check if both start and end dates are given
	    if (newProject.getStartDate() != null && newProject.getEndDate() != null) {
	        // Compare start and end dates
	        if (newProject.getEndDate().before(newProject.getStartDate())) {
	            // End date is before the start date, handle the error condition
	            redirectAttributes.addFlashAttribute("error", "end date is before start date");
	            // Send user back to the project creation page
	            return new RedirectView("/manage");
	        }
	    }
		
		//Get Enum value of work location and add to bean
		WorkLocation location = WorkLocation.valueOf(workLocation);
		newProject.setWorkLocation(location);
		
		//If work location is not remote then there must be an address
		if(!workLocation.equalsIgnoreCase("Remote"))
		{
			newProject.setAddress(address);
		}
		
		newProject.setGeneralDescription(generalDescription);
			
		//If tools/software tags have been selected
		if (toolsUsed != null) {
			//Prepare set to add to the project
			Set<Tool> listOfToolsUsed = new HashSet<Tool>();
            for (String tool : toolsUsed) {
            	//Add the tool by name
            	listOfToolsUsed.add(toolService.getToolByName(tool));
            }
            //Add the listOfTools to the project
            newProject.setAssociatedTools(listOfToolsUsed);
		}
		
		//Set who the project was created by
		newProject.setCreatedBy(userService.getUserByLogin(userDetails.getUsername()));
		
		//Finally Save the project
		projectService.addProject(newProject);
		//Send success boolean with message
		redirectAttributes.addFlashAttribute("success", true);
		//Send user back to same page
		return new RedirectView("/manage");		
	
		
	}
	
	/**
	 * When a user is on the manage project roles page and select a project to delete this function is called
	 * @param projectId the id of the project the user wants to delete
	 * @return the Ok status and the Id of the project delete so it can be hidden in the datatable via javascript
	 */
	@DeleteMapping("/project/delete/{projectId}")
	protected ResponseEntity<String> deleteProject(@PathVariable("projectId") Integer projectId)
	{
		//Get the project to delete 
		Project projectToDelete = projectService.findOne(projectId);
		
		//Delete the project from the DB
		projectService.deleteProject(projectToDelete);		
		
		//Return the id of that project so the row can be hidden from the table
		return new ResponseEntity<String>(Integer.toString(projectToDelete.getProjectID()),HttpStatus.OK);
		

	}

}
