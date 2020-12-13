package com.vivek.common.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * The Class TokenDetails.
 */
public class TokenDetails extends User {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** userSessionID. */
	private String userSessionID;

	/** jwtString. */
	private String jwtString;

	/**
	 * Instantiates a new token details.
	 *
	 * @param username    the username
	 * @param password    the password
	 * @param authorities the authorities
	 */
	public TokenDetails(final String username, final String password,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
	}

	/**
	 * Gets the user session ID.
	 *
	 * @return the user session ID
	 */
	public String getUserSessionID() {
		return userSessionID;
	}

	/**
	 * Sets the user session ID.
	 *
	 * @param userSessionID the new user session ID
	 */
	public void setUserSessionID(final String userSessionID) {
		this.userSessionID = userSessionID;
	}

	/**
	 * Gets the jwt string.
	 *
	 * @return the jwt string
	 */
	public String getJwtString() {
		return jwtString;
	}

	/**
	 * Sets the jwt string.
	 *
	 * @param jwtString the new jwt string
	 */
	public void setJwtString(final String jwtString) {
		this.jwtString = jwtString;
	}

}
