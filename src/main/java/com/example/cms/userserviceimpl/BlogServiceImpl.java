package com.example.cms.userserviceimpl;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.userexception.BlogNotFoundByIdException;
import com.example.cms.userexception.BlogTitleAlreadyExistException;
import com.example.cms.userexception.TitleEmptyException;
import com.example.cms.userexception.UserNotFoundByIdException;
import com.example.cms.usermodel.Blog;
import com.example.cms.usermodel.User;
import com.example.cms.userrepository.BlogRepository;
import com.example.cms.userrepository.UserRepository;
import com.example.cms.userservice.BlogService;
import com.example.cms.utility.ResponseStructure;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private ResponseStructure<BlogResponse> structure;

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> blogRegistration(BlogRequest blogRequest,int userId) {
		return userRepository.findById(userId).map(user->{
			if(blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new BlogTitleAlreadyExistException("Failed to create blog");

			if(blogRequest.getTopics().length<1)
				throw new TitleEmptyException("Title is Empty");

			Blog blog=mapToBlogEntity(blogRequest);
			blog.setUsers(Arrays.asList(user));
			blogRepository.save(blog);

			return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value()).setMessage("Blog Registered Successfully").setData(mapToBlogResponse(blog)));
		}).orElseThrow(()->new UserNotFoundByIdException("Faild to create blog"));
	}

	private BlogResponse mapToBlogResponse(Blog blog) {
		return BlogResponse.builder()
				.blogId(blog.getBlogId())
				.title(blog.getTitle())
				.topics(blog.getTopics())
				.about(blog.getAbout())
				.users(blog.getUsers())
				.build();
	}

	private Blog mapToBlogEntity(BlogRequest blogRequest) {
		Blog blog=new Blog();
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

	@Override
	public boolean checkForBlogTitleAvailability(String title) {
		return blogRepository.existsByTitle(title);
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> findBlogById(int blogId) {
		return blogRepository.findById(blogId).map(blog->
		ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
				.setMessage("Blog found by the given Id")
				.setData(mapToBlogResponse(blog))))
				.orElseThrow(()->new BlogNotFoundByIdException("Invalid blogId"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> blogService(BlogRequest blogRequest, int blogId) {
		return blogRepository.findById(blogId).map(blog->{
			if(blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new BlogTitleAlreadyExistException("Failed to update blog");
			if(blogRequest.getTopics().length<1)
				throw new TitleEmptyException("Title is Empty");

			Blog blogs=mapToBlogEntity(blogRequest);
			blogs.setBlogId(blog.getBlogId());
			blogs.setUsers(blog.getUsers());
			blogRepository.save(blogs);

			return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value()).setMessage("Blog Updated Successfully").setData(mapToBlogResponse(blog)));
		}).orElseThrow(()->new BlogNotFoundByIdException("Faild to Update blog"));
	}

}
