package com.artigo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewArticleDto {

	@NotBlank(message = "Título é um campo obrigatório")
	private String title;
	@NotBlank(message = "Conteúdo é um campo obrigatório")
	private String content;
	
}
