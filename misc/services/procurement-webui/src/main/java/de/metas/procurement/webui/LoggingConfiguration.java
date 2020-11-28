package de.metas.procurement.webui;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.vaadin.spring.request.VaadinRequestEndListener;
import org.vaadin.spring.request.VaadinRequestStartListener;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WebBrowser;
import com.vaadin.ui.UI;

import de.metas.procurement.webui.model.User;

/*
 * #%L
 * de.metas.procurement.webui
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
 * Logging configuration
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Component
public class LoggingConfiguration implements VaadinRequestStartListener, VaadinRequestEndListener
{
	private static final String MDC_Param_RemoteAddr = "RemoteAddr";
	private static final String MDC_Param_LoggedUser = "LoggedUser";
	private static final String MDC_Param_UserAgent = "UserAgent";
	private static final String MDC_Param_SessionId = "SessionId";
	private static final String MDC_Param_UIId = "UIId";

	@Override
	public void onRequestStart(final VaadinRequest request, final VaadinResponse response)
	{
		updateMDC();
	}

	@Override
	public void onRequestEnd(final VaadinRequest request, final VaadinResponse response, final VaadinSession session)
	{
		destroyMDC();
	}

	public void updateMDC()
	{
		//
		// Remote address
		try
		{
			final VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
			if (vaadinRequest != null)
			{
				final String remoteAddr = vaadinRequest.getRemoteAddr();
				MDC.put(MDC_Param_RemoteAddr, remoteAddr);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			MDC.put(MDC_Param_RemoteAddr, "?");
		}

		//
		// LoggedUser
		try
		{
			final MFSession mfSession = MFProcurementUI.getCurrentMFSession();
			if (mfSession != null)
			{
				final User user = mfSession.getUser();
				if (user != null)
				{
					final String email = user.getEmail();
					if (email != null)
					{
						MDC.put(MDC_Param_LoggedUser, email);
					}
				}
			}

		}
		catch (final Exception e)
		{
			e.printStackTrace();
			MDC.put(MDC_Param_LoggedUser, "?");
		}

		//
		// UserAgent
		try
		{
			final Page page = Page.getCurrent();
			if (page != null)
			{
				final WebBrowser webBrowser = page.getWebBrowser();
				if (webBrowser != null)
				{
					final String userAgent = webBrowser.getBrowserApplication();
					MDC.put(MDC_Param_UserAgent, userAgent);
				}
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			MDC.put(MDC_Param_UserAgent, "?");
		}

		//
		// SessionId
		try
		{
			final VaadinSession session = VaadinSession.getCurrent();
			if (session != null)
			{
				final int sessionId = System.identityHashCode(session);
				MDC.put(MDC_Param_SessionId, Integer.toString(sessionId));
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			MDC.put(MDC_Param_SessionId, "?");
		}

		//
		// UI Id
		try
		{
			final UI ui = UI.getCurrent();
			if (ui != null)
			{
				final int uiId = ui.getUIId();
				MDC.put(MDC_Param_UIId, Integer.toString(uiId));
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			MDC.put(MDC_Param_UIId, "?");
		}
	}

	public void destroyMDC()
	{
		MDC.remove(MDC_Param_RemoteAddr);
		MDC.remove(MDC_Param_LoggedUser);
		MDC.remove(MDC_Param_UserAgent);
		MDC.remove(MDC_Param_SessionId);
		MDC.remove(MDC_Param_UIId);
	}
}
