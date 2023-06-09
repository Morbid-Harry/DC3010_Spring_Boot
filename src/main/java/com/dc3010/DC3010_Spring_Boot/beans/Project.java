package com.dc3010.DC3010_Spring_Boot.beans;

import java.sql.Date;
import java.util.Set;

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
import jakarta.persistence.OneToMany;

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
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "created_By")
    private User createdBy;

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public WorkLocation getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(WorkLocation workLocation) {
		this.workLocation = workLocation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGeneralDescription() {
		return generalDescription;
	}

	public void setGeneralDescription(String generalDescription) {
		this.generalDescription = generalDescription;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Set<Tool> getAssociatedTools() {
		return associatedTools;
	}

	public void setAssociatedTools(Set<Tool> associatedTools) {
		this.associatedTools = associatedTools;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	
}
