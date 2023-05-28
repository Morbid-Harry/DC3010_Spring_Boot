package com.dc3010.DC3010_Spring_Boot.Repository;

import org.springframework.data.repository.CrudRepository;

import com.dc3010.DC3010_Spring_Boot.beans.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer>{
	
}
