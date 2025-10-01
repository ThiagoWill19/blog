package com.artigo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.artigo.dtos.NewUserDTO;
import com.artigo.models.User;
import com.artigo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void createNewUser(NewUserDTO newUserDTO) {
		
		if(!userRepository.existsByEmail(newUserDTO.getEmail())) {
			User user = new User();
			user.setName(newUserDTO.getName());
			user.setEmail(newUserDTO.getEmail());
			String password = encoder.encode(newUserDTO.getPassword());
			user.setPassword(password);
			
			userRepository.save(user);
		}
		
		
		
	}
}
