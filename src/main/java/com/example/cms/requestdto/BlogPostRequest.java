package com.example.cms.requestdto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogPostRequest {
	@NotNull(message = "Title should not be Null or Empty")
	private String title;
	private String subTitle;
	@Size(min =5,max =500)
	private String summary;
	
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private String createdBy;
	private String lastModifiedBy;
}
