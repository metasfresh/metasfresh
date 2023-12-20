/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.core.restapi.processing;

import de.metas.camel.externalsystems.common.RestServiceRoutes;
import de.metas.camel.externalsystems.common.http.EmptyBodyRequestWrapper;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ToEmptyRequestBodyFilter implements Filter
{
	@Override
	public void doFilter(
			@NonNull final ServletRequest servletRequest,
			@NonNull final ServletResponse servletResponse,
			@NonNull final FilterChain filterChain) throws IOException
	{
		final HttpServletRequest currentRequest = (HttpServletRequest)servletRequest;
		final HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;

		try
		{
			final AntPathMatcher antPathMatcher = new AntPathMatcher();
			final boolean metasfreshApiEndpoint = antPathMatcher.match("**" + RestServiceRoutes.METASFRESH.getPath(),
																	   currentRequest.getRequestURL().toString());

			if (metasfreshApiEndpoint)
			{
				final HttpServletRequest wrappedRequest = new EmptyBodyRequestWrapper(currentRequest);
				filterChain.doFilter(wrappedRequest, servletResponse);
			}
			else
			{
				filterChain.doFilter(servletRequest, servletResponse);
			}
		}
		catch (final Exception exception)
		{
			SecurityContextHolder.clearContext();
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception.getMessage());
		}
	}
}
