package de.metas.event.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.base.MoreObjects;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.SubscriberExceptionHandler;
import de.metas.event.Event;
import de.metas.event.EventBusConfig;
import de.metas.event.EventBusStats;
import de.metas.event.EventEnqueuer;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.event.log.EventLogEntryCollector;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogUserService;
import de.metas.util.JSONObjectMapper;
import de.metas.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

final class EventBus implements IEventBus
{
	private static final Logger logger = EventBusConfig.getLogger(EventBus.class);

	/**
	 * Log all event bus exceptions
	 */
	private static final SubscriberExceptionHandler exceptionHandler = (exception, context) -> {
		final String errmsg = "Could not dispatch event: " + context.getSubscriber() + " to " + context.getSubscriberMethod()
				+ "\n Event: " + context.getEvent()
				+ "\n Bus: " + context.getEventBus();
		logger.error(errmsg, exception);
	};

	private static final JSONObjectMapper<Object> sharedJsonSerializer = JSONObjectMapper.forClass(Object.class);

	private com.google.common.eventbus.EventBus eventBus;

	private final IdentityHashMap<IEventListener, GuavaEventListenerAdapter> subscribedEventListener2GuavaListener = new IdentityHashMap<>();

	@Getter
	private boolean destroyed = false;

	@Getter
	private final boolean async;

	@Getter
	@NonNull
	private final Topic topic;

	private final ExecutorService executorOrNull;

	private final MicrometerEventBusStatsCollector micrometerEventBusStatsCollector;

	private final EventEnqueuer eventEnqueuer;

	private final EventBusMonitoringService eventBusMonitoringService;

	private final EventLogService eventLogService;

