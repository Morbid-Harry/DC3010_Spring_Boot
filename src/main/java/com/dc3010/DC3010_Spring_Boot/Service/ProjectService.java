package com.dc3010.DC3010_Spring_Boot.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc3010.DC3010_Spring_Boot.Repository.ProjectRepository;
import com.dc3010.DC3010_Spring_Boot.beans.Project;

@Service
public class ProjectService{
	
	@Autowired
	private ProjectRepository projectRepo;
	
	public void addProject(Project project)
	{
		projectRepo.save(project);
	}
	
	public List<Project> findAll()
	{
		List<Project> projects = new ArrayList<>();
		//Add all the projects found from the iterablie to a list
		projectRepo.findAll().forEach(projects::add);
		 
		return projects;
		 
	}
	
}
