package com.artigo.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.artigo.models.Article;

import lombok.Data;

@Data
public class ArticleDto {

	private UUID id;
	private String title;
	private String content;
	private LocalDateTime creationDate;
	private String autor;
	
	public ArticleDto(Article article) {
		this.id = article.getId();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.creationDate = article.getCreationDate();
		this.autor = article.getAutor().getName();
	}
	
	public ArticleDto() {
		
	}
	
	
}
