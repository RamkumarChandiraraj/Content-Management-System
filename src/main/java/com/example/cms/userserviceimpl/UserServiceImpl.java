package com.example.cms.userserviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.requestdto.UserRequest;
import com.example.cms.responsedto.UserResponse;
import com.example.cms.userexception.UserAlreadyExistByEmailException;
import com.example.cms.userexception.UserNotFoundByIdException;
import com.example.cms.usermodel.User;
import com.example.cms.userrepository.UserRepository;
import com.example.cms.userservice.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	private UserRepository userRepository;
	private ResponseStructure<UserResponse> structure;
	private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> userRegistration(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("Faild to register User");

		User user=userRepository.save(mapToUserEntity(userRequest, new User()));

		return ResponseEntity.ok(structure.setStatusCode(HttpStatus.CREATED.value()).setMessage("User registered successfully")
				.setData(mapToUserResponse(user)));
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.email(user.getEmail())
				.createdAt(user.getCreatedAt())
				.lastModifiedAt(user.getLastModifiedAt())
				.deleted(user.isDeleted())
				.build();
	}

	private User mapToUserEntity(UserRequest userRequest, User user) {
		user.setEmail(userRequest.getEmail());
		//encoding the password from passwordEncoder
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		user.setUsername(userRequest.getUsername());

		return user;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUniqueUser(int userId) {
		return userRepository.findById(userId).map(user->
		ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
				.setMessage("User found by the given Id")
				.setData(mapToUserResponse(user))))
				.orElseThrow(()->new UserNotFoundByIdException("Invalid userId"));
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> softDeleteUser(int userId) {
		return userRepository.findById(userId).map(user->{user.setDeleted(true);
		userRepository.save(user);
		return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
				.setMessage("User deleted")
				.setData(mapToUserResponse(user)));
		}).orElseThrow(()->new UserNotFoundByIdException("User not found by Id"));
	}


}
