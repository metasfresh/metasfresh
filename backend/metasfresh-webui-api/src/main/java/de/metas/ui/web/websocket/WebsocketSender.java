package de.metas.ui.web.websocket;

import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.async.Debouncer;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
 * <p>
 * NOTE: by default, all methods will send the events after the current DB transaction is committed.
 * If there is no current transaction, the events will be sent right away.
 *
 * @author metas-dev <dev@metasfresh.com>
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

	private final Debouncer<WebsocketEvent> debouncer;

	public WebsocketSender(final SimpMessagingTemplate websocketMessagingTemplate)
	{
		this.websocketMessagingTemplate = websocketMessagingTemplate;

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		this.debouncer = Debouncer.<WebsocketEvent>builder()
				.name(WebsocketSender.class.getSimpleName() + "-debouncer")
				.bufferMaxSize(sysConfigBL.getIntValue("webui.WebsocketSender.debouncer.bufferMaxSize", 500))
				.delayInMillis(sysConfigBL.getIntValue("webui.WebsocketSender.debouncer.delayInMillis", 200))
				.distinct(true)
				.consumer(this::sendEventsNow)
				.build();
		logger.info("debouncer: {}", debouncer);

		autoflushQueue = new WebsocketEventsQueue("AUTOFLUSH", /* autoflush */debouncer, true);
	}

	@Override
	public void afterPropertiesSet()
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

	public void convertAndSend(final WebsocketTopicName destination, final Object event)
	{
		getQueue().enqueueObject(destination, event);
	}

	public void sendMessage(final WebsocketTopicName destination, final Message<?> message)
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
		final WebsocketEventsQueue queue = new WebsocketEventsQueue(name, debouncer, autoflush);

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

	private void sendEventsNow(final List<WebsocketEvent> events)
	{
		events.forEach(this::sendEventNow);
	}

	private void sendEventNow(final WebsocketEvent event)
	{
		logger.debug("Sending {}", event);

		final WebsocketTopicName destination = event.getDestination();
		final Object payload = event.getPayload();
		final boolean converted = event.isConverted();

		if (converted)
		{
			final Message<?> message = (Message<?>)payload;
			websocketMessagingTemplate.send(destination.getAsString(), message);
		}
		else
		{
			websocketMessagingTemplate.convertAndSend(destination.getAsString(), payload);
		}

		eventsLog.logEvent(destination, payload);
		System.out.println("\tSent to " + destination + ": " + payload);
	}

	@lombok.Value
	@lombok.Builder
	private static class WebsocketEvent
	{
		WebsocketTopicName destination;
		Object payload;
		boolean converted;
	}

	private static class WebsocketEventsQueue
	{
		/**
		 * internal name, used for logging
		 */
		private final String name;
		private final boolean autoflush;
		private final List<WebsocketEvent> events = new ArrayList<>();
		private final Debouncer<WebsocketEvent> debouncer;

		public WebsocketEventsQueue(
				@NonNull final String name,
				final Debouncer<WebsocketEvent> debouncer,
				final boolean autoflush)
		{
			this.name = name;
			this.autoflush = autoflush;
			this.debouncer = debouncer;
		}

		public void enqueueObject(final WebsocketTopicName destination, final Object payload)
		{
			final WebsocketEvent event = WebsocketEvent.builder()
					.destination(destination)
					.payload(payload)
					.converted(false)
					.build();
			if (autoflush)
			{
				debouncer.add(event);
			}
			else
			{
				enqueue(event);
			}
		}

		public void enqueueMessage(final WebsocketTopicName destination, final Message<?> message)
		{
			final WebsocketEvent event = WebsocketEvent.builder()
					.destination(destination)
					.payload(message)
					.converted(true)
					.build();
			if (autoflush)
			{
				debouncer.add(event);
			}
			else
			{
				enqueue(event);
			}
		}

		private void enqueue(@NonNull final WebsocketEvent event)
		{
			events.add(event);
			logger.debug("[name={}] Enqueued event={}", name, event);
		}

		public void sendEventsAndClear()
		{
			logger.debug("Sending all queued events");

			final List<WebsocketEvent> eventsToSend = new ArrayList<>(events);
			events.clear();

			debouncer.addAll(eventsToSend);
		}
	}
}
