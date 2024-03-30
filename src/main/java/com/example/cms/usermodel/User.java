package com.example.cms.usermodel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
//to make the class as auditing the @EntityListeners is required
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int userId;
	private String username;
	private String email;
	private String password;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime createdAt;
	@LastModifiedDate
	LocalDateTime lastModifiedAt;
	
	private boolean deleted;
	
	@OneToMany(mappedBy = "user")
	private List<Blog> blogs=new ArrayList<>();
}
