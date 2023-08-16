package com.dc3010.DC3010_Spring_Boot.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc3010.DC3010_Spring_Boot.Repository.ToolRepository;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;

/**
 * Handles all database CRUD operations for the Tool Table and Bean
 * @author Harry
 *
 */
@Service
public class ToolService {
	
	@Autowired
	ToolRepository toolRepo;
	
	/**
	 * Saves a tool to the tool table or updates an exisitng tool
	 * @param tool the tool to either add or update to the DB
	 */
	public void addTool(Tool tool)
	{
		toolRepo.save(tool);
	}
	
	/**
	 * 
	 * @return all tools that are stored in the database
	 */
	public List<Tool> findAll()
	{
		List<Tool> tools = new ArrayList<>();
		//Add all the tools found from the iterable to a list
		toolRepo.findAll().forEach(tools::add);
		 
		return tools; 
	}
	
	/**
	 * 
	 * @param name of the tool in the database
	 * @return the tool that has the given name parameter
	 */
	public Tool getToolByName(String name)
	{
		return toolRepo.findToolByToolName(name);
	}
}
