package com.vivek.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vivek.service.dto.FileDto;

/**
 * The Interface FileStorageService.
 */
public interface FileStorageService {

	/**
	 * Gets the f ile.
	 *
	 * @param fileId the file id
	 * @return the f ile
	 */
	public FileDto getFile(long fileId);

	/**
	 * Store file.
	 *
	 * @param file the file
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public long storeFile(MultipartFile file) throws IOException;
	
	/**
	 * Gets the file list.
	 *
	 * @return the file list
	 */
	public List<FileDto> getFileList();
}
