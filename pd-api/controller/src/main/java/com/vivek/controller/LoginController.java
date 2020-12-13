package com.vivek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivek.common.dto.TokenDetails;
import com.vivek.controller.util.TokenProvider;
import com.vivek.service.dto.LoginDto;

/**
 * The Class UserJWTController.
 */
@RestController
public class LoginController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/** The Constant AUTHORIZATION_HEADER. */
	public static final String AUTHORIZATION_HEADER = "Authorization";

	/** The Constant TOKEN_PREFIX. */
	private static final String TOKEN_PREFIX = " Bearer ";

	/** The token validity in sec. */
	@Value("${security.authentication.jwt.validity.insec}")
	private long tokenValidityInSec;

	/** authenticationManager. */
	@Autowired
	private AuthenticationManager authenticationManager;

	/** The token provider. */
	@Autowired
	private TokenProvider tokenProvider;

	/**
	 * Authorize with DB.
	 *
	 * @param loginVM the login VM
	 * @return the response entity
	 */
	@PostMapping("/api/authenticate")
	public ResponseEntity<JWTToken> authorizeWithDB(@RequestBody final LoginDto loginVM) {
		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());
		ResponseEntity<JWTToken> responseEntity = null;
		final Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		if (authentication.isAuthenticated()) {
			LOGGER.info("User {} authenticated successfully.", loginVM.getUsername());
		} else {
			LOGGER.info("User {} authentication failed.", loginVM.getUsername());
		}
		final TokenDetails tokenDetails = getUser(authentication);
		responseEntity = authorize(authentication, tokenDetails);

		if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
			LOGGER.info("User authentication and authorization process is successful.");
		} else {
			LOGGER.info("User authentication or authorization process is failed.");
		}
		return responseEntity;
	}

	/**
	 * Authorize.
	 *
	 * @param authentication the authentication
	 * @param tokenDetails   the token details
	 * @return the response entity
	 */
	private ResponseEntity<JWTToken> authorize(final Authentication authentication, final TokenDetails tokenDetails) {
		final String jwt = tokenProvider.createToken(authentication);
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(AUTHORIZATION_HEADER, TOKEN_PREFIX + jwt);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		if (tokenDetails != null) {
			LOGGER.info("User '{}' session started.", tokenDetails.getUsername());
		}
		final JWTToken token = new JWTToken(jwt);
		token.setTokenValidityInSecond(tokenValidityInSec);
		token.setRole(authentication.getAuthorities().toString());
		return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
	}

	/**
	 * Gets the user.
	 *
	 * @param authentication the authentication
	 * @return the user
	 */
	private TokenDetails getUser(final Authentication authentication) {
		TokenDetails user = null;
		if (authentication.getDetails() instanceof TokenDetails) {
			user = (TokenDetails) authentication.getDetails();
		} else if (authentication.getPrincipal() instanceof TokenDetails) {
			user = (TokenDetails) authentication.getPrincipal();
		}
		return user;
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		/** The id token. */
		private String idToken;

		/** The token validity in seconds. */
		private long tokenValidityInSecond;

		/** The role. */
		private String role;

		/**
		 * Instantiates a new JWT token.
		 */
		JWTToken() {
		}

		/**
		 * Instantiates a new JWT token.
		 *
		 * @param idToken the id token
		 */
		JWTToken(final String idToken) {
			this.idToken = idToken;
		}

		/**
		 * Gets the id token.
		 *
		 * @return the id token
		 */
		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		/**
		 * Sets the id token.
		 *
		 * @param idToken the new id token
		 */
		void setIdToken(final String idToken) {
			this.idToken = idToken;
		}

		/**
		 * Gets the token validity in second.
		 *
		 * @return the tokenValidityInSecond
		 */
		public long getTokenValidityInSecond() {
			return tokenValidityInSecond;
		}

		/**
		 * Sets the token validity in second.
		 *
		 * @param tokenValidityInSecond the tokenValidityInSecond to set
		 */
		public void setTokenValidityInSecond(final long tokenValidityInSecond) {
			this.tokenValidityInSecond = tokenValidityInSecond;
		}

		/**
		 * Gets the role.
		 *
		 * @return the role
		 */
		public String getRole() {
			return role;
		}

		/**
		 * Sets the role.
		 *
		 * @param role the new role
		 */
		public void setRole(String role) {
			this.role = role;
		}
	}

}
