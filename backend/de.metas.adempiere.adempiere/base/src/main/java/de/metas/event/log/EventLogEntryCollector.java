package de.metas.event.log;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.event.Event;
import de.metas.event.log.EventLogUserService.EventLogEntryRequest;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.AccessLevel;
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
	private static final Logger logger = LogManager.getLogger(EventLogEntryCollector.class);
	private static final ThreadLocal<EventLogEntryCollector> threadLocalCollector = new ThreadLocal<>();

	@Getter
	private final Event event;

	@Getter(AccessLevel.PACKAGE)
	@VisibleForTesting
	private final EventLogEntryCollector previousEntryCollector;

	private final List<EventLogEntry> eventLogEntries = new ArrayList<>();

	private EventLogEntryCollector(
			@NonNull final Event event,
			@Nullable final EventLogEntryCollector previousEntryCollector)
	{
		this.event = event;
		this.previousEntryCollector = previousEntryCollector;
	}

	public static EventLogEntryCollector createThreadLocalForEvent(@NonNull final Event event)
	{
		final EventLogEntryCollector previousEntryCollector = threadLocalCollector.get();
		final EventLogEntryCollector entryCollector = new EventLogEntryCollector(event, previousEntryCollector);
		threadLocalCollector.set(entryCollector);

		return entryCollector;
	}

	public static EventLogEntryCollector getThreadLocal()
	{
		final EventLogEntryCollector eventLogCollector = threadLocalCollector.get();
		Check.errorIf(eventLogCollector == null,
				"Missing thread-local EventLogEntryCollector instance. It is expected that one was created using createThreadLocalForEvent().");
		return eventLogCollector;
	}

	public void addEventLog(@NonNull final EventLogEntryRequest eventLogRequest)
	{
		final EventLogEntry eventLogEntry = EventLogEntry.builder()
				.uuid(event.getUuid())
				.clientId(eventLogRequest.getClientId())
				.orgId(eventLogRequest.getOrgId())
				.processed(eventLogRequest.isProcessed())
				.error(eventLogRequest.isError())
				.adIssueId(eventLogRequest.getAdIssueId())
				.message(eventLogRequest.getMessage())
				.eventHandlerClass(eventLogRequest.getEventHandlerClass())
				.build();

		eventLogEntries.add(eventLogEntry);
	}

	@Override
	public void close()
	{
		// Restore previous entry collector
		// or clear the current one
		if (previousEntryCollector != null)
		{
			threadLocalCollector.set(previousEntryCollector);
		}
		else
		{
			threadLocalCollector.remove();
		}

		// Avoid throwing exception because EventLogService is not available in unit tests
		if (Adempiere.isUnitTestMode())
		{
			return;
		}

		try
		{
			final EventLogService eventStoreService = SpringContextHolder.instance.getBean(EventLogService.class);
			eventStoreService.saveEventLogEntries(eventLogEntries);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed saving {}. Ignored", eventLogEntries, ex);
		}
	}
}
