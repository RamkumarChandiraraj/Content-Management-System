package com.example.cms.userserviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.cms.requestdto.BlogRequest;
import com.example.cms.responsedto.BlogResponse;
import com.example.cms.responsedto.ContributionPanelResponse;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.userexception.BlogNotFoundByIdException;
import com.example.cms.userexception.BlogTitleAlreadyExistException;
import com.example.cms.userexception.IllegalAccessRequestException;
import com.example.cms.userexception.PanelNotFoundByIDException;
import com.example.cms.userexception.TitleEmptyException;
import com.example.cms.userexception.UserNotFoundByIdException;
import com.example.cms.usermodel.Blog;
import com.example.cms.usermodel.ContributionPanel;
import com.example.cms.usermodel.User;
import com.example.cms.userrepository.BlogRepository;
import com.example.cms.userrepository.ContributionPanlRepository;
import com.example.cms.userrepository.UserRepository;
import com.example.cms.userservice.BlogService;
import com.example.cms.utility.ResponseStructure;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService{
	private BlogRepository blogRepository;
	private UserRepository userRepository;
	private ContributionPanlRepository contributionPanlRepository;
	private ResponseStructure<BlogResponse> structure;
	private ResponseStructure<ContributionPanelResponse> panelResponseStructure;
	private ResponseStructure<UserResponse> userResponseStructure;

	private BlogResponse mapToBlogResponse(Blog blog) {
		return BlogResponse.builder()
				.blogId(blog.getBlogId())
				.title(blog.getTitle())
				.topics(blog.getTopics())
				.about(blog.getAbout())
				.userId(blog.getUser().getUserId())
				.build();
	}

	private Blog mapToBlogEntity(BlogRequest blogRequest) {
		Blog blog=new Blog();
		blog.setTitle(blogRequest.getTitle());
		blog.setTopics(blogRequest.getTopics());
		blog.setAbout(blogRequest.getAbout());
		return blog;
	}

	private ContributionPanelResponse mapToContributionPanelResponse(ContributionPanel panel)
	{
		return ContributionPanelResponse.builder()
				.panelId(panel.getPanelId())
				.build();
	}

	private UserResponse maptoUserResponse(User user)
	{
		UserResponse response=new UserResponse();
		response.setUserId(user.getUserId());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setCreatedAt(user.getCreatedAt());
		response.setLastModifiedAt(user.getLastModifiedAt());
		response.setDeleted(user.isDeleted());
		return response;
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponse>> blogRegistration(BlogRequest blogRequest,int userId) {
		return userRepository.findById(userId).map(user->{
			if(blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new BlogTitleAlreadyExistException("Failed to create blog");

			if(blogRequest.getTopics().length<1)
				throw new TitleEmptyException("Title is Empty");

			ContributionPanel panel=new ContributionPanel();
			panel = contributionPanlRepository.save(panel);
			Blog blog=mapToBlogEntity(blogRequest);
			blog.setContributorPanel(panel);
			blog.setUser(user);
			blog=blogRepository.save(blog);
			user.getBlogs().add(blog);
			userRepository.save(user);

			return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value()).setMessage("Blog Registered Successfully").setData(mapToBlogResponse(blog)));
		}).orElseThrow(()->new UserNotFoundByIdException("Faild to create blog"));
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
	public ResponseEntity<ResponseStructure<BlogResponse>> updateBlogData(BlogRequest blogRequest, int blogId) {
		return blogRepository.findById(blogId).map(blog->{
			if(blogRepository.existsByTitle(blogRequest.getTitle()))
				throw new BlogTitleAlreadyExistException("Failed to update blog");
			if(blogRequest.getTopics().length<1)
				throw new TitleEmptyException("Title is Empty");

			Blog blogs=mapToBlogEntity(blogRequest);
			blogs.setBlogId(blog.getBlogId());
			blogs.setUser(blog.getUser());
			//blogs.setUsers(blog.getUser());
			blogRepository.save(blogs);

			return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value()).setMessage("Blog Updated Successfully").setData(mapToBlogResponse(blog)));
		}).orElseThrow(()->new BlogNotFoundByIdException("Faild to Update blog"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ContributionPanelResponse>> addContributor(int userId, int panelId) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner->{
			return contributionPanlRepository.findById(panelId).map(panel->{
				if(!blogRepository.existsByUserAndContributorPanel(owner, panel))
					throw new IllegalAccessRequestException("Failed to add Contributer");
				return userRepository.findById(userId).map(contributor->{
					panel.getContributors().add(contributor);
					contributionPanlRepository.save(panel);
					return ResponseEntity.ok(panelResponseStructure.setStatusCode(HttpStatus.OK.value())
							.setMessage("Contributor added successfully")
							.setData(mapToContributionPanelResponse(panel)));
				}).orElseThrow(()->new UserNotFoundByIdException("Failed to add Contributor"));
			}).orElseThrow(()->new PanelNotFoundByIDException("Failed to fetch Panel"));
		}).get();
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> removeUser(int userId, int panelId) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByEmail(email).map(owner->{
			return contributionPanlRepository.findById(panelId).map(panel->{
				if(!blogRepository.existsByUserAndContributorPanel(owner, panel))
					throw new IllegalAccessRequestException("Failed to delete User");
				return userRepository.findById(userId).map(user->{
					panel.getContributors().remove(user);
					contributionPanlRepository.save(panel);
					return ResponseEntity.ok(userResponseStructure.setStatusCode(HttpStatus.OK.value())
							.setMessage("User deleted successfully")
							.setData(maptoUserResponse(user)));
				}).orElseThrow(()->new UserNotFoundByIdException("Failed to add Contributer"));
			}).orElseThrow(()->new PanelNotFoundByIDException("Failed to fetch Panel"));
		}).get();
	}
}
