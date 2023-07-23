package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.Repository.ToolRepository;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;

@SpringBootTest
class ToolServiceTests {
	
	@Autowired
	private ToolRepository toolRepo; 
	
	@Autowired
	private ToolService toolService;

	@Test
	@DisplayName("Test should pass when finding a tool that exists in database")
	void findToolThatExists()
	{

		Tool toolFound = toolService.getToolByName("HTML");
		assertNotNull(toolFound);
		assertEquals("HTML", toolFound.getToolName());
	}
	
	@Test
	@DisplayName("Test should pass when null is returned for a tool that doesnt exist in database")
	void findToolThatDoesNotExist()
	{

		Tool toolFound = toolService.getToolByName("NOTREAL");
		assertNull(toolFound);
	}
	
	@Test
	@DisplayName("Test should pass when adding a tool to SQL")
	void addTool()
	{
		Tool newTool = new Tool();
		newTool.setToolName("TEST TOOL");
		toolService.addTool(newTool);
		newTool = toolService.getToolByName("TEST TOOL");
		assertNotNull(newTool);
		assertEquals("TEST TOOL", newTool.getToolName());
		
		//Remove so not in real db
		toolRepo.deleteById(newTool.getToolID());
	}
	
	
	@Test
	@DisplayName("Test should pass when size of tools array is 9")
	void getAllTools()
	{
		List<Tool> allTools = new ArrayList();
		allTools = toolService.findAll();
		assertEquals(9, allTools.size());
	}
	
}
