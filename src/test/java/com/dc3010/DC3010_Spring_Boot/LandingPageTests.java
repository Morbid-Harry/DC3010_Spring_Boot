package com.dc3010.DC3010_Spring_Boot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dc3010.DC3010_Spring_Boot.controller.LandingPage;

@SpringBootTest
public class LandingPageTests {
		
		@InjectMocks
	    private LandingPage landingPageController;

	    private MockMvc mockMvc;

	    @BeforeEach
	    public void setup() {
	        mockMvc = MockMvcBuilders.standaloneSetup(landingPageController).build();
	    }

	    @Test
	    @DisplayName("Test should pass when controller sends to view /login")
	    public void testLandingGet() throws Exception {
	        mockMvc.perform(MockMvcRequestBuilders.get("/"))
	               .andExpect(MockMvcResultMatchers.status().isOk())
	               .andExpect(MockMvcResultMatchers.view().name("/login"));
	    }
		
}
