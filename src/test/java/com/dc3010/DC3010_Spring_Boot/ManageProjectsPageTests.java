package com.dc3010.DC3010_Spring_Boot;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.dc3010.DC3010_Spring_Boot.Repository.ProjectRepository;
import com.dc3010.DC3010_Spring_Boot.Service.ProjectService;
import com.dc3010.DC3010_Spring_Boot.Service.ToolService;
import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.Project;
import com.dc3010.DC3010_Spring_Boot.beans.Tool;

@SpringBootTest
@AutoConfigureMockMvc
public class ManageProjectsPageTests {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectRepository projectRepo;

	@Autowired
	private ToolService toolService;

	@Autowired
	private UserService userService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when the controller is on view manageproject.html and all tools in db are in model")
	void testDoGet() throws Exception {

		MvcResult mvcResult = mockMvc.perform(get("/manage")).andExpect(status().isOk())
				.andExpect(view().name("manageproject.html")).andExpect(model().attributeExists("toolRecords"))
				.andReturn();

		// Check the service and model number of tool records is the same size
		Map<String, Object> modelAttributes = mvcResult.getModelAndView().getModel();
		List<Tool> allTools = (List<Tool>) modelAttributes.get("toolRecords");

		// Expect the modelAttribute to have same size as toolService.findAll()
		assertEquals(toolService.findAll().size(), allTools.size());

	}

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when controller successfully creates a project and is validated against the db record created")
	void testCreateProjectSuccess() throws Exception {
		String[] toolsUsed = { "Cucumber", "Selenium" };

		mockMvc.perform(post("/create/project").param("client-name", "UnitClient").param("project-name", "UnitProject")
				.param("project-grade", "A5").param("start-date", "2023-07-20").param("end-date", "2023-07-25")
				.param("work-location", "Remote").param("general-description", "Unit Test Description")
				.param("tools-used", toolsUsed)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/manage")).andExpect(flash().attributeExists("success"));

		// Get the newly created project
		List<Project> allProjects = projectService.findAll();
		Project newProject = new Project();

		// Find the project we created
		for (Project project : allProjects) {
			if (project.getCompanyName().equalsIgnoreCase("UnitClient")) {
				newProject = project;
				break;
			}
		}

		// Check the values are correct
		assertEquals(newProject.getCompanyName(), "UnitClient");
		assertEquals(newProject.getProjectName(), "UnitProject");
		assertEquals(newProject.getGrade(), "A5");
		assertEquals(newProject.getStartDate().toString(), "2023-07-20");
		assertEquals(newProject.getEndDate().toString(), "2023-07-25");
		assertEquals(newProject.getWorkLocation().toString(), "Remote");
		assertEquals(newProject.getGeneralDescription(), "Unit Test Description");

		// Finally remove from db
		projectRepo.delete(newProject);

	}

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when controller successfully creates a project with client site location and address parameters and is validated against the db record created")
	void testCreateProjectSuccessClientSite() throws Exception {
		String[] toolsUsed = { "Cucumber", "Selenium" };

		mockMvc.perform(post("/create/project").param("client-name", "UnitClient").param("project-name", "UnitProject")
				.param("project-grade", "A5").param("start-date", "2023-07-20").param("end-date", "2023-07-25")
				.param("work-location", "Client_Site").param("address", "This is a unit test address")
				.param("general-description", "Unit Test Description").param("tools-used", toolsUsed))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/manage"))
				.andExpect(flash().attributeExists("success"));

		// Get the newly created project
		List<Project> allProjects = projectService.findAll();
		Project newProject = new Project();

		// Find the project we created
		for (Project project : allProjects) {
			if (project.getCompanyName().equalsIgnoreCase("UnitClient")) {
				newProject = project;
				break;
			}
		}

		// Check the values are correct
		assertEquals(newProject.getCompanyName(), "UnitClient");
		assertEquals(newProject.getProjectName(), "UnitProject");
		assertEquals(newProject.getGrade(), "A5");
		assertEquals(newProject.getStartDate().toString(), "2023-07-20");
		assertEquals(newProject.getEndDate().toString(), "2023-07-25");
		assertEquals(newProject.getWorkLocation().toString(), "Client_Site");
		assertEquals(newProject.getAddress(), "This is a unit test address");

		assertEquals(newProject.getGeneralDescription(), "Unit Test Description");

		// Finally remove from db
		projectRepo.delete(newProject);

	}

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when controller successfully creates a project with no toolsUsed given and is validated against the db record created")
	void testCreateProjectSuccessToolsNull() throws Exception {

		mockMvc.perform(post("/create/project").param("client-name", "UnitClient").param("project-name", "UnitProject")
				.param("project-grade", "A5").param("start-date", "2023-07-20").param("end-date", "2023-07-25")
				.param("work-location", "Client_Site").param("address", "This is a unit test address")
				.param("general-description", "Unit Test Description")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/manage")).andExpect(flash().attributeExists("success"));

		// Get the newly created project
		List<Project> allProjects = projectService.findAll();
		Project newProject = new Project();

		// Find the project we created
		for (Project project : allProjects) {
			if (project.getCompanyName().equalsIgnoreCase("UnitClient")) {
				newProject = project;
				break;
			}
		}

		// Check the values are correct
		assertEquals(newProject.getCompanyName(), "UnitClient");
		assertEquals(newProject.getProjectName(), "UnitProject");
		assertEquals(newProject.getGrade(), "A5");
		assertEquals(newProject.getStartDate().toString(), "2023-07-20");
		assertEquals(newProject.getEndDate().toString(), "2023-07-25");
		assertEquals(newProject.getWorkLocation().toString(), "Client_Site");
		assertEquals(newProject.getAddress(), "This is a unit test address");
		assertEquals(newProject.getGeneralDescription(), "Unit Test Description");

		// Finally remove from db
		projectRepo.delete(newProject);

	}

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when controller successfully creates a project with no start date given and is validated against the db record created")
	void testCreateProjectSuccessNoStartDate() throws Exception {

		mockMvc.perform(post("/create/project").param("client-name", "UnitClient").param("project-name", "UnitProject")
				.param("project-grade", "A5").param("start-date", "").param("end-date", "2023-07-25")
				.param("work-location", "Client_Site").param("address", "This is a unit test address")
				.param("general-description", "Unit Test Description")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/manage")).andExpect(flash().attributeExists("success"));

		// Get the newly created project
		List<Project> allProjects = projectService.findAll();
		Project newProject = new Project();

		// Find the project we created
		for (Project project : allProjects) {
			if (project.getCompanyName().equalsIgnoreCase("UnitClient")) {
				newProject = project;
				break;
			}
		}

		// Check the values are correct
		assertEquals(newProject.getCompanyName(), "UnitClient");
		assertEquals(newProject.getProjectName(), "UnitProject");
		assertEquals(newProject.getGrade(), "A5");

		// Check Start date is empty
		assertNull(newProject.getStartDate());
		assertEquals(newProject.getEndDate().toString(), "2023-07-25");
		assertEquals(newProject.getWorkLocation().toString(), "Client_Site");
		assertEquals(newProject.getAddress(), "This is a unit test address");
		assertEquals(newProject.getGeneralDescription(), "Unit Test Description");

		// Finally remove from db
		projectRepo.delete(newProject);

	}

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when controller successfully creates a project with no end date given and is validated against the db record created")
	void testCreateProjectSuccessNoEndDate() throws Exception {

		mockMvc.perform(post("/create/project").param("client-name", "UnitClient").param("project-name", "UnitProject")
				.param("project-grade", "A5").param("start-date", "2023-07-25").param("end-date", "")
				.param("work-location", "Client_Site").param("address", "This is a unit test address")
				.param("general-description", "Unit Test Description")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/manage")).andExpect(flash().attributeExists("success"));

		// Get the newly created project
		List<Project> allProjects = projectService.findAll();
		Project newProject = new Project();

		// Find the project we created
		for (Project project : allProjects) {
			if (project.getCompanyName().equalsIgnoreCase("UnitClient")) {
				newProject = project;
				break;
			}
		}

		// Check the values are correct
		assertEquals(newProject.getCompanyName(), "UnitClient");
		assertEquals(newProject.getProjectName(), "UnitProject");
		assertEquals(newProject.getGrade(), "A5");
		assertEquals(newProject.getStartDate().toString(), "2023-07-25");

		// Check End date is empty
		assertNull(newProject.getEndDate());
		assertEquals(newProject.getWorkLocation().toString(), "Client_Site");
		assertEquals(newProject.getAddress(), "This is a unit test address");
		assertEquals(newProject.getGeneralDescription(), "Unit Test Description");

		// Finally remove from db
		projectRepo.delete(newProject);

	}

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when controller fails to create a project with an end date value before the start date value")
	void testCreateProjectFailEndDateBeforeStartDate() throws Exception {

		String[] toolsUsed = { "Cucumber", "Selenium" };

		mockMvc.perform(post("/create/project").param("client-name", "ClientName").param("project-name", "ProjectName")
				.param("project-grade", "Grade").param("start-date", "2023-07-25").param("end-date", "2023-07-20")
				.param("work-location", "Remote").param("general-description", "Description")
				.param("tools-used", toolsUsed)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/manage")).andExpect(flash().attributeExists("error"));

	}

	@Test
	@WithUserDetails("rm_user1")
	@DisplayName("Test should pass when controller successfully deletes a project from the DB")
	void testDeleteProject() throws Exception {

		mockMvc.perform(post("/create/project").param("client-name", "UnitClient").param("project-name", "UnitProject")
				.param("project-grade", "A5").param("start-date", "2023-07-25").param("end-date", "")
				.param("work-location", "Client_Site").param("address", "This is a unit test address")
				.param("general-description", "Unit Test Description")).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/manage")).andExpect(flash().attributeExists("success"));

		//Get the newly created project
		List<Project> allProjects = projectService.findAll();
		Project newProject = new Project();

		//Find the project created
		for (Project project : allProjects) {
			if (project.getCompanyName().equalsIgnoreCase("UnitClient")) {
				newProject = project;
				break;
			}
		}

		int id = newProject.getProjectID();

		mockMvc.perform(delete("/project/delete/{projectId}", id)).andExpect(status().isOk());

		assertThrows(NoSuchElementException.class, () -> projectService.findOne(id));

	}

}
