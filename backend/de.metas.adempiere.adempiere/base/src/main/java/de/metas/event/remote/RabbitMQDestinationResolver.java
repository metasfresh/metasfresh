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

package de.metas.event.remote;

import com.google.common.annotations.VisibleForTesting;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RabbitMQDestinationResolver
{
	@NonNull
	private final List<IEventBusQueueConfiguration> eventBusQueueConfigurationList;

	public RabbitMQDestinationResolver(@NonNull final List<IEventBusQueueConfiguration> eventBusQueueConfigurationList)
	{
		validateSingleQueueForTopic(eventBusQueueConfigurationList);

		this.eventBusQueueConfigurationList = eventBusQueueConfigurationList;
	}

	/**
	 *
	 */
	@VisibleForTesting
	public void registerQueue(@NonNull final IEventBusQueueConfiguration anonymousQueueConfiguration)
	{
		this.eventBusQueueConfigurationList.add(anonymousQueueConfiguration);
	}

	@VisibleForTesting
	public void deregisterQueue(@NonNull final String topicName)
	{
		eventBusQueueConfigurationList.stream()
				.filter(configuration -> configuration.getTopicName().isPresent())
				.filter(configuration -> configuration.getTopicName().get().equals(topicName))
				.findFirst()
				.ifPresent(eventBusQueueConfigurationList::remove);
	}

	@NonNull
	public String getAMQPQueueNameByTopicName(@NonNull final String topicName)
	{
		return eventBusQueueConfigurationList.stream()
				.filter(queueConfig -> queueConfig.getTopicName().isPresent())
				.filter(queueConfig -> queueConfig.getTopicName().get().equals(topicName))
				.map(IEventBusQueueConfiguration::getQueueName)
				.findFirst()
				.orElseGet(() -> SpringContextHolder.instance.getBean(AnonymousQueue.class, RabbitMQEventBusConfiguration.DefaultQueueConfiguration.QUEUE_BEAN_NAME).getName());
	}

	@NonNull
	public String getAMQPExchangeNameByTopicName(@NonNull final String topicName)
	{
		return eventBusQueueConfigurationList.stream()
				.filter(queueConfig -> queueConfig.getTopicName().isPresent())
				.filter(queueConfig -> queueConfig.getTopicName().get().equals(topicName))
				.map(IEventBusQueueConfiguration::getFanoutExchangeName)
				.findFirst()
				.orElseGet(() -> SpringContextHolder.instance.getBean(RabbitMQEventBusConfiguration.DefaultQueueConfiguration.class).getFanoutExchangeName());
	}

	private static void validateSingleQueueForTopic(@NonNull final List<IEventBusQueueConfiguration> anonymousQueueConfigurationList)
	{
		final Set<String> collectedTopicNames = new HashSet<>();

		anonymousQueueConfigurationList.stream()
				.map(IEventBusQueueConfiguration::getTopicName)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(topicName -> {
					if (collectedTopicNames.contains(topicName))
					{
						throw new AdempiereException("Given Topic was assigned to multiple queues!")
								.setParameter("TopicName", topicName);
					}

					collectedTopicNames.add(topicName);
				});
	}
}
