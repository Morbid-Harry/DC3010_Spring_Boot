package com.dc3010.DC3010_Spring_Boot;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.dc3010.DC3010_Spring_Boot.Repository.ProjectRepository;
import com.dc3010.DC3010_Spring_Boot.Service.EmailService;
import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;
import com.dc3010.DC3010_Spring_Boot.beans.User;


@SpringBootTest
@AutoConfigureMockMvc
public class DashboardPageTests {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private ToolService toolService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
    private MockMvc mockMvc;
	
	
	@Test
	@WithUserDetails("haedward")
	@DisplayName("Test should pass when controller returns correct sized attributes for allTools and projectRecords as per database. Should also send to view dashboard.html")
	public void testGetDashboardPage() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard.html"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("projectRecords"))
                .andExpect(model().attributeExists("allTools")).andReturn();
		
		//Get the attributes from the result
		Map<String, Object> modelAttributes = mvcResult.getModelAndView().getModel();
		
		//projectRecords attrbitue should be the same size as projectService.findAll
		List<Project> actualAllProjects = (List<Project>) modelAttributes.get("projectRecords");
		List<Project> expectedAllProjects = projectService.findAll();
		assertEquals(expectedAllProjects.size(), actualAllProjects.size());
		
		//User should be Harry Edwards user
		User actualUser = (User) modelAttributes.get("user");
		assertEquals("Harry", actualUser.getFirstName());
		assertEquals("Edwards", actualUser.getLastName());
		
		//All tools should be same size a toolService.findAll
		List<Tool> actualAllTools = (List<Tool>) modelAttributes.get("allTools");
		List<Tool> expectedAllTools = toolService.findAll();
		assertEquals(expectedAllTools.size(), actualAllTools.size());
	}
	
    @Test
    @WithUserDetails("haedward")
	@DisplayName("Test should pass when controller responds with a valid json message for a given company ID")
    public void testFindOneProject() throws Exception {

        mockMvc.perform(get("/findOne/{id}", 51))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.project.projectID", is(51)))
                .andExpect(jsonPath("$.project.projectName", is("Project:268")))
                .andExpect(jsonPath("$.project.companyName", is("Company:539")))
                .andExpect(jsonPath("$.project.grade", is("A9")))
                .andExpect(jsonPath("$.project.startDate", is("2023-05-30")))
                .andExpect(jsonPath("$.project.endDate", is("2023-12-31")))
                .andExpect(jsonPath("$.project.workLocation", is("Client_Site")))
                .andExpect(jsonPath("$.project.address", is("Test Address: 586")))
                .andExpect(jsonPath("$.project.generalDescription", is("This is a general description for the Client: Company:539 and Project:268")));
    }
    
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when controller returns ok status message when giving a valid user id and project id")
    public void testSendInterestEmail() throws Exception {
    	//As this goes to an external page e.g. outlook there is no other test that can be done
        mockMvc.perform(get("/show-interest/{projectId}", 51)).andExpect(status().isOk());
        
    }
    
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when users favourites size increase corretly when favouriting a role when calling url: /favourite/{projectId}")
    public void testFavouriteProjectWithExistingFavourites() throws Exception {

    	User loggedInUser = userService.getUserByLogin("haedward");
    	//Check size of favourites before adding is 3
    	int usersFavoutiesBeforeAdding = loggedInUser.getFavourtiedProjects().size();
    	
    	assertEquals(2, usersFavoutiesBeforeAdding);
    	
    	
        mockMvc.perform(post("/favourite/{projectId}", 51)).andExpect(status().isOk());
        
        
        loggedInUser = userService.getUserByLogin("haedward");
        //Check after calling endpoint favourites is now 3
    	int usersFavoutiesAfterAdding = loggedInUser.getFavourtiedProjects().size();
    	assertEquals(3, usersFavoutiesAfterAdding);
        
        
        // Try to add the same project again, should return BAD_REQUEST
        mockMvc.perform(post("/favourite/{projectId}", 51)).andExpect(status().isBadRequest());
        
        //Get the updated user
        loggedInUser = userService.getUserByLogin("haedward");
        //Check size of favouties after removing is still 3
        int usersFavoutiesAfterAddingAgain = loggedInUser.getFavourtiedProjects().size();
        assertEquals(3, usersFavoutiesAfterAddingAgain);
        
        //Remove from favourites so test can be ran again
        mockMvc.perform(post("/favourite/remove/{projectId}", 51)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when users favourites size increase corretly when favouriting a role when calling url: /favourite/{projectId} for a user that has never favourited a role")
    public void testFavouriteProjectWithNoExistingFavourites() throws Exception {

    	User loggedInUser = userService.getUserByLogin("admin");
    	//Check size of favourites before adding is 0
    	int usersFavoutiesBeforeAdding = loggedInUser.getFavourtiedProjects().size();
    	
    	assertEquals(0, usersFavoutiesBeforeAdding);
    	
    	
        mockMvc.perform(post("/favourite/{projectId}", 51)).andExpect(status().isOk());
        
        
        loggedInUser = userService.getUserByLogin("admin");
        //Check after calling endpoint favourites is now 3
    	int usersFavoutiesAfterAdding = loggedInUser.getFavourtiedProjects().size();
    	assertEquals(1, usersFavoutiesAfterAdding);
        
        
        // Try to add the same project again, should return BAD_REQUEST
        mockMvc.perform(post("/favourite/{projectId}", 51)).andExpect(status().isBadRequest());
        
        //Get the updated user
        loggedInUser = userService.getUserByLogin("admin");
        //Check size of favouties after removing is still 3
        int usersFavoutiesAfterAddingAgain = loggedInUser.getFavourtiedProjects().size();
        assertEquals(1, usersFavoutiesAfterAddingAgain);
        
        //Remove from favourites so test can be ran again
        mockMvc.perform(post("/favourite/remove/{projectId}", 51)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    
    

}
