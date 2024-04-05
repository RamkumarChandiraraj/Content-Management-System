package com.example.cms.userservice;

import org.springframework.http.ResponseEntity;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(int postId, PublishRequest publishRequest);

	ResponseEntity<ResponseStructure<BlogPostResponse>> unPublishBlogPost(int postId);
}
