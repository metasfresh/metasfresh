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

import com.google.common.annotations.VisibleForTesting;
import de.metas.event.Event;
import de.metas.event.EventEnqueuer;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.util.Services;
import lombok.NonNull;
import de.metas.common.util.pair.ImmutablePair;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Enqueue events into an in-memory processing queue
 */
@VisibleForTesting
public class PlainEventEnqueuer implements EventEnqueuer
{
	private final EventsQueue eventsQueue = new EventsQueue();

	@Override
	public void enqueueDistributedEvent(final Event event, final Topic topic)
	{
		eventsQueue.enqueue(event, topic);
	}

	@Override
	public void enqueueLocalEvent(final Event event, final Topic topic)
	{
		eventsQueue.enqueue(event, topic);
	}

	private static class EventsQueue
	{
		private final LinkedBlockingQueue<ImmutablePair<Event, Topic>> eventsQueue = new LinkedBlockingQueue<>();
		private final IEventBusFactory busFactory = Services.get(IEventBusFactory.class);

		private void enqueue(@NonNull final Event event, @NonNull final Topic topic)
		{
			eventsQueue.add(ImmutablePair.of(event, topic));

			processQueue();
		}

		private synchronized void processQueue()
		{
			while (!eventsQueue.isEmpty())
			{
				final ImmutablePair<Event, Topic> eventAndTopic = eventsQueue.poll();

				final IEventBus eventBus = busFactory.getEventBus(eventAndTopic.getRight());

				eventBus.processEvent(eventAndTopic.getLeft());
			}
		}
	}
}
