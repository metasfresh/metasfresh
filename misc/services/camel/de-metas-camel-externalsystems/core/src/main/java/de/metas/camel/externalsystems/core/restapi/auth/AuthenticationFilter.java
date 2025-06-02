/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2021 metas GmbH
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
import lombok.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter
{
	private static final String HEADER_AUTH_TOKEN = "X-Auth-Token";

	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(@NotNull final AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(
			@NotNull final ServletRequest servletRequest,
			@NonNull final ServletResponse servletResponse,
			@NonNull final FilterChain filterChain) throws IOException
	{
		final HttpServletRequest httpRequest = (HttpServletRequest)(servletRequest);
		final HttpServletResponse httpResponse = (HttpServletResponse)(servletResponse);

		final String token = httpRequest.getHeader(HEADER_AUTH_TOKEN);

		try
		{
			final PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(token, null);

			final Authentication authentication = authenticationManager.authenticate(preAuthenticatedAuthenticationToken);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(httpRequest, httpResponse);
		}
		catch (final Exception authenticationException)
		{
			SecurityContextHolder.clearContext();
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
		}
	}
}
