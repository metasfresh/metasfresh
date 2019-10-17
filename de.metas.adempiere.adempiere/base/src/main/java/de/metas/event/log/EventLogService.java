package de.metas.event.log;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.adempiere.model.InterfaceWrapperHelper.setValue;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.error.AdIssueId;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.event.remote.JacksonJsonEventSerializer;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
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
	private static final Logger logger = LogManager.getLogger(EventLogService.class);

	private final CCache<UUID, EventLogId> uuid2eventLogId = CCache.newLRUCache(
			I_AD_EventLog.Table_Name + "#by#" + I_AD_EventLog.COLUMNNAME_Event_UUID,
			500,
			15 /* expireMinutes=15 because it's unlikely that event log records need to be in cache for longer periods of time */);

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

	public void saveEvent(
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

		save(eventLogRecord);
	}

	public void saveEventLogEntries(@NonNull final Collection<EventLogEntry> eventLogEntries)
	{
		if (eventLogEntries.isEmpty())
		{
			return;
		}

		//
		// warn up UUID->EventLogId cache
		final ImmutableSet<UUID> uuids = eventLogEntries.stream().map(EventLogEntry::getUuid).collect(ImmutableSet.toImmutableSet());
		getEventLogIdsUsingCacheOutOfTrx(uuids);

		//
		// Save each entry
		eventLogEntries.forEach(this::saveEventLogEntry);

		//
		// Update EventLog's error flag from entries
		updateUpdateEventLogErrorFlagFromEntries(eventLogEntries);
	}

	private void saveEventLogEntry(@NonNull final EventLogEntry eventLogEntry)
	{
		final EventLogId eventLogId = getEventLogIdUsingCacheOutOfTrx(eventLogEntry.getUuid());

		final I_AD_EventLog_Entry eventLogEntryRecord = newInstanceOutOfTrx(I_AD_EventLog_Entry.class);

		setValue(eventLogEntryRecord, I_AD_EventLog_Entry.COLUMNNAME_AD_Client_ID, eventLogEntry.getClientId());
		eventLogEntryRecord.setAD_Org_ID(eventLogEntry.getOrgId());

		eventLogEntryRecord.setAD_EventLog_ID(eventLogId.getRepoId());
		eventLogEntryRecord.setAD_Issue_ID(AdIssueId.toRepoId(eventLogEntry.getAdIssueId()));
		eventLogEntryRecord.setIsError(eventLogEntry.isError());
		eventLogEntryRecord.setProcessed(eventLogEntry.isProcessed());
		eventLogEntryRecord.setMsgText(eventLogEntry.getMessage());
		eventLogEntryRecord.setClassname(eventLogEntry.getEventHandlerClassName());
		saveRecord(eventLogEntryRecord);
	}

	private void updateUpdateEventLogErrorFlagFromEntries(final Collection<EventLogEntry> eventLogEntries)
	{
		if (eventLogEntries.isEmpty())
		{
			return;
		}

		final ImmutableSet<EventLogId> eventLogIds = eventLogEntries.stream()
				.filter(EventLogEntry::isError)
				.map(eventLogEntry -> getEventLogIdUsingCacheOutOfTrx(eventLogEntry.getUuid()))
				.collect(ImmutableSet.toImmutableSet());
		if (eventLogIds.isEmpty())
		{
			return;
		}

		final List<I_AD_EventLog> eventLogs = loadByRepoIdAwaresOutOfTrx(eventLogIds, I_AD_EventLog.class);
		if (eventLogs.isEmpty())
		{
			return;
		}

		for (final I_AD_EventLog eventLog : eventLogs)
		{
			eventLog.setIsError(true);
			saveRecord(eventLog);
		}
	}

	private EventLogId getEventLogIdUsingCacheOutOfTrx(@NonNull final UUID uuid)
	{
		final Collection<EventLogId> eventLogIds = getEventLogIdsUsingCacheOutOfTrx(ImmutableSet.of(uuid));
		if (eventLogIds.isEmpty())
		{
			throw new AdempiereException("No EventLog found for " + uuid);
		}
		else
		{
			if (eventLogIds.size() > 1) // shall not happen
			{
				logger.warn("More than one EventLogId found for {}: {}. Returning first one.", uuid, eventLogIds);
			}
			return eventLogIds.iterator().next();
		}
	}

	private Collection<EventLogId> getEventLogIdsUsingCacheOutOfTrx(@NonNull final Collection<UUID> uuids)
	{
		return uuid2eventLogId.getAllOrLoad(uuids, this::retrieveAllRecordIdOutOfTrx);
	}

	private ImmutableMap<UUID, EventLogId> retrieveAllRecordIdOutOfTrx(@NonNull final Collection<UUID> uuids)
	{
		if (uuids.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<String> uuidStrs = uuids.stream().map(UUID::toString).collect(ImmutableSet.toImmutableSet());

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_EventLog.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_EventLog.COLUMN_Event_UUID, uuidStrs)
				.orderBy(I_AD_EventLog.COLUMN_AD_EventLog_ID) // just to have a predictable order
				.create()
				.listColumns(I_AD_EventLog.COLUMNNAME_AD_EventLog_ID, I_AD_EventLog.COLUMNNAME_Event_UUID)
				.stream()
				.map(map -> {
					final int eventLogRepoId = NumberUtils.asInt(map.get(I_AD_EventLog.COLUMNNAME_AD_EventLog_ID), -1);
					final EventLogId eventLogId = EventLogId.ofRepoId(eventLogRepoId);

					final UUID uuid = UUID.fromString(map.get(I_AD_EventLog.COLUMNNAME_Event_UUID).toString());
					return GuavaCollectors.entry(uuid, eventLogId);
				})
				.collect(GuavaCollectors.toImmutableMap());
	}
}
