package com.example.cms.userserviceimpl;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.cms.enums.PostType;
import com.example.cms.requestdto.PublishRequest;
import com.example.cms.requestdto.ScheduleRequest;
import com.example.cms.responsedto.BlogPostResponse;
import com.example.cms.responsedto.PublishResponse;
import com.example.cms.userexception.BlogPostNotFoundByIdException;
import com.example.cms.userexception.InvalidPostStateException;
import com.example.cms.userexception.TimeDateNotVaidException;
import com.example.cms.userexception.TimeInvalidException;
import com.example.cms.usermodel.BlogPost;
import com.example.cms.usermodel.Publish;
import com.example.cms.usermodel.Schedule;
import com.example.cms.userrepository.BlogPostRepository;
import com.example.cms.userrepository.PublishRepository;
import com.example.cms.userrepository.ScheduleRepository;
import com.example.cms.userservice.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService{

	private PublishRepository publishRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<PublishResponse> publishResponseStructure;
	private ResponseStructure<BlogPostResponse> blogPostResponseStructure;
	private ScheduleRepository scheduleRepository;

	@Override
	public ResponseEntity<ResponseStructure<PublishResponse>> publishBlogPost(int postId,PublishRequest publishRequest) {
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		return blogPostRepository.findById(postId).map(blogPost->{
			if(!blogPost.getBlog().getUser().getEmail().equals(email)||(!blogPost.getCreatedBy().equals(email)))
				throw new IllegalArgumentException("Illegal Argument Exception");
			Publish publish=null;
			if(blogPost.getPublish()!=null)
			{
				publish=mapToPublishRequest(publishRequest, blogPost.getPublish());
			}
			else
				publish=mapToPublishRequest(publishRequest, new Publish());
			if(publishRequest.getSchedule()!=null)
			{
				if(!publishRequest.getSchedule().getDateTime().isAfter(LocalDateTime.now()))
				{
					throw new TimeDateNotVaidException("Time and Date not valid");
				}
				if(publish.getSchedule()==null)
				{
					publish.setSchedule(scheduleRepository.save(mapToSchedule(publishRequest.getSchedule(), new Schedule())));
					blogPost.setPostType(PostType.SCHEDULE);
				}
				else
				{
					publish.setSchedule(scheduleRepository.save(mapToSchedule(publishRequest.getSchedule(),publish.getSchedule())));
					blogPost.setPostType(PostType.SCHEDULE);
				}
			}
			else
				blogPost.setPostType(PostType.PUBLISHED);
			publish.setBlogPost(blogPost);
			blogPost.setPublish(publish);
			publishRepository.save(publish);
			blogPostRepository.save(blogPost);
			return ResponseEntity.ok(publishResponseStructure.setStatusCode(HttpStatus.CREATED.value())
					.setMessage("the blog post draft created successfully")
					.setData(mapToPublishResponse(publish)));
		}).orElseThrow(()-> new BlogPostNotFoundByIdException("blogPost not find by Id"));
	}

	public PublishResponse mapToPublishResponse(Publish publish) {
		return PublishResponse.builder()
				.publishId(publish.getPublishId())
				.seoTitle(publish.getSeoTitle())
				.seoDescription(publish.getSeoDescription())
				.seoTags(publish.getSeoTags())
				.createdAt(publish.getCreatedAt())
				.build();
	}

	public Publish mapToPublishRequest(PublishRequest publishRequest,Publish p) {
		Publish publish=new Publish();
		publish.setSeoTitle(publishRequest.getSeoTitle());
		publish.setSeoDescription(publishRequest.getSeoDescription());
		publish.setSeoTags(publishRequest.getSeoTags());
		return publish;

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponse>> unPublishBlogPost(int postId) {
		return blogPostRepository.findById(postId).map(blogPost->{
			if(blogPost.getPostType()!=PostType.PUBLISHED)
				throw new InvalidPostStateException("PostType is not in the Published State");
			blogPost.setPostType(PostType.DRAFT);
			blogPostRepository.save(blogPost);
			return ResponseEntity.ok(blogPostResponseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessage("Changed from PUBLISHED to DRAFT state")
					.setData(mapToBlogPostResponse(blogPost)));
		}).orElseThrow(()->new BlogPostNotFoundByIdException("BlogPost Id not found"));
	}

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

	private Schedule mapToSchedule(ScheduleRequest scheduleRequest,Schedule schedule) {
		schedule.setDateTime(scheduleRequest.getDateTime());

		return schedule;
	}
}
