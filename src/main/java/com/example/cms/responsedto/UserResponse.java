package com.example.cms.responsedto;

import java.time.LocalDateTime;
import java.util.List;
import com.example.cms.usermodel.Blog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private int userId;
	private String username;
	private String email;
	
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
	private boolean deleted;
	
	private List<Blog> blogs;
}
