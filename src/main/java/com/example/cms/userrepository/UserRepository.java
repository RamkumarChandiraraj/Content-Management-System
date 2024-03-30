package com.example.cms.userrepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cms.usermodel.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
}
