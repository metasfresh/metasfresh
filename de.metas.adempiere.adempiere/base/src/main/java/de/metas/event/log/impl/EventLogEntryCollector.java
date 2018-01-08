package de.metas.event.log.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;

import de.metas.event.Event;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogUserService.EventLogEntryRequest;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class EventLogEntryCollector implements IAutoCloseable
{

	private final static ThreadLocal<EventLogEntryCollector> threadLocalCollector = new ThreadLocal<>();

	@Getter
	private final Event event;

	private List<EventLogEntry> eventLogs = new ArrayList<>();

	private EventLogEntryCollector(@NonNull final Event event)
	{
		this.event = event;
	}

	public static EventLogEntryCollector createThreadLocalForEvent(@NonNull final Event event)
	{
		assertNoCurrentLogCollector();

		final EventLogEntryCollector newInstance = new EventLogEntryCollector(event);
		threadLocalCollector.set(newInstance);

		return newInstance;
	}

	private static void assertNoCurrentLogCollector()
	{
		final EventLogEntryCollector eventLogCollector = threadLocalCollector.get();

		Check.errorIf(eventLogCollector != null,
				"An EventLogCollector was already created and not yet closed; eventLogCollector={}",
				eventLogCollector);

	}

	public static EventLogEntryCollector getThreadLocal()
	{
		final EventLogEntryCollector eventLogCollector = threadLocalCollector.get();
		Check.errorIf(eventLogCollector == null,
				"Missing thread-local event log collector. It is expected that one was created using provideEventLogCollectorForCurrentThread().");
		return eventLogCollector;
	}

	public void addEventLog(@NonNull final EventLogEntryRequest eventLogRequest)
	{
		final EventLogEntry eventLog = EventLogEntry.builder().uuid(event.getUuid())
				.processed(eventLogRequest.isProcessed())
				.error(eventLogRequest.isError())
				.adIssueId(eventLogRequest.getAdIssueId())
				.message(eventLogRequest.getMessage())
				.eventHandlerClass(eventLogRequest.getEventHandlerClass())
				.build();

		eventLogs.add(eventLog);
	}

	public UUID getEventUuid()
	{
		return event.getUuid();
	}

	@Override
	public void close()
	{
		threadLocalCollector.remove();
		final EventLogService eventStoreService = Adempiere.getBean(EventLogService.class);
		eventLogs.forEach(eventLog -> {
			eventStoreService.storeEventLogEntry(eventLog);
		});
	}
}
