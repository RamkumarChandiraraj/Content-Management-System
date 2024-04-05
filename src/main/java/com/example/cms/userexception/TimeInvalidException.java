package com.example.cms.userexception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TimeInvalidException extends RuntimeException {
	private String message;
}
