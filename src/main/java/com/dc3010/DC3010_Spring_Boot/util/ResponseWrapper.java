package com.dc3010.DC3010_Spring_Boot.util;

import java.util.ArrayList;

import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;

/**
 * The purpose of this class is specifically to wrap the Project and the associated tools into one response for the dashboard view details screen
 * Without this class I am unable to pull the associated tools from the project when opening a modal window as spring/js does not work well with an object that also holds a set
 * @author Harry
 *
 */
public class ResponseWrapper {
	private Project project;
	private ArrayList<String> tools;
	
	// Constructor with only the data
    public ResponseWrapper(Project project) {
        this.project = project;
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ArrayList<String> getTools() {
		return tools;
	}
	
	/**
	 * Gets the project associated tools Set and adds them in a tools arraylist as a string which is compatible with JSON
	 */
	public void setTools() {
		
		ArrayList<Tool> toAddTools = new ArrayList<Tool>(); 
		toAddTools.addAll(project.getAssociatedTools());
		
		project.setAssociatedTools(null);
		
		tools = new ArrayList<String>();
		for(Tool tool : toAddTools)
		{
			tools.add(tool.getToolName());
		}
	}
    
    
}
