package de.metas.event.impl;

import java.util.IdentityHashMap;

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

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.base.MoreObjects;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.SubscriberExceptionHandler;

import de.metas.event.Event;
import de.metas.event.EventBusConfig;
import de.metas.event.IEventBus;
import de.metas.event.IEventListener;
import de.metas.event.Type;
import de.metas.event.log.EventLogEntryCollector;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogUserService;
import de.metas.util.Check;
import de.metas.util.JSONObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

final class EventBus implements IEventBus
{
	private static final transient Logger logger = EventBusConfig.getLogger(EventBus.class);

	/** Log all event bus exceptions */
	private static final SubscriberExceptionHandler exceptionHandler = (exception, context) -> {
		final String errmsg = "Could not dispatch event: " + context.getSubscriber() + " to " + context.getSubscriberMethod()
				+ "\n Event: " + context.getEvent()
				+ "\n Bus: " + context.getEventBus();
		logger.error(errmsg, exception);
	};

	private static final String PROP_Body = "body";
	private static final JSONObjectMapper<Object> sharedJsonSerializer = JSONObjectMapper.forClass(Object.class);

	@Getter
	private final String topicName;
	private com.google.common.eventbus.EventBus eventBus;

	private final IdentityHashMap<IEventListener, GuavaEventListenerAdapter> subscribedEventListener2GuavaListener = new IdentityHashMap<>();

	@Getter
	private boolean destroyed = false;

	@Getter
	private final boolean async;

	/**
	 * The default type is local, unless the factory makes this event bus "remote" by registering some sort of forwarder-subscriber.
	 */
	@Getter
	private Type type = Type.LOCAL;

	private final ExecutorService executorOrNull;

	/**
	 * @param executor if not null, the system creates an {@link AsyncEventBus}; also, it shuts down this executor on {@link #destroy()}
	 */
	public EventBus(
			@NonNull final String topicName,
			@Nullable final ExecutorService executor)
	{
		this.executorOrNull = executor;
		this.topicName = Check.assumeNotEmpty(topicName, "name not empty");

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
				.add("topicName", topicName)
				.add("type", type)
				.add("destroyed", destroyed ? Boolean.TRUE : null)
				.toString();
	}

	/**
	 * To be invoked only by the factory.
	 */
	/* package */void setTypeRemote()
	{
		this.type = Type.REMOTE;
	}

	void destroy()
	{
		this.destroyed = true;
		this.eventBus = null;

		if (executorOrNull != null)
		{
			executorOrNull.shutdown(); // not 100% sure it's needed, but better safe than sorry
		}
		logger.trace("{0} - Destroyed", this);
	}

	@Override
	public void subscribe(@NonNull Consumer<Event> eventConsumer)
	{
		final IEventListener listener = (eventBus, event) -> eventConsumer.accept(event);
		subscribe(listener);
	}

	@Override
	public <T> void subscribeOn(@NonNull final Class<T> type, @NonNull final Consumer<T> eventConsumer)
	{
		subscribe(new TypedConsumerAsEventListener<>(type, eventConsumer));
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
	public void postObject(@NonNull final Object obj)
	{
		final String json = sharedJsonSerializer.writeValueAsString(obj);
		postEvent(Event.builder()
				.putProperty(PROP_Body, json)
				.shallBeLogged()
				.build());
	}

	@Override
	public void postEvent(@NonNull final Event event)
	{
		try (final MDCCloseable mdc = EventMDC.putEvent(event))
		{
			// Do nothing if destroyed
			if (destroyed)
			{
				logger.warn("Attempt to post an event to a destroyed bus. Ignored. \n Bus: {} \n Event: {}", this, event);
				return;
			}

			final Event eventToPost;

			// as long as we have just one common event-log-DB, we store events only on the machine they were created on, in order to avoid duplicates.
			if (event.isShallBeLogged() && event.isLocalEvent())
			{
				eventToPost = event.withStatusWasLogged();

				final EventLogService eventLogService = SpringContextHolder.instance.getBean(EventLogService.class);
				eventLogService.saveEvent(eventToPost, this);
			}
			else
			{
				eventToPost = event;
			}

			logger.debug("{} - Posting event: {}", this, eventToPost);
			eventBus.post(eventToPost);
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
			try (final MDCCloseable mdc = EventMDC.putEvent(event))
			{
				logger.debug("TypedConsumerAsEventListener.onEvent - eventBodyType={}", eventBodyType.getName());

				final String json = event.getPropertyAsString(PROP_Body);
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
			try (final MDCCloseable mdc = EventMDC.putEvent(event))
			{
				logger.debug("GuavaEventListenerAdapter.onEvent - eventListener to invoke={}", eventListener);
				invokeEventListener(this.eventListener, event);
			}
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
		try (final EventLogEntryCollector collector = EventLogEntryCollector.createThreadLocalForEvent(event))
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
