package com.artigo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.artigo.dtos.NewUserDTO;
import com.artigo.exceptions.CreateAccountException;
import com.artigo.repositories.UserRepository;
import com.artigo.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder encoder;

	@InjectMocks
	private UserService userService;

	private NewUserDTO newUserDto;

	@BeforeEach
	void setup() {
		newUserDto = new NewUserDTO();
		newUserDto.setName("Thiago");
		newUserDto.setEmail("thiago@email.com");
		newUserDto.setPassword("Ab1234#");

	}

	@DisplayName("Create new user if email does not exist in DB")
	@Test
	void criateNewUserWhenEmailNotExists() throws Exception {

		when(userRepository.existsByEmail(newUserDto.getEmail())).thenReturn(false);
		
		when(encoder.encode(newUserDto.getPassword())).thenReturn("encoded-password");
		
		userService.createNewUser(newUserDto);
		
		verify(userRepository).save(argThat(user ->
				user.getName().equals("Thiago") &&
				user.getEmail().equals("thiago@email.com") &&
				user.getPassword().equals("encoded-password")));
		
		 verify(encoder).encode("Ab1234#");

	}
	
	
	@DisplayName("Should throw exception when email already exists")
	@Test
	void shoudThrowExceptionWhenEmailAlreadyExists() {
		
		when(userRepository.existsByEmail(newUserDto.getEmail())).thenReturn(true);
		
		CreateAccountException ex = assertThrows(CreateAccountException.class,
				() -> userService.createNewUser(newUserDto));
		
		assertEquals("Email já cadastrado no sistema!", ex.getMessage());
		verify(userRepository, never()).save(any());
		verify(encoder, never()).encode(any());	
	}

}
