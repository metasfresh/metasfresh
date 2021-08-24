package de.metas.ui.web.websocket;

import com.google.common.base.MoreObjects;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
 * {@link WebSocketProducer}s registry.
 * <p>
 * This component is responsible for:
 * <ul>
 * <li>automatically registering all {@link WebSocketProducerFactory} implementations which were found in spring context
 * <li>as soon as there is a subscriber for a websocket topic it will create/start a {@link WebSocketProducer} and it will call it on a given rate.
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
public final class WebSocketProducersRegistry
{
	private static final Logger logger = LogManager.getLogger(WebSocketProducersRegistry.class);
	private final WebsocketSender websocketSender;
	private final ApplicationContext context;

	private final ScheduledExecutorService scheduler;

	private final ConcurrentHashMap<String, WebSocketProducerFactory> _producerFactoriesByTopicNamePrefix = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<WebsocketTopicName, WebSocketProducerInstance> _producersByTopicName = new ConcurrentHashMap<>();

	public WebSocketProducersRegistry(
			@NonNull final WebsocketSender websocketSender,
			@NonNull final ApplicationContext context)
	{
		scheduler = Executors.newSingleThreadScheduledExecutor(CustomizableThreadFactory.builder()
				.setThreadNamePrefix(getClass().getName())
				.setDaemon(true)
				.build());
		this.websocketSender = websocketSender;
		this.context = context;
	}

	@PostConstruct
	private void registerProducerFactoriesFromContext()
	{
		BeanFactoryUtils.beansOfTypeIncludingAncestors(context, WebSocketProducerFactory.class)
				.values()
				.forEach(this::registerProducerFactory);

	}

	public void registerProducerFactory(final WebSocketProducerFactory producerFactory)
	{
		Check.assumeNotNull(producerFactory, "Parameter producerFactory is not null");

		final String topicNamePrefix = producerFactory.getTopicNamePrefix();
		Check.assumeNotEmpty(topicNamePrefix, "topicNamePrefix is not empty");

		_producerFactoriesByTopicNamePrefix.compute(topicNamePrefix, (k, existingProducerFactory) -> {
			if (existingProducerFactory == null)
			{
				return producerFactory;
			}
			else
			{
				throw new IllegalArgumentException("Registering more then one producer factory for same topic prefix is not allowed"
						+ "\n Topic prefix: " + topicNamePrefix
						+ "\n Existing producer factory: " + existingProducerFactory
						+ "\n New producer factory: " + producerFactory);
			}
		});

		logger.info("Registered producer factory: {}", producerFactory);
	}

	@Nullable
	private WebSocketProducerFactory getWebSocketProducerFactoryOrNull(final WebsocketTopicName topicName)
	{
		if (topicName == null)
		{
			return null;
		}

		return _producerFactoriesByTopicNamePrefix
				.entrySet()
				.stream()
				.filter(topicPrefixAndFactory -> topicName.startsWith(topicPrefixAndFactory.getKey()))
				.map(Map.Entry::getValue)
				.findFirst()
				.orElse(null);

	}

	private WebSocketProducerInstance getWebSocketProducerOrNull(final WebsocketTopicName topicName)
	{
		return _producersByTopicName.get(topicName);
	}

	@Nullable
	private WebSocketProducerInstance getOrCreateWebSocketProducerOrNull(final WebsocketTopicName topicName)
	{
		final WebSocketProducerInstance existingProducer = _producersByTopicName.get(topicName);
		if (existingProducer != null)
		{
			return existingProducer;
		}

		final WebSocketProducerFactory producerFactory = getWebSocketProducerFactoryOrNull(topicName);
		if (producerFactory == null)
		{
			return null;
		}

		return _producersByTopicName.computeIfAbsent(topicName, k -> {
			final WebSocketProducer producer = producerFactory.createProducer(topicName);
			return new WebSocketProducerInstance(topicName, producer, scheduler, websocketSender);
		});
	}

	private void forEachExistingWebSocketProducerInstance(final Consumer<WebSocketProducerInstance> action)
	{
		final long parallelismThreshold = Long.MAX_VALUE;
		_producersByTopicName.forEachValue(parallelismThreshold, action);
	}

	public void onTopicSubscribed(
			@NonNull final WebsocketSubscriptionId subscriptionId,
			@NonNull final WebsocketTopicName topicName)
	{
		final WebSocketProducerInstance producer = getOrCreateWebSocketProducerOrNull(topicName);
		if (producer == null)
		{
			return;
		}

		producer.subscribe(subscriptionId);
	}

	public void onTopicUnsubscribed(
			@NonNull final WebsocketSubscriptionId subscriptionId,
			@Nullable final WebsocketTopicName topicName)
	{
		if (topicName != null)
		{
			final WebSocketProducerInstance producer = getWebSocketProducerOrNull(topicName);
			if (producer != null)
			{
				producer.unsubscribe(subscriptionId);
			}
		}
		else
		{
			forEachExistingWebSocketProducerInstance(producer -> producer.unsubscribe(subscriptionId));
		}
	}

