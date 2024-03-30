package com.example.cms.userrepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cms.usermodel.Blog;
import com.example.cms.usermodel.ContributionPanel;
import com.example.cms.usermodel.User;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

	boolean existsByTitle(String title);

	boolean existsByUserAndContributorPanel(User owner, ContributionPanel panel);
	
}
