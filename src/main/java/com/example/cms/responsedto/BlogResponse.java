package com.example.cms.responsedto;

import java.util.List;
import com.example.cms.usermodel.User;
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
public class BlogResponse {
	private int blogId;
	private String title;
	private String[] topics;
	private String about;
	
	private List<User> users;
}
