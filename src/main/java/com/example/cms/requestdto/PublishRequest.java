package com.example.cms.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PublishRequest {
	@NotBlank
	@NotEmpty
	@NotNull
	private String seoTitle;
	private String seoDescription;
	private String[] seoTags;
	
	private ScheduleRequest schedule;
}
