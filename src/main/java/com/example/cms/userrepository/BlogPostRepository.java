package com.example.cms.userrepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cms.enums.PostType;
import com.example.cms.usermodel.BlogPost;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer>{
	
	boolean existsByTitle(String title);

	Optional<BlogPost> findByPostIdAndPostType(int postId, PostType published);

	List<BlogPost> findByPostType(PostType schedule);

	List<BlogPost> findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime now, PostType schedule);

}
