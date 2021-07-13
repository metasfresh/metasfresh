package de.metas.event.log;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.common.util.time.SystemTime;
import de.metas.error.AdIssueId;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.logging.LogManager;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/*
 * #%L
 * de.metas.async
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

/**
 * Blatant ripoff of IWorkpackageLogsRepository's implementation.
 */
@Repository
public class EventLogsRepository
{
	private static final Logger logger = LogManager.getLogger(EventLogsRepository.class);

	private final CCache<UUID, EventLogId> uuid2eventLogId = CCache.newLRUCache(
			I_AD_EventLog.Table_Name + "#by#" + I_AD_EventLog.COLUMNNAME_Event_UUID,
			500,
			15 /* expireMinutes=15 because it's unlikely that event log records need to be in cache for longer periods of time */);

	public void saveLogs(@NonNull final Collection<EventLogEntry> logEntries)
	{
		if (logEntries.isEmpty())
		{
			return;
		}

		// warm up UUID->EventLogId cache
		final ImmutableSet<UUID> uuids = logEntries.stream().map(EventLogEntry::getUuid).collect(ImmutableSet.toImmutableSet());
		getEventLogIdsUsingCacheOutOfTrx(uuids);

		final Set<EventLogId> eventLogsWithError = insertEventLogIds(logEntries);

		// Update EventLog's error flag from entries
		updateUpdateEventLogErrorFlagFromEntries(eventLogsWithError);
	}

	@NonNull
	private Set<EventLogId> insertEventLogIds(@NonNull final Collection<EventLogEntry> logEntries)
	{
		final Set<EventLogId> eventLogsWithError = new HashSet<>();

		final String sql = "INSERT INTO " + I_AD_EventLog_Entry.Table_Name + "("
				+ I_AD_EventLog_Entry.COLUMNNAME_AD_Client_ID + ","  // 1
				+ I_AD_EventLog_Entry.COLUMNNAME_AD_Org_ID + "," // 2
				+ I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_ID + "," // 3
				+ I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_Entry_ID + "," // 4
				+ I_AD_EventLog_Entry.COLUMNNAME_AD_Issue_ID + "," // 5
				+ I_AD_EventLog_Entry.COLUMNNAME_Created + "," // 6
				+ I_AD_EventLog_Entry.COLUMNNAME_CreatedBy + "," // 7
				+ I_AD_EventLog_Entry.COLUMNNAME_IsActive + "," // 8
				+ I_AD_EventLog_Entry.COLUMNNAME_IsError + "," // 9
				+ I_AD_EventLog_Entry.COLUMNNAME_Processed + "," // 10
				+ I_AD_EventLog_Entry.COLUMNNAME_MsgText + "," // 11
				+ I_AD_EventLog_Entry.COLUMNNAME_Classname + "," // 12
				+ I_AD_EventLog_Entry.COLUMNNAME_Updated + "," // 13
				+ I_AD_EventLog_Entry.COLUMNNAME_UpdatedBy  // 14
				+ ")"
				+ " VALUES ("
				+ "?," // 1 - AD_Client_ID
				+ "?," // 2 - AD_Org_ID
				+ "?," // 3 - AD_EventLog_ID
				+ DB.TO_TABLESEQUENCE_NEXTVAL(I_AD_EventLog_Entry.Table_Name) + "," // 4 - AD_EventLog_Entry_ID
				+ "?," // 5 - AD_Issue_ID
				+ "?," // 6 - Created
				+ "?," // 7 - CreatedBy
				+ "'Y'," // 8 - IsActive
				+ "?," // 9 - IsError
				+ "?," // 10 - Processed
				+ "?," // 11 - MsgText
				+ "?," // 12 - Classname
				+ "?," // 13 - Updated
				+ "?" // 14 - UpdatedBy
				+ ")";

		PreparedStatement pstmt = null;
		try
		{
			// NOTE: always create the logs out of transaction because we want them to be persisted even if the workpackage processing fails
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);

			final Timestamp timestamp = SystemTime.asTimestamp();
			final int ad_user_id = Env.getAD_User_ID();

			for (final EventLogEntry logEntry : logEntries)
			{
				final EventLogId eventLogId = getEventLogIdUsingCacheOutOfTrx(logEntry.getUuid());

				final Object[] params = {
						logEntry.getClientId(), // 1 - AD_Client_ID
						logEntry.getOrgId(), // 2 - AD_Org_ID
						eventLogId.getRepoId(), // 3 - AD_EventLog_ID
						// + DB.TO_TABLESEQUENCE_NEXTVAL(I_AD_EventLog_Entry.Table_Name) + "," // 4 - AD_EventLog_Entry_ID
						AdIssueId.toRepoId(logEntry.getAdIssueId()), // 5 - AD_Issue_ID
						timestamp, // 6 - Created
						ad_user_id, // 7 - CreatedBy
						// + "'Y'," // 8 - IsActive
						StringUtils.ofBoolean(logEntry.isError(), "N"), // 9 - IsError
						StringUtils.ofBoolean(logEntry.isProcessed(), "N"), // 10 - Processed
						logEntry.getMessage(), // 11 - MsgText
						logEntry.getEventHandlerClassName(), // 12 - Classname
						timestamp, // 9 - Updated
						ad_user_id // 10 - UpdatedBy
				};
				DB.setParameters(pstmt, params);
				pstmt.addBatch();

				if (logEntry.isError())
				{
					eventLogsWithError.add(eventLogId);
				}
			}

			pstmt.executeBatch();
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(pstmt);
		}
		return eventLogsWithError;
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

	private void updateUpdateEventLogErrorFlagFromEntries(@NonNull final Set<EventLogId> eventLogsWithError)
	{
		if (eventLogsWithError.isEmpty())
		{
			return;
		}

		Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_EventLog.class)
				.addInArrayFilter(I_AD_EventLog.COLUMNNAME_AD_EventLog_ID, eventLogsWithError)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_AD_EventLog.COLUMNNAME_IsError, true)
				.execute();
	}
}
