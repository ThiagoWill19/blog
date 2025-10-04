package com.artigo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artigo.configurations.UserDetailsImpl;

@Controller
@RequestMapping("/creatorArea")
public class UserController {

	@GetMapping
	public String creatorArea(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("userName",userDetails.getUser().getName());
		return "/creatorAreaPage";
	}
	
}
