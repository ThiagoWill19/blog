package com.artigo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.artigo.services.ArticleService;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	

}