	/**
	 * @param executor if not null, the system creates an {@link AsyncEventBus}; also, it shuts down this executor on {@link #destroy()}
	 */
	public EventBus(
			@NonNull final Topic topic,
			@Nullable final ExecutorService executor,
			@NonNull final MicrometerEventBusStatsCollector micrometerEventBusStatsCollector,
			@NonNull final EventEnqueuer eventEnqueuer,
			@NonNull final EventBusMonitoringService eventBusMonitoringService,
			@NonNull final EventLogService eventLogService)
	{
		this.micrometerEventBusStatsCollector = micrometerEventBusStatsCollector;
		this.executorOrNull = executor;
		this.topic = topic;
		this.eventEnqueuer = eventEnqueuer;
		this.eventBusMonitoringService = eventBusMonitoringService;
		this.eventLogService = eventLogService;

		if (executor == null)
		{
			this.eventBus = new com.google.common.eventbus.EventBus(exceptionHandler);
			this.async = false;

		}
		else
		{
			this.eventBus = new com.google.common.eventbus.AsyncEventBus(executor, exceptionHandler);
			this.async = true;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("topicName", topic.getName())
				.add("type", topic.getType())
				.add("destroyed", destroyed ? Boolean.TRUE : null)
				.toString();
	}

	void destroy()
	{
		this.destroyed = true;
		this.eventBus = null;

		if (executorOrNull != null)
		{
			executorOrNull.shutdown(); // not 100% sure it's needed, but better safe than sorry
		}
		logger.trace("Destroyed EventBus={}", this);
	}

	@Override
	public void subscribe(@NonNull final Consumer<Event> eventConsumer)
	{
		final IEventListener listener = (eventBus, event) -> eventConsumer.accept(event);
		subscribe(listener);
	}

	@Override
	public <T> IEventListener subscribeOn(@NonNull final Class<T> type, @NonNull final Consumer<T> eventConsumer)
	{
		final TypedConsumerAsEventListener<T> listener = new TypedConsumerAsEventListener<>(type, eventConsumer);
		subscribe(listener);
		return listener;
	}

	@Override
	public void subscribe(@NonNull final IEventListener listener)
	{
		// Do nothing if destroyed
		if (destroyed)
		{
			logger.warn("Attempt to register a listener to a destroyed bus. Ignored. \n Bus: {} \n Listener: {}", this, listener);
			return;
		}

		if (subscribedEventListener2GuavaListener.get(listener) != null)
		{
			logger.warn("Attempt to register a listener that was already registered. Ignored. \n Bus: {} \n Listener: {}", this, listener);
			return;
		}

		final GuavaEventListenerAdapter listenerAdapter = new GuavaEventListenerAdapter(listener);

		subscribedEventListener2GuavaListener.put(listener, listenerAdapter);

		eventBus.register(listenerAdapter);
		logger.debug("registered listener; Listener: {}; Bus:\n{}", listener, this);
	}

	@Override
	public void unsubscribe(@NonNull final IEventListener listener)
	{
		// Do nothing if destroyed
		if (destroyed)
		{
			logger.warn("Attempt to unregister a listener from a destroyed bus. -> Ignore; Listener: {}; Bus:\n{}", listener, this);
			return;
		}

		final GuavaEventListenerAdapter listenerAdapter = subscribedEventListener2GuavaListener.get(listener);
		if (listenerAdapter == null)
		{
			logger.warn("Attempt to unregister a listener that is aparently not registered. -> Ignore; Listener: {}; Bus:\n{}", listener, this);
			return;
		}

		try
		{
			eventBus.unregister(listenerAdapter);
			logger.debug("unregistered listener; Listener: {}; Bus:\n{}", listener, this);
		}
		catch (final IllegalArgumentException e)
		{
			logger.warn("Attempt to unregister a listener, but internal event bus sais was not registered; -> Ignore; Listener: {}; Bus:\n{}", listener, this);
		}
	}

	@Override
	public void enqueueObject(@NonNull final Object obj)
	{
		final String json = sharedJsonSerializer.writeValueAsString(obj);
		enqueueEvent(Event.builder()
				.withBody(json)
				.shallBeLogged()
				.build());
	}

	@Override
	public void enqueueObjectsCollection(@NonNull final Collection<?> objs)
	{
		if (objs.isEmpty())
		{
			return;
		}

		objs.forEach(this::enqueueObject);
	}

	@Override
	public void processEvent(@NonNull final Event event)
	{
		try (final MDCCloseable ignored = EventMDC.putEvent(event))
		{
			// Do nothing if destroyed
			if (destroyed)
			{
				logger.warn("Attempt to post an event to a destroyed bus. Ignored. \n Bus: {} \n Event: {}", this, event);
				return;
			}

			logger.debug("{} - Posting event: {}, Timestamp={}, ThreadId={}", this, event, Instant.now(), Thread.currentThread().getId());
			eventBus.post(event);

			micrometerEventBusStatsCollector.incrementEventsEnqueued();
		}
	}

	public void enqueueEvent(@NonNull final Event event)
	{
		try (final MDCCloseable ignored = EventMDC.putEvent(event))
		{
			// Do nothing if destroyed
			if (destroyed)
			{
				logger.warn("Attempt to enqueue an event using a destroyed bus. Ignored. \n Bus: {} \n Event: {}", this, event);
				return;
			}

			final Event eventToEnqueue;

			if (event.isShallBeLogged())
			{
				eventToEnqueue = event.withStatusWasLogged();
				eventLogService.saveEvent(eventToEnqueue, getTopic());
			}
			else
			{
				eventToEnqueue = event;
			}

			try
			{
				if (EventBusConfig.isMonitorIncomingEvents())
				{
					eventBusMonitoringService.addInfosAndMonitorSpan(eventToEnqueue, topic, this::enqueueEvent0);
				}
				else
				{
					logger.debug("{} - Enqueueing event: {}", this, eventToEnqueue);
					enqueueEvent0(eventToEnqueue);
				}
			}
			catch (final Exception e)
			{
				logger.warn(StringUtils.formatMessage("Failed to enqueue event to topic name. Ignored; topicName={}; event={}", topic.getName(), event), e);
			}
		}
	}

	private static class TypedConsumerAsEventListener<T> implements IEventListener
	{
		@NonNull
		private final Consumer<T> eventConsumer;
		@NonNull
		private final JSONObjectMapper<T> jsonDeserializer;
		@NonNull
		private final Class<T> eventBodyType;

		public TypedConsumerAsEventListener(
				@NonNull final Class<T> eventBodyType,
				@NonNull final Consumer<T> eventConsumer)
		{
			this.eventBodyType = eventBodyType;
			this.jsonDeserializer = JSONObjectMapper.forClass(eventBodyType);
			this.eventConsumer = eventConsumer;
		}

		@Override
		public void onEvent(final IEventBus eventBus, final Event event)
		{
			try (final MDCCloseable ignored = EventMDC.putEvent(event))
			{
				logger.debug("TypedConsumerAsEventListener.onEvent - eventBodyType={}", eventBodyType.getName());

				final String json = event.getBody();
				final T obj = jsonDeserializer.readValue(json);
				eventConsumer.accept(obj);
			}
		}
	}

	@AllArgsConstructor
	@ToString
	private class GuavaEventListenerAdapter
	{
		@NonNull
		private final IEventListener eventListener;

		@Subscribe
		public void onEvent(@NonNull final Event event)
		{
			micrometerEventBusStatsCollector.incrementEventsDequeued();

			micrometerEventBusStatsCollector
					.getEventProcessingTimer()
					.record(() ->
					{
						try (final MDCCloseable ignored = EventMDC.putEvent(event))
						{
							logger.debug("GuavaEventListenerAdapter.onEvent - eventListener to invoke={}", eventListener);
							invokeEventListener(this.eventListener, event);
						}
					});
		}
	}

	private void invokeEventListener(
			@NonNull final IEventListener eventListener,
			@NonNull final Event event)
	{
		if (event.isWasLogged())
		{
			invokeEventListenerWithLogging(eventListener, event);
		}
		else
		{
			eventListener.onEvent(this, event);
		}
	}

	private void invokeEventListenerWithLogging(
			@NonNull final IEventListener eventListener,
			@NonNull final Event event)
	{
		try (final EventLogEntryCollector ignored = EventLogEntryCollector.createThreadLocalForEvent(event))
		{
			try
			{
				eventListener.onEvent(this, event);
			}
			catch (final RuntimeException ex)
			{
				if (!Adempiere.isUnitTestMode())
				{
					final EventLogUserService eventLogUserService = SpringContextHolder.instance.getBean(EventLogUserService.class);
					eventLogUserService
							.newErrorLogEntry(eventListener.getClass(), ex)
							.createAndStore();
				}
				else
				{
					logger.warn("Got exception while invoking eventListener={} with event={}", eventListener, event, ex);
				}
			}
		}
	}

	@Override
	public EventBusStats getStats()
	{
		return micrometerEventBusStatsCollector.snapshot();
	}

	private void enqueueEvent0(final Event event)
	{
		if (Type.LOCAL == topic.getType())
		{
			eventEnqueuer.enqueueLocalEvent(event, topic);
		}
		else
		{
			eventEnqueuer.enqueueDistributedEvent(event, topic);
		}
	}
}
