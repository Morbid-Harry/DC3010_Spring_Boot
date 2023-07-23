package com.dc3010.DC3010_Spring_Boot;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dc3010.DC3010_Spring_Boot.Service.EmailService;

@SpringBootTest
public class EmailServiceTests {

	
	@Autowired
	EmailService emailService;
	
	
	@Test
	@DisplayName("Test should pass when sending a valid email")
	void sendValidEmail()
	{
		assertDoesNotThrow(() -> emailService.sendEmail("rm_test_user@outlook.com", "project_finder_notifier@outlook.com", "Test Email"));
	}
	
}
