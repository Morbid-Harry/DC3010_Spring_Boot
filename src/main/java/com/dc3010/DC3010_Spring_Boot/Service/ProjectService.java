package com.dc3010.DC3010_Spring_Boot.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dc3010.DC3010_Spring_Boot.Repository.ProjectRepository;
import com.dc3010.DC3010_Spring_Boot.beans.Project;

/**
 * Handles all database CRUD operations for the Project Table and Bean
 * @author Harry
 *
 */
@Service
public class ProjectService{
	
	@Autowired
	private ProjectRepository projectRepo;
	
	/**
	 * Saves a project to the project table or updates an exisitng project
	 * @param project the project to either add or update to the DB
	 */
	public void addProject(Project project)
	{
		projectRepo.save(project);
	}
	
	/**
	 * 
	 * @param id of the project to get from the database if not fouund will throw an exception
	 * @return the project object with the corresponding ID
	 */
	public Project findOne(Integer id)
	{
		return projectRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Project not found with ID: " + id));
	}
	
	/**
	 * 
	 * @return all projects that are stored in the database
	 */
	public List<Project> findAll()
	{
		List<Project> projects = new ArrayList<>();
		//Add all the projects found from the iterablie to a list
		projectRepo.findAll().forEach(projects::add);
		 
		return projects;
		 
	}
	
	/**
	 * 
	 * @param project the project to delete from the database
	 */
	public void deleteProject(Project project)
	{
		projectRepo.delete(project);
	}
	
}
