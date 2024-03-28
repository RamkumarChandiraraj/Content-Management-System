package com.example.cms.userservice;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.utility.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponse>> blogRegistration(BlogRequest blogRequest,int userId);

	boolean checkForBlogTitleAvailability(String title);

	ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId);

	ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(BlogRequest blogRequest, int blogId);

}
