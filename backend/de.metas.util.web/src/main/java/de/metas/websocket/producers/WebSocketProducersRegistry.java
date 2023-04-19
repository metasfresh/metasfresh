package de.metas.websocket.producers;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.websocket.WebsocketHeaders;
import de.metas.websocket.WebsocketSessionId;
import de.metas.websocket.WebsocketSubscriptionId;
import de.metas.websocket.WebsocketTopicName;
import de.metas.websocket.sender.WebsocketSender;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.UnaryOperator;
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
public final class WebSocketProducersRegistry
{
	private static final Logger logger = LogManager.getLogger(WebSocketProducersRegistry.class);
	private final WebsocketSender websocketSender;
	private final ImmutableMap<String, WebSocketProducerFactory> producerFactoriesByTopicNamePrefix;

	private final ScheduledExecutorService scheduler;

	private final ConcurrentHashMap<WebsocketTopicName, WebSocketProducerInstance> _producersByTopicName = new ConcurrentHashMap<>();

	public WebSocketProducersRegistry(
			@NonNull final WebsocketSender websocketSender,
			@NonNull List<WebSocketProducerFactory> factories)
	{
		scheduler = Executors.newSingleThreadScheduledExecutor(CustomizableThreadFactory.builder()
				.setThreadNamePrefix(getClass().getName())
				.setDaemon(true)
				.build());
		this.websocketSender = websocketSender;

		this.producerFactoriesByTopicNamePrefix = factories
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						WebSocketProducersRegistry::extractAndValidateTopicNamePrefix,
						factory -> factory));

		logger.info("Registered producer factories: {}", producerFactoriesByTopicNamePrefix);
	}

	private static String extractAndValidateTopicNamePrefix(@NonNull final WebSocketProducerFactory factory)
	{
		final String topicNamePrefix = factory.getTopicNamePrefix();
		if (topicNamePrefix == null || Check.isBlank(topicNamePrefix))
		{
			throw new AdempiereException("Invalid topicNamePrefix for " + factory);
		}
		return topicNamePrefix;

	}

	@Nullable
	private WebSocketProducerFactory getWebSocketProducerFactoryOrNull(@NonNull final WebsocketTopicName topicName)
	{
		return producerFactoriesByTopicNamePrefix
				.entrySet()
				.stream()
				.filter(topicPrefixAndFactory -> topicName.startsWith(topicPrefixAndFactory.getKey()))
				.map(Map.Entry::getValue)
				.findFirst()
				.orElse(null);

	}

	private WebSocketProducerInstance createProducerInstance(
			@NonNull final WebsocketTopicName topicName,
			@NonNull final WebSocketProducerFactory producerFactory)
	{
		final WebSocketProducer producer = producerFactory.createProducer(topicName);
		producer.setWebsocketSender(websocketSender);

		return WebSocketProducerInstance.builder()
				.topicName(topicName)
				.producer(producer)
				.scheduler(scheduler)
				.websocketSender(websocketSender)
				.destroyIfNoActiveSubscriptions(producerFactory.isDestroyIfNoActiveSubscriptions())
				.build();
	}

	private void forEachProducerCompute(@NonNull final UnaryOperator<WebSocketProducerInstance> mapper)
	{
		for (final WebsocketTopicName topicName : _producersByTopicName.keySet())
		{
			_producersByTopicName.computeIfPresent(topicName, (k, producer) -> mapper.apply(producer));
		}
	}

	public void onTopicSubscribed(
			@NonNull final WebsocketSubscriptionId subscriptionId,
			@NonNull final WebsocketTopicName topicName,
			@NonNull final WebsocketHeaders headers)
	{
		_producersByTopicName.compute(topicName, (k, existingProducer) -> {
			if (existingProducer != null)
			{
				existingProducer.subscribe(subscriptionId, headers);
				return existingProducer;
			}
			else
			{
				final WebSocketProducerFactory producerFactory = getWebSocketProducerFactoryOrNull(topicName);
				if (producerFactory == null)
				{
					return null;
				}

				final WebSocketProducerInstance newProducer = createProducerInstance(topicName, producerFactory);
				newProducer.subscribe(subscriptionId, headers);
				return newProducer;
			}
		});
	}

	public void onTopicUnsubscribed(
			@NonNull final WebsocketSubscriptionId subscriptionId,
			@Nullable final WebsocketTopicName topicName)
	{
		if (topicName != null)
		{
			_producersByTopicName.computeIfPresent(topicName, (k, producer) -> {
				producer.unsubscribe(subscriptionId);
				return producer.toNullIfShallBeDestroyed();
			});
		}
		else
		{
			forEachProducerCompute(producer -> {
				producer.unsubscribe(subscriptionId);
				return producer.toNullIfShallBeDestroyed();
			});
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
				_producersByTopicName.computeIfPresent(topicName, (k, producer) -> {
					producer.unsubscribe(sessionId);
					return producer.toNullIfShallBeDestroyed();
				});
			}
		}
		else
		{
			forEachProducerCompute(producer -> {
				producer.unsubscribe(sessionId);
				return producer.toNullIfShallBeDestroyed();
			});
		}
	}

	public <T extends WebSocketProducer> Stream<T> streamActiveProducersOfType(@NonNull final Class<T> producerType)
	{
		return _producersByTopicName.values()
				.stream()
				.filter(producerInstance -> producerType.isInstance(producerInstance.producerControls))
				.filter(WebSocketProducerInstance::hasActiveSubscriptions)
				.map(producerInstance -> producerType.cast(producerInstance.producerControls));
	}

	private static final class WebSocketProducerInstance
	{
		private final WebsocketTopicName topicName;

		private final WebSocketProducer producerControls;
		private final WebSocketProducer.ProduceEventsOnPollSupport onPollingEventsSupplier;
		private final ScheduledExecutorService scheduler;
		private final WebsocketSender websocketSender;
		private final boolean destroyIfNoActiveSubscriptions;

		private final AtomicBoolean running = new AtomicBoolean(false);
		private final HashSet<WebsocketSubscriptionId> activeSubscriptionIds = new HashSet<>();
		@Nullable private ScheduledFuture<?> scheduledFuture;

		@Builder
		private WebSocketProducerInstance(
				@NonNull final WebsocketTopicName topicName,
				@NonNull final WebSocketProducer producer,
				@NonNull final ScheduledExecutorService scheduler,
				@NonNull final WebsocketSender websocketSender,
				boolean destroyIfNoActiveSubscriptions)
		{
			this.topicName = topicName;
			this.producerControls = producer;
			this.onPollingEventsSupplier = (producer instanceof WebSocketProducer.ProduceEventsOnPollSupport)
					? (WebSocketProducer.ProduceEventsOnPollSupport)producer
					: null;
			this.scheduler = scheduler;
			this.websocketSender = websocketSender;
			this.destroyIfNoActiveSubscriptions = destroyIfNoActiveSubscriptions;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("topicName", topicName)
					.add("producer", producerControls)
					.toString();
		}

		public synchronized void subscribe(
				@NonNull final WebsocketSubscriptionId subscriptionId,
				@NonNull final WebsocketHeaders headers)
		{
			activeSubscriptionIds.add(subscriptionId);
			logger.trace("{}: session {} subscribed", this, subscriptionId);

			producerControls.onNewSubscription(subscriptionId, headers);

			startIfSubscriptions();
		}

		private void startIfSubscriptions()
		{
			if (activeSubscriptionIds.isEmpty())
			{
				return;
			}

			final boolean wasRunning = running.getAndSet(true);
			if (!wasRunning)
			{
				producerControls.onStart();
			}

			startScheduledFutureIfApplies();
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

		public synchronized boolean isShallBeDestroyed()
		{
			return destroyIfNoActiveSubscriptions && !hasActiveSubscriptions();
		}

		private WebSocketProducerInstance toNullIfShallBeDestroyed()
		{
			return isShallBeDestroyed() ? null : this;
		}

		private void stopIfNoSubscription()
		{
			if (hasActiveSubscriptions())
			{
				return;
			}

			final boolean wasRunning = running.getAndSet(false);
			if (wasRunning)
			{
				producerControls.onStop();
			}

			stopScheduledFuture();

			logger.debug("{} stopped", this);
		}

		private void startScheduledFutureIfApplies()
		{
			// Does not apply
			if (onPollingEventsSupplier == null)
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
			scheduledFuture = scheduler.scheduleAtFixedRate(this::pollAndPublish, initialDelayMillis, periodMillis, TimeUnit.MILLISECONDS);
			logger.trace("{}: start producing using initialDelayMillis={}, periodMillis={}", this, initialDelayMillis, periodMillis);

		}

		private void stopScheduledFuture()
		{
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
		}

		private void pollAndPublish()
		{
			if (onPollingEventsSupplier == null)
			{
				return;
			}

			try
			{
				final List<?> events = onPollingEventsSupplier.produceEvents();
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
					logger.trace("Got no events from {}", onPollingEventsSupplier);
				}
			}
			catch (final Exception ex)
			{
				logger.warn("Failed producing event for {}. Ignored.", this, ex);
			}
		}
	}
}
