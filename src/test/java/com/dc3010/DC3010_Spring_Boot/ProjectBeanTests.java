package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.beans.WorkLocation;

@SpringBootTest
public class ProjectBeanTests {
	
	@Test
	@DisplayName("Pass when setting and getting project company name")
	void setGetCompanyName()
	{
		Project newProject = new Project();
		newProject.setCompanyName("Company");
		
		String actualCName = newProject.getCompanyName();
		
		assertEquals("Company", actualCName);
	}
	
	@Test
	@DisplayName("Pass when setting and getting project ID")
	void setGetProjectId()
	{
		Project newProject = new Project();
		newProject.setProjectID(1);
		
		int actualID = newProject.getProjectID();
		
		assertEquals(1, actualID);
	}
	
	@Test
	@DisplayName("Pass when setting and getting project name")
	void setGetProjectName()
	{
		Project newProject = new Project();
		newProject.setProjectName("Project");
		
		String actualPName = newProject.getProjectName();
		
		assertEquals("Project", actualPName);
	}
	
	@Test
	@DisplayName("Pass when setting and getting project Grade")
	void setGetGrade()
	{
		Project newProject = new Project();
		newProject.setGrade("A10");
		
		String actualGrade = newProject.getGrade();
		
		assertEquals("A10", actualGrade);
	}
	
	@Test
	@DisplayName("Pass when setting and getting project Start Date")
	void setGetStartDate()
	{
		Project newProject = new Project();
		newProject.setStartDate(Date.valueOf("2023-07-15"));
		
		Date actualStartDate = newProject.getStartDate();
		
		assertEquals("2023-07-15", actualStartDate.toString());
	}
	
	@Test
	@DisplayName("Pass when setting and getting project End Date")
	void setGetEndDate()
	{
		Project newProject = new Project();
		newProject.setEndDate(Date.valueOf("2023-07-15"));
		
		Date actualEndDate = newProject.getEndDate();
		
		assertEquals("2023-07-15", actualEndDate.toString());
	}
	
	@Test
	@DisplayName("Pass when setting and getting project Work Location")
	void setGetWorkLocation()
	{
		Project newProject = new Project();
		newProject.setWorkLocation(WorkLocation.Hybrid);
		
		WorkLocation actualWorkLocation = newProject.getWorkLocation();
		
		assertEquals(WorkLocation.Hybrid, actualWorkLocation);
	}
	
	@Test
	@DisplayName("Pass when setting and getting project address")
	void setGetAddress()
	{
		Project newProject = new Project();
		newProject.setAddress("Test Address");
		
		String actualAddress = newProject.getAddress();
		
		assertEquals("Test Address", actualAddress);
	}
	
	@Test
	@DisplayName("Pass when setting and getting project description")
	void setGetDescription()
	{
		Project newProject = new Project();
		newProject.setGeneralDescription("Test Description");
		
		String actualDescription = newProject.getGeneralDescription();
		
		assertEquals("Test Description", actualDescription);
	}
	
	@Test
	@DisplayName("Pass when setting and getting project associated tools")
	void setGetAssociatedTools()
	{
		Project newProject = new Project();
		
		Set<Tool> tools = new HashSet();
		Tool newTool = new Tool();
		newTool.setToolName("Associated Tool");
		tools.add(newTool);
		
		newProject.setAssociatedTools(tools);
		
		Tool[] actualTools = tools.toArray(new Tool[0]);
		
		assertNotNull(newProject.getAssociatedTools());
		
		assertEquals("Associated Tool", actualTools[0].getToolName());
	}
	
	@Test
	@DisplayName("Pass when setting and getting project created by")
	void setGetCreatedBy()
	{
		Project newProject = new Project();
		
		
		User newUser = new User();
		
		newUser.setFirstName("FirstName");
		
		newProject.setCreatedBy(newUser);
			
		
		assertEquals("FirstName", newProject.getCreatedBy().getFirstName());
	}
	
	@Test
	@DisplayName("Pass when setting and getting project favourited by")
	void setGetFavouritedBy()
	{
		Project newProject = new Project();
		
		User newUser = new User();
		
		newUser.setFirstName("FirstName");
		
		List<User> favouritedBy = new ArrayList();
		
		favouritedBy.add(newUser);
		
		newProject.setFavourtiedBy(favouritedBy);	
		
		assertEquals("FirstName", newProject.getFavourtiedBy().get(0).getFirstName());
	}
	
}
