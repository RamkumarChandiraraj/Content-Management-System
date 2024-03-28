package com.example.cms.requestdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BlogRequest {
	
	@NotNull(message = "Title should not be Null or Empty")
	@Pattern(regexp = "^[a-zA-Z ]+$")
	private String title;
	private String[] topics;
	private String about;
}
