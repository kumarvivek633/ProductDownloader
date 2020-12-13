package com.vivek.service.converter;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.vivek.dao.domain.User;
import com.vivek.dao.repository.UserRepository;
import com.vivek.service.dto.UserDto;

/**
 * The Class UserDtoToUserConverter.
 */
@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

	/** The role dto to role converter. */
	@Autowired
	RoleDtoToRoleConverter roleDtoToRoleConverter;
	
	@Autowired
	private UserRepository userRepository;

	/**
	 * Convert.
	 *
	 * @param source the source
	 * @return the user
	 */
	@Override
	public User convert(UserDto source) {
		Optional<User> optUser = userRepository.findOneByUserNameIgnoreCase(source.getUserName());
		User user = null;
		if (optUser.isPresent()) {
			user = optUser.get();
		}else {
			user = new User();
		}
		user.setLastUpdateTime(Instant.now());
		user.setUserName(source.getUserName());
		if (source.getUserPassword() != null) {
			user.setUserPassword(source.getUserPassword());
		}
		if (source.getRole() != null) {
			user.setRole(roleDtoToRoleConverter.convert(source.getRole()));
		}
		if (source.getUserFirstName() != null) {
			user.setUserFirstName(source.getUserFirstName());
		}
		if (source.getUserLastName() != null) {
			user.setUserLastName(source.getUserLastName());
		}
		return user;
	}

}
