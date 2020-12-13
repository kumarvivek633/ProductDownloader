package com.vivek.service;

import java.util.List;

import com.vivek.service.dto.UserDto;

/**
 * The Interface UserDetailsService.
 */
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

	/**
	 * Save user.
	 *
	 * @param userToSave the user to save
	 * @return the user
	 * @throws Exception the exception
	 */
	public UserDto saveUser(UserDto userToSave) throws Exception;

	/**
	 * Gets the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	public UserDto getUser(String userName);

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<UserDto> getUsers();

	/**
	 * Delete user.
	 *
	 * @param userName the user name
	 */
	public void deleteUser(String userName);
}
