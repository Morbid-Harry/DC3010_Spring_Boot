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

@Controller
public class ProfilePage {
	
	@Autowired
	private UserService service;

	@GetMapping("/profile")
	public ModelAndView getProfilePage(Model model, @AuthenticationPrincipal SecUserDetails userDetails)
	{
		User user = service.getUserByLogin(userDetails.getUsername());
		
		//Instead of comparing the users value against every grade dropdown in html 
		model.addAttribute("grades", Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10"));
		
		model.addAttribute("user", user);
		
		return new ModelAndView("editprofile.html");
	}
	
	@PostMapping("/update/user/details")
	public RedirectView updateUserDetails(RedirectAttributes redirectAttributes,Model model, @AuthenticationPrincipal SecUserDetails userDetails,  @RequestParam("update-first-name") String firstName, @RequestParam("update-last-name") String lastName, @RequestParam("update-user-grade") String grade)
	{
		User currentUser = service.getUserByLogin(userDetails.getUsername());
		
		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		currentUser.setGrade(grade);
		
		service.addUser(currentUser);
		
		redirectAttributes.addFlashAttribute("detailssuccess", true);
		
		return new RedirectView("/profile");	
	}
	
	@PostMapping("/update/user/password")
	public RedirectView updateUserPassword(RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal SecUserDetails userDetails, @RequestParam("current-password") String currentPassword, @RequestParam("new-password") String newPassword, @RequestParam("confirm-password") String confirmPassword)
	{
		User currentUser = service.getUserByLogin(userDetails.getUsername());
		PasswordUtils passUtil = new PasswordUtils();	
		
		System.out.println();
		
		//Check if current password matches password in DB
		if(passUtil.matches(currentPassword, userDetails.getPassword()))
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
