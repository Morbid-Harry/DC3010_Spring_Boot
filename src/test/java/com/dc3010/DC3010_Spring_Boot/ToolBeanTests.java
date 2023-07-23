package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;

@SpringBootTest
public class ToolBeanTests {
	
	
	@Test
	@DisplayName("Pass when setting and getting tool ID")
	void setGetToolID()
	{
		Tool newTool = new Tool();
		newTool.setToolID(1);
		
	    int actualID = newTool.getToolID();
	    
	    assertEquals(1, actualID);
	}
	
	@Test
	@DisplayName("Pass when setting and getting tool name")
	void setGetToolName()
	{
		Tool newTool = new Tool();
		newTool.setToolName("TESTTOOL");
		
		String actualName = newTool.getToolName();
	    
	    assertEquals("TESTTOOL", actualName);
	}
	
	@Test
	@DisplayName("Pass when setting and getting projects")
	void setGetProjects()
	{
		Tool newTool = new Tool();
		newTool.setToolName("TESTTOOL");
		
		Project testProject = new Project();
		testProject.setCompanyName("Company");
		testProject.setProjectName("Project");
		
		List<Project> toolProjects = new ArrayList();
		
		toolProjects.add(testProject);
		newTool.setAssociatedProjects(toolProjects);

		List<Project> actualProjects = newTool.getAssociatedProjects();
	    
		assertEquals("Company", actualProjects.get(0).getCompanyName());
		assertEquals("Project", actualProjects.get(0).getProjectName());
	}
}
