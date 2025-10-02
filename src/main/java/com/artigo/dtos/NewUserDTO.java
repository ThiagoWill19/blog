package com.artigo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {

	@NotBlank(message = "O nome é obrigatório.")
	private String name;
	
	@NotBlank(message = "O e-mail é obrigatório.")
	@Email(message = "O e-mail informado é inválido.")
	private String email;
	
	@Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
	@Pattern(regexp = "^[\\w\\p{Punct}]{6,}$", message = "A senha deve conter no mínimo 6 caracteres e não pode conter espaços.")
	private String password;
}
