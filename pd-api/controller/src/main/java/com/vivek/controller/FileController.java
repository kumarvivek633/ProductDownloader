package com.vivek.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vivek.service.FileStorageService;
import com.vivek.service.dto.FileDto;

/**
 * The Class FileController.
 */
@RestController
public class FileController {

	/** The file storage service. */
	@Autowired
	private FileStorageService fileStorageService;

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@PostMapping("/api/upload-file")
	@PreAuthorize("hasRole('ADMIN')")
	public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		fileStorageService.storeFile(file);
	}

	/**
	 * Download file.
	 *
	 * @param fileId the file id
	 * @return the response entity
	 */
	@GetMapping("/api/download-file/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable long fileId) {
		FileDto dbFile = fileStorageService.getFile(fileId);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}

	/**
	 * Files list.
	 *
	 * @return the list
	 */
	@GetMapping("/api/files")
	public List<FileDto> filesList() {
		return fileStorageService.getFileList();
	}

}
