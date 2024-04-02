package com.example.cms.usermodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Blog {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int blogId;
	private String title;
	private String[] topics;
	private String about;

	@ManyToOne
	//@JoinColumn(name = "user_Id")
	private User user;

	@OneToOne
	private ContributionPanel contributorPanel;
	
	@OneToMany(mappedBy = "blog")
	private List<BlogPost> blogPosts=new ArrayList<>();
}
