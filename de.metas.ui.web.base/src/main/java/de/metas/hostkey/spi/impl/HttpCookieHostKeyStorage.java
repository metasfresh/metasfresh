package de.metas.hostkey.spi.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.hostkey.api.IHostKeyBL;
import de.metas.hostkey.spi.IHostKeyStorage;
import de.metas.logging.LogManager;
import de.metas.ui.web.base.util.CookieUtil;
import de.metas.ui.web.base.util.IHttpSessionProvider;

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
 * Http Servlet implementation of {@link IHostKeyStorage}.
 * 
 * HostKey is stored and fetched from browser cookies.
 * 
 * @author tsa
 * 
 */
public class HttpCookieHostKeyStorage implements IHostKeyStorage
{
	public static void createUpdateHostKey()
	{
		final HttpServletRequest request = null;
		final HttpServletResponse response = null;
		createUpdateHostKey(request, response);
	}

	public static void createUpdateHostKey(final HttpServletRequest request, final HttpServletResponse response)
	{
		final IHostKeyBL hostKeyBL = Services.get(IHostKeyBL.class);

		//
		// Create HostKey if needed
		final HttpCookieHostKeyStorage servletHostkeyStorage = new HttpCookieHostKeyStorage(request, response);
		hostKeyBL.getCreateHostKey(servletHostkeyStorage);

		//
		// Configure default storage (which fetches hostkey from current Execution)
		hostKeyBL.setHostKeyStorage(new HttpCookieHostKeyStorage());
	}

	private static final transient Logger logger = LogManager.getLogger(HttpCookieHostKeyStorage.class);

	public static final String COOKIE_Name = "adempiere.hostkey";
	public static final String COOKIE_Description = "Used by metasfresh to identify if user logged in from same browser, no matter on which network he/she connects."
			+ " Please note that this information is mandatory for providing host base configuration.";
	public static int COOKIE_MaxAge = 365 * 24 * 60 * 60; // 1year (in seconds)

	private final HttpServletRequest _httpRequest;

	private final HttpServletResponse _httpResponse;

	/**
	 * Constructs an {@link HttpCookieHostKeyStorage} which will use current execution's HttpRequest and HttpResponse
	 */
	public HttpCookieHostKeyStorage()
	{
		this(null, null);
	}

	/**
	 * 
	 * @param request if null, current execution's request will be used
	 * @param response if null, current execution's response will be used
	 */
	public HttpCookieHostKeyStorage(final HttpServletRequest request, final HttpServletResponse response)
	{
		super();
		this._httpRequest = request;
		this._httpResponse = response;
	}

	private final HttpServletRequest getActualHttpServletRequest()
	{
		if (_httpRequest != null)
		{
			return _httpRequest;
		}
		return Services.get(IHttpSessionProvider.class).getCurrentRequest();
	}

	private final HttpServletResponse getActualHttpServletResponse()
	{
		if (_httpResponse != null)
		{
			return _httpResponse;
		}
		return Services.get(IHttpSessionProvider.class).getCurrentResponse();
	}

	@Override
	public void setHostKey(final String hostkey)
	{
		final HttpServletResponse httpResponse = getActualHttpServletResponse();

		// Always set back the cookie, because we want to renew the expire date
		if (!CookieUtil.setCookie(httpResponse, COOKIE_Name, hostkey, COOKIE_Description, COOKIE_MaxAge))
		{
			logger.warn("Cannot set cookie " + COOKIE_Name + ": " + hostkey);
		}
	}

	@Override
	public String getHostKey()
	{
		final HttpServletRequest httpRequest = getActualHttpServletRequest();
		final String hostkey = CookieUtil.getCookie(httpRequest, COOKIE_Name);

		if (Check.isEmpty(hostkey, true))
		{
			logger.info("No host key found");
		}

		return hostkey;
	}
}
