package com.dc3010.DC3010_Spring_Boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingPage {
	@RequestMapping("/")
	public String homePage() {
		
		return "/login";
	}
}
