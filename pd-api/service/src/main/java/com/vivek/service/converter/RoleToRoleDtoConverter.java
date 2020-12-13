package com.vivek.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.vivek.dao.domain.Role;
import com.vivek.service.dto.RoleDto;

/**
 * The Class RoleToRoleDtoConverter.
 */
@Component
public class RoleToRoleDtoConverter implements Converter<Role, RoleDto> {

	/**
	 * Convert.
	 *
	 * @param source the source
	 * @return the role dto
	 */
	@Override
	public RoleDto convert(Role source) {
		return new RoleDto(source.getId(), source.getRoleName());
	}

}
