/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.event.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.event.Event;
import de.metas.event.EventBusConfig;
import de.metas.event.EventEnqueuer;
import de.metas.event.Topic;
import de.metas.event.remote.rabbitmq.RabbitMQDestinationResolver;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static de.metas.event.EventBusConfig.getSenderId;
import static de.metas.event.remote.rabbitmq.queues.default_queue.DefaultReceiveFromRabbitMQEndpoint.HEADER_SenderId;
import static de.metas.event.remote.rabbitmq.queues.default_queue.DefaultReceiveFromRabbitMQEndpoint.HEADER_TopicName;
import static de.metas.event.remote.rabbitmq.queues.default_queue.DefaultReceiveFromRabbitMQEndpoint.HEADER_TopicType;

@Component
@RequiredArgsConstructor
public class RabbitMQEnqueuer implements EventEnqueuer
{
	@NonNull private static final Logger logger = EventBusConfig.getLogger(RabbitMQEnqueuer.class);
	@NonNull private final RabbitOperations rabbitOperations;
	@NonNull private final RabbitMQDestinationResolver rabbitMQDestinationResolver;

	/**
	 * Sends collected by {@link #enqueueEventsInOneBatch(Runnable)} on this thread; {@code null} outside a batch.
	 */
	private final ThreadLocal<List<PendingSend>> currentBatch = new ThreadLocal<>();

	@Override
	public void enqueueDistributedEvent(final Event event, final Topic topic)
	{
		final String topicName = topic.getName();
		final String amqpExchangeName = rabbitMQDestinationResolver.getAMQPExchangeNameByTopicName(topicName);
		final String routingKey = amqpExchangeName; // this corresponds to the way we bound our queues to the exchanges in IEventBusQueueConfiguration implementations

		sendNowOrAddToBatch(new PendingSend(
				event,
				topic,
				rabbitOperations -> {
					rabbitOperations.convertAndSend(
							amqpExchangeName,
							routingKey,
							event,
							getMessagePostProcessor(topic));

					logger.debug("Send event; topicName={}; event={}; type={}; timestamp={}, ThreadId={}", topicName, event, topic.getType(), SystemTime.asTimestamp(), Thread.currentThread().getId());
				}));
	}

	@Override
	public void enqueueLocalEvent(final Event event, final Topic topic)
	{
		final String queueName = rabbitMQDestinationResolver.getAMQPQueueNameByTopicName(topic.getName());

		sendNowOrAddToBatch(new PendingSend(
				event,
				topic,
				rabbitOperations -> {
					rabbitOperations.convertAndSend(queueName,
							event,
							getMessagePostProcessor(topic));

					logger.debug("Send event; topicName={}; event={}; type={}", topic.getName(), event, topic.getType());
				}));
	}

	/**
	 * Collects the events that the given code enqueues on this thread and then sends them, in that order, over one channel.
	 * Needed because RabbitMQ keeps the order only within a channel, and Spring's {@code CachingConnectionFactory}
	 * opens a new channel for a plain send when the cached one is busy.
	 * <p>
	 * The events collected so far are also sent if the given code throws.
	 */
	@Override
	public void enqueueEventsInOneBatch(@NonNull final Runnable enqueueEvents)
	{
		if (currentBatch.get() != null)
		{
			enqueueEvents.run(); // nested: the outermost batch sends
			return;
		}

		final List<PendingSend> batch = new ArrayList<>();
		currentBatch.set(batch);
		try
		{
			enqueueEvents.run();
		}
		finally
		{
			currentBatch.remove();
			sendInOneChannel(batch);
		}
	}

	private void sendNowOrAddToBatch(@NonNull final PendingSend send)
	{
		final List<PendingSend> batch = currentBatch.get();
		if (batch != null)
		{
			batch.add(send);
		}
		else
		{
			send.sendWith(rabbitOperations);
		}
	}

	/**
	 * Sends within one {@link RabbitOperations#invoke}, which binds one channel to this thread.
	 * Note: {@code invoke} physically closes that channel afterwards (no publisher confirms), so a single event is sent the plain way.
	 * <p>
	 * As with single sends, an event that cannot be sent is logged and skipped.
	 * RabbitMQ reports most publish errors asynchronously by closing the channel, so the <i>next</i> publish fails.
	 * Therefore a send that fails after other sends on the same channel is retried on a new channel; only a send that fails as first one of its channel is skipped.
	 */
	private void sendInOneChannel(@NonNull final List<PendingSend> sends)
	{
		if (sends.size() == 1)
		{
			sendAndLogIfFailed(sends.get(0));
			return;
		}

		int nextIndex = 0;
		while (nextIndex < sends.size())
		{
			final int startIndex = nextIndex;
			final int[] currentIndex = { startIndex };
			try
			{
				rabbitOperations.invoke(operations -> {
					for (; currentIndex[0] < sends.size(); currentIndex[0]++)
					{
						sends.get(currentIndex[0]).sendWith(operations);
					}
					return null;
				});
				return;
			}
			catch (final Exception e)
			{
				final int failedIndex = currentIndex[0];
				if (failedIndex >= sends.size())
				{
					logger.warn("All {} events were sent, but releasing the channel failed. Ignored.", sends.size(), e);
					return;
				}
				else if (failedIndex > startIndex)
				{
					logger.debug("Sending failed after {} sends on the same channel; retrying on a new channel: {}", failedIndex - startIndex, sends.get(failedIndex).getEvent(), e);
					nextIndex = failedIndex;
				}
				else
				{
					logFailedSend(sends.get(failedIndex), e);
					nextIndex = failedIndex + 1;
				}
			}
		}
	}

	private void sendAndLogIfFailed(@NonNull final PendingSend send)
	{
		try
		{
			send.sendWith(rabbitOperations);
		}
		catch (final Exception e)
		{
			logFailedSend(send, e);
		}
	}

	private static void logFailedSend(@NonNull final PendingSend send, @NonNull final Exception e)
	{
		logger.warn(StringUtils.formatMessage("Failed to enqueue event to topic name. Ignored; topicName={}; event={}", send.getTopic().getName(), send.getEvent()), e);
	}

	@NonNull
	private MessagePostProcessor getMessagePostProcessor(@NonNull final Topic topic)
	{
		return message -> {
			final Map<String, Object> headers = message.getMessageProperties().getHeaders();
			headers.put(HEADER_SenderId, getSenderId());
			headers.put(HEADER_TopicName, topic.getName());
			headers.put(HEADER_TopicType, topic.getType());
			return message;
		};
	}

	@Value
	private static class PendingSend
	{
		@NonNull Event event;
		@NonNull Topic topic;
		@NonNull Consumer<RabbitOperations> sender;

		void sendWith(@NonNull final RabbitOperations rabbitOperations)
		{
			try (final MDCCloseable ignored = EventMDC.putEvent(event))
			{
				sender.accept(rabbitOperations);
			}
		}
	}
}
