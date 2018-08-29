package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigationController {
	
	@RequestMapping(value="/greeting")
	public String initialRequest(Model model) {
		model.addAttribute("text", "Suprasanna");
		return "greeting";
	}

}
