package com.artigo.services;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.artigo.dtos.ArticleDto;
import com.artigo.dtos.NewArticleDto;
import com.artigo.exceptions.ArticleNotFoundException;
import com.artigo.models.Article;
import com.artigo.models.User;
import com.artigo.repositories.ArticleRepository;

@Service
public class ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	public void newArticle(NewArticleDto newArticleDto, User user) {

		Article article = new Article();

		// Sanitizar conteúdo HTML
		article.setTitle(Jsoup.clean(newArticleDto.getTitle(), Safelist.none()));
		article.setContent(Jsoup.clean(newArticleDto.getContent(), Safelist.relaxed()));

		article.setCreationDate(LocalDateTime.now());

		article.setAutor(user);

		articleRepository.save(article);
	}
	

	public Page<ArticleDto> getArticles(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Article> pag = articleRepository.findAll(pageable);
		return pag.map(p -> new ArticleDto(p));
	}
	

	public Page<ArticleDto> getAllByUser(int page, int size, User user) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Article> pag = articleRepository.findAllByAutorOrderByCreationDateDesc(pageable, user);
		return pag.map(p -> new ArticleDto(p));
	}
	

	public ArticleDto findById(UUID id, User user) throws Exception {

		if (!articleRepository.existsById(id)) {
			throw new ArticleNotFoundException("Artigo não encontrado com o ID informado");
		}

		Article article = articleRepository.findById(id).get();

		if (article.getAutor().getEmail().equals(user.getEmail())) {

			ArticleDto articleDto = new ArticleDto(article);
			return articleDto;

		} else {
			throw new AccessDeniedException("Você não tem permissão para acessar este artigo!");
		}

	}
	

	public void deleteById(UUID id, User user) throws Exception {

		if (!articleRepository.existsById(id)) {
			throw new ArticleNotFoundException("Artigo não encontrado com o ID informado");
		}

		Article article = articleRepository.findById(id).get();

		if (article.getAutor().getEmail().equals(user.getEmail())) {
			articleRepository.deleteById(id);
		} else {
			throw new AccessDeniedException("Você não tem permissão para excluir este artigo!");
		}
	}
	

	public Page<ArticleDto> findByTitle(String title, User user, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Article> pag;
		
		if (title.equals("ALL")) {
			pag = articleRepository.findAllByAutorOrderByCreationDateDesc(pageable, user);
		} else {
			pag = articleRepository.findAllByAutor_nameAndTitleContainingIgnoreCaseOrderByCreationDateDesc(user.getName(), title, pageable);
		}

		return pag.map(p -> new ArticleDto(p));
	}
	
	
	public void editArticle(ArticleDto articleDto, User user) throws Exception {
		
		if (!articleRepository.existsById(articleDto.getId())) {
			throw new ArticleNotFoundException("Artigo não encontrado com o ID informado");
		}
		
		Article article = articleRepository.findById(articleDto.getId()).get();
		
		if (article.getAutor().getEmail().equals(user.getEmail())) {
			
			article.setTitle(Jsoup.clean(articleDto.getTitle(), Safelist.none()));
			article.setContent(Jsoup.clean(articleDto.getContent(), Safelist.relaxed()));
			articleRepository.save(article);
			
		} else {
			throw new AccessDeniedException("Você não tem permissão para editar este artigo!");
		}
		
	}
}
