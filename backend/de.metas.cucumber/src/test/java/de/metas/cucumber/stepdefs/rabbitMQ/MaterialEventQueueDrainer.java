/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.rabbitMQ;

import de.metas.event.remote.rabbitmq.RabbitMQDestinationResolver;
import de.metas.event.remote.rabbitmq.queues.async_batch.AsyncBatchQueueConfiguration;
import de.metas.event.remote.rabbitmq.queues.material_dispo.MaterialEventsQueueConfiguration;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

/**
 * Shared helper for draining the material and async RabbitMQ queues during cucumber tests.
 *
 * Used by both {@link RabbitMQ_StepDef} (explicit drain steps) and action step defs
 * (e.g. {@code C_Order_StepDef.completeOrder}) that auto-drain after emitting async events.
 *
 * The two queues are drained in order: material first (carries the synchronous event chain
 * — {@code SupplyRequiredEvent}, {@code TransactionCreatedEvent}, etc.), then async
 * (carries the workpackages those events enqueue — invoice candidate generation, shipment
 * schedule recomputation, purchase order creation, ...).
 *
 * Drain timeout: 5 minutes per queue.
 */
public final class MaterialEventQueueDrainer
{
	private MaterialEventQueueDrainer() {}

	/**
	 * Drain both the material and async queues. Use after any action step that emits async
	 * events (order completion, shipment generation, invoice processing, …). The drain
	 * guarantees that downstream polling steps observe a settled DB state.
	 */
	public static void drainMaterialAndAsyncQueues() throws InterruptedException
	{
		drainQueueByTopic(MaterialEventsQueueConfiguration.EVENTBUS_TOPIC.getName());
		drainQueueByTopic(AsyncBatchQueueConfiguration.EVENTBUS_TOPIC.getName());
	}

	/**
	 * Same as {@link #drainMaterialAndAsyncQueues()} but wraps {@link InterruptedException}
	 * so the call can be made from step defs that don't throw checked exceptions.
	 */
	public static void drainMaterialAndAsyncQueuesUninterruptibly()
	{
		try
		{
			drainMaterialAndAsyncQueues();
		}
		catch (final InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new AdempiereException("Interrupted while draining material+async RabbitMQ queues", e);
		}
	}

	/**
	 * Drains a single queue identified by its event-bus topic name. Polls every second,
	 * fails after 5 minutes if the queue is not yet empty.
	 */
	public static void drainQueueByTopic(@NonNull final String topicName) throws InterruptedException
	{
		final RabbitMQDestinationResolver rabbitMQDestinationSolver = SpringContextHolder.instance.getBean(RabbitMQDestinationResolver.class);
		final AmqpTemplate amqpTemplate = SpringContextHolder.instance.getBean(AmqpTemplate.class);

		final long nowMillis = System.currentTimeMillis();
		final long deadLineMillis = nowMillis + (300 * 1000L);    // dev-note: await maximum 5 minutes

		final String queueName = rabbitMQDestinationSolver.getAMQPQueueNameByTopicName(topicName);
		final RabbitAdmin rabbitAdmin = new RabbitAdmin(((RabbitTemplate)amqpTemplate));

		long messageCount;
		do
		{
			//noinspection BusyWait
			Thread.sleep(1000);

			final QueueInformation queueInformation = Optional.ofNullable(rabbitAdmin.getQueueInfo(queueName))
					.orElseThrow(() -> new AdempiereException("Queue does not exist!")
							.appendParametersToMessage()
							.setParameter("QueueName", queueName));

			messageCount = queueInformation.getMessageCount();
		}
		while (messageCount > 0 && deadLineMillis > System.currentTimeMillis());

		if (messageCount > 0)
		{
			throw new AdempiereException("Queue has not been entirely processed in 5 minutes !")
					.appendParametersToMessage()
					.setParameter("QueueName", queueName)
					.setParameter("messageCount", messageCount);
		}
	}
}
