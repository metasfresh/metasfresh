package de.metas.ui.web.websocket;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.adempiere.util.Check;
import org.adempiere.util.concurrent.CustomizableThreadFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link WebSocketProducer}s registry.
 *
 * This component is responsible for:
 * <ul>
 * <li>automatically registering all {@link WebSocketProducerFactory} implementations which were found in spring context
 * <li>as soon as there is a subscriber for a websocket topic it will create/start a {@link WebSocketProducer} and it will call it on a given rate.
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public final class WebSocketProducersRegistry
{
	private static final Logger logger = LogManager.getLogger(WebSocketProducersRegistry.class);

	private final ScheduledExecutorService scheduler;
	@Autowired
	private SimpMessagingTemplate websocketMessagingTemplate;
	@Autowired
	private ApplicationContext context;

	private final ConcurrentHashMap<String, WebSocketProducerFactory> _producerFactoriesByTopicNamePrefix = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<String, WebSocketProducerInstance> _producersByTopicName = new ConcurrentHashMap<>();

	public WebSocketProducersRegistry()
	{
		scheduler = Executors.newSingleThreadScheduledExecutor(CustomizableThreadFactory.builder()
				.setThreadNamePrefix(getClass().getName())
				.setDaemon(true)
				.build());
	}

	@PostConstruct
	private void registerProducerFactoriesFromContext()
	{
		BeanFactoryUtils.beansOfTypeIncludingAncestors(context, WebSocketProducerFactory.class)
				.values()
				.forEach(producerFactory -> registerProducerFactory(producerFactory));

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

	private WebSocketProducerFactory getWebSocketProducerFactoryOrNull(final String topicName)
	{
		if (topicName == null || topicName.isEmpty())
		{
			return null;
		}

		return _producerFactoriesByTopicNamePrefix
				.entrySet()
				.stream()
				.filter(topicPrefixAndFactory -> topicName.startsWith(topicPrefixAndFactory.getKey()))
				.map(topicPrefixAndFactory -> topicPrefixAndFactory.getValue())
				.findFirst()
				.orElse(null);

	}

	private WebSocketProducerInstance getCreateWebSocketProducerInstanceOrNull(final String topicName)
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
			return new WebSocketProducerInstance(topicName, producer, scheduler, websocketMessagingTemplate);
		});
	}

	private WebSocketProducerInstance getExistingWebSocketProducerInstanceOrNull(final String topicName)
	{
		return _producersByTopicName.get(topicName);
	}

	private void forEachExistingWebSocketProducerInstance(final Consumer<WebSocketProducerInstance> action)
	{
		final long parallelismThreshold = Long.MAX_VALUE;
		_producersByTopicName.forEachValue(parallelismThreshold, action);
	}

	public void onTopicSubscribed(final String sessionId, final String topicName)
	{
		final WebSocketProducerInstance producer = getCreateWebSocketProducerInstanceOrNull(topicName);
		if (producer == null)
		{
			return;
		}

		producer.subscribe(sessionId);
	}

	public void onTopicUnsubscribed(final String sessionId, final String topicName)
	{
		if(topicName == null)
		{
			onSessionDisconnect(sessionId);
			return;
		}
		
		final WebSocketProducerInstance producer = getExistingWebSocketProducerInstanceOrNull(topicName);
		if (producer == null)
		{
			return;
		}

		producer.unsubscribe(sessionId);
	}

	public void onSessionDisconnect(final String sessionId)
	{
		forEachExistingWebSocketProducerInstance(producer -> producer.unsubscribe(sessionId));
	}

	private static final class WebSocketProducerInstance
	{
		// private static final transient Logger logger = LogManager.getLogger(WebSocketProducerInstance.class);

		private final String topicName;
		private final WebSocketProducer producer;
		private final ScheduledExecutorService scheduler;
		private final SimpMessagingTemplate websocketMessagingTemplate;

		private final Set<String> subscribedSessionIds = new HashSet<>();
		private ScheduledFuture<?> scheduledFuture;

		private WebSocketProducerInstance(
				final String topicName //
				, final WebSocketProducer producer //
				, final ScheduledExecutorService scheduler //
				, final SimpMessagingTemplate websocketMessagingTemplate //
		)
		{
			Check.assumeNotEmpty(topicName, "topicName is not empty");
			Check.assumeNotNull(producer, "Parameter producer is not null");
			Check.assumeNotNull(scheduler, "Parameter scheduler is not null");
			Check.assumeNotNull(websocketMessagingTemplate, "Parameter websocketMessagingTemplate is not null");

			this.topicName = topicName;
			this.producer = producer;
			this.scheduler = scheduler;
			this.websocketMessagingTemplate = websocketMessagingTemplate;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("topicName", topicName)
					.add("producer", producer)
					.toString();
		}

		public synchronized void subscribe(final String sessionId)
		{
			Check.assumeNotEmpty(sessionId, "sessionId is not empty");
			subscribedSessionIds.add(sessionId);
			if (subscribedSessionIds.isEmpty())
			{
				// shall not happen
				return;
			}

			logger.trace("{}: session {} subscribed", this, sessionId);

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

		public synchronized void unsubscribe(final String sessionId)
		{
			subscribedSessionIds.remove(sessionId);
			logger.trace("{}: session {} unsubscribed", this, sessionId);

			//
			// Stop producer if there is nobody listening
			if (!subscribedSessionIds.isEmpty())
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
				final Object event = producer.produceEvent();
				websocketMessagingTemplate.convertAndSend(topicName, event);

				logger.trace("Event sent to {}: {}", topicName, event);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed producing event for {}. Ignored.", this, ex);
			}
		}
	}
}