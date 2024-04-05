package com.example.cms.usermodel;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.example.cms.enums.PostType;
import com.example.cms.requestdto.ScheduleRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Publish {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int publishId;
	private String seoTitle;
	private String seoDescription;
	private String[] seoTags;
	
	@OneToOne
	private BlogPost blogPost;
	
	private LocalDateTime createdAt;
	
	@OneToOne
	private Schedule schedule;
}
