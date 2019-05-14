package de.metas.event.log;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.setValue;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.cache.CCache;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.log.impl.EventLogEntry;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.event.remote.JacksonJsonEventSerializer;
import de.metas.util.Services;
import lombok.NonNull;

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
	private final static CCache<UUID, Integer> uuid2eventLogId = CCache.newLRUCache(
			I_AD_EventLog.Table_Name + "#by#" + I_AD_EventLog.COLUMNNAME_Event_UUID,
			500,
			15 /* expireMinutes=15 because it's unlikely that event log records need to be in cache for longer periods of time */);

	public Event loadEventForReposting(@NonNull final I_AD_EventLog eventLogRecord)
	{
		return loadEventForReposting(eventLogRecord, ImmutableList.of());
	}

	public Event loadEventForReposting(
			@NonNull final I_AD_EventLog eventLogRecord,
			@NonNull final List<String> handlersToIgnore)
	{
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
				.storeEvent(false)
				.build();
	}

	public void storeEvent(
			@NonNull final Event event,
			@NonNull final IEventBus eventBus)
	{
		final String eventString = JacksonJsonEventSerializer.instance.toString(event);

		final I_AD_EventLog eventLogRecord = newInstance(I_AD_EventLog.class, PlainContextAware.newOutOfTrx());
		eventLogRecord.setEvent_UUID(event.getUuid().toString());
		eventLogRecord.setEventTime(Timestamp.from(event.getWhen()));
		eventLogRecord.setEventData(eventString);
		eventLogRecord.setEventTopicName(eventBus.getTopicName());
		eventLogRecord.setEventTypeName(eventBus.getType().toString());

		save(eventLogRecord);
	}

	public void storeEventLogEntry(@NonNull final EventLogEntry eventLogEntry)
	{
		final int eventLogRecordId = retrieveEventLogIdUsingCacheOutOfTrx(eventLogEntry.getUuid());

		final I_AD_EventLog_Entry eventLogEntryRecord = newInstance(I_AD_EventLog_Entry.class, PlainContextAware.newOutOfTrx());

		setValue(eventLogEntryRecord, I_AD_EventLog_Entry.COLUMNNAME_AD_Client_ID, eventLogEntry.getClientId());
		eventLogEntryRecord.setAD_Org_ID(eventLogEntry.getOrgId());

		eventLogEntryRecord.setAD_EventLog_ID(eventLogRecordId);
		eventLogEntryRecord.setAD_Issue_ID(eventLogEntry.getAdIssueId());
		eventLogEntryRecord.setIsError(eventLogEntry.isError());
		eventLogEntryRecord.setProcessed(eventLogEntry.isProcessed());
		eventLogEntryRecord.setMsgText(eventLogEntry.getMessage());
		eventLogEntryRecord.setClassname(eventLogEntry.getEventHandlerClassName());
		save(eventLogEntryRecord);

		if (eventLogEntry.isError())
		{
			final I_AD_EventLog eventLogRecord = eventLogEntryRecord.getAD_EventLog();
			if (!eventLogRecord.isError())
			{
				eventLogRecord.setIsError(true);
				save(eventLogRecord);
			}
		}
	}

	private int retrieveEventLogIdUsingCacheOutOfTrx(@NonNull final UUID uuid)
	{
		final int eventStoreRecordId = uuid2eventLogId.getOrLoad(uuid, this::retrieveRecordIdOutOfTrx);
		return eventStoreRecordId;
	}

	private Integer retrieveRecordIdOutOfTrx(final UUID uuid)
	{
		final int eventStoreRecordId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_EventLog.class, PlainContextAware.newOutOfTrx())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_EventLog.COLUMN_Event_UUID, uuid.toString())
				.create()
				.firstIdOnly();

		return eventStoreRecordId;
	}
}
