package com.example.cms.userrepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.usermodel.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

	boolean existsByTitle(String title);
	
}
