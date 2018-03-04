package de.metas.ui.web.websocket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public final class WebsocketEventsLog
{
	private static final Logger logger = LogManager.getLogger(WebsocketEventsLog.class);

	private final AtomicBoolean logEventsEnabled = new AtomicBoolean(false);
	private final AtomicInteger logEventsMaxSize = new AtomicInteger(500);
	private final List<WebsocketEventLogRecord> loggedEvents = new LinkedList<>();

	public final void logEvent(final String destination, final Object event)
	{
		if (!logEventsEnabled.get())
		{
			return;
		}

		synchronized (loggedEvents)
		{
			loggedEvents.add(new WebsocketEventLogRecord(destination, event));
			final int maxSize = logEventsMaxSize.get();
			while (loggedEvents.size() > maxSize)
			{
				loggedEvents.remove(0);
			}
		}
	}

	public void setLogEventsEnabled(final boolean enabled)
	{
		final boolean enabledOld = logEventsEnabled.getAndSet(enabled);
		logger.info("Changed logEventsEnabled from {} to {}", enabledOld, enabled);
	}

	public void setLogEventsMaxSize(final int logEventsMaxSizeNew)
	{
		Preconditions.checkArgument(logEventsMaxSizeNew > 0, "logEventsMaxSize > 0");
		final int logEventsMaxSizeOld = logEventsMaxSize.getAndSet(logEventsMaxSizeNew);
		logger.info("Changed logEventsMaxSize from {} to {}", logEventsMaxSizeOld, logEventsMaxSizeNew);
	}

	public List<WebsocketEventLogRecord> getLoggedEvents()
	{
		synchronized (loggedEvents)
		{
			return new ArrayList<>(loggedEvents);
		}
	}

	public List<WebsocketEventLogRecord> getLoggedEvents(final String destinationFilter)
	{
		return getLoggedEvents()
				.stream()
				.filter(websocketEvent -> websocketEvent.isDestinationMatching(destinationFilter))
				.collect(ImmutableList.toImmutableList());
	}
}
