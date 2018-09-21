package de.metas.event.log;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.setValue;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.IQuery;
import org.compiere.util.CCache;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

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

	private final EventLogUserService eventLogUserService;

	public EventLogService(@NonNull final EventLogUserService eventLogUserService)
	{
		this.eventLogUserService = eventLogUserService;
	}

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

		final Event.Builder eventBuilderWithAdditionalInfo = createEventWithProcessedHandlers(eventFromStoredString, processedHandlers);

		return eventLogUserService
				.addEventLogAdvise(eventBuilderWithAdditionalInfo, false)
				.build();
	}

	private Event.Builder createEventWithProcessedHandlers(
			@NonNull final Event eventFromStoredString,
			@NonNull final List<String> processedHandlers)
	{
		return eventFromStoredString.toBuilder()
				.putPropertyFromObject(
						EventLogUserService.PROPERTY_PROCESSED_BY_HANDLER_CLASS_NAMES,
						processedHandlers);
	}

	public void storeEvent(
			@NonNull final Event event,
			@NonNull final IEventBus eventBus)
	{
		final String eventString = JacksonJsonEventSerializer.instance.toString(event);

		final I_AD_EventLog eventLogRecord = retrieveOrCreateRecordOutOfTrx(event.getUuid());
		eventLogRecord.setEventTime(Timestamp.from(event.getWhen()));
		eventLogRecord.setEventData(eventString);
		eventLogRecord.setEventTopicName(eventBus.getName());
		eventLogRecord.setEventTypeName(eventBus.getType().toString());

		save(eventLogRecord);
	}

	private I_AD_EventLog retrieveOrCreateRecordOutOfTrx(@NonNull final UUID uuid)
	{
		final I_AD_EventLog eventStoreRecord = createEventLogQueryByUuidOutOfTrx(uuid)
				.firstOnly(I_AD_EventLog.class);
		if (eventStoreRecord != null)
		{
			return eventStoreRecord;
		}

		final I_AD_EventLog newEventStoreRecord = createNewRecordWithUuidOutOfTrx(uuid);
		return newEventStoreRecord;

	}

	public void storeEventLogEntry(@NonNull final EventLogEntry eventLogEntry)
	{
		final int eventLogRecordId = retrieveOrCreateEventLogIdUsingCacheOutOfTrx(eventLogEntry.getUuid());

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

		final I_AD_EventLog eventLogRecord = eventLogEntryRecord.getAD_EventLog();
		if (eventLogEntry.isError())
		{
			eventLogRecord.setIsError(true);
		}
		// update eventLogRecord's client and org; they weren't known when the eventLogRecord was created
		if (eventLogRecord.getAD_Client_ID() == 0 && eventLogEntry.getClientId() > 0)
		{
			setValue(eventLogRecord, I_AD_EventLog.COLUMNNAME_AD_Client_ID, eventLogEntry.getClientId());
			eventLogRecord.setAD_Org_ID(eventLogEntry.getOrgId()); // different event log entries with different orgs: sort them out when they occur
		}
		save(eventLogRecord);
	}

	private int retrieveOrCreateEventLogIdUsingCacheOutOfTrx(@NonNull final UUID uuid)
	{
		final Supplier<Integer> valueInitializer = () -> retrieveOrCreateRecordIdOutOfTrx(uuid);
		final int eventStoreRecordId = uuid2eventLogId.get(uuid, valueInitializer);
		return eventStoreRecordId;
	}

	private int retrieveOrCreateRecordIdOutOfTrx(final UUID uuid)
	{
		final int eventStoreRecordId = createEventLogQueryByUuidOutOfTrx(uuid)
				.firstIdOnly();
		if (eventStoreRecordId > 0)
		{
			return eventStoreRecordId;
		}

		final I_AD_EventLog newEventStoreRecord = createNewRecordWithUuidOutOfTrx(uuid);
		save(newEventStoreRecord);

		return newEventStoreRecord.getAD_EventLog_ID();
	}

	private IQuery<I_AD_EventLog> createEventLogQueryByUuidOutOfTrx(final UUID uuid)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_EventLog.class, PlainContextAware.newOutOfTrx())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_EventLog.COLUMN_Event_UUID, uuid.toString())
				.create();
	}

	private I_AD_EventLog createNewRecordWithUuidOutOfTrx(@NonNull final UUID uuid)
	{
		final I_AD_EventLog newEventStoreRecord = newInstance(I_AD_EventLog.class, PlainContextAware.newOutOfTrx());
		newEventStoreRecord.setEvent_UUID(uuid.toString());
		return newEventStoreRecord;
	}
}
