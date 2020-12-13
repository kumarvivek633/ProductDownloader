package com.vivek.service.dto;

import java.time.Instant;

/**
 * The Class UserDto.
 */
public class UserDto {

	/** The user name. */
	private String userName;

	/** The user password. */
	private String userPassword;

	/** The user first name. */
	private String userFirstName;

	/** The user last name. */
	private String userLastName;

	/** The last update time. */
	private Instant lastUpdateTime;

	/** The role. */
	private RoleDto role;
	
	public UserDto() {
		super();
	}

	public UserDto(String userName, String userFirstName, String userLastName,
			Instant lastUpdateTime, RoleDto role) {
		super();
		this.userName = userName;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.lastUpdateTime = lastUpdateTime;
		this.role = role;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the user password.
	 *
	 * @return the user password
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * Sets the user password.
	 *
	 * @param userPassword the new user password
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * Gets the user first name.
	 *
	 * @return the user first name
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * Sets the user first name.
	 *
	 * @param userFirstName the new user first name
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * Gets the user last name.
	 *
	 * @return the user last name
	 */
	public String getUserLastName() {
		return userLastName;
	}

	/**
	 * Sets the user last name.
	 *
	 * @param userLastName the new user last name
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	/**
	 * Gets the last update time.
	 *
	 * @return the last update time
	 */
	public Instant getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * Sets the last update time.
	 *
	 * @param lastUpdateTime the new last update time
	 */
	public void setLastUpdateTime(Instant lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public RoleDto getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(RoleDto role) {
		this.role = role;
	}

}
