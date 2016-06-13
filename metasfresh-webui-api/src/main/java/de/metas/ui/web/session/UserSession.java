package de.metas.ui.web.session;

import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;

import org.compiere.util.Env;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import de.metas.ui.web.base.session.UserPreference;

/*
 * #%L
 * metasfresh-webui-api
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

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("serial")
public class UserSession implements Serializable
{
	private UserPreference userPreference;
	private boolean loggedIn = false;
	private Locale locale = Locale.getDefault();

	public UserSession()
	{
		super();
		this.userPreference = new UserPreference();
	}

	public Properties getCtx()
	{
		return Env.getCtx();
	}
	
	public UserPreference getUserPreference()
	{
		return userPreference;
	}
	
	public boolean isLoggedIn()
	{
		return loggedIn;
	}
	
	public void setLoggedIn(final boolean loggedIn)
	{
		this.loggedIn = loggedIn;
	}
	
	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}
	
	public Locale getLocale()
	{
		return locale;
	}
}
