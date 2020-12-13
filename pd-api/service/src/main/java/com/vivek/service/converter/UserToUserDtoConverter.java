package com.vivek.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.vivek.dao.domain.User;
import com.vivek.service.dto.UserDto;

/**
 * The Class UserToUserDtoConverter.
 */
@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

	/** The role to role dto converter. */
	@Autowired
	private RoleToRoleDtoConverter roleToRoleDtoConverter;

	/**
	 * Convert.
	 *
	 * @param user the user
	 * @return the user dto
	 */
	@Override
	public UserDto convert(User user) {
		return new UserDto(user.getUserName(), user.getUserFirstName(), user.getUserLastName(),
				user.getLastUpdateTime(), roleToRoleDtoConverter.convert(user.getRole()));
	}
}
