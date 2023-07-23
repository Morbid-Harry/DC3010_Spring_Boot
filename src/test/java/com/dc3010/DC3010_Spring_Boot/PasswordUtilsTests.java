package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.util.PasswordUtils;

@SpringBootTest
public class PasswordUtilsTests {
	
	
	@Test
	@DisplayName("Pass when password returned is hashed")
	void hashPassword()
	{
		PasswordUtils passUtil = new PasswordUtils();
		
		String passToEncode = "Pass";
		
		assertNotEquals(passToEncode, passUtil.encode(passToEncode));
	}
	
	@Test
	@DisplayName("Pass when matches returns true to given password")
	void matchPassword()
	{
		PasswordUtils passUtil = new PasswordUtils();
		
		String passToEncode = "Pass";
		
		String encodedPass = passUtil.encode(passToEncode);
		
		assertTrue(passUtil.matches(passToEncode, encodedPass));
	}
}
