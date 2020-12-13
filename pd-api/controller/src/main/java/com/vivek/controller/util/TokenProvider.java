package com.vivek.controller.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivek.common.dto.TokenDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenProvider {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenProvider.class);

	/** AUTHORITIES KEY. */
	private static final String AUTHORITIES_KEY = "auth";

	/** OVERSIGHT_USER KEY. */
	private static final String USER = "User";

	/**
	 * The constant SESSION_ID
	 */
	private static final String SESSION_ID = "sessionId";

	/** Key. */
	private byte[] key;

	@Value("security.authentication.jwt.secret")
	private String securityKey;

	@Value("${security.authentication.jwt.validity.insec}")
	private long tokenValidityInSec;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * This method is used to initialize properties.
	 */
	@PostConstruct
	public void init() {
		this.key = securityKey.getBytes(StandardCharsets.UTF_8);
	}

	public String createToken(final Authentication authentication) {
		final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		final long now = new Date().getTime();
		final Date validity = new Date(now + (this.tokenValidityInSec * 1000));
		String jsonString = null;
		TokenDetails tokenDetails = null;
		if (authentication.getPrincipal() instanceof TokenDetails) {
			try {
				tokenDetails = (TokenDetails) authentication.getPrincipal();
				tokenDetails.eraseCredentials();
				tokenDetails.setJwtString(null);
				jsonString = objectMapper.writeValueAsString(tokenDetails);
			} catch (final JsonProcessingException exception) {
				LOGGER.error("Failed to set Object from ObjectMapper api. Reason - {}",
						exception.getLocalizedMessage());
			}
		}
		final String sessionId = UUID.randomUUID().toString();
		final String token = Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.claim(USER, jsonString).claim(SESSION_ID, sessionId).signWith(SignatureAlgorithm.HS256, key)
				.setExpiration(validity).compact();
		if (tokenDetails != null) {
			tokenDetails.setJwtString(token);
			tokenDetails.setUserSessionID(sessionId);
		}
		return token;
	}

	/**
	 * This method provides authentication from JWT Token.
	 * 
	 * @param token JWT Token
	 * @return Authentication instance
	 */
	public Authentication getAuthentication(final String token) {
		final Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		final Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		final TokenDetails tokenDetails = new TokenDetails(claims.getSubject(), "", authorities);
		if (claims.get(USER) != null) {
			final Object sessionId = claims.get(SESSION_ID);
			if (sessionId != null) {
				tokenDetails.setUserSessionID(sessionId.toString());
			}
			tokenDetails.setJwtString(token);
		}
		return new UsernamePasswordAuthenticationToken(tokenDetails, token, authorities);
	}

	/**
	 * This method is used to validate JWT Token.
	 * 
	 * @param authToken JWT Token
	 * @return boolean flag
	 */
	public boolean validateToken(final String authToken) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException | MalformedJwtException e) {
			LOGGER.info("Invalid JWT signature.");
			LOGGER.trace("Invalid JWT signature trace: {}", e);
		} catch (final ExpiredJwtException e) {
			// TODO
			// LOGGER.info("User {} have expired JWT token.",
			// SecurityUtil.getUserTokenDetails().getUserId());
			LOGGER.trace("Expired JWT token trace: {}", e);
		} catch (final UnsupportedJwtException e) {
			LOGGER.info("Unsupported JWT token.");
			LOGGER.trace("Unsupported JWT token trace: {}", e);
		} catch (final IllegalArgumentException e) {
			LOGGER.info("JWT token compact of handler are invalid.");
			LOGGER.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return false;
	}

}
