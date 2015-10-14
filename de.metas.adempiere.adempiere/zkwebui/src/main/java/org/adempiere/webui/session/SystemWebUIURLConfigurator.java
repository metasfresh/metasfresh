package org.adempiere.webui.session;

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


import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.ad.service.ISystemBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_System;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * If {@link I_AD_System#getWebUI_URL()} is not set, set it (first time only) after first {@link HttpServletRequest}.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/04272_Passwort_zur%C3%BCcksetzen_Mail_an_alle_Benutzer_schicken_k%C3%B6nnen_%282013052110000079%29
 */
public class SystemWebUIURLConfigurator implements IWebUIServletListener
{
	private boolean initAfterRequestPerformed = false;
	private final ReentrantLock initAfterRequestLock = new ReentrantLock();

	@Override
	public void init(ServletConfig servletConfig)
	{
		initAfterRequestPerformed = false;
	}

	@Override
	public void destroy()
	{
		initAfterRequestPerformed = false;
		// nothing
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		initAfterRequest(request);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		initAfterRequest(request);
	}

	/**
	 * Performs initializations which require ServletRequest to perform.
	 * 
	 * NOTE: this method will actually run only first time.
	 * 
	 * @param request
	 */
	private final void initAfterRequest(final ServletRequest request)
	{
		if (initAfterRequestPerformed)
		{
			return;
		}

		initAfterRequestLock.lock();
		try
		{
			initSystemWebUIURL(request);
		}
		finally
		{
			initAfterRequestPerformed = true;
			initAfterRequestLock.unlock();
		}
	}

	/**
	 * Configures {@link I_AD_System#COLUMNNAME_WebUI_URL}
	 * 
	 * @param request
	 */
	private final void initSystemWebUIURL(final ServletRequest request)
	{
		final I_AD_System system = Services.get(ISystemBL.class).get(Env.getCtx());
		String webUIURL = system.getWebUI_URL();
		if (!Util.isEmpty(webUIURL, true))
		{
			// WebUI_URL was already set. We are not setting it again because maybe someone configured particularly in that way
			return;
		}

		if (!(request instanceof HttpServletRequest))
		{
			return;
		}

		final HttpServletRequest httpRequest = (HttpServletRequest)request;

		webUIURL = httpRequest.getScheme()
				+ "://" + httpRequest.getServerName()
				+ ":" + httpRequest.getServerPort()
				+ httpRequest.getContextPath()
				+ "/index.zul";
		system.setWebUI_URL(webUIURL);
		InterfaceWrapperHelper.save(system);
	}

}
