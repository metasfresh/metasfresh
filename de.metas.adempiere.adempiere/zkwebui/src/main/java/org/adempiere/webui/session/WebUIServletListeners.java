package org.adempiere.webui.session;

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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.util.Check;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * This class loads {@link IWebUIServletListener}s and facilitate calling them.
 * 
 * Listeners are loaded from AD_EntityType.WebUIServletListenerClass, for each entity type that is active.
 * 
 * @author tsa
 * 
 */
public final class WebUIServletListeners implements IWebUIServletListener
{
	public static final WebUIServletListeners instance = new WebUIServletListeners();

	public static final String CLASSNAME = "WebUIServletListener";

	public static WebUIServletListeners get()
	{
		return instance;
	}

	private final transient Logger logger = LogManager.getLogger(getClass());
	private List<IWebUIServletListener> listeners = null;

	private WebUIServletListeners()
	{

	}

	private ReentrantLock listenersLoadLock = new ReentrantLock();

	public List<IWebUIServletListener> getListeners()
	{
		listenersLoadLock.lock();
		try
		{
			if (listeners != null)
			{
				return listeners;
			}

			final List<IWebUIServletListener> list = new ArrayList<IWebUIServletListener>();

			//
			// Standard listeners
			list.add(new SystemWebUIURLConfigurator());

			//
			// EntityType listeners
			for (final String entityType : EntityTypesCache.instance.getEntityTypeNames())
			{
				final IWebUIServletListener activator = loadListener(entityType);
				if (activator != null)
				{
					list.add(activator);
				}
			}

			listeners = list;
			return listeners;
		}
		finally
		{
			listenersLoadLock.unlock();
		}
	}

	/**
	 * Loads and returns the proper listener for given entityType
	 * 
	 * @param entityType
	 * @return loaded {@link IWebUIServletListener} or null
	 */
	private IWebUIServletListener loadListener(final String entityType)
	{
		// Don't load if entityType is not active
		if (entityType == null)
		{
			return null;
		}
		if(!EntityTypesCache.instance.isActive(entityType))
		{
			return null;
		}

		final String classname = EntityTypesCache.instance.getWebUIServletListenerClass(entityType);
		if (Check.isEmpty(classname, true))
		{
			return null;
		}

		try
		{
			final IWebUIServletListener listener = Util.getInstance(IWebUIServletListener.class, classname);
			logger.info("Loaded " + listener + " (class=" + listener.getClass() + ", entityType=" + entityType + ")");
			return listener;
		}
		catch (Exception e)
		{
			logger.error("Error loading " + classname, e);
			return null;
		}
	}

	@Override
	public void init(ServletConfig servletConfig)
	{
		for (final IWebUIServletListener a : getListeners())
		{
			try
			{
				a.init(servletConfig);
			}
			catch (Exception e)
			{
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void destroy()
	{
		for (final IWebUIServletListener a : getListeners())
		{
			try
			{
				a.destroy();
			}
			catch (Exception e)
			{
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		for (final IWebUIServletListener a : getListeners())
		{
			try
			{
				a.doGet(request, response);
			}
			catch (Exception e)
			{
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		for (final IWebUIServletListener a : getListeners())
		{
			try
			{
				a.doPost(request, response);
			}
			catch (Exception e)
			{
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}
}
