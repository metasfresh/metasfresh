package de.metas.hostkey.spi.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import de.metas.hostkey.spi.IHostKeyStorage;
import de.metas.logging.LogManager;
import de.metas.hostkey.spi.IHttpSessionProvider;
import de.metas.util.Check;
import de.metas.util.Services;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
	private static final transient Logger logger = LogManager.getLogger(HttpCookieHostKeyStorage.class);

	private static final String COOKIE_Name = "metasfresh.hostkey";
	private static final String COOKIE_Description = "Used by metasfresh to identify if user logged in from same browser, no matter on which network he/she connects."
			+ " Please note that this information is mandatory for providing host base configuration.";
	private static final int COOKIE_MaxAge = 365 * 24 * 60 * 60; // 1year (in seconds)

	private HttpServletRequest getActualHttpServletRequest()
	{
		return Services.get(IHttpSessionProvider.class).getCurrentRequest();
	}

	private HttpServletResponse getActualHttpServletResponse()
	{
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
