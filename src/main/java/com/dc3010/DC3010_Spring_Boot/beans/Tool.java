package com.dc3010.DC3010_Spring_Boot.beans;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

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
	
	public int getToolID() {
		return toolID;
	}

	public void setToolID(int toolID) {
		this.toolID = toolID;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public List<Project> getAssociatedProjects() {
		return associatedProjects;
	}

	public void setAssociatedProjects(List<Project> associatedProjects) {
		this.associatedProjects = associatedProjects;
	}
	
}
