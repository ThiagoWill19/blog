package com.artigo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.artigo.configurations.UserDetailsImpl;
import com.artigo.dtos.NewArticleDto;
import com.artigo.models.Article;
import com.artigo.services.ArticleService;

@Controller
@RequestMapping("/creatorArea")
public class UserController {
	
	@Autowired
	private ArticleService articleService;

	@GetMapping
	public String creatorArea(Model model, 
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		
		Page<Article> articlePage =  articleService.getAllByUser(page, size, userDetails.getUser());
		
		model.addAttribute("userName",userDetails.getUser().getName());
		model.addAttribute("articles", articlePage);
		model.addAttribute("currentPage", articlePage.getNumber());
        model.addAttribute("totalPages", articlePage.getTotalPages());
		return "/creatorAreaPage";
	}
	
	@GetMapping("/newArticle")
	public String newArticlePage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("userName",userDetails.getUser().getName());
        model.addAttribute("newArticleDto", new NewArticleDto());
		return "/newArticlePage";
	}
	
	@PostMapping("/newArticle")
	public String newArticle(Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			NewArticleDto newArticleDto) {
		
		articleService.newArticle(newArticleDto, userDetails.getUser());
		
		return "redirect:/creatorArea";
		
	}
	
}
