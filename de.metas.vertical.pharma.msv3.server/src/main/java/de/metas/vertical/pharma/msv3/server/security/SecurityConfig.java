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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import de.metas.vertical.pharma.msv3.server.MSV3ServerConstants;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	private static final String REALM = "MSV3_SERVER";
	public static final String ROLE_CLIENT = "ROLE_CLIENT";
	public static final String ROLE_SERVER_ADMIN = "ROLE_SERVER_ADMIN";

	@Autowired
	public void configureGlobalSecurity(
			final AuthenticationManagerBuilder auth,
			final MSV3ServerAuthenticationService msv3authService) throws Exception
	{
		auth.userDetailsService(msv3authService);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception
	{
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers(MSV3ServerConstants.WEBSERVICE_ENDPOINT_PATH + "/**").hasAuthority(ROLE_CLIENT)
				.antMatchers(MSV3ServerConstants.REST_ENDPOINT_PATH + "/**").hasAuthority(ROLE_SERVER_ADMIN)
				.and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Bean
	public BasicAuthenticationEntryPoint getBasicAuthEntryPoint()
	{
		final BasicAuthenticationEntryPoint authenticationEntryPoint = new BasicAuthenticationEntryPoint();
		authenticationEntryPoint.setRealmName(REALM);
		return authenticationEntryPoint;
	}

	/* To allow Pre-flight [OPTIONS] request from browser */
	@Override
	public void configure(final WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}
