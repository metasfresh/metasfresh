package de.metas.ui.web.vaadin.session;

import org.adempiere.util.lang.Mutable;

import com.vaadin.server.VaadinSession;

import de.metas.ui.web.base.session.UserPreference;

/*
 * #%L
 * test_vaadin
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

public class UserSession
{
	public static final UserSession getCurrent()
	{
		final VaadinSession vaadinSession = VaadinSession.getCurrent();
		if(vaadinSession == null)
		{
			return null;
		}
		
		if (vaadinSession.hasLock())
		{
			return getCurrent(vaadinSession);
		}
		else
		{
			final Mutable<UserSession> userSessionRef = new Mutable<UserSession>();
			vaadinSession.accessSynchronously(new Runnable()
			{
				
				@Override
				public void run()
				{
					userSessionRef.setValue(getCurrent(vaadinSession));
				}
			});
			
			return userSessionRef.getValue();
		}
	}
	
	private static final UserSession getCurrent(final VaadinSession vaadinSession)
	{
		UserSession userSession = vaadinSession.getAttribute(UserSession.class);
		if (userSession == null)
		{
			userSession = new UserSession();
			vaadinSession.setAttribute(UserSession.class, userSession);
		}
		return userSession;
	}
	
	private final UserSessionContextProvider contextProvider = new UserSessionContextProvider();
	private final UserPreference userPreference = new UserPreference();
	private boolean loggedIn = false;
	
	private UserSession()
	{
		super();
	}
	
	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public void setLoggedIn(final boolean loggedIn)
	{
		this.loggedIn = loggedIn;
	}
	
	public UserSessionContextProvider getContextProvider()
	{
		return contextProvider;
	}
	
	public UserPreference getUserPreference()
	{
		return userPreference;
	}
}
