package de.metas.event.rest;

import com.google.common.collect.ImmutableList;
import de.metas.event.EventBusStats;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.util.Check;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@RequestMapping(EventBusRestController.ENDPOINT)
@RestController
public class EventBusRestController
{
	// FIXME: move MetasfreshRestAPIConstants to de.metas.util
	public static final String ENDPOINT_API = "/api";
	public static final String ENDPOINT = ENDPOINT_API + "/eventBus";

	private final IEventBusFactory eventBusFactory;

	public EventBusRestController(final IEventBusFactory eventBusFactory)
	{
		this.eventBusFactory = eventBusFactory;
	}

	@GetMapping
	public JSONEventBusAggregatedStats getSummary(
			@RequestParam(name = "topicName", required = false) final String topicName)
	{
		final List<IEventBus> eventBusInstances = getEventBusInstances(topicName);

		return JSONEventBusAggregatedStats.builder()
				.eventBusInstancesCount(eventBusInstances.size())
				.eventBusInstances(toJSONEventBusStats(eventBusInstances))
				.build();
	}

	private List<IEventBus> getEventBusInstances(@Nullable final String topicName)
	{
		if (!Check.isBlank(topicName))
		{
			final ArrayList<IEventBus> eventBusInstances = new ArrayList<>();

			{
				final IEventBus remoteEventBus = eventBusFactory.getEventBusIfExists(Topic.distributed(topicName));
				if (remoteEventBus != null)
				{
					eventBusInstances.add(remoteEventBus);
				}
			}

			{
				final IEventBus localEventBus = eventBusFactory.getEventBusIfExists(Topic.local(topicName));
				if (localEventBus != null)
				{
					eventBusInstances.add(localEventBus);
				}
			}

			return eventBusInstances;
		}
		else
		{
			return eventBusFactory.getAllEventBusInstances();
		}
	}

	private static ImmutableList<JSONEventBusStats> toJSONEventBusStats(final List<IEventBus> eventBusInstances)
	{
		return eventBusInstances.stream()
				.map(eventBus -> toJSONEventBusStats(eventBus))
				.collect(ImmutableList.toImmutableList());
	}

	private static JSONEventBusStats toJSONEventBusStats(final IEventBus eventBus)
	{
		final EventBusStats stats = eventBus.getStats();
		final Topic eventBusTopic = eventBus.getTopic();

		return JSONEventBusStats.builder()
				.topicName(eventBusTopic.getName())
				.type(eventBusTopic.getType())
				.async(eventBus.isAsync())
				.destroyed(eventBus.isDestroyed())
				//
				.eventsEnqueued(stats.getEventsEnqueued())
				.eventsDequeued(stats.getEventsDequeued())
				.eventsToDequeue(stats.getEventsToDequeue())
				//
				.build();
	}
}
