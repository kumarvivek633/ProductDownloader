package com.vivek.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * The Class SecurityConfiguration.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/** The jwt configurer. */
	@Autowired
	private JWTConfigurer jwtConfigurer;

	/** The Constant SECURITY_IGNORE. */
	private static final String[] SECURITY_IGNORE = { "/api/authenticate" };

	/**
	 * Spring Security Configuration method.
	 *
	 * @param webSecurity WebSecurity instance.
	 * @throws Exception the exception
	 */
	@Override
	public void configure(final WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/app/**/*.{js,html}")
				.antMatchers("/i18n/**").antMatchers("/content/**").antMatchers("/swagger-ui/index.html")
				.antMatchers("/test/**");
	}

	/**
	 * Spring Security Configuration method.
	 *
	 * @param http HttpSecurity instance.
	 * @throws Exception the exception
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		final CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
		cookieCsrfTokenRepository.setCookieHttpOnly(false);

		http.csrf().disable().headers().frameOptions().disable().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(SECURITY_IGNORE).permitAll().antMatchers("/api/**").authenticated().and()
				.apply(jwtConfigurer);
	}

	/**
	 * Granted authority defaults.
	 *
	 * @return the granted authority defaults
	 */
	@Bean
	GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return new GrantedAuthorityDefaults("");
	}

}
