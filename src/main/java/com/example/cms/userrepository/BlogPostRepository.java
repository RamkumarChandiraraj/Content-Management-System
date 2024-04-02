package com.example.cms.userrepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cms.usermodel.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer>{
	
	boolean existsByTitle(String title);
}
