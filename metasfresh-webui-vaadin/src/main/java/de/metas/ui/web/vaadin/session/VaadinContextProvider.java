package de.metas.ui.web.vaadin.session;

import java.util.Properties;

import org.adempiere.context.ContextProvider;
import org.adempiere.context.ThreadLocalContextProvider;
import org.adempiere.util.lang.IAutoCloseable;

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

public class VaadinContextProvider implements ContextProvider
{
	final ThreadLocalContextProvider serverContextProvider = new ThreadLocalContextProvider();
	
	private final ContextProvider getDelegate()
	{
		final UserSession userSession = UserSession.getCurrent();
		if (userSession == null)
		{
			return serverContextProvider;
		}
		return userSession.getContextProvider();
	}

	@Override
	public void init()
	{
		getDelegate().init();
	}

	@Override
	public Properties getContext()
	{
		return getDelegate().getContext();
	}

	@Override
	public IAutoCloseable switchContext(final Properties ctx)
	{
		return getDelegate().switchContext(ctx);
	}

	@Override
	public void reset()
	{
		getDelegate().reset();
	}
}
