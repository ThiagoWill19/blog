package com.artigo.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.artigo.models.Article;
import com.artigo.models.User;

public interface ArticleRepository extends JpaRepository<Article, UUID>{

		Page<Article> findAllByAutorOrderByCreationDateDesc(Pageable pageable, User user);
		
}
