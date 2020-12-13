package com.vivek.service.dto;

/**
 * The Class RoleDto.
 */
public class RoleDto {

	/** The id. */
	private Long id;

	/** The role name. */
	private String roleName;

	/**
	 * Instantiates a new role dto.
	 */
	public RoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new role dto.
	 *
	 * @param id the id
	 * @param roleName the role name
	 */
	public RoleDto(Long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
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
	 * Gets the role name.
	 *
	 * @return the role name
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the role name.
	 *
	 * @param roleName the new role name
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
