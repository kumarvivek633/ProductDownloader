package com.vivek.application.interceptors;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.vivek.application.util.TokenUtil;
import com.vivek.controller.util.TokenProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class JWTFilter.
 */
public class JWTFilter extends GenericFilterBean {

	/** The token provider. */
	private TokenProvider tokenProvider;

	/** The Constant EXCLUDE_JWT_FILTER. */
	private static final String[] EXCLUDE_JWT_FILTER = { "/api/download-file/" };

	/**
	 * Instantiates a new JWT filter.
	 *
	 * @param tokenProvider the token provider
	 */
	public JWTFilter(final TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	/**
	 * Do filter.
	 *
	 * @param servletRequest  the servlet request
	 * @param servletResponse the servlet response
	 * @param filterChain     the filter chain
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String jwt = null;
		if (urlContains(httpServletRequest.getRequestURI())) {
			jwt = httpServletRequest.getParameter("jwt");
		} else {
			jwt = TokenUtil.resolveToken(httpServletRequest);
		}
		if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
			final Authentication authentication = this.tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * Url contains.
	 *
	 * @param requestURI the request URI
	 * @return true, if successful
	 */
	private boolean urlContains(final String requestURI) {
		boolean urlContainsRequestUri = false;
		for (final String uri : EXCLUDE_JWT_FILTER) {
			if (requestURI.contains(uri)) {
				urlContainsRequestUri = true;
			}
		}
		return urlContainsRequestUri;
	}
}
