package com.dc3010.DC3010_Spring_Boot;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.dc3010.DC3010_Spring_Boot.Repository.UserRepository;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.User;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateUserPageTests {
	
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;
    
    @Autowired 
    private UserRepository userRepo;

    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when controller get request to /create returns the view createuser.html")
    public void testDoGet() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createuser.html"));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when controller post request to /register returns the success attribute with valid standard user parameters")
    public void testDoPostSuccessUserRole() throws Exception {
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String username = "johnDoe";
        String password = "password";
        String grade = "A1";
        

        
        mockMvc.perform(post("/register")
                .param("register-email", email)
                .param("register-first-name", firstName)
                .param("register-last-name", lastName)
                .param("register-user-name", username)
                .param("register-password", password)
                .param("register-user-grade", grade))
                .andExpect(status().isOk())
                .andExpect(view().name("createuser.html"))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attribute("success", "User created!"));
        
        //Delete the user afterwards
        User newUser = userService.getUserByLogin("johnDoe");
        userRepo.delete(newUser);
    }
    
    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when controller post request to /register returns the success attribute with valid admin user parameters")
    public void testDoPostSuccessAdminRole() throws Exception {
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String username = "johnDoe";
        String password = "password";
        String grade = "A1";
        String[] role = new String[1];
        role[0] = "Admin";
        

        
        mockMvc.perform(post("/register")
                .param("register-email", email)
                .param("register-first-name", firstName)
                .param("register-last-name", lastName)
                .param("register-user-name", username)
                .param("register-password", password)
                .param("register-user-grade", grade)
        		.param("roles", role))
                .andExpect(status().isOk())
                .andExpect(view().name("createuser.html"))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attribute("success", "User created!"));
        
        //Delete the user afterwards
        User newUser = userService.getUserByLogin("johnDoe");
        userRepo.delete(newUser);
    }
    
    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when controller post request to /register returns the success attribute with valid resource manager user parameters")
    public void testDoPostSuccessRMRole() throws Exception {
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String username = "johnDoe";
        String password = "password";
        String grade = "A1";
        String[] role = new String[1];
        role[0] = "RM";
        

        
        mockMvc.perform(post("/register")
                .param("register-email", email)
                .param("register-first-name", firstName)
                .param("register-last-name", lastName)
                .param("register-user-name", username)
                .param("register-password", password)
                .param("register-user-grade", grade)
        		.param("roles", role))
                .andExpect(status().isOk())
                .andExpect(view().name("createuser.html"))
                .andExpect(model().attributeExists("success"))
                .andExpect(model().attribute("success", "User created!"));
        
        //Delete the user afterwards
        User newUser = userService.getUserByLogin("johnDoe");
        userRepo.delete(newUser);
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when controller post request to /register returns the error attribute with paramaters that include an email and username that are already in use")
    public void testDoPostUserAlreadyExistsBothEmailAndUserName() throws Exception {
        String email = "harry.edwards@capgemini.com";
        String firstName = "John";
        String lastName = "Doe";
        String username = "haedward";
        String password = "password";
        String grade = "A1";


        mockMvc.perform(post("/register")
                .param("register-email", email)
                .param("register-first-name", firstName)
                .param("register-last-name", lastName)
                .param("register-user-name", username)
                .param("register-password", password)
                .param("register-user-grade", grade))
                .andExpect(status().isOk())
                .andExpect(view().name("createuser.html"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "User already exists"));

    }
    
    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when controller post request to /register returns the error attribute with paramaters that include an email that is already in use")
    public void testDoPostUserAlreadyExistsJustEmail() throws Exception {
        String email = "harry.edwards@capgemini.com";
        String firstName = "John";
        String lastName = "Doe";
        String username = "NEWUSERNAME";
        String password = "password";
        String grade = "A1";


        mockMvc.perform(post("/register")
                .param("register-email", email)
                .param("register-first-name", firstName)
                .param("register-last-name", lastName)
                .param("register-user-name", username)
                .param("register-password", password)
                .param("register-user-grade", grade))
                .andExpect(status().isOk())
                .andExpect(view().name("createuser.html"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "User already exists"));

    }
    
    @Test
    @WithUserDetails("admin")
    @DisplayName("Test should pass when controller post request to /register returns the error attribute with paramaters that include a username that is already in use")
    public void testDoPostUserAlreadyExistsJustUserName() throws Exception {
        String email = "new@email.com";
        String firstName = "John";
        String lastName = "Doe";
        String username = "haedward";
        String password = "password";
        String grade = "A1";


        mockMvc.perform(post("/register")
                .param("register-email", email)
                .param("register-first-name", firstName)
                .param("register-last-name", lastName)
                .param("register-user-name", username)
                .param("register-password", password)
                .param("register-user-grade", grade))
                .andExpect(status().isOk())
                .andExpect(view().name("createuser.html"))
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "User already exists"));

    }

}
