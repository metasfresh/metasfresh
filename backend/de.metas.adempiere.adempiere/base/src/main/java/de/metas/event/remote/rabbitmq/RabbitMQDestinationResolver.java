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

package de.metas.event.remote.rabbitmq;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.event.remote.IEventBusQueueConfiguration;
import de.metas.event.remote.rabbitmq.queues.default_queue.DefaultQueueConfiguration;
import de.metas.logging.LogManager;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Service
public class RabbitMQDestinationResolver
{
	@NonNull private static final Logger logger = LogManager.getLogger(RabbitMQDestinationResolver.class);

	@NonNull private QueueConfigurationsMap queueConfigs;

	public RabbitMQDestinationResolver(@NonNull final List<IEventBusQueueConfiguration> eventBusQueueConfigurationList)
	{
		this.queueConfigs = new QueueConfigurationsMap(eventBusQueueConfigurationList);
		logger.info("Registered event bus queue configurations: {}", queueConfigs);
	}

	@VisibleForTesting
	public void registerQueue(@NonNull final IEventBusQueueConfiguration queueConfig)
	{
		queueConfigs = queueConfigs.withQueueAdded(queueConfig);
		logger.info("Registered queue {}. Currently registered queues are: {}", queueConfig, queueConfigs);
	}

	@VisibleForTesting
	public void deregisterQueue(@NonNull final String topicName)
	{
		queueConfigs = queueConfigs.withQueueRemovedByTopicName(topicName);
		logger.info("Unregistered queue by topic {}. Currently registered queues are: {}", topicName, queueConfigs);
	}

	@NonNull
	public String getAMQPQueueNameByTopicName(@NonNull final String topicName)
	{
		return queueConfigs.getByTopicNameOrDefault(topicName).getQueueName();
	}

	@NonNull
	public String getAMQPExchangeNameByTopicName(@NonNull final String topicName)
	{
		return queueConfigs.getByTopicNameOrDefault(topicName).getExchangeName();
	}

	//
	//
	// -------------------------------------------------------------------------
	//
	//

	@ToString(of = { "byTopicName", "defaultQueueConfiguration" })
	private static class QueueConfigurationsMap
	{
		private final ImmutableList<IEventBusQueueConfiguration> list;
		private final ImmutableMap<String, IEventBusQueueConfiguration> byTopicName;
		private final IEventBusQueueConfiguration defaultQueueConfiguration;

		QueueConfigurationsMap(final List<IEventBusQueueConfiguration> list)
		{
			final ImmutableMap.Builder<String, IEventBusQueueConfiguration> byTopicName = ImmutableMap.builder();
			final ArrayList<DefaultQueueConfiguration> defaultQueueConfigurations = new ArrayList<>();
			for (final IEventBusQueueConfiguration queueConfig : list)
			{
				queueConfig.getTopicName().ifPresent(topicName -> byTopicName.put(topicName, queueConfig));

				if (queueConfig instanceof DefaultQueueConfiguration)
				{
					defaultQueueConfigurations.add((DefaultQueueConfiguration)queueConfig);
				}
			}

			if (defaultQueueConfigurations.size() != 1)
			{
				throw new AdempiereException("There shall be exactly one default queue configuration but found " + defaultQueueConfigurations)
						.setParameter("all configurations", list)
						.appendParametersToMessage();
			}

			this.list = ImmutableList.copyOf(list);
			this.byTopicName = byTopicName.build();
			this.defaultQueueConfiguration = CollectionUtils.singleElement(defaultQueueConfigurations);
		}

		@Nullable
		public IEventBusQueueConfiguration getByTopicNameOrNull(@NonNull final String topicName)
		{
			return byTopicName.get(topicName);
		}

		@NonNull
		public IEventBusQueueConfiguration getByTopicNameOrDefault(@NonNull final String topicName)
		{
			return byTopicName.getOrDefault(topicName, defaultQueueConfiguration);
		}

		public QueueConfigurationsMap withQueueAdded(@NonNull final IEventBusQueueConfiguration queueConfig)
		{
			final ArrayList<IEventBusQueueConfiguration> newList = new ArrayList<>(list);
			newList.add(queueConfig);
			return new QueueConfigurationsMap(newList);
		}

		public QueueConfigurationsMap withQueueRemovedByTopicName(@NonNull final String topicName)
		{
			final IEventBusQueueConfiguration queueConfig = getByTopicNameOrNull(topicName);
			return queueConfig != null ? withQueueRemoved(queueConfig) : this;
		}

		public QueueConfigurationsMap withQueueRemoved(@NonNull final IEventBusQueueConfiguration queueConfig)
		{
			final ArrayList<IEventBusQueueConfiguration> newList = new ArrayList<>(list);
			newList.remove(queueConfig);
			return new QueueConfigurationsMap(newList);
		}
	}
}
