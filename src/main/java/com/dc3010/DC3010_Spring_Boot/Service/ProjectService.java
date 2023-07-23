package com.dc3010.DC3010_Spring_Boot.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
	
	
	public Project findOne(Integer id)
	{
		return projectRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + id));
	}
	
	public List<Project> findAll()
	{
		List<Project> projects = new ArrayList<>();
		//Add all the projects found from the iterablie to a list
		projectRepo.findAll().forEach(projects::add);
		 
		return projects;
		 
	}
	
	public void deleteProject(Project project)
	{
		projectRepo.delete(project);
	}
	
}
