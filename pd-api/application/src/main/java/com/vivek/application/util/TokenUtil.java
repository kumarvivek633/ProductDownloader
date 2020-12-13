/**
 * TokenUtil.java Apr 19, 2019 Copyright 2019, Oversight Systems, Inc. Proprietary and confidential.
 */
package com.vivek.application.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

/**
 * Utility class for dealing with JWT tokens.
 *
 * @author kkandpal
 */
public final class TokenUtil {

	/**
	 * Instantiates a new token util.
	 */
	private TokenUtil() {
	}

	/** The Constant AUTHORIZATION_HEADER. */
	private static final String AUTHORIZATION_HEADER = "Authorization";

	/**
	 * Resolve token.
	 *
	 * @param request the request
	 * @return the string
	 */
	public static String resolveToken(final HttpServletRequest request) {
		final String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		String token = null;
		if (StringUtils.hasText(bearerToken)) {
			if (bearerToken.startsWith("Bearer ")) {
				token = bearerToken.substring(7);
			} else {
				token = bearerToken;
			}
		}
		return token;
	}

}
