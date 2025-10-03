package com.artigo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/creatorArea")
public class UserController {

	@GetMapping
	public String creatorArea(Model model) {
		return "/creatorAreaPage";
	}
}
