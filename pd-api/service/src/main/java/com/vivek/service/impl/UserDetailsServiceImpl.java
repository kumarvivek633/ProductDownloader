package com.vivek.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.common.util.PasswordUtil;
import com.vivek.dao.domain.User;
import com.vivek.dao.repository.UserRepository;
import com.vivek.service.UserDetailsService;
import com.vivek.service.dto.UserDto;

/**
 * The Class UserDetailsServiceImpl.
 */
@Transactional
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;

	/** The converter. */
	@Autowired
	private ConversionService converter;

	/**
	 * Load user by username.
	 *
	 * @param login the login
	 * @return the user details
	 */
	public UserDetails loadUserByUsername(final String login) {
		final String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		return userRepository.findOneByUserNameIgnoreCase(lowercaseLogin).map(user -> createSpringSecurityUser(user))
				.orElseThrow(() -> new UsernameNotFoundException(
						"User " + lowercaseLogin + " was not found in the database"));
	}

	/**
	 * Save user.
	 *
	 * @param userToSave the user to save
	 * @return the user
	 * @throws Exception the exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public UserDto saveUser(UserDto userToSave) throws Exception {
		if (userToSave != null && userToSave.getUserPassword() != null) {
			userToSave.setUserPassword(PasswordUtil.getSaltedHash(userToSave.getUserPassword()));
		}
		User user = userRepository.save(converter.convert(userToSave, User.class));
		return converter.convert(user, UserDto.class);
	}

	/**
	 * Gets the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	@Override
	public UserDto getUser(String userName) {
		UserDto user = null;
		Optional<User> optionalUser = userRepository.findOneByUserNameIgnoreCase(userName);
		if (optionalUser.isPresent()) {
			user = converter.convert(optionalUser.get(), UserDto.class);
		}
		return user;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<UserDto> getUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(u -> converter.convert(u, UserDto.class)).collect(Collectors.toList());
	}

	/**
	 * Delete user.
	 *
	 * @param userName the user name
	 */
	@Override
	public void deleteUser(String userName) {
		Optional<User> optionalUser = userRepository.findOneByUserNameIgnoreCase(userName);
		if (optionalUser.isPresent()) {
			userRepository.delete(optionalUser.get());
		}
	}

	/**
	 * Creates the spring security user.
	 *
	 * @param user the user
	 * @return the org.springframework.security.core.userdetails. user
	 */
	private org.springframework.security.core.userdetails.User createSpringSecurityUser(final User user) {
		final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
		user.setAuthorities(grantedAuthorities);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
				grantedAuthorities);
	}
}
