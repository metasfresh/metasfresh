package org.adempiere.ui.notifications;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.Set;

import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.event.EventBusConstants;
import de.metas.logging.LogManager;

/**
 * Service used to manage the event notifier popup (that one that pops up on the bottom-right corner of the screen).
 * 
 * @author tsa
 *
 */
public final class SwingEventNotifierService
{
	public static final transient SwingEventNotifierService instance = new SwingEventNotifierService();
	private static final transient Logger logger = LogManager.getLogger(SwingEventNotifierService.class);

	/** Current notifications UI frame */
	private SwingEventNotifierFrame frame = null;

	public static SwingEventNotifierService getInstance()
	{
		return instance;
	}

	private SwingEventNotifierService()
	{
		super();

		try
		{
			JMXRegistry.get().registerJMX(new JMXSwingEventNotifierService(), OnJMXAlreadyExistsPolicy.Replace);
		}
		catch (Exception e)
		{
			logger.warn("Failed registering JMX bean", e);
		}
	}

	public final synchronized boolean isRunning()
	{
		return frame != null && !frame.isDisposed();
	}

	/**
	 * Start listening to subscribed topics and display the events.
	 * 
	 * If this was already started, this method does nothing.
	 */
	public synchronized void start()
	{
		if (!EventBusConstants.isEnabled())
		{
			logger.info("Not starting because it's not enabled");
			return;
		}

		try
		{
			if (frame != null && frame.isDisposed())
			{
				frame = null;
			}
			if (frame == null)
			{
				frame = new SwingEventNotifierFrame();
			}
		}
		catch (Exception e)
		{
			logger.warn("Failed starting the notification frame: " + frame, e);
		}
	}

	/**
	 * Stop listening events and destroy the whole popup.
	 * 
	 * If this was already started, this method does nothing.
	 */
	public synchronized void stop()
	{
		if (frame == null)
		{
			return;
		}

		try
		{
			frame.dispose();
		}
		catch (Exception e)
		{
			logger.warn("Failed disposing the notification frame: " + frame, e);
		}
	}

	public synchronized Set<String> getSubscribedTopicNames()
	{
		if (frame != null && !frame.isDisposed())
		{
			return frame.getSubscribedTopicNames();
		}
		else
		{
			return ImmutableSet.of();
		}
	}
}
