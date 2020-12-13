package com.vivek.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * The Class CustomAuthenticationManager.
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager {

	/** The database AUTH provider. */
	@Autowired
	private DatabaseAuthProvider dbAuthProvider;

	/**
	 * Authenticate.
	 *
	 * @param authentication the authentication
	 * @return the authentication
	 */
	@Override
	public Authentication authenticate(final Authentication authentication) {
		return dbAuthProvider.authenticate(authentication);
	}

}