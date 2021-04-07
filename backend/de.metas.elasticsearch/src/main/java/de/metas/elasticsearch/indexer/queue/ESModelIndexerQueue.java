/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.indexer.queue;

import com.google.common.collect.ImmutableSet;
import de.metas.elasticsearch.config.ESModelIndexerId;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ESModelIndexerQueue
{
	public static final Topic TOPIC = Topic.remote("de.metas.elasticsearch.indexRequests");

	private final IEventBus eventBus;

	public ESModelIndexerQueue(
			@NonNull final IEventBusFactory eventBusFactory)
	{
		eventBus = eventBusFactory.getEventBus(TOPIC);
	}

	public void addToIndex(final ESModelIndexerId modelIndexerId, final String modelTableName, final Set<Integer> modelIds)
	{
		if (modelIds.isEmpty())
		{
			return;
		}

		eventBus.postObject(ESModelIndexEvent.builder()
				.type(ESModelIndexEventType.ADD)
				.modelIndexerId(modelIndexerId)
				.modelTableName(modelTableName)
				.modelIds(ImmutableSet.copyOf(modelIds))
				.build());
	}

	public void removeToIndex(final ESModelIndexerId modelIndexerId, final String modelTableName, final Set<Integer> modelIds)
	{
		if (modelIds.isEmpty())
		{
			return;
		}

		eventBus.postObject(ESModelIndexEvent.builder()
				.type(ESModelIndexEventType.REMOVE)
				.modelIndexerId(modelIndexerId)
				.modelTableName(modelTableName)
				.modelIds(ImmutableSet.copyOf(modelIds))
				.build());
	}
}
