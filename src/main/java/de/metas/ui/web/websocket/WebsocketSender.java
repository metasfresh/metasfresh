package de.metas.ui.web.websocket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import lombok.NonNull;

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

/**
 * Websocket events sender.
 *
 * NOTE: by default, all methods will send the events after the current DB transaction is committed.
 * If there is no current transaction, the events will be sent right away.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class WebsocketSender implements InitializingBean
{
	private static final transient Logger logger = LogManager.getLogger(WebsocketSender.class);

	private final SimpMessagingTemplate websocketMessagingTemplate;
	private final WebsocketEventsLog eventsLog = new WebsocketEventsLog();
	private final WebsocketEventsQueue autoflushQueue;

	@Value("${metasfresh.webui.websocket.logEventsEnabled:false}")
	private boolean logEventsEnabledDefault;

	public WebsocketSender(final SimpMessagingTemplate websocketMessagingTemplate)
	{
		this.websocketMessagingTemplate = websocketMessagingTemplate;
		autoflushQueue = new WebsocketEventsQueue("AUTOFLUSH", websocketMessagingTemplate, eventsLog, /* autoflush */true);
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		eventsLog.setLogEventsEnabled(logEventsEnabledDefault);
	}

	public void convertAndSend(final Collection<? extends WebsocketEndpointAware> events)
	{
		events.forEach(this::convertAndSend);
	}

	public void convertAndSend(final WebsocketEndpointAware event)
	{
		convertAndSend(event.getWebsocketEndpoint(), event);
	}

	public void convertAndSend(final String destination, final Object event)
	{
		getQueue().enqueueObject(destination, event);
	}

	public void sendMessage(final String destination, final Message<?> message)
	{
		getQueue().enqueueMessage(destination, message);
	}

	private WebsocketEventsQueue getQueue()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isActive(trx))
		{
			return autoflushQueue;
		}
		else if (!trx.getTrxListenerManager().canRegisterOnTiming(TrxEventTiming.AFTER_COMMIT))
		{
			return autoflushQueue;
		}
		else
		{
			return trx.getProperty(WebsocketEventsQueue.class.getName(), () -> createAndBindTrxQueue(trx));
		}
	}

	private WebsocketEventsQueue createAndBindTrxQueue(@NonNull final ITrx trx)
	{
		final String name = trx.getTrxName();
		final boolean autoflush = false;
		final WebsocketEventsQueue queue = new WebsocketEventsQueue(name, websocketMessagingTemplate, eventsLog, autoflush);

		// Bind
		trx.getTrxListenerManager()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(innerTrx -> queue.sendEventsAndClear());

		return queue;
	}

	public void setLogEventsEnabled(final boolean enabled)
	{
		eventsLog.setLogEventsEnabled(enabled);
	}

	public void setLogEventsMaxSize(final int logEventsMaxSize)
	{
		eventsLog.setLogEventsMaxSize(logEventsMaxSize);
	}

	public List<WebsocketEventLogRecord> getLoggedEvents(final String destinationFilter)
	{
		return eventsLog.getLoggedEvents(destinationFilter);
	}

	@lombok.Value
	@lombok.Builder
	private static final class WebsocketEvent
	{
		private final String destination;
		private final Object payload;
		private final boolean converted;
	}

	private static class WebsocketEventsQueue
	{
		/** internal name, used for logging */
		private final String name;
		private final SimpMessagingTemplate websocketMessagingTemplate;
		private final WebsocketEventsLog eventsLog;
		private final boolean autoflush;
		private final List<WebsocketEvent> events = new ArrayList<>();

		public WebsocketEventsQueue(
				@NonNull final String name,
				@NonNull final SimpMessagingTemplate websocketMessagingTemplate,
				@NonNull final WebsocketEventsLog eventsLog,
				final boolean autoflush)
		{
			this.name = name;
			this.websocketMessagingTemplate = websocketMessagingTemplate;
			this.eventsLog = eventsLog;
			this.autoflush = autoflush;
		}

		public void enqueueObject(final String destination, final Object payload)
		{
			final boolean converted = false;
			if (autoflush)
			{
				sendEvent(destination, payload, converted);
			}
			else
			{
				enqueue(WebsocketEvent.builder()
						.destination(destination)
						.payload(payload)
						.converted(converted)
						.build());
			}
		}

		public void enqueueMessage(final String destination, final Message<?> message)
		{
			final boolean converted = true;
			if (autoflush)
			{
				sendEvent(destination, message, converted);
			}
			else
			{
				enqueue(WebsocketEvent.builder()
						.destination(destination)
						.payload(message)
						.converted(converted)
						.build());
			}
		}

		private void enqueue(@NonNull final WebsocketEvent event)
		{
			events.add(event);
			logger.info("[name={}] Enqueued event={}", name, event);
		}

		public void sendEventsAndClear()
		{
			logger.info("Sending all queued events");

			final List<WebsocketEvent> eventsToSend = new ArrayList<>(events);
			events.clear();

			eventsToSend.forEach(this::sendEvent);
		}

		private void sendEvent(final WebsocketEvent event)
		{
			final String destination = event.getDestination();
			final Object payload = event.getPayload();
			final boolean converted = event.isConverted();
			sendEvent(destination, payload, converted);
		}

		private void sendEvent(final String destination, final Object payload, final boolean converted)
		{
			logger.info("[name={}] Sending to destination={}: payload={}", name, destination, payload);

			if (converted)
			{
				final Message<?> message = (Message<?>)payload;
				websocketMessagingTemplate.send(destination, message);
			}
			else
			{
				websocketMessagingTemplate.convertAndSend(destination, payload);
				eventsLog.logEvent(destination, payload);
			}
		}
	}
}
