package com.example.cms.userexception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimeDateNotVaidException extends RuntimeException {
	private String message;
}
