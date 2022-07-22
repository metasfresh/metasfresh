package de.metas.event.log;

import com.google.common.collect.ImmutableList;
import de.metas.async.QueueWorkPackageId;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.event.remote.JacksonJsonEventSerializer;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Service
public class EventLogService
{

	private final EventLogsRepository eventLogsRepository;

	public EventLogService(@NonNull final EventLogsRepository eventLogsRepository)
	{
		this.eventLogsRepository = eventLogsRepository;
	}

	public Event loadEventForReposting(@NonNull final EventLogId eventLogId)
	{
		return loadEventForReposting(eventLogId, ImmutableList.of());
	}

	public Event loadEventForReposting(
			@NonNull final EventLogId eventLogId,
			@NonNull final List<String> handlersToIgnore)
	{
		final I_AD_EventLog eventLogRecord = loadOutOfTrx(eventLogId, I_AD_EventLog.class);

		final String eventString = eventLogRecord.getEventData();
		final Event eventFromStoredString = JacksonJsonEventSerializer.instance.fromString(eventString);

		final List<String> processedHandlers = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_EventLog_Entry.class, PlainContextAware.newOutOfTrx())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_ID, eventLogRecord.getAD_EventLog_ID())
				.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Processed, true)
				.addNotEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Classname, null)
				.addNotInArrayFilter(I_AD_EventLog_Entry.COLUMN_Classname, handlersToIgnore)
				.create()
				.listDistinct(I_AD_EventLog_Entry.COLUMNNAME_Classname, String.class);

		return eventFromStoredString.toBuilder()
				.putPropertyFromObject(
						EventLogUserService.PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES,
						processedHandlers)
				.wasLogged() // the event was logged; otherwise we would not be able to load it
				.build();
	}

	public EventLogId saveEvent(
			@NonNull final Event event,
			@NonNull final IEventBus eventBus)
	{
		final String eventString = JacksonJsonEventSerializer.instance.toString(event);

		final I_AD_EventLog eventLogRecord = newInstanceOutOfTrx(I_AD_EventLog.class);
		eventLogRecord.setEvent_UUID(event.getUuid().toString());
		eventLogRecord.setEventTime(Timestamp.from(event.getWhen()));
		eventLogRecord.setEventData(eventString);
		eventLogRecord.setEventTopicName(eventBus.getTopicName());
		eventLogRecord.setEventTypeName(eventBus.getType().toString());
		final QueueWorkPackageId workpackageQueueRepoId = event.getQueueWorkPackageId();
		if (workpackageQueueRepoId != null)
		{
			eventLogRecord.setC_Queue_WorkPackage_ID(workpackageQueueRepoId.getRepoId());
		}

		save(eventLogRecord);

		return EventLogId.ofRepoId(eventLogRecord.getAD_EventLog_ID());
	}

	public void saveEventLogEntries(@NonNull final Collection<EventLogEntry> eventLogEntries)
	{
		if (eventLogEntries.isEmpty())
		{
			return;
		}

		// Save each entry
		eventLogsRepository.saveLogs(eventLogEntries);
	}
}
