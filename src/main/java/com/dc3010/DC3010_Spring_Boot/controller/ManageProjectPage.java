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
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

@Controller
public class ManageProjectPage {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ToolService toolService;
	
	@Autowired
	private UserService userService;
	
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
	
	@DeleteMapping("/project/delete/{projectId}")
	protected ResponseEntity<Void> deleteProject(@PathVariable("projectId") Integer projectId)
	{
		//Get the project to delete 
		Project projectToDelete = projectService.findOne(projectId);
		
		projectService.deleteProject(projectToDelete);
		
		// Return a success message or any appropriate response
        return new ResponseEntity<>(HttpStatus.OK);
		

	}

}
