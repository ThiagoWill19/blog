package com.artigo.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artigo.models.User;


public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByEmail(String userEmail);
	
	Optional<User> findByEmail(String email);

}
