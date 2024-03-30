package com.example.cms.userexception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PanelNotFoundByIDException extends RuntimeException{
	private String message;
}
