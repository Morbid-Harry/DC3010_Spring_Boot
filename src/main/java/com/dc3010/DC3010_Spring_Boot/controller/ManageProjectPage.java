package com.dc3010.DC3010_Spring_Boot.controller;

import java.sql.Date;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;
import com.dc3010.DC3010_Spring_Boot.beans.WorkLocation;

@Controller
public class ManageProjectPage {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ToolService toolService;
	
	@GetMapping("/manage")
	ModelAndView doGet(Model model)
	{
		
		//Get the list of tools that is in the tool table
		List<Tool> toolRecords = toolService.findAll();
		
		//add to model to be displayed as tags in manage projects page
		model.addAttribute("toolRecords", toolRecords);
		
		ModelAndView modelAndView = new ModelAndView("manageproject.html");
		
		return modelAndView;
		
	}
	
	@PostMapping("/create/project")
	RedirectView createProject(RedirectAttributes redirectAttributes, @RequestParam("client-name") String clientName, @RequestParam("project-name") String projectName, @RequestParam("project-grade") String grade, 
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
		if (toolsUsed != null && toolsUsed.length > 0) {
			//Prepare set to add to the project
			Set<Tool> listOfToolsUsed = new HashSet<Tool>();
            for (String tool : toolsUsed) {
            	//Add the tool by name
            	listOfToolsUsed.add(toolService.getToolByName(tool));
            }
            //Add the listOfTools to the project
            newProject.setAssociatedTools(listOfToolsUsed);
		}
		
		//Finally Save the project
		projectService.addProject(newProject);
		//Send success boolean with message
		redirectAttributes.addFlashAttribute("success", true);
		//Send user back to same page
		return new RedirectView("/manage");		
	
		
	}
}
