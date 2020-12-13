package com.vivek.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.vivek.dao.domain.Role;
import com.vivek.service.dto.RoleDto;

/**
 * The Class RoleDtoToRoleConverter.
 */
@Component
public class RoleDtoToRoleConverter implements Converter<RoleDto, Role> {

	/**
	 * Convert.
	 *
	 * @param source the source
	 * @return the role dto
	 */
	@Override
	public Role convert(RoleDto source) {
		Role role = new Role();
		role.setId(source.getId());
		role.setRoleName(source.getRoleName());
		return role;
	}

}
