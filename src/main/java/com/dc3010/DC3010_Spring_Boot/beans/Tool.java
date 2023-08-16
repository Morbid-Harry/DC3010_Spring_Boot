package com.dc3010.DC3010_Spring_Boot.beans;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * Class that represents the database "Tool" table
 * @author Harry
 *
 */
@Entity(name = "tool")
public class Tool {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tool_id")
	private int toolID;
	
	@Column(name = "tool_name")
	private String toolName;
	
	@ManyToMany(mappedBy = "associatedTools")
	List<Project> associatedProjects;
	
	/**
	 * 
	 * @return the tool id associated with the tool
	 */
	public int getToolID() {
		return toolID;
	}
	
	/**
	 * 
	 * @param toolID the desired id of the tool normally set when passing a tool object with no id to the tool service using the addTool function
	 */
	public void setToolID(int toolID) {
		this.toolID = toolID;
	}
	
	/**
	 * 
	 * @return the name of the tool
	 */
	public String getToolName() {
		return toolName;
	}
	
	/**
	 * 
	 * @param toolName the desired name of the tool
	 */
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	
	/**
	 * 
	 * @return the list of projects that use this tool
	 */
	public List<Project> getAssociatedProjects() {
		return associatedProjects;
	}
	
	/**
	 * 
	 * @param associatedProjects the desired list of projects that use this tool 
	 */
	public void setAssociatedProjects(List<Project> associatedProjects) {
		this.associatedProjects = associatedProjects;
	}
	
}
