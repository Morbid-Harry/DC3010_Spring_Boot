package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.User;

@SpringBootTest
public class UserBeanTests {
	
	@Test
	@DisplayName("Pass when setting and getting user bean first name")
	void setGetFirstName()
	{
		User newUser = new User();
		
		newUser.setFirstName("TestName");
		String actualName = newUser.getFirstName();
		
		assertEquals("TestName", actualName);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean last name")
	void setGetLastName()
	{
		User newUser = new User();
		
		newUser.setLastName("TestLastName");
		String actualName = newUser.getLastName();
		
		assertEquals("TestLastName", actualName);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean ID")
	void setGetId()
	{
		User newUser = new User();
		
		newUser.setUserID(1);
		int actualId = newUser.getUserID();
		
		assertEquals(1, actualId);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean login")
	void setGetLogin()
	{
		User newUser = new User();
		
		newUser.setLogin("TestLogin");
		String actualLogin = newUser.getLogin();
		
		assertEquals("TestLogin", actualLogin);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean email")
	void setGetEmail()
	{
		User newUser = new User();
		
		newUser.setEmail("Test@Email.com");
		String actualEmail = newUser.getEmail();
		
		assertEquals("Test@Email.com", actualEmail);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean password")
	void setGetPassword()
	{
		User newUser = new User();
		
		newUser.setPassword("testpass");
		String actualPass = newUser.getPassword();
		
		assertEquals("testpass", actualPass);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean role")
	void setGetRole()
	{
		User newUser = new User();
		
		newUser.setRole("USER_ROLE");
		String actualRole = newUser.getRole();
		
		assertEquals("USER_ROLE", actualRole);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean Grade")
	void setGetGrade()
	{
		User newUser = new User();
		
		newUser.setGrade("A5");
		String actualGrade = newUser.getGrade();
		
		assertEquals("A5", actualGrade);
	}
	
	@Test
	@DisplayName("Pass when setting and getting user bean favourited projects")
	void setGetFavouritedProjects()
	{
		User newUser = new User();
		
		Project testProject = new Project();
		testProject.setCompanyName("Company");
		testProject.setProjectName("Project");
		
		List<Project> userProjects = new ArrayList();
		
		userProjects.add(testProject);
		newUser.setFavourtiedProjects(userProjects);

		List<Project> actualProjects = newUser.getFavourtiedProjects();
		
		assertEquals("Company", actualProjects.get(0).getCompanyName());
		assertEquals("Project", actualProjects.get(0).getProjectName());
	}
}

