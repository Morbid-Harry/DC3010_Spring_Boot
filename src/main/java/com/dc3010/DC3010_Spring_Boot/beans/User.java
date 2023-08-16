package com.dc3010.DC3010_Spring_Boot.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * Class that represents the database "User" table
 * @author Harry
 *
 */
@Entity(name = "user")
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int userID;
	
	@Column(name="user_first_name")
	private String firstName;
	
	@Column(name="user_last_name")
	private String lastName;
	
	@Column(name="user_login")
	private String login;
	
	@Column(name="user_email")
	private String email;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="role")
	private String role;
	
	@Column(name="grade")
	private String grade;
	
	@ManyToMany(mappedBy = "favourtiedBy")
	@JsonIgnore
	List<Project> favourtiedProjects;
		
	public User()
	{
		
	}
	/**
	 * 
	 * @return the last name of the user
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * 
	 * @param last name the desired last name of the user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * 
	 * @return the user id of the user
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * 
	 * @param userID the desired of the user typically set after adding the user via the userService "addUser" function and returned via a "getBy" function 
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * 
	 * @return first name of the user
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * 
	 * @param firstName the desired first name of the user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * 
	 * @return the username of the user
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * 
	 * @param login the desired username of the user
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * 
	 * @return the email of the user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 
	 * @param email the desired email of the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 
	 * @param password the desired password of the user, should first be hashed using the PasswordUtils class 
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 
	 * @return the role of the user such as ROLE_USER,ROLE_ADMIN,ROLE_RM
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * 
	 * @param role the desired role of the user must either be ROLE_USER,ROLE_ADMIN,ROLE_RM in any combination 
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * 
	 * @return the grade of the user
	 */
	public String getGrade() {
		return grade;
	}
	
	/**
	 * 
	 * @param grade the desired grade of the user can be up from A1 to A10
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	/**
	 * 
	 * @return projects favourited by the user
	 */
	public List<Project> getFavourtiedProjects() {
		return favourtiedProjects;
	}
	
	/**
	 * 
	 * @param favourtiedProjects list of desired projects favourited by this user
	 */
	public void setFavourtiedProjects(List<Project> favourtiedProjects) {
		this.favourtiedProjects = favourtiedProjects;
	}
	
	

	
}
