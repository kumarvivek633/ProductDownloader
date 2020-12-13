package com.vivek.controller.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.vivek.common.dto.TokenDetails;
import com.vivek.common.util.PasswordUtil;
import com.vivek.service.UserDetailsService;

/**
 * The Class DatabaseAuthProvider.
 */
@Component
public class DatabaseAuthProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;


	/** LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseAuthProvider.class);

	public DatabaseAuthProvider(final UserDetailsService userDetailsService) {
		super();
		this.setHideUserNotFoundExceptions(false);
		setUserDetailsService(userDetailsService);
	}
	/**
	 * Authenticate.
	 *
	 * @param authentication the authentication
	 * @return the authentication
	 */
	@Override
	public Authentication authenticate(final Authentication authentication) {
		final String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		boolean cacheWasUsed = true;
		UserDetails user = getUserCache().getUserFromCache(username);

		if (user == null) {
			cacheWasUsed = false;
			try {
				user = userDetailsService.loadUserByUsername(username);
			} catch (final UsernameNotFoundException notFound) {
				LOGGER.error("User {} not found ", username, notFound);
				throw notFound;
			}
			Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
		}
		final TokenDetails tokenDetails = new TokenDetails(user.getUsername(), "1",
				user.getAuthorities());
		try {
			getPreAuthenticationChecks().check(user);
			additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
		} catch (final BadCredentialsException exception) {
			throw new BadCredentialsException("Bad credentials for user  " + user.getUsername(), exception);
		}

		if (!cacheWasUsed) {
			getUserCache().putUserInCache(user);
		}
		Object principalToReturn = tokenDetails;

		if (isForcePrincipalAsString()) {
			principalToReturn = user.getUsername();
		}
		LOGGER.info("User {} is successfully authenticaticated using DB authentication mode.", user.getUsername());
		return createSuccessAuthentication(principalToReturn, authentication, tokenDetails);
	}

	/**
	 * Additional authentication checks.
	 *
	 * @param userDetails    the user details
	 * @param authentication the authentication
	 */
	@Override
	protected void additionalAuthenticationChecks(final UserDetails userDetails,
			final UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() == null) {
			LOGGER.error("Authentication failed: no credentials provided");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
		final String presentedPassword = authentication.getCredentials().toString();
		try {
			if (!PasswordUtil.check(presentedPassword, userDetails.getPassword())) {
				LOGGER.error("Authentication failed: password does not match stored value");
				throw new BadCredentialsException(
						messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}
		} catch (Exception e) {
			LOGGER.error("Authentication failed: password does not match stored value");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

}
