package com.artigo.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.artigo.configurations.UserDetailsImpl;
import com.artigo.dtos.ArticleDto;
import com.artigo.dtos.NewArticleDto;
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
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "ALL") String title) {
		
		Page<ArticleDto> articlePage =  articleService.findByTitle(title, userDetails.getUser(), page, size);
		
		model.addAttribute("userName",userDetails.getUser().getName());
		model.addAttribute("articles", articlePage);
		model.addAttribute("currentPage", articlePage.getNumber());
        model.addAttribute("totalPages", articlePage.getTotalPages());
        model.addAttribute("title", title);
		return "/user/creatorAreaPage";
	}
	
	@GetMapping("/newArticle")
	public String newArticlePage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		model.addAttribute("userName",userDetails.getUser().getName());
        model.addAttribute("newArticleDto", new NewArticleDto());
		return "/article/newArticlePage";
	}
	
	@PostMapping("/newArticle")
	public String newArticle(Model model,
			@AuthenticationPrincipal UserDetailsImpl userDetails,
			NewArticleDto newArticleDto) {
		
		articleService.newArticle(newArticleDto, userDetails.getUser());
		
		return "redirect:/creatorArea";
		
	}
	
	@GetMapping("/article/{id}")
	public String findArticleById(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable UUID id,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		try {
			
			model.addAttribute("article", articleService.findById(id, userDetails.getUser()));
			return "/article/articlePage";
			
		} catch (Exception e) {
			
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/creatorArea";
		
	}
	
	@GetMapping("/article/delete/{id}")
	public String deleteArticle(Model model,
			RedirectAttributes redirectAttributes,
			@PathVariable UUID id,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		try {
			articleService.deleteById(id, userDetails.getUser());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/creatorArea";
	}
	
	@GetMapping("/article/edit/{id}")
	public String editArticlePage(Model model,
			@PathVariable UUID id,
			RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserDetailsImpl userDetails) {
		
		try {
			model.addAttribute("userName",userDetails.getUser().getName());
			model.addAttribute("articleDto", articleService.findById(id, userDetails.getUser()));
			return "/article/editArticlePage2";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/creatorArea";
	}
	
	@PostMapping("/article/edit")
	public String editArticle(Model model,
			ArticleDto articleDto,
			RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserDetailsImpl userDetails ) {
		
		try {
			articleService.editArticle(articleDto, userDetails.getUser());
			return "redirect:/creatorArea/article/" + articleDto.getId();
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/creatorArea";
	}
	
}
