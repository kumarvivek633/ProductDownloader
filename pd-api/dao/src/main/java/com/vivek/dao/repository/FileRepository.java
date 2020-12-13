package com.vivek.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vivek.dao.domain.File;

/**
 * The Interface FileRepository.
 */
public interface FileRepository extends JpaRepository<File, Long> {
	
	@Query(value = "select new File(id, fileName, fileType) from File")
	public List<File> getAllFilesWithoutContent(); 

}
