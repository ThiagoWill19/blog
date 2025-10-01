package com.artigo.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artigo.models.Article;

public interface ArticleRepository extends JpaRepository<Article, UUID>{

}
