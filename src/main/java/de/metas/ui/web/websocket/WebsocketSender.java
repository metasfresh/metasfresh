package de.metas.ui.web.websocket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
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

@Component
public class WebsocketSender implements InitializingBean
{
	private static final transient Logger logger = LogManager.getLogger(WebsocketSender.class);

	@Autowired
	private SimpMessagingTemplate websocketMessagingTemplate;

	@Value("${metasfresh.webui.websocket.logEventsEnabled:false}")
	private boolean logEventsEnabledDefault;

	private final AtomicBoolean logEventsEnabled = new AtomicBoolean(false);
	private final AtomicInteger logEventsMaxSize = new AtomicInteger(500);
	private final List<WebsocketEvent> loggedEvents = new LinkedList<>();

	@Override
	public void afterPropertiesSet() throws Exception
	{
		setLogEventsEnabled(logEventsEnabledDefault);
	}

	public void convertAndSend(final String destination, final Object event)
	{
		websocketMessagingTemplate.convertAndSend(destination, event);
		logEvent(destination, event);
	}

	public void send(final String destination, final Message<?> message)
	{
		websocketMessagingTemplate.send(destination, message);
	}

	private final void logEvent(final String destination, final Object event)
	{
		if (!logEventsEnabled.get())
		{
			return;
		}

		synchronized (loggedEvents)
		{
			loggedEvents.add(new WebsocketEvent(destination, event));
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

	public List<WebsocketEvent> getLoggedEvents()
	{
		synchronized (loggedEvents)
		{
			return new ArrayList<>(loggedEvents);
		}
	}

	public List<WebsocketEvent> getLoggedEvents(final String destinationFilter)
	{
		return getLoggedEvents()
				.stream()
				.filter(websocketEvent -> websocketEvent.isDestinationMatching(destinationFilter))
				.collect(ImmutableList.toImmutableList());
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Value
	public static final class WebsocketEvent
	{
		private final String destination;
		private final Object payload;

		private boolean isDestinationMatching(final String destinationFilter)
		{
			if (destinationFilter == null || destinationFilter.isEmpty())
			{
				return true;
			}

			return destination.toLowerCase().contains(destinationFilter.toLowerCase().trim());
		}
	}
}
