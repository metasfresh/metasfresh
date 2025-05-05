/*
 * #%L
 * de.metas.util.web
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

package de.metas.util.web.github;

import de.metas.security.UserNotAuthorizedException;
import de.metas.util.web.request.EditableHttpServletRequest;
import lombok.NonNull;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GithubIssueFilter implements Filter
{
	private final IAuthenticateGithubService authenticateGithubService;

	public GithubIssueFilter(@NonNull final IAuthenticateGithubService authenticateGithubService)
	{
		this.authenticateGithubService = authenticateGithubService;
	}

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException
	{

	}

	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException
	{
		final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse)response;

		try
		{
			final EditableHttpServletRequest requestWrapper = new EditableHttpServletRequest(httpServletRequest);

			authenticateGithubService.authenticateAndLocateAuthorization(requestWrapper);

			chain.doFilter(requestWrapper, httpServletResponse);
		}
		catch (final UserNotAuthorizedException ex)
		{
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getLocalizedMessage());
		}
	}
}
