package com.dc3010.DC3010_Spring_Boot.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.dc3010.DC3010_Spring_Boot.Service.UserService;
import com.dc3010.DC3010_Spring_Boot.beans.User;
import com.dc3010.DC3010_Spring_Boot.util.PasswordUtils;
import com.dc3010.DC3010_Spring_Boot.util.SecUserDetails;

/**
 * Controller which handles HTTP response and requests for the Profile page
 * @author Harry
 *
 */
@Controller
public class ProfilePage {
	
	@Autowired
	private UserService service;
	
	/**
	 * When a user navigates to the profile page this function is called and will display the page along with autopopulating the users information into the form
	 * @param model contains data of the associated page such as grades and the logged in users information to autopopulate the profile form
	 * @param userDetails the current logged in user
	 * @return the editprofile.html view along with 2 model attributes grades and user
	 */
	@GetMapping("/profile")
	public ModelAndView getProfilePage(Model model, @AuthenticationPrincipal SecUserDetails userDetails)
	{
		User user = service.getUserByLogin(userDetails.getUsername());
		
		//Instead of comparing the users value against every grade dropdown in html 
		model.addAttribute("grades", Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10"));
		
		model.addAttribute("user", user);
		
		return new ModelAndView("editprofile.html");
	}
	
	/**
	 * When a user updates their details this function is called majority of the validation is done client-side
	 * @param redirectAttributes as the user is redirected back to the same page this is used to display a success alert to the user after updating their profile
	 * @param userDetails details of the loggined in user
	 * @param firstName the first name the user wants to change their name to in the database
	 * @param lastName the last name the user wants to change their name to in the database
	 * @param grade the grade the user wants to change their grade to in the database
	 * @return a redirect to the same view along with a success alert to the user
	 */
	@PostMapping("/update/user/details")
	public RedirectView updateUserDetails(RedirectAttributes redirectAttributes, @AuthenticationPrincipal SecUserDetails userDetails,  @RequestParam("update-first-name") String firstName, @RequestParam("update-last-name") String lastName, @RequestParam("update-user-grade") String grade)
	{
		User currentUser = service.getUserByLogin(userDetails.getUsername());
		
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setGrade(grade);
		
		service.addUser(currentUser);
		
		redirectAttributes.addFlashAttribute("detailssuccess", true);
		
		return new RedirectView("/profile");	
	}
	
	/**
	 * When a user updates their password this function is called. 3 scenarios can occur. Either password changed successfully, the current password is incorrect, or the confirm and new password don't match for the later 2 a failure message will be displayed
	 * @param redirectAttributes 3 possible attributes are given either passwordsuccess, passwordpairmatchingfailure or passwordexistingmatchingfailure and this determines the type of alert shown to user
	 * @param userDetails the details of the logged in user
	 * @param currentPassword the current password given by the logged in user
	 * @param newPassword the new password given by the logged in user
	 * @param confirmPassword this should match the new password
	 * @return returns the same view with 1 of 3 flash attributes which will determine the type of alert shown to the user
	 */
	@PostMapping("/update/user/password")
	public RedirectView updateUserPassword(RedirectAttributes redirectAttributes, @AuthenticationPrincipal SecUserDetails userDetails, @RequestParam("current-password") String currentPassword, @RequestParam("new-password") String newPassword, @RequestParam("confirm-password") String confirmPassword)
	{
		User currentUser = service.getUserByLogin(userDetails.getUsername());
	
		PasswordUtils passUtil = new PasswordUtils();	
		
		System.out.println();
		
		//Check if current password matches password in DB
		if(passUtil.matches(currentPassword, currentUser.getPassword()))
		{
			//Then need to check if the new password and confirm password are the same
			if(confirmPassword.equals(newPassword))
			{
				//Then update the user in DB
				currentUser.setPassword(passUtil.encode(newPassword));
				service.addUser(currentUser);
				redirectAttributes.addFlashAttribute("passwordsuccess", true);
			}
			else
			{
				//if new pass and confirm pass dont match then return with error message
				redirectAttributes.addFlashAttribute("passwordpairmatchingfailure", true);
			}	
		}
		else {
			//if current password doesnt match return with incorrect matching password
			redirectAttributes.addFlashAttribute("passwordexistingmatchingfailure", true);
		}
		
		return new RedirectView("/profile");
	}
}
