package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dc3010.DC3010_Spring_Boot.Repository.UserRepository;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.PasswordUtils;

@SpringBootTest
public class UserServiceTests {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;
	
	@Test
	@DisplayName("Test should pass when finding a user by login that exists in database")
	void findUserByLoginThatExists()
	{
		User userFound = userService.getUserByLogin("haedward");
		assertNotNull(userFound);
		assertEquals("Harry", userFound.getFirstName());
	}
	
	@Test
	@DisplayName("Test should pass when finding a user by login that doesn't exist in database")
	void tryFindUserByLoginThatNotExist()
	{
		User userFound = userService.getUserByLogin("notreal");
		assertNull(userFound);
	}
	
	@Test
	@DisplayName("Test should pass when finding a user by Email that exists in database")
	void findUserByEmailThatExists()
	{
		User userFound = userService.getUserByEmail("harry.edwards@capgemini.com");
		assertNotNull(userFound);
		assertEquals("Harry", userFound.getFirstName());
	}
	
	@Test
	@DisplayName("Test should pass when finding a user by Email that doesn't exist in database")
	void tryFindUserByEmailThatNotExist()
	{
		User userFound = userService.getUserByEmail("notreal@notreal.com");
		assertNull(userFound);
	}
	
	@Test
	@DisplayName("Test should pass when finding a user by Id that exists in database")
	void findUserByIdThatExists()
	{
		User userFound = userService.getUserById(1);
		assertNotNull(userFound);
		assertEquals("Harry", userFound.getFirstName());
	}
	
	@Test
	@DisplayName("Test should pass when finding a user by ID that doesn't exist in database")
	void tryFindUserByIdThatNotExist()
	{
		User userFound = userService.getUserById(9999);
		assertNull(userFound);
	}
	
	@Test
	@DisplayName("Test should pass when finding a user by login for spring security")
	void findUserByLoginSECDETAILS()
	{
		UserDetails userFound = userService.loadUserByUsername("haedward");
		assertNotNull(userFound);
		assertEquals("haedward", userFound.getUsername());
	}
	
	@Test
	@DisplayName("Test should pass when finding a user by login for spring security")
	void tryFindUserByLoginSECDETAILS()
	{
		Throwable exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("notreal"));
		assertEquals("Username not found notreal", exception.getMessage());
	}
	
	@Test
	@DisplayName("Test should pass when saving a user to the database")
	void addUser()
	{
		User newUser = new User();
		newUser.setFirstName("New");
		newUser.setLastName("User");
		newUser.setLogin("newuser");
		newUser.setPassword("newpass");
		newUser.setEmail("new@user.com");
		newUser.setGrade("A9");
		newUser.setRole("ROLE_USER");
		
		userService.addUser(newUser);
		
		User getNewUser = userService.getUserByLogin("newuser");
		assertNotNull(getNewUser);
		getNewUser.getFirstName();
		
		userRepo.delete(newUser);
		
	}
	
}
