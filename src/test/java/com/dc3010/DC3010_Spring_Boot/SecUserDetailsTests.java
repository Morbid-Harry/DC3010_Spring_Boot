package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

@SpringBootTest
public class SecUserDetailsTests {

	
	@Test
	@DisplayName("Test should pass whem creating a user with SecUserDetails")
	void createSecUser()
	{
		User newUser =  new User();
		newUser.setFirstName("New");
	    newUser.setLastName("User");
		newUser.setLogin("newuser");
		newUser.setPassword("newpass");
		newUser.setEmail("new@user.com");
		newUser.setGrade("A9");
		newUser.setRole("ROLE_USER");
		
		SecUserDetails secUser = new SecUserDetails(newUser);
		
		assertNotNull(secUser);
		
		assertEquals("newuser", secUser.getUsername());
		assertEquals("newpass", secUser.getPassword());
		
		String[] roles = {"ROLE_USER"};
		
		Collection<? extends GrantedAuthority> actualAuthorities = secUser.getAuthorities();
		
		List<SimpleGrantedAuthority> expectedAuthorities = Arrays.stream(roles)
                .map(SimpleGrantedAuthority::new)
                .toList();
		assertEquals(expectedAuthorities, actualAuthorities);
		
		assertTrue(secUser.isAccountNonExpired());
		assertTrue(secUser.isAccountNonLocked());
		assertTrue(secUser.isCredentialsNonExpired());
		assertTrue(secUser.isEnabled());
	}
}
