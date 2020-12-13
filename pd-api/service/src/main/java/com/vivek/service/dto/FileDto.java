package com.vivek.service.dto;

/**
 * The Class FileDto.
 */
public class FileDto {

	/** The id. */
	private Long id;

	/** The file name. */
	private String fileName;

	/** The file type. */
	private String fileType;

	/** The data. */
	private byte[] data;

	/**
	 * Instantiates a new file dto.
	 */
	public FileDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new file dto.
	 *
	 * @param id the id
	 * @param fileName the file name
	 * @param fileType the file type
	 * @param data the data
	 */
	public FileDto(Long id, String fileName, String fileType, byte[] data) {
		super();
		this.id = id;
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