	public void onSessionDisconnect(
			@NonNull final WebsocketSessionId sessionId,
			@Nullable final Collection<WebsocketTopicName> topicNames)
	{
		if (topicNames != null && !topicNames.isEmpty())
		{
			for (final WebsocketTopicName topicName : topicNames)
			{
				final WebSocketProducerInstance producer = getWebSocketProducerOrNull(topicName);
				if (producer != null)
				{
					producer.unsubscribe(sessionId);
				}
			}
		}
		else
		{
			forEachExistingWebSocketProducerInstance(producer -> producer.unsubscribe(sessionId));
		}
	}

	public <T extends WebSocketProducer> Stream<T> streamActiveProducersOfType(
			@NonNull final Class<T> producerType)
	{
		return _producersByTopicName.values()
				.stream()
				.filter(producerInstance -> producerType.isInstance(producerInstance.producer))
				.filter(producerInstance -> producerInstance.hasActiveSubscriptions())
				.map(producerInstance -> producerType.cast(producerInstance.producer));
	}

	private static final class WebSocketProducerInstance
	{
		private final WebsocketTopicName topicName;
		private final WebSocketProducer producer;
		private final ScheduledExecutorService scheduler;
		private final WebsocketSender websocketSender;

		private final HashSet<WebsocketSubscriptionId> activeSubscriptionIds = new HashSet<>();
		@Nullable private ScheduledFuture<?> scheduledFuture;

		private WebSocketProducerInstance(
				@NonNull final WebsocketTopicName topicName,
				@NonNull final WebSocketProducer producer,
				@NonNull final ScheduledExecutorService scheduler,
				@NonNull final WebsocketSender websocketSender)
		{
			this.topicName = topicName;
			this.producer = producer;
			this.scheduler = scheduler;
			this.websocketSender = websocketSender;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("topicName", topicName)
					.add("producer", producer)
					.toString();
		}

		public synchronized void subscribe(@NonNull final WebsocketSubscriptionId subscriptionId)
		{
			activeSubscriptionIds.add(subscriptionId);
			logger.trace("{}: session {} subscribed", this, subscriptionId);

			producer.onNewSubscription(subscriptionId);

			startIfSubscriptions();
		}

		private void startIfSubscriptions()
		{
			if (activeSubscriptionIds.isEmpty())
			{
				return;
			}

			//
			// Check if the producer was already scheduled
			if (scheduledFuture != null)
			{
				return;
			}

			//
			// Schedule producer
			final long initialDelayMillis = 1000;
			final long periodMillis = 1000;
			scheduledFuture = scheduler.scheduleAtFixedRate(this::executeAndPublish, initialDelayMillis, periodMillis, TimeUnit.MILLISECONDS);
			logger.trace("{}: start producing using initialDelayMillis={}, periodMillis={}", this, initialDelayMillis, periodMillis);
		}

		public synchronized void unsubscribe(final WebsocketSubscriptionId subscriptionId)
		{
			activeSubscriptionIds.remove(subscriptionId);
			logger.trace("{}: subscription {} unsubscribed", this, subscriptionId);

			stopIfNoSubscription();
		}

		public synchronized void unsubscribe(@NonNull final WebsocketSessionId sessionId)
		{
			final boolean removed = activeSubscriptionIds.removeIf(subscriptionId -> subscriptionId.isMatchingSessionId(sessionId));
			logger.trace("{}: session {} unsubscribed", this, sessionId);

			if (removed)
			{
				stopIfNoSubscription();
			}
		}

		public synchronized boolean hasActiveSubscriptions()
		{
			return !activeSubscriptionIds.isEmpty();
		}

		private void stopIfNoSubscription()
		{
			if (hasActiveSubscriptions())
			{
				return;
			}

			if (scheduledFuture == null)
			{
				return;
			}

			try
			{
				scheduledFuture.cancel(true);
			}
			catch (final Exception ex)
			{
				logger.warn("{}: Failed stopping scheduled future: {}. Ignored and considering it as stopped", this, scheduledFuture, ex);
			}
			scheduledFuture = null;

			logger.debug("{} stopped", this);
		}

		private void executeAndPublish()
		{
			try
			{
				final JSONOptions jsonOpts = JSONOptions.newInstance();
				final List<?> events = producer.produceEvents(jsonOpts);
				if (events != null && !events.isEmpty())
				{
					for (final Object event : events)
					{
						websocketSender.convertAndSend(topicName, event);
						logger.trace("Event sent to {}: {}", topicName, event);
					}
				}
				else
				{
					logger.trace("Got no events from {}", producer);
				}
			}
			catch (final Exception ex)
			{
				logger.warn("Failed producing event for {}. Ignored.", this, ex);
			}
		}
	}
}
