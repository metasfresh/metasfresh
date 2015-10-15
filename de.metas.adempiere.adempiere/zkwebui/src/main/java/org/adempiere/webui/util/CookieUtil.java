package org.adempiere.webui.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.logging.Level;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.CLogger;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

/**
 * Web Browser Cookie Helper
 * 
 * @author tsa
 * 
 */
public final class CookieUtil
{
	private static final CLogger logger = CLogger.getCLogger(CookieUtil.class);

	public static int DEFAULT_CookieMaxAge = 365 * 24 * 60 * 60; // 1year (in seconds)

	private CookieUtil()
	{
		super();
	}

	public static String getCookie(final String name)
	{
		final Execution execution = Executions.getCurrent();
		Check.assume(execution != null, "execution not null");

		final Object nativeRequest = execution.getNativeRequest();
		Check.assume(nativeRequest != null, "nativeRequest not null");
		if (nativeRequest instanceof HttpServletRequest)
		{
			final HttpServletRequest request = (HttpServletRequest)nativeRequest;
			return getCookie(request, name);
		}
		else
		{
			throw new AdempiereException("Native request is not supported: " + nativeRequest);
		}
	}

	public static boolean setCookie(final String name, final String value)
	{
		final Execution execution = Executions.getCurrent();
		Check.assume(execution != null, "execution not null");

		final Object nativeResponse = execution.getNativeResponse();
		Check.assume(nativeResponse != null, "nativeResponse not null");
		if (nativeResponse instanceof HttpServletResponse)
		{
			final HttpServletResponse response = (HttpServletResponse)nativeResponse;
			return setCookie(response, name, value);
		}
		else
		{
			throw new AdempiereException("Native response is not supported: " + nativeResponse);
		}
	}

	public static String getCookie(final HttpServletRequest request, final String name)
	{
		Check.assume(request != null, "request not null");
		Check.assume(!Check.isEmpty(name), "name not empty");

		final Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0)
		{
			return null;
		}

		for (final Cookie cookie : cookies)
		{
			if (name.equals(cookie.getName()))
			{
				return cookie.getValue();
			}
		}

		return null;
	}

	public static boolean setCookie(final HttpServletResponse response, final String name, final String value)
	{
		final String description = null;
		return setCookie(response, name, value, description, DEFAULT_CookieMaxAge);
	}

	public static boolean setCookie(final HttpServletResponse response, final String name, final String value, final String description, final int maxAge)
	{
		Check.assume(response != null, "response not null");
		Check.assume(!Check.isEmpty(name), "name not empty");

		if (response.isCommitted())
		{
			final AdempiereException ex = new AdempiereException("Response '" + response + "' was already committed. Cannot set the cookie anymore (name=" + name + ", value=" + value + ")");
			logger.log(Level.WARNING, ex.getLocalizedMessage(), ex);
			return false;
		}

		final Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge); // 1year (in seconds)
		cookie.setSecure(false); // send cookie even if not using HTTPS (default)

		// Leaving the Domain empty (domain from Request will be used) to prevent browser rejecting the cookie
		// See http://www.ietf.org/rfc/rfc2109.txt, chapter 4.3.2
		// cookie.setDomain(null);

		if (description != null)
		{
			cookie.setComment(description);
		}
		response.addCookie(cookie);

		return true;
	}
}
