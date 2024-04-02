package com.example.cms.userservice;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPost(int blogId, BlogPostRequest request);

	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(int postId, BlogPostRequest request);

	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId);

}
