package de.metas.ui.web.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

import de.metas.logging.LogManager;
import de.metas.ui.web.session.UserSession;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * metasfresh-webui logging component.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class ServletLoggingFilter implements Filter
{
	private static final Logger logger = LogManager.getLogger(ServletLoggingFilter.class);

	//
	// Core MDC parameters
	private static final String MDC_Param_RemoteAddr = "RemoteAddr";
	private static final String MDC_Param_RemoteAddr_DefaultValue = "server";

	private static final String MDC_Param_LoggedUser = "LoggedUser";
	private static final String MDC_Param_LoggedUser_DefaultValue = "_none";

	private static final String MDC_Param_UserAgent = "UserAgent";

	//
	// Derivated MDC parameters
	/**
	 * mainly this is needed for "de/metas/ui/web/logging/FILE-byLoggedUserAndRemoteAddr.xml"
	 */
	private static final String MDC_Param_LoggedUserAndRemoteAddr = "LoggedUserAndRemoteAddr";

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
		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			updateMDC(request);

			chain.doFilter(request, response);
		}
		finally
		{
			//
			// log the request
			if (logger.isInfoEnabled())
			{
				final String requestInfo = extractRequestInfo(request);
				logger.info("Executed in {}: {}", stopwatch.stop(), requestInfo);
			}

			//
			// Cleanup MDC (keep it last)
			cleanupMDC();
		}
	}

	public void updateMDC(final ServletRequest request)
	{
		if (!(request instanceof HttpServletRequest))
		{
			return;
		}
		final HttpServletRequest httpRequest = (HttpServletRequest)request;

		//
		// Core MDC parameters
		final String remoteAddr = extractRemoteAddr(httpRequest);
		final String loggedUser = extractLoggedUser(httpRequest);
		final String userAgent = extractUserAgent(httpRequest);
		MDC.put(MDC_Param_RemoteAddr, remoteAddr);
		MDC.put(MDC_Param_LoggedUser, loggedUser);
		MDC.put(MDC_Param_UserAgent, userAgent);

		//
		// Derivated MDC parameters
		MDC.put(MDC_Param_LoggedUserAndRemoteAddr, extractLoggedUserAndRemoteAddr(loggedUser, remoteAddr));
	}

	public void cleanupMDC()
	{
		MDC.remove(MDC_Param_RemoteAddr);
		MDC.remove(MDC_Param_LoggedUser);
		MDC.remove(MDC_Param_UserAgent);
	}

	private static final String extractRequestInfo(final ServletRequest request)
	{
		String requestInfo;
		if (request instanceof HttpServletRequest)
		{
			final HttpServletRequest httpRequest = (HttpServletRequest)request;
			final String urlStr = httpRequest.getRequestURL().toString();
			URI uri;
			try
			{
				uri = new URI(urlStr);
			}
			catch (final URISyntaxException e)
			{
				uri = null;
			}

			String path = null;
			if (uri != null)
			{
				path = uri.getPath();
			}
			if (path == null)
			{
				path = urlStr;
			}

			final String queryString = httpRequest.getQueryString();

			requestInfo = path + (queryString != null ? "?" + queryString : "");
		}
		else
		{
			requestInfo = request.toString();
		}

		return requestInfo;
	}

	private static final String extractRemoteAddr(final HttpServletRequest httpRequest)
	{
		try
		{
			final String remoteAddr = httpRequest.getRemoteAddr();
			if (remoteAddr == null || remoteAddr.isEmpty())
			{
				return MDC_Param_RemoteAddr_DefaultValue;
			}
			return remoteAddr;
		}
		catch (final Exception e)
		{
			return "?";
		}
	}

	private static final String extractLoggedUser(final HttpServletRequest httpRequest)
	{
		try
		{
			final UserSession userSession = UserSession.getCurrentOrNull();
			if (userSession == null)
			{
				return MDC_Param_LoggedUser_DefaultValue;
			}

			final String userName = userSession.getUserName();
			if (userName == null || userName.isEmpty())
			{
				return MDC_Param_LoggedUser_DefaultValue;
			}

			return userName;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return "?";
		}
	}

	private static final String extractUserAgent(final HttpServletRequest httpRequest)
	{
		try
		{
			final String userAgent = httpRequest.getHeader("User-Agent");
			return userAgent;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return "?";
		}
	}

	private static final String extractLoggedUserAndRemoteAddr(final String loggedUser, final String remoteAddr)
	{
		// NOTE: guard against null parameters, if those at this point they shall not
		return (loggedUser == null ? "?" : loggedUser)
				+ "/"
				+ (remoteAddr == null ? "?" : remoteAddr);
	}
}
