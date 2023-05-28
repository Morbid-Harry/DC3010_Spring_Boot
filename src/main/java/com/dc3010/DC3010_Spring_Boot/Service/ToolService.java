package com.dc3010.DC3010_Spring_Boot.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc3010.DC3010_Spring_Boot.Repository.ToolRepository;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;

@Service
public class ToolService {
	
	@Autowired
	ToolRepository toolRepo;
	
	
	public void addTool(Tool tool)
	{
		toolRepo.save(tool);
	}
	
	public List<Tool> findAll()
	{
		List<Tool> tools = new ArrayList<>();
		//Add all the tools found from the iterablie to a list
		toolRepo.findAll().forEach(tools::add);
		 
		return tools; 
	}
	
	public Tool getToolByName(String name)
	{
		return toolRepo.findToolByToolName(name);
	}
}
