package com.dc3010.DC3010_Spring_Boot.Repository;

import org.springframework.data.repository.CrudRepository;

import com.dc3010.DC3010_Spring_Boot.beans.Tool;

public interface ToolRepository extends CrudRepository<Tool, Integer>{
	
	
	Tool findToolByToolName(String name);
}
