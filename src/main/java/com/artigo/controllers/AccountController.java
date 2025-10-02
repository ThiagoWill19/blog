package com.artigo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artigo.dtos.NewUserDTO;
import com.artigo.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private UserService userService;
	

	@GetMapping("/login")
	public String login() {
		return "Login";
	}
	
	@GetMapping("/create-account")
	public String createAccount(Model model) {
		model.addAttribute("newUser", new NewUserDTO());
		return "/newAccountPage";
	}
	
	@PostMapping
	public String createNewUser(Model model, @Valid @ModelAttribute("newUser") NewUserDTO newUser, BindingResult result) {
		
		if(result.hasErrors()) {
			return "/newAccountPage";
		}
		
		try {
			userService.createNewUser(newUser);
		} catch (Exception e) {
			 model.addAttribute("error", e.getMessage());
			return "/newAccountPage";
		}

		return "redirect:/login";
	}
}
