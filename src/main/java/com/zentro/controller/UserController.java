package com.zentro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zentro.dto.user.LoginDto;
import com.zentro.dto.user.UserRegisterDto;
import com.zentro.dto.user.UserResponseDto;
import com.zentro.service.UserService;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {		


	private final UserService userService;

	
	//@Autowired
	public UserController(UserService userService) {
		//super();
		this.userService = userService;
	}

	@PostMapping("/login")
	public UserResponseDto authenticationUser(@RequestBody LoginDto logindto ) {
		
		return userService.loginUser(logindto);
	
	}
	
	
	@PostMapping("/register")
	public UserResponseDto registerUser(@RequestBody UserRegisterDto userRegister) {
		
		
		
		return userService.registerUser(userRegister);
	}
	
	
	@GetMapping("/{userId}")
	public UserResponseDto getUser(@PathVariable long userId) {
		
		
		
		return userService.getUserById(userId);
	}
	
	@GetMapping("/")
	public  List<UserResponseDto> getAllUser() {
		
		
		
		return userService.getAllUser();
	}
	
	
	
	
	@DeleteMapping("/{userId}")
	public  void deleteUser(@PathVariable Long userId) {
		
		
		
		 userService.deleteUser(userId);
	}
	
	
	@PutMapping("/{userId}")
	public UserResponseDto updateUser(@PathVariable Long userId ,@RequestBody UserRegisterDto userRegisterDto)
	{
		
		return userService.updateUser(userId, userRegisterDto);
	}

	@PatchMapping("/{userId}/change-password")
	public void changePassword(@PathVariable Long userId, @RequestBody UserRegisterDto userRegisterDto)
	{
		userService.changePassword(userId, userRegisterDto);
	}
	
	
	
}
