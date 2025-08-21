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

import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	private static final String REALM = "MSV3_SERVER";
	public static final String ROLE_CLIENT = "ROLE_CLIENT";
	public static final String ROLE_SERVER_ADMIN = "ROLE_SERVER_ADMIN";

	@Autowired
	private MSV3ServerAuthenticationService msv3authService;

	@Bean
	public AuthenticationManager authenticationManager(@NonNull final HttpSecurity http) throws Exception {
		final AuthenticationManagerBuilder authenticationManagerBuilder = 
			http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(msv3authService);
		return authenticationManagerBuilder.build();
	}

	@Bean
	public SecurityFilterChain filterChain(@NonNull final HttpSecurity http) throws Exception
	{
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authz -> authz
					.requestMatchers(MSV3ServerConstants.WEBSERVICE_ENDPOINT_PATH + "/**").hasAuthority(ROLE_CLIENT)
					.requestMatchers(MSV3ServerConstants.REST_ENDPOINT_PATH + "/**").hasAuthority(ROLE_SERVER_ADMIN)
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				)
				.httpBasic(httpBasic -> httpBasic
					.realmName(REALM)
					.authenticationEntryPoint(getBasicAuthEntryPoint())
				)
				.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);

		return http.build();
	}

	@Bean
	public BasicAuthenticationEntryPoint getBasicAuthEntryPoint()
	{
		final BasicAuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
		authenticationEntryPoint.setRealmName(REALM);
		return authenticationEntryPoint;
	}

}
