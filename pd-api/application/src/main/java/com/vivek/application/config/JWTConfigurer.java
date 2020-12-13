package com.vivek.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.vivek.application.interceptors.JWTFilter;
import com.vivek.controller.util.TokenProvider;

/**
 * The Class JWTConfigurer.
 */
@Configuration
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	/** The token provider. */
	@Autowired
	private TokenProvider tokenProvider;

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.addFilterAfter(new JWTFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
	}
}
