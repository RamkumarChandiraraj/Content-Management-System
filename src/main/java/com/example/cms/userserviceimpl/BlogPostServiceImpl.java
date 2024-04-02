package com.example.cms.userserviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.cms.enums.PostType;
import com.example.cms.requestdto.BlogPostRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.userexception.BlogNotFoundByIdException;
import com.example.cms.userexception.BlogPostAlreadyExistByTitleException;
import com.example.cms.userexception.BlogPostNotFoundByIdException;
import com.example.cms.userexception.IllegalAccessRequestException;
import com.example.cms.userexception.UserNotFoundByIdException;
import com.example.cms.usermodel.Blog;
import com.example.cms.usermodel.BlogPost;
import com.example.cms.userrepository.BlogPostRepository;
import com.example.cms.userrepository.BlogRepository;
import com.example.cms.userrepository.ContributionPanlRepository;
import com.example.cms.userrepository.UserRepository;
import com.example.cms.userservice.BlogPostService;
import com.example.cms.utility.ResponseStructure;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService{

	private BlogPostRepository blogPostRepository;
	private ResponseStructure<BlogPostResponse> responseStructure;
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private ContributionPanlRepository contributionPanlRepository;

	private BlogPostResponse mapToBlogPostResponse(BlogPost post) {
		return BlogPostResponse.builder()
				.postId(post.getPostId())
				.title(post.getTitle())
				.subTitle(post.getSubTitle())
				.postType(post.getPostType())
				.summary(post.getSummary())
				.createdAt(post.getCreatedAt())
				.createdBy(post.getCreatedBy())
				.lastModifiedAt(post.getLastModifiedAt())
				.lastModifiedBy(post.getLastModifiedBy())
				.build();
	}

	private BlogPost mapToBlogPostEntity(BlogPostRequest blogRequest, BlogPost blog) {
		blog.setTitle(blogRequest.getTitle());
		blog.setSubTitle(blogRequest.getSubTitle());
		blog.setSummary(blogRequest.getSummary());
		return blog;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> createBlogPost(int blogId, BlogPostRequest request) {
		if(!validateUser(blogId))
			throw new IllegalAccessRequestException("Fail to create Draft");
		if(blogPostRepository.existsByTitle(request.getTitle()))
			throw new BlogPostAlreadyExistByTitleException("Faild to create Draft");
		return blogRepository.findById(blogId).map(blog->{
			BlogPost blogPost=mapToBlogPostEntity(request, new BlogPost());
			blogPost.setBlog(blog);
			blogPost.setPostType(PostType.DRAFT);
			blogPostRepository.save(blogPost);
			blog.getBlogPosts().add(blogPost);
			blogRepository.save(blog);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Draft created Successfully")
					.setData(mapToBlogPostResponse(blogPost)));
		}).orElseThrow(()-> new BlogNotFoundByIdException("Failed to fetch blog"));
	}

	public boolean validateUser(int blogId)
	{
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(user->{
			return blogRepository.findById(blogId).map(blog->{
				if(blog.getUser().getEmail().equals(email)|| contributionPanlRepository.existsByPanelIdAndContributors(blog.getContributorPanel().getPanelId(), user))
					return true;
				else 
					return false;
			}).orElseThrow(()-> new BlogNotFoundByIdException("Failed to validate User"));
		}).orElseThrow(()-> new UserNotFoundByIdException("Failed to validate User"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> updateBlogPost(int postId, BlogPostRequest request) {
		return blogPostRepository.findById(postId).map(blogPost->{
			if(!validateUser(blogPost.getBlog().getBlogId()))
				throw new IllegalAccessRequestException("Failed to update Draft");
			BlogPost updatedBlogPost=mapToBlogPostEntity(request, new BlogPost());
			updatedBlogPost.setPostId(blogPost.getPostId());
			updatedBlogPost.setPostType(blogPost.getPostType());
			updatedBlogPost.setCreatedAt(blogPost.getCreatedAt());
			updatedBlogPost.setCreatedBy(blogPost.getCreatedBy());
			updatedBlogPost.setBlog(blogPost.getBlog());
			updatedBlogPost=blogPostRepository.save(updatedBlogPost);
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("BlogPost Updated Successfully")
					.setData(mapToBlogPostResponse(updatedBlogPost)));
		}).orElseThrow(()->new BlogPostNotFoundByIdException("Faild to update Draft"));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> deleteBlogPost(int postId) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner->{
			return blogPostRepository.findById(postId).map(blogPost->{
				if(!blogPost.getBlog().getUser().getEmail().equals(email)||(!blogPost.getCreatedBy().equals(email)))
					throw new IllegalAccessRequestException("BlogPost won't be created by you, so you don't have the authority to delete the blogPost");
				blogPostRepository.deleteById(postId);
				int blogId=blogPost.getBlog().getBlogId();
				Blog blog=blogRepository.findById(blogId).orElseThrow(()-> new BlogNotFoundByIdException("Blog not Found"));
				blog.getBlogPosts().remove(blogPost);
				blogRepository.save(blog);
				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
						.setMessage("The BlogPost Draft is Deleted Successfully")
						.setData(mapToBlogPostResponse(blogPost)));
			}).orElseThrow(()->new BlogPostNotFoundByIdException("BlogPOst not found by Id"));
		}).get();
	}

}
