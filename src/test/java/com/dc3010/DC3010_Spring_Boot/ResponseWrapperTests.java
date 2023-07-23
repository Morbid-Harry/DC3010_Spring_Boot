package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;
import com.dc3010.DC3010_Spring_Boot.util.ResponseWrapper;

@SpringBootTest
public class ResponseWrapperTests {

	@Test
	@DisplayName("Pass when setting and getting project")
	void setGetWrapperProject()
	{
		Project newProject = new Project();
		newProject.setProjectName("Project Name");

		ResponseWrapper wrapper = new ResponseWrapper(newProject);
		
		assertEquals("Project Name", wrapper.getProject().getProjectName());
	}
	
	@Test
	@DisplayName("Pass when setting and getting tools")
	void setGetWrapperTools()
	{
		Project newProject = new Project();
		newProject.setProjectName("Project Name");
		
		Set<Tool> tools = new HashSet();
		Tool newTool = new Tool();
		newTool.setToolName("Associated Tool");
		Tool newTool2 = new Tool();
		newTool2.setToolName("Associated Tool 2");
		
		tools.add(newTool);
		tools.add(newTool2);
		
		newProject.setAssociatedTools(tools);
		
		ResponseWrapper wrapper = new ResponseWrapper(newProject);
		
		wrapper.setTools();		
		
		assertEquals(2, wrapper.getTools().size());
	}
}
