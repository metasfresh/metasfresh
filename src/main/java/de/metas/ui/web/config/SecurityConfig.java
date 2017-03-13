package de.metas.ui.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import de.metas.ui.web.security.MetasfreshUserDetailsService;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	private MetasfreshUserDetailsService userDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception
	{
		return super.userDetailsServiceBean();
	}

	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		// authenticationProvider.setPasswordEncoder(new ShaPasswordEncoder());

		return authenticationProvider;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception
	{
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected AuthenticationManager authenticationManager() throws Exception
	{
		return super.authenticationManager();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception
	{
		//@formatter:off
		http
				.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
		
				.csrf().disable() // FIXME: disabled for now... need to figure out how to configure with REST
				.authorizeRequests()
					//
					// Swagger-UI
					.antMatchers("/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/springfox-swagger-ui/**", "/configuration/**")
						.permitAll()
					//
					// Login
					.antMatchers("/rest/api/login/auth").permitAll()
					//
					// Others
					.anyRequest()
						.permitAll() // FIXME: until we really implement the spring security it's better to permit ALL
						// .authenticated()
				//
//				.and()
//				.httpBasic()
		;
		//@formatter:on
	}
}
