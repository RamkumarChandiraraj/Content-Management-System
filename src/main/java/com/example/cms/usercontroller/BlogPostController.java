package com.example.cms.usercontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.userservice.BlogPostService;
import com.example.cms.utility.ResponseStructure;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogPostController {
	private BlogPostService blogPostService;

	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPost(@PathVariable int blogId,@RequestBody BlogPostRequest request){
		return blogPostService.createBlogPost(blogId,request);
	}

	@PutMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(@PathVariable int postId,@RequestBody BlogPostRequest request){
		return blogPostService.updateBlogPost(postId,request);
	}
	
	@DeleteMapping("/blog-posts/{postId}")
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(@PathVariable int postId){
		return blogPostService.deleteBlogPost(postId);
	}
	
	@GetMapping("/blog-posts/{postId}")
	public  ResponseEntity<ResponseStructure<BlogPostResponse>> findBlogPostById(@PathVariable int postId){
		return blogPostService.findBlogPostById(postId);
	}
	@GetMapping("/blog-posts/{postId}/publishes")
	public  ResponseEntity<ResponseStructure<BlogPostResponse>> findByIdAndPosttype(@PathVariable int postId){
		return blogPostService.findByIdAndPosttype(postId);
	}
}
