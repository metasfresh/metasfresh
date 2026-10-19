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
	 * The sends collected by {@link #enqueueEventsInOneBatch(Runnable)} on the current thread; {@code null} if the thread is not inside a batch.
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
	 * Collects the events that the given code enqueues on the current thread, and then sends them in that order over <b>one</b> channel.
	 * <p>
	 * Why: RabbitMQ only keeps the order of messages that were published on the same channel.
	 * Each plain {@code convertAndSend} checks a channel out of Spring's {@code CachingConnectionFactory} and gives it back afterwards.
	 * If the cached channel is busy (checked out by another thread), a new channel is opened (the cache size is not a limit),
	 * so two consecutive sends of this thread can go out on different channels and reach the consumer swapped.
	 */
	@Override
	public void enqueueEventsInOneBatch(@NonNull final Runnable enqueueEvents)
	{
		if (currentBatch.get() != null)
		{
			// nested call: the outermost batch sends the events
			enqueueEvents.run();
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
	 * Sends all given events within one {@link RabbitOperations#invoke}, which binds one channel to this thread for all of them.
	 * <p>
	 * Like single sends: an event whose send fails is logged and skipped, and the remaining events are still sent.
	 * Because the broker may have closed the channel on that failure, they are sent within a new {@code invoke}, so on a new channel.
	 */
	private void sendInOneChannel(@NonNull final List<PendingSend> sends)
	{
		int nextIndex = 0;
		while (nextIndex < sends.size())
		{
			final int[] currentIndex = { nextIndex };
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
				if (currentIndex[0] >= sends.size())
				{
					// all events were sent; only releasing the channel failed
					logger.warn("Failed to release the channel after sending {} events. Ignored.", sends.size(), e);
					return;
				}

				final PendingSend failedSend = sends.get(currentIndex[0]);
				logger.warn(StringUtils.formatMessage("Failed to enqueue event to topic name. Ignored; topicName={}; event={}", failedSend.getTopic().getName(), failedSend.getEvent()), e);
				nextIndex = currentIndex[0] + 1;
			}
		}
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

	@lombok.Value
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
