package de.metas.event.impl;

import java.util.concurrent.atomic.AtomicLong;

import de.metas.event.EventBusStats;

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

final class EventBusStatsCollector
{
	private final AtomicLong eventsEnqueued = new AtomicLong();
	private final AtomicLong eventsDequeued = new AtomicLong();

	public void incrementEventsEnqueued()
	{
		eventsEnqueued.incrementAndGet();
	}

	public void incrementEventsDequeued()
	{
		eventsDequeued.incrementAndGet();
	}

	public EventBusStats snapshot()
	{
		return EventBusStats.builder()
				.eventsEnqueued(eventsDequeued.get())
				.eventsDequeued(eventsDequeued.get())
				.build();
	}
}
