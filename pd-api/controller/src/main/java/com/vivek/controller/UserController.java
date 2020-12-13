package com.vivek.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.service.UserDetailsService;
import com.vivek.service.dto.UserDto;

/**
 * The Class UserController.
 */
@RestController
public class UserController {

	/** The user detail service. */
	@Autowired
	private UserDetailsService userDetailService;

	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the user
	 * @throws Exception 
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/users")
	public UserDto addUser(@RequestBody final UserDto user) throws Exception {
		return userDetailService.saveUser(user);
	}

	/**
	 * Adds the user.
	 *
	 * @param userName the user Name
	 * @return the user
	 */
	@GetMapping("/api/users")
	public UserDto getUser(@RequestParam final String userName) {
		return userDetailService.getUser(userName);
	}

	/**
	 * Delete user.
	 *
	 * @param userName the user name
	 */
	@DeleteMapping("/api/users")
	@CrossOrigin
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@RequestParam final String userName) {
		userDetailService.deleteUser(userName);
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@GetMapping("/api/all-users")
	public List<UserDto> getUsers() {
		return userDetailService.getUsers();
	}
}
