package com.dc3010.DC3010_Spring_Boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MainClassTests {
	
	@Test
	public void applicationContextLoaded() {
	}

	@Test
	@DisplayName("Test needed just for 100% coverage")
	public void applicationContextTest() {
	   Dc3010SpringBootApplication.main(new String[] {});
	}

}
