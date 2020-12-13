package com.vivek.dao.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class User.
 */
@Entity
@Table(name = "Users")
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user id. */
	@Id
	@Column(name = "user_name")
	private String userName;

	/** The user password. */
	@JsonIgnore
	@Column(name = "user_password")
	private String userPassword;

	/** The user first name. */
	@Column(name = "user_first_name")
	private String userFirstName;

	/** The user last name. */
	@Column(name = "user_last_name")
	private String userLastName;

	/** The last update time. */
	@Column(name = "last_update_time")
	private Instant lastUpdateTime;
	
	/** The version. */
	@Version
    @Column(nullable = false)
    private Long version;

	/** The role. */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_role", nullable = false)
	private Role role;

	/** The authorities. */
	@Transient
	@JsonIgnore
	private List<GrantedAuthority> authorities;

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
	public void setUserName(final String userName) {
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
	public void setUserPassword(final String userPassword) {
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
	public void setUserFirstName(final String userFirstName) {
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
	public void setUserLastName(final String userLastName) {
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
	public void setLastUpdateTime(final Instant lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(final Role role) {
		this.role = role;
	}

	/**
	 * Gets the authorities.
	 *
	 * @return the authorities
	 */
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * Sets the authorities.
	 *
	 * @param authorities the new authorities
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

}
