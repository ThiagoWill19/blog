package com.artigo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.artigo.dtos.NewArticleDto;
import com.artigo.models.Article;
import com.artigo.models.User;
import com.artigo.repositories.ArticleRepository;
import com.artigo.services.ArticleService;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {
	
	@Mock
	private ArticleRepository articleRepository;
	
	@InjectMocks
	private ArticleService articleService;
	
	private NewArticleDto dto;
	private User user;
	
	@BeforeEach
	void setup() {
		dto = new NewArticleDto();
		dto.setTitle("<h1>Título Teste</h1>");
		dto.setContent("<p>Conteúdo <b>importante</b></p><script>alert('xss');</script>");
		
		user = new User();
		user.setId(UUID.randomUUID());
		user.setName("Thiago");
	}
	
	@DisplayName("Must create articles with sanitized data")
	@Test
	void mustCreateArticlesWithSanitizedData() {
		
		articleService.newArticle(dto, user);
		
		ArgumentCaptor<Article> captor = ArgumentCaptor.forClass(Article.class);
		verify(articleRepository).save(captor.capture());
		Article articleSaved = captor.getValue();
		
		assertEquals("Título Teste", articleSaved.getTitle());
		
		assertTrue(articleSaved.getContent().contains("<b>importante</b>"));
		assertFalse(articleSaved.getContent().contains("<script>"));
		
		assertEquals(LocalDate.now(), articleSaved.getCreationDate());
		
		assertEquals(user, articleSaved.getAutor());
		
	}

}
