package com.example.cms.requestdto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleRequest {
	private LocalDateTime dateTime;
}
