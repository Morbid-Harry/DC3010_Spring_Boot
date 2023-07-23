package com.dc3010.DC3010_Spring_Boot;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.User;



	

@SpringBootTest
@AutoConfigureMockMvc
public class FavouritesPageTests {
	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    
    @Autowired
    ProjectService projectService;
    
    @Autowired
    ToolService toolService;
    
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when the users favorite project size and model attribute are the same size")
    void testDoGet() throws Exception {
    	
    	User loggedInUser = userService.getUserByLogin("haedward");
    	      		
    	MvcResult mvcResult = mockMvc.perform(get("/favourites"))
                .andExpect(status().isOk())
                .andExpect(view().name("favourites.html"))
                .andExpect(model().attributeExists("user", "projectRecords", "allTools"))
                .andExpect(model().size(3))
                .andReturn();
        
    	//Retrieve model attributes from MvcResult
        Map<String, Object> modelAttributes = mvcResult.getModelAndView().getModel();
        
        //Access user attribute is the correct user
        User userAttribute = (User) modelAttributes.get("user");
        assertEquals(loggedInUser.getFirstName(), userAttribute.getFirstName());
        
        //Access project attribte is the correct project size
        List<Project> projectAttribute = (List<Project>) modelAttributes.get("projectRecords");
        assertEquals(loggedInUser.getFavourtiedProjects().size(), projectAttribute.size());
        
        
    }
    
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when the users favorite project size decereases when calling /favourite/remove/{projectId}")
    void testRemovefavouriteProject() throws Exception {
    	User loggedInUser = userService.getUserByLogin("haedward");
    	//Check size of favourites before removing is 2
    	int usersFavoutiesBeforeRemoving = loggedInUser.getFavourtiedProjects().size();
    	
    	assertEquals(2, usersFavoutiesBeforeRemoving);
    	
        int projectIdToRemove = 50;


        mockMvc.perform(post("/favourite/remove/{projectId}", projectIdToRemove)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        //Get the updated user
        loggedInUser = userService.getUserByLogin("haedward");
        //Check size of favouties after removing is 1
        int usersFavoutiesAfterRemoving = loggedInUser.getFavourtiedProjects().size();
        assertEquals(1, usersFavoutiesAfterRemoving);
        
        //Add the project back to favourites so test can be ran again
        mockMvc.perform(post("/favourite/{projectId}", projectIdToRemove)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
