package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.Repository.ProjectRepository;
import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.WorkLocation;

@SpringBootTest
public class ProjectServiceTests {
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	
	@Test
	@DisplayName("Test should pass when finding a project that exists in database")
	void findProjectThatExists()
	{
		Project projectFound = projectService.findOne(51);
		assertNotNull(projectFound);
		assertEquals("Company:539", projectFound.getCompanyName());
	}
	
	@Test
	@DisplayName("Test should pass when exception message is returned for a tool that doesn't exist in database")
	void findProjectThatDoesNotExist()
	{
		Throwable exception = assertThrows(NoSuchElementException.class, () -> projectService.findOne(999));
		assertEquals("Project not found with ID: 999", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test should pass when adding a project to the DB")
	void addProject()
	{
		Project newProject = new Project();
		newProject.setCompanyName("Test Company");
		newProject.setProjectName("Test Project");
		newProject.setGrade("A10");
		newProject.setStartDate(Date.valueOf("2023-05-30"));
		newProject.setEndDate(Date.valueOf("2023-07-30"));
		newProject.setWorkLocation(WorkLocation.valueOf("Client_Site"));
		newProject.setAddress("Test Address");
		newProject.setGeneralDescription("Test Description");
		newProject.setCreatedBy(userService.getUserById(1));

		
		
		
		projectService.addProject(newProject);
		
		
		
		List<Project> allProjects = projectService.findAll(); 
		
		for(Project project : allProjects)
		{
			if(project.getCompanyName().equals("Test Company"))
			{
				newProject = project;
			}
		}
		
		assertNotNull(newProject);
		assertEquals("Test Company", newProject.getCompanyName());
		
		projectRepo.deleteById(newProject.getProjectID());
	}
	
	@Test
	@DisplayName("Test should pass when delete a project from the DB")
	void deleteProject()
	{
		Project newProject = new Project();
		newProject.setCompanyName("Test Company");
		newProject.setProjectName("Test Project");
		newProject.setGrade("A10");
		newProject.setStartDate(Date.valueOf("2023-05-30"));
		newProject.setEndDate(Date.valueOf("2023-07-30"));
		newProject.setWorkLocation(WorkLocation.valueOf("Client_Site"));
		newProject.setAddress("Test Address");
		newProject.setGeneralDescription("Test Description");
		newProject.setCreatedBy(userService.getUserById(1));

		
		
		
		projectService.addProject(newProject);
		
		
		
		List<Project> allProjects = projectService.findAll(); 
		
		for(Project project : allProjects)
		{
			if(project.getCompanyName().equals("Test Company"))
			{
				newProject = project;
			}
		}
		
		assertNotNull(newProject);
		assertEquals("Test Company", newProject.getCompanyName());
		
		int id = newProject.getProjectID();
		
		projectService.deleteProject(newProject);
		
		assertThrows(NoSuchElementException.class, () -> projectService.findOne(id));
	}
	
	@Test
	@DisplayName("Test should pass when findall returns a non-null list of projects")
	void findAllProjects()
	{
		List<Project> allProjects = new ArrayList<>();
		allProjects = projectService.findAll();
		
		assertNotNull(allProjects);
	}
}
