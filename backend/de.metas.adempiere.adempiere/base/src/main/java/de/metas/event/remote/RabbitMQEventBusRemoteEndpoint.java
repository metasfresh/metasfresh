package de.metas.event.remote;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import de.metas.event.Event;
import de.metas.event.Event.Builder;
import de.metas.event.EventBusConfig;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService.SpanMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.SubType;
import de.metas.monitoring.adapter.PerformanceMonitoringService.TransactionMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.TransactionMetadata.TransactionMetadataBuilder;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class RabbitMQEventBusRemoteEndpoint implements IEventBusRemoteEndpoint
{
	private static final Logger logger = LogManager.getLogger(RabbitMQEventBusRemoteEndpoint.class);
	private final PerformanceMonitoringService perfMonService;
	private IEventBusFactory eventBusFactory;

	private static final String PROP_TRACE_INFO_PREFIX = "traceInfo.";

	private static final String HEADER_SenderId = "metasfresh-events.SenderId";
	private static final String HEADER_TopicName = "metasfresh-events.TopicName";

	private final String senderId;
	private final AmqpTemplate amqpTemplate;

	private final IEventListener eventBus2amqpListener = EventBus2RemoteEndpointHandler.newInstance(this);

	public RabbitMQEventBusRemoteEndpoint(
			@NonNull final AmqpTemplate amqpTemplate,
			@NonNull final PerformanceMonitoringService perfMonService)
	{
		senderId = EventBusConfig.getSenderId();
		this.amqpTemplate = amqpTemplate;
		this.perfMonService = perfMonService;
	}

	@Override
	public void setEventBusFactory(@NonNull final IEventBusFactory eventBusFactory)
	{
		this.eventBusFactory = eventBusFactory;
	}

	@RabbitListener(queues = {
			RabbitMQEventBusConfiguration.DefaultQueueConfiguration.QUEUE_NAME_SPEL,
			RabbitMQEventBusConfiguration.CacheInvalidationQueueConfiguration.QUEUE_NAME_SPEL,
			RabbitMQEventBusConfiguration.AccountingQueueConfiguration.QUEUE_NAME_SPEL,
	})
	public void onRemoteEvent(
			@Payload final Event event,
			@Header(HEADER_SenderId) final String senderId,
			@Header(HEADER_TopicName) final String topicName)
	{
		final Topic topic = Topic.of(topicName, Type.REMOTE);
		final IEventBus localEventBus = eventBusFactory.getEventBusIfExists(topic);
		if (localEventBus == null)
		{
			logger.debug("onRemoteEvent - localEventBus for topicName={} is null; -> ignoring event", topicName);
			return;
		}
		if (Objects.equals(getSenderId(), senderId))
		{
			logger.debug("onRemoteEvent - event's senderId is equal to the *local* sender id; -> ignoring event", senderId);
			return;
		}

		final boolean monitorIncomingEvents = EventBusConfig.isMonitorIncomingEvents();
		final boolean localEventBusAsync = localEventBus.isAsync();
		try
		{
			if (monitorIncomingEvents && !localEventBusAsync)
			{
				logger.debug("onRemoteEvent - localEventBus is not async and isMonitorIncomingEvents=true; -> monitoring event processing; localEventBus={}", localEventBus);
				extractInfosAndMonitor(localEventBus, event, senderId, topicName);
			}
			else
			{
				logger.debug("onRemoteEvent - localEventBus.isAsync={} and isMonitorIncomingEvents={}; -> cannot monitor event processing; localEventBus={}", localEventBusAsync, monitorIncomingEvents, localEventBus);
				onRemoteEvent0(localEventBus, event, senderId, topicName);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("onRemoteEvent - Failed forwarding event to topic {}: {}", topicName, event, ex);
		}
	}

	private void extractInfosAndMonitor(
			@NonNull final IEventBus localEventBus,
			@NonNull final Event event,
			@NonNull final String senderId,
			@NonNull final String topicName)
	{
		// extract remote tracing infos from the event (if there are any) and create a (distributed) monitoring transaction.

		final TransactionMetadataBuilder transactionMetadata = TransactionMetadata.builder();
		for (final Entry<String, Object> entry : event.getProperties().entrySet())
		{
			final String key = entry.getKey();
			if (key.startsWith(PROP_TRACE_INFO_PREFIX))
			{
				transactionMetadata.distributedTransactionHeader(
						key.substring(PROP_TRACE_INFO_PREFIX.length()),
						Objects.toString(entry.getValue()));
			}
		}
		transactionMetadata
				.name("Process remote-event; topic=" + topicName)
				.type(de.metas.monitoring.adapter.PerformanceMonitoringService.Type.EVENTBUS_REMOTE_ENDPOINT)
				.label("de.metas.event.remote-event.senderId", event.getSenderId())
				.label("de.metas.event.remote-event.topicName", topicName)
				.label("de.metas.event.remote-event.endpointImpl", this.getClass().getSimpleName());

		perfMonService.monitorTransaction(
				() -> onRemoteEvent0(localEventBus, event, senderId, topicName),
				transactionMetadata.build());
	}

	private void onRemoteEvent0(
			@NonNull final IEventBus localEventBus,
			@NonNull final Event event,
			@NonNull final String senderId,
			@NonNull final String topicName)
	{
		event.markReceivedByEventBusId(createEventBusId(topicName));

		localEventBus.postEvent(event);

		final long durationMillis = System.currentTimeMillis() - event.getWhen().toEpochMilli();
		logger.debug("Received and processed event in {}ms, topic={}: {}", durationMillis, topicName, event);
	}

	@Override
	public void sendEvent(final String topicName, final Event event)
	{
		// If the event comes from this bus, don't forward it back
		final String eventBusId = createEventBusId(topicName);
		if (event.wasReceivedByEventBusId(eventBusId))
		{
			return;
		}
		try
		{
			if (EventBusConfig.isMonitorIncomingEvents())
			{
				addInfosAndMonitorSpan(topicName, event);
			}
			else
			{
				sendEvent0(topicName, event);
			}
		}
		catch (

		final Exception e)
		{
			logger.warn(StringUtils.formatMessage("Failed to send event to topic name. Ignored; topicName={}; event={}", topicName, event), e);
		}
	}

	private void addInfosAndMonitorSpan(@NonNull final String topicName, @NonNull final Event event)
	{
		final Builder eventToSendBuilder = event.toBuilder();
		final SpanMetadata request = SpanMetadata.builder()
				.type(de.metas.monitoring.adapter.PerformanceMonitoringService.Type.EVENTBUS_REMOTE_ENDPOINT.getCode())
				.subType(SubType.EVENT_SEND.getCode())
				.name("Post distributed-event on topic " + topicName)
				.label("de.metas.event.distributed-event.senderId", event.getSenderId())
				.label("de.metas.event.distributed-event.topicName", topicName)
				// allow perfMonService to inject properties into the event which enable distributed tracing
				.distributedHeadersInjector((name, value) -> eventToSendBuilder.putProperty(PROP_TRACE_INFO_PREFIX + name, value))
				.build();
		perfMonService.monitorSpan(
				() -> sendEvent0(topicName, eventToSendBuilder.build()),
				request);
	}

	private void sendEvent0(final String topicName, final Event event)
	{
		// If the event comes from this bus, don't forward it back
		final String eventBusId = createEventBusId(topicName);
		if (event.wasReceivedByEventBusId(eventBusId))
		{
			return;
		}

		final String amqpExchangeName = RabbitMQEventBusConfiguration.getAMQPExchangeNameByTopicName(topicName);
		final String routingKey = ""; // ignored for fan-out exchanges
		amqpTemplate.convertAndSend(
				amqpExchangeName,
				routingKey,
				event,
				message -> {
					final Map<String, Object> headers = message.getMessageProperties().getHeaders();
					headers.put(HEADER_SenderId, getSenderId());
					headers.put(HEADER_TopicName, topicName);
					return message;
				});

		logger.debug("Send event; topicName={}; event={}", topicName, event);
	}

	private String createEventBusId(final String topicName)
	{
		return getSenderId() + "_" + topicName;
	}

	private String getSenderId()
	{
		return senderId;
	}

	@Override
	public boolean bindIfNeeded(@NonNull final IEventBus eventBus)
	{
		eventBus.subscribe(eventBus2amqpListener);
		return true; // need to return true, otherwise, the system will only create "local" topics
	}

	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void checkConnection()
	{
		// TODO Auto-generated method stub
	}

}
