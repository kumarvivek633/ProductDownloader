package com.vivek.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.vivek.dao.domain.File;
import com.vivek.dao.repository.FileRepository;
import com.vivek.service.FileStorageService;
import com.vivek.service.dto.FileDto;

/**
 * The Class FileStorageServiceImpl.
 */
@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {
	
	/** The db file repository. */
	@Autowired
	private FileRepository dbFileRepository;

	/** The converter. */
	@Autowired
	private ConversionService converter;

	/**
	 * Store file.
	 *
	 * @param fileMultipart the file multipart
	 * @return the long
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public long storeFile(MultipartFile fileMultipart) throws IOException {
		String fileName = StringUtils.cleanPath(fileMultipart.getOriginalFilename());
		File dbFile = new File(fileName, fileMultipart.getContentType(), fileMultipart.getBytes());
		File file = dbFileRepository.save(dbFile);
		return file.getId();
	}

	/**
	 * Gets the file.
	 *
	 * @param fileId the file id
	 * @return the file
	 */
	@Override
	public FileDto getFile(long fileId) {
		Optional<File> file = dbFileRepository.findById(fileId);
		FileDto fileDto = null;
		if (file.isPresent()) {
			fileDto = converter.convert(file.get(), FileDto.class);
		}
		return fileDto;
	}

	/**
	 * Gets the file list.
	 *
	 * @return the file list
	 */
	@Override
	public List<FileDto> getFileList() {
		List<File> files = dbFileRepository.getAllFilesWithoutContent();
		return files.stream().map(f -> converter.convert(f, FileDto.class)).collect(Collectors.toList());
	}
}
