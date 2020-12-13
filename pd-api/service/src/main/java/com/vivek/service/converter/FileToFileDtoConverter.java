package com.vivek.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.vivek.dao.domain.File;
import com.vivek.service.dto.FileDto;

// TODO: Auto-generated Javadoc
/**
 * The Class FileToFileDtoConverter.
 */
@Component
public class FileToFileDtoConverter implements Converter<File, FileDto> {

	/**
	 * Convert.
	 *
	 * @param user the user
	 * @return the user dto
	 */
	@Override
	public FileDto convert(File user) {
		return new FileDto(user.getId(), user.getFileName(), user.getFileType(), user.getData());
	}
}
