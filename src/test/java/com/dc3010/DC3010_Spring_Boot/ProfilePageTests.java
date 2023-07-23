package com.dc3010.DC3010_Spring_Boot;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.dc3010.DC3010_Spring_Boot.Service.UserService;


@SpringBootTest
@AutoConfigureMockMvc
public class ProfilePageTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithUserDetails("haedward")
	@DisplayName("Test should pass when controller sends to view editprofile.html and attributes grades and user exist")
	public void testGetProfilePage() throws Exception {
		
		mockMvc.perform(get("/profile")).andExpect(status().isOk())
                .andExpect(view().name("editprofile.html"))
                .andExpect(model().attributeExists("grades"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("grades", Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10")));
	}
	
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when a controller post request to /update/user/details returns a flash attribute detailssuccess with given parameters")
    public void testUpdateUserDetails() throws Exception {


        mockMvc.perform(post("/update/user/details")
                .param("update-first-name", "John")
                .param("update-last-name", "Doe")
                .param("update-user-grade", "A2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("detailssuccess"));
        
        //Reset the user changes
        mockMvc.perform(post("/update/user/details")
                .param("update-first-name", "Harry")
                .param("update-last-name", "Edwards")
                .param("update-user-grade", "A5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("detailssuccess"));
    }
    
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when a controller post request to /update/user/password returns a flash attribute passwordsuccess with given parameters")
    public void testUpdateUserPasswordSuccess() throws Exception {
        String currentPassword = "pass";
        String newPassword = "newPassword";


        mockMvc.perform(post("/update/user/password")
                .param("current-password", currentPassword)
                .param("new-password", newPassword)
                .param("confirm-password", newPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("passwordsuccess"));
        
        //Reset the password changes
        currentPassword = "newPassword";
        newPassword = "pass";
        mockMvc.perform(post("/update/user/password")
                .param("current-password", currentPassword)
                .param("new-password", newPassword)
                .param("confirm-password", newPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("passwordsuccess"));

    }
    
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when a controller post request to /update/user/password returns a flash attribute passwordexistingmatchingfailure with invalid parameters")
    public void testUpdateUserPasswordCurrentNotMatchingFailure() throws Exception {
        String currentPassword = "notactualcurrentpassword";
        String newPassword = "newPassword";


        mockMvc.perform(post("/update/user/password")
                .param("current-password", currentPassword)
                .param("new-password", newPassword)
                .param("confirm-password", newPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("passwordexistingmatchingfailure"));

    }
    
    @Test
    @WithUserDetails("haedward")
    @DisplayName("Test should pass when a controller post request to /update/user/password returns a flash attribute passwordpairmatchingfailure with invalid parameters")
    public void testUpdateUserPasswordConfirmNotMatchingFailure() throws Exception {
        String currentPassword = "pass";
        String newPassword = "newPassword";
        String notMatchingPassword = "newPasswordNotMatching";


        mockMvc.perform(post("/update/user/password")
                .param("current-password", currentPassword)
                .param("new-password", newPassword)
                .param("confirm-password", notMatchingPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"))
                .andExpect(flash().attributeExists("passwordpairmatchingfailure"));

    }
}
	
	 
