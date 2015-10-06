package de.metas.hostkey.spi.impl;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.webui.util.CookieUtil;
import org.compiere.util.CLogger;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import de.metas.hostkey.spi.IHostKeyStorage;

/**
 * Zk/Http Servlet implementation of {@link IHostKeyStorage}.
 * 
 * HostKey is stored and fetched from browser cookies.
 * 
 * @author tsa
 * 
 */
public class ZkHostKeyStorage implements IHostKeyStorage
{
	private static final transient CLogger logger = CLogger.getCLogger(ZkHostKeyStorage.class);

	private static final String COOKIE_Name = "adempiere.hostkey";
	private static final String COOKIE_Description = "Used by ADempiere to identify if user logged in from same browser, no matter on which network he/she connects."
			+ " Please note that this information is mandatory for providing host base configuration.";
	private static int COOKIE_MaxAge = 365 * 24 * 60 * 60; // 1year (in seconds)

	private final HttpServletRequest _httpRequest;

	private final HttpServletResponse _httpResponse;

	/**
	 * Constructs an {@link ZkHostKeyStorage} which will use current execution's HttpRequest and HttpResponse
	 */
	public ZkHostKeyStorage()
	{
		this(null, null);
	}

	/**
	 * 
	 * @param request if null, current execution's request will be used
	 * @param response if null, current execution's response will be used
	 */
	public ZkHostKeyStorage(final HttpServletRequest request, final HttpServletResponse response)
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

		final Execution execution = Executions.getCurrent();
		Check.assume(execution != null, "execution not null");

		final Object nativeRequest = execution.getNativeRequest();
		Check.assume(nativeRequest != null, "nativeRequest not null");
		if (nativeRequest instanceof HttpServletRequest)
		{
			final HttpServletRequest request = (HttpServletRequest)nativeRequest;
			return request;
		}
		else
		{
			throw new AdempiereException("Native request is not supported: " + nativeRequest);
		}
	}

	private final HttpServletResponse getActualHttpServletResponse()
	{
		if (_httpResponse != null)
		{
			return _httpResponse;
		}

		final Execution execution = Executions.getCurrent();
		Check.assume(execution != null, "execution not null");

		final Object nativeResponse = execution.getNativeResponse();
		Check.assume(nativeResponse != null, "nativeResponse not null");
		if (nativeResponse instanceof HttpServletResponse)
		{
			final HttpServletResponse response = (HttpServletResponse)nativeResponse;
			return response;
		}
		else
		{
			throw new AdempiereException("Native response is not supported: " + nativeResponse);
		}
	}

	@Override
	public void setHostKey(final String hostkey)
	{
		final HttpServletResponse httpResponse = getActualHttpServletResponse();

		// Always set back the cookie, because we want to renew the expire date
		if (!CookieUtil.setCookie(httpResponse, COOKIE_Name, hostkey, COOKIE_Description, COOKIE_MaxAge))
		{
			logger.log(Level.WARNING, "Cannot set cookie " + COOKIE_Name + ": " + hostkey);
		}
	}

	@Override
	public String getHostKey()
	{
		final HttpServletRequest httpRequest = getActualHttpServletRequest();
		final String hostkey = CookieUtil.getCookie(httpRequest, COOKIE_Name);

		if (Check.isEmpty(hostkey, true))
		{
			logger.log(Level.INFO, "No host key found");
		}

		return hostkey;
	}
}
