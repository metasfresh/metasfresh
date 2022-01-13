/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.event.impl;

import de.metas.event.EventBusStats;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import lombok.Builder;
import lombok.Value;

import java.util.concurrent.atomic.AtomicLong;

@Value
@Builder
public class MicrometerEventBusStatsCollector
{
	Counter eventsEnqueued;
	Counter eventsDequeued;
	AtomicLong queueLength;

	Timer eventProcessingTimer;

	public void incrementEventsEnqueued()
	{
		eventsEnqueued.increment();
		updateQueueLength();
	}

	public void incrementEventsDequeued()
	{
		eventsDequeued.increment();
		updateQueueLength();
	}

	private void updateQueueLength()
	{ // not sure why but when i did just queueLength.getAndIncrement() / getAndDecrement(), the length wasn't correct
		queueLength.set(Math.round(eventsEnqueued.count() - Math.round(eventsDequeued.count())));
	}
	
	public EventBusStats snapshot()
	{
		return EventBusStats.builder()
				.eventsEnqueued(Math.round(eventsEnqueued.count()))
				.eventsDequeued(Math.round(eventsDequeued.count()))
				.build();
	}
}
