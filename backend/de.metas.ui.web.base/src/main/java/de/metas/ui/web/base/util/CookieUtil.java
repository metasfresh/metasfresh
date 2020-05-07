package de.metas.ui.web.base.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.ui.web.base
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

/**
 * Web Browser Cookie Helper
 * 
 * @author tsa
 * 
 */
public final class CookieUtil
{
	private static final Logger logger = LogManager.getLogger(CookieUtil.class);

	public static int DEFAULT_CookieMaxAge = 365 * 24 * 60 * 60; // 1year (in seconds)

	private CookieUtil()
	{
		super();
	}

	public static String getCookie(final String name)
	{
		final HttpServletRequest request = Services.get(IHttpSessionProvider.class).getCurrentRequest();
		return getCookie(request, name);
	}

	public static boolean setCookie(final String name, final String value)
	{
		final HttpServletResponse response = Services.get(IHttpSessionProvider.class).getCurrentResponse();
		return setCookie(response, name, value);
	}

	public static String getCookie(final HttpServletRequest request, final String name)
	{
		Check.assume(request != null, "request not null");
		final Cookie[] cookies = request.getCookies();
		
		return getCookie(cookies, name);
	}

	public static String getCookie(final Cookie[] cookies, final String name)
	{
		if (cookies == null || cookies.length == 0)
		{
			return null;
		}

		Check.assumeNotEmpty(name, "name is not empty");

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
			logger.warn(ex.getLocalizedMessage(), ex);
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
