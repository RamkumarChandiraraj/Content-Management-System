package com.example.cms.usercontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.userservice.BlogService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogController {

	private BlogService blogService;

	@PostMapping("/users/{userId}/blogs")
	public ResponseEntity<ResponseStructure<BlogResponse>> blogRegistration(@RequestBody @Valid BlogRequest blogRequest,@PathVariable int userId)
	{
		return blogService.blogRegistration(blogRequest,userId);
	}

	@GetMapping("/titles/{title}/blogs")
	public boolean checkForBlogTitleAvailability(@PathVariable String title)
	{
		return blogService.checkForBlogTitleAvailability(title);
	}
	
	@GetMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(@PathVariable int blogId)
	{
		return blogService.findBlogById(blogId);
	}
	
	@PutMapping("/blogs/{blogId}")
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(@RequestBody BlogRequest blogRequest, int blogId)
	{
		return blogService.updateBlogData(blogRequest,blogId);
	}
}
