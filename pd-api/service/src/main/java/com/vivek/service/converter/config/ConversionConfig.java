package com.vivek.service.converter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

import com.vivek.service.converter.FileToFileDtoConverter;
import com.vivek.service.converter.RoleDtoToRoleConverter;
import com.vivek.service.converter.RoleToRoleDtoConverter;
import com.vivek.service.converter.UserDtoToUserConverter;
import com.vivek.service.converter.UserToUserDtoConverter;

/**
 * The Class ConversionConfig.
 */
@Configuration
public class ConversionConfig {

	/** The user to user dto converter. */
	@Autowired
	private UserToUserDtoConverter userToUserDtoConverter;

	/** The role to role dto converter. */
	@Autowired
	private RoleToRoleDtoConverter roleToRoleDtoConverter;

	/** The file to file dto converter. */
	@Autowired
	private FileToFileDtoConverter fileToFileDtoConverter;
	
	@Autowired
	private RoleDtoToRoleConverter roleDtoToRoleConverter;
	
	@Autowired
	private UserDtoToUserConverter userDtoToUserConverter;
	/**
	 * Talent converter.
	 *
	 * @return the conversion service
	 */
	@Bean()
	@Primary
	public ConversionService converter() {
		final DefaultConversionService converterService = new DefaultConversionService();
		converterService.addConverter(userToUserDtoConverter);
		converterService.addConverter(roleToRoleDtoConverter);
		converterService.addConverter(fileToFileDtoConverter);
		converterService.addConverter(roleDtoToRoleConverter);
		converterService.addConverter(userDtoToUserConverter);
		return converterService;
	}

}
