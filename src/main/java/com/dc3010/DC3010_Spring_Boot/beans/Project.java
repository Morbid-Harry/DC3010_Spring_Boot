package com.dc3010.DC3010_Spring_Boot.beans;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

/**
 * Class that represents the Database "Project" Table
 * @author Harry
 *
 */
@Entity(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="project_id")
	private int projectID;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="project_name")
	private String projectName;
	
	@Column(name="grade_required")
	private String grade;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Enumerated(EnumType.STRING)
	private WorkLocation workLocation;
	
	@Column(name="address")
	private String address;
	
	@Column(name="role_description")
	private String generalDescription;
	
	@ManyToMany
	@JoinTable(
			  name = "projects-tools", 
			  joinColumns = @JoinColumn(name = "project_id"), 
			  inverseJoinColumns = @JoinColumn(name = "tool_id"))
	Set<Tool> associatedTools;
	
	@ManyToOne
    @JoinColumn(name = "created_By")
    private User createdBy;
	
	
	@ManyToMany
	@JoinTable(
			  name = "favourites", 
			  joinColumns = @JoinColumn(name = "favourite_project_id"), 
			  inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	List<User> favourtiedBy;
	
	/**
	 * 
	 * @return the project Id of the project
	 */
	public int getProjectID() {
		return projectID;
	}
	
	/**
	 * 
	 * @param projectID the desired ID of the project not normally used as this will automatically set by spring JPA when sending to create a project in the project service
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	
	/**
	 * 
	 * @return the company name for the project 
	 */
	public String getCompanyName() {
		return companyName;
	}
	
	/**
	 * 
	 * @param companyName the desired company name of the project
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	 * 
	 * @return the project name of the project
	 */
	public String getProjectName() {
		return projectName;
	}
	
	/**
	 * 
	 * @param projectName the desired project name of the project
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/**
	 * 
	 * @return the start date of the project
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * 
	 * @param startDate the desired start date of the project
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 
	 * @return the end date of the project
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * 
	 * @param endDate the desired end date of the project
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 
	 * @return the enum value of the work location either Remote,Client_Site,Hybrid
	 */
	public WorkLocation getWorkLocation() {
		return workLocation;
	}
	
	/**
	 * 
	 * @param workLocation the desired enum value of work location either Remote,Client_Site,Hybrid
	 */
	public void setWorkLocation(WorkLocation workLocation) {
		this.workLocation = workLocation;
	}
	
	/**
	 * 
	 * @return the address of the project
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * 
	 * @param address the desired address of the project
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 
	 * @return the description of the project
	 */
	public String getGeneralDescription() {
		return generalDescription;
	}
	
	/**
	 * 
	 * @param generalDescription the desired description of the project
	 */
	public void setGeneralDescription(String generalDescription) {
		this.generalDescription = generalDescription;
	}
	
	/**
	 * 
	 * @return the required grade of the project
	 */
	public String getGrade() {
		return grade;
	}
	
	/**
	 * 
	 * @param grade the desired required grade of the project
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	/**
	 * 
	 * @return the tools associted with the project 
	 */
	public Set<Tool> getAssociatedTools() {
		return associatedTools;
	}
	
	/**
	 * 
	 * @param associatedTools the desired tools associated with the project
	 */
	public void setAssociatedTools(Set<Tool> associatedTools) {
		this.associatedTools = associatedTools;
	}
	
	/**
	 * 
	 * @return the user that created this project
	 */
	public User getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * 
	 * @param createdBy the desried user object that created the project
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
	/**
	 * 
	 * @return a list of users that favourited the project
	 */
	public List<User> getFavourtiedBy() {
		return favourtiedBy;
	}
	
	/**
	 * 
	 * @param favourtiedBy list of users that have favorited the project
	 */
	public void setFavourtiedBy(List<User> favourtiedBy) {
		this.favourtiedBy = favourtiedBy;
	}
	
	
	
}
