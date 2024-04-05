package com.example.cms.utility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.cms.enums.PostType;
import com.example.cms.usermodel.BlogPost;
import com.example.cms.userrepository.BlogPostRepository;
import com.example.cms.userrepository.PublishRepository;
import com.example.cms.userrepository.ScheduleRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleJobs {

	private BlogPostRepository blogPostRepository;

	@Scheduled(fixedDelay = 60*1000l)
	public void publishScheduleBlogPosts() {
		List<BlogPost> posts=blogPostRepository.findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(),PostType.SCHEDULE);
		posts.stream().map(post->{
			post.setPostType(PostType.PUBLISHED);
			return post;
		}).collect(Collectors.toList());
		blogPostRepository.saveAllAndFlush(posts);
	}	
}
