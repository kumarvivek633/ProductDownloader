package com.vivek.service;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.Test;

import com.vivek.dao.domain.File;
import com.vivek.dao.repository.FileRepository;
import com.vivek.service.dto.FileDto;
import com.vivek.service.impl.FileStorageServiceImpl;

/**
 * The Class FileStorageServiceImplTest.
 */
public class FileStorageServiceImplTest extends AbstractBaseMockitoTest {

	/** The testee. */
	@InjectMocks
	FileStorageServiceImpl testee;

	/** The file repository. */
	@Mock
	private FileRepository fileRepository;

	/** The converter. */
	@Mock
	private ConversionService converter;

	/** The multipart file. */
	@Mock
	MultipartFile multipartFile;

	/**
	 * Test load user by username.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testStoreFile() throws IOException {
		when(multipartFile.getOriginalFilename()).thenReturn("file.txt");
		when(multipartFile.getContentType()).thenReturn("text/html");
		when(multipartFile.getBytes()).thenReturn("test".getBytes());
		when(fileRepository.save(Mockito.any())).thenReturn(new File(1l, "file", "test"));
		long fileId = testee.storeFile(multipartFile);
		verify(fileRepository, atLeast(1)).save(Mockito.any());
		assertNotNull(fileId);
		assertEquals(fileId, 1l);
	}

	/**
	 * Test get file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetFile() throws IOException {
		File file = new File(1l, "test.txt", "png");
		when(fileRepository.findById(Mockito.any())).thenReturn(Optional.of(file));
		when(converter.convert(Mockito.any(), Mockito.any())).thenReturn(new FileDto());
		FileDto output = testee.getFile(Mockito.anyLong());
		verify(fileRepository, atLeast(1)).findById(Mockito.any());
		assertNotNull(output);
	}

	/**
	 * Test get file list.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testGetFileList() throws IOException {
		File file = new File(1l, "test.txt", "png");
		List<File> files = new ArrayList<>();
		files.add(file);
		when(fileRepository.getAllFilesWithoutContent()).thenReturn(files);
		when(converter.convert(Mockito.any(), Mockito.any())).thenReturn(new FileDto());
		List<FileDto> output = testee.getFileList();
		verify(fileRepository, atLeast(1)).getAllFilesWithoutContent();
		assertNotNull(output);
	}
}
