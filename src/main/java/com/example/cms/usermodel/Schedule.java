package com.example.cms.usermodel;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int scheduleId;
	
	private LocalDateTime dateTime;
}
