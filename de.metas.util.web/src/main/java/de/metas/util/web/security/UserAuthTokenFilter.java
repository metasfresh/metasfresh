package de.metas.util.web.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.adempiere.ad.security.UserNotAuthorizedException;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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

@WebFilter({ "/api/*" })
public class UserAuthTokenFilter implements Filter
{
	public static final String HEADER_Authorization = "Authorization";

	@Autowired
	private UserAuthTokenService userAuthTokenService;

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
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		final HttpServletResponse httpResponse = (HttpServletResponse)response;

		try
		{
			userAuthTokenService.run(
					() -> extractTokenString(httpRequest),
					() -> chain.doFilter(request, response));
		}
		catch (final UserNotAuthorizedException ex)
		{
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getLocalizedMessage());
			return;
		}
	}

	private String extractTokenString(final HttpServletRequest httpRequest)
	{
		final String authorizationString = httpRequest.getHeader(HEADER_Authorization);
		if (Check.isEmpty(authorizationString, true))
		{
			throw new AdempiereException("Provide token in `" + HEADER_Authorization + "` HTTP header");
		}

		if (authorizationString.startsWith("Token "))
		{
			return authorizationString.substring(5).trim();
		}
		else if (authorizationString.startsWith("Basic "))
		{
			final String userAndTokenString = new String(DatatypeConverter.parseBase64Binary(authorizationString.substring(5).trim()));
			final int index = userAndTokenString.indexOf(':');
			// final String username = userAndTokenString.substring(0, index);
			return userAndTokenString.substring(index + 1);
		}
		else
		{
			return authorizationString;
		}
	}
}
