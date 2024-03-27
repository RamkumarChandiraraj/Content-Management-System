package com.example.cms.usercontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.usermodel.User;
import com.example.cms.userservice.UserService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> userRegistration(@RequestBody @Valid UserRequest userRequest)
	{
		return userService.userRegistration(userRequest);
	}

	@GetMapping("/test")
	public String test()
	{
		return "Hello from cms";
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUniqueUser(@PathVariable int userId)
	{
		return userService.findUniqueUser(userId);
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> softDeleteUser(@PathVariable int userId)
	{
		return userService.softDeleteUser(userId);
	}
}
