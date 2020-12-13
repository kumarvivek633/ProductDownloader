package com.vivek.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The Class File.
 */
@Entity
@Table(name = "files")
public class File {

	/** The id. */
	@Id
	@GeneratedValue(generator = "file_seq")
	@SequenceGenerator(name = "file_seq", sequenceName = "FILE_SEQ")
	private Long id;

	/** The file name. */
	@Column(name = "file_name")
	private String fileName;

	/** The file type. */
	@Column(name = "file_type")
	private String fileType;

	/** The data. */
	@Lob
	@Column(name = "data")
	private byte[] data;

	
	public File() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public File(Long id, String fileName, String fileType) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileType = fileType;
	}

	public File(String fileName, String fileType, byte[] data) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the file type.
	 *
	 * @return the file type
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * Sets the file type.
	 *
	 * @param fileType the new file type
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

}
