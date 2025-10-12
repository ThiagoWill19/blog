package com.artigo.services;

import java.time.LocalDate;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artigo.dtos.NewArticleDto;
import com.artigo.models.Article;
import com.artigo.models.User;
import com.artigo.repositories.ArticleRepository;

@Service
public class ArticleService {
	
	@Autowired
	ArticleRepository articleRepository;
	
	public void newArticle(NewArticleDto newArticleDto, User user) {
		
		Article article = new Article();
		
		//Sanitizar conteúdo HTML
		article.setTitle(Jsoup.clean(newArticleDto.getTitle(), Safelist.none()));
		article.setContent(Jsoup.clean(newArticleDto.getContent(), Safelist.relaxed()));
		
		article.setCreationDate(LocalDate.now());
		
		article.setAutor(user);
		
		articleRepository.save(article);
	}

}
