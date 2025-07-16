/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.camel.externalsystems.core.restapi.auth;

import com.sun.istack.NotNull;
import de.metas.camel.externalsystems.common.RestServiceRoutes;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static de.metas.camel.externalsystems.core.restapi.auth.preauthenticated.ActuatorIdentity.ACTUATOR_AUTHORITY;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig
{
	private final TokenAuthProvider tokenAuthProvider;
	private final ProducerTemplate producerTemplate;
	private final AuthenticationConfiguration authConfig;

	public WebSecurityConfig(
			@NotNull final TokenAuthProvider tokenAuthProvider,
			@NotNull final ProducerTemplate producerTemplate,
			@NotNull final AuthenticationConfiguration authConfig)
	{
		this.tokenAuthProvider = tokenAuthProvider;
		this.producerTemplate = producerTemplate;
		this.authConfig = authConfig;
	}

	@Bean
	public SecurityFilterChain filterChain(@NotNull final HttpSecurity http) throws Exception
	{
		//@formatter:off
		http.authenticationProvider(tokenAuthProvider)
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/**" + RestServiceRoutes.WOO.getPath()).hasAuthority(RestServiceRoutes.WOO.getStringAuthority())
						.requestMatchers("/**" + RestServiceRoutes.GRS.getPath()).hasAuthority(RestServiceRoutes.GRS.getStringAuthority())
						.requestMatchers("/actuator/**/*").hasAuthority(ACTUATOR_AUTHORITY)
						.anyRequest()
						.authenticated()
				);
		//@formatter:on

		http.addFilterBefore(new AuthenticationFilter(authConfig.getAuthenticationManager()), BasicAuthenticationFilter.class);
		http.addFilterAfter(new AuditTrailFilter(producerTemplate), AuthenticationFilter.class);

		return http.build();
	}
}
