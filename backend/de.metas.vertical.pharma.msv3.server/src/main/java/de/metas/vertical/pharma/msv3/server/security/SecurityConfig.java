package de.metas.vertical.pharma.msv3.server.security;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;

/**
 * Spring Security configuration.
 * <p>
 * Migrated from the removed {@code WebSecurityConfigurerAdapter} (Spring Security 5) to the
 * component-based model (Spring Security 6): a {@link SecurityFilterChain} bean replaces
 * {@code configure(HttpSecurity)}, and a {@link WebSecurityCustomizer} bean replaces
 * {@code configure(WebSecurity)}. The {@code UserDetailsService} is wired onto the
 * {@link HttpSecurity} builder (was {@code configureGlobalSecurity(AuthenticationManagerBuilder)}).
 * The authorization rules, realm, stateless session policy, CSRF-disabled state and OPTIONS
 * pre-flight bypass are preserved 1:1.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	private static final String REALM = "MSV3_SERVER";
	public static final String ROLE_CLIENT = "ROLE_CLIENT";
	public static final String ROLE_SERVER_ADMIN = "ROLE_SERVER_ADMIN";

	@Bean
	public SecurityFilterChain securityFilterChain(
			final HttpSecurity http,
			final MSV3ServerAuthenticationService msv3authService) throws Exception
	{
		http
				.userDetailsService(msv3authService)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(MSV3ServerConstants.WEBSERVICE_ENDPOINT_PATH + "/**").hasAuthority(ROLE_CLIENT)
						.requestMatchers(MSV3ServerConstants.REST_ENDPOINT_PATH + "/**").hasAuthority(ROLE_SERVER_ADMIN)
						// Preserve Spring Security 5 behaviour: the original adapter config had no anyRequest() rule,
						// so requests matching neither endpoint (actuator, WSDL, /error, ...) were permitted. SS6
						// authorizeHttpRequests denies unmatched requests by default, so make the permit explicit.
						.anyRequest().permitAll())
				.httpBasic(basic -> basic.realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	public BasicAuthenticationEntryPoint getBasicAuthEntryPoint()
	{
		final BasicAuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
		authenticationEntryPoint.setRealmName(REALM);
		return authenticationEntryPoint;
	}

	/* To allow Pre-flight [OPTIONS] request from browser */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer()
	{
		return web -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
	}
}
