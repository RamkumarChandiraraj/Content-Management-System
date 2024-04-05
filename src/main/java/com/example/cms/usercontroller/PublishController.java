package com.example.cms.usercontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.userservice.PublishService;
import com.example.cms.utility.ResponseStructure;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PublishController {
	private PublishService publishService;
	
	@PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(@PathVariable int postId,@RequestBody @Valid PublishRequest publishRequest)
	{
		return publishService.publishBlogPost(postId,publishRequest);
	}
	
	@PutMapping("/blog-post/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unPublishBlogPost(@PathVariable int postId)
	{
		return publishService.unPublishBlogPost(postId);
	}
}
