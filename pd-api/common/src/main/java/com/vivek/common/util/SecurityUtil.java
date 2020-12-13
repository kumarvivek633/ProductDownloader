/*
 * 
 */
package com.vivek.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.vivek.common.dto.TokenDetails;

/**
 * The Class SecurityUtil.
 */
public final class SecurityUtil {

	/**
	 * Instantiates a new security util.
	 */
	private SecurityUtil() {
	}

	/**
	 * Gets the user token details.
	 *
	 * @return the user token details
	 */
	public static TokenDetails getUserTokenDetails() {
		TokenDetails tokenDetails = null;
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		final Authentication authentication = securityContext.getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			tokenDetails = (TokenDetails) authentication.getPrincipal();
		}
		return tokenDetails;
	}
}
