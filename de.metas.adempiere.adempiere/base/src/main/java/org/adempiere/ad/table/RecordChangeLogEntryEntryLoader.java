package org.adempiere.ad.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Column;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import com.google.common.collect.ListMultimap;

import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.po.POTrlInfo;
import de.metas.i18n.po.POTrlRepository;
import de.metas.user.UserId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class RecordChangeLogEntryEntryLoader
{
	private static final POTrlRepository trlRepo = POTrlRepository.instance;

	private static final CCache<AdTableId, RecordChangeLogEntryValuesResolver> adTabled2RecordChangeLogEntryValuesResolver = CCache
			.<AdTableId, RecordChangeLogEntryValuesResolver> builder()
			.cacheName("adTabled2RecordChangeLogEntryValuesResolver")
			.build();

	private static final POInfo adColumnPOInfo = POInfo.getPOInfo(I_AD_Column.Table_Name);

	public static ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> retrieveLogEntries(@NonNull final List<TableRecordReference> recordReferences)
	{
		if (recordReferences.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final StringBuilder completeSQL = new StringBuilder();
		final ImmutableList.Builder<Object> completeParams = ImmutableList.builder();

		final ListMultimap<AdTableId, Integer> tableId2RecordIds = TableRecordReferenceSet.of(recordReferences).extractTableId2RecordIds();

		for (final AdTableId tableId : tableId2RecordIds.keySet())
		{
			final SqlWithParams sqlWithParams = createAD_ChangeLog_SQL(tableId, tableId2RecordIds.get(tableId));

			final boolean firstSQL = completeSQL.length() == 0;
			if (!firstSQL)
			{
				completeSQL.append(" UNION\n");
			}
			completeSQL.append(sqlWithParams.getSql());
			completeSQL.append("\n");

			completeParams.addAll(sqlWithParams.getSqlParams());
		}

		completeSQL.append(" ORDER BY Updated DESC");

		final Builder<TableRecordReference, RecordChangeLogEntry> result = ImmutableListMultimap.builder();

		final List<RecordRefWithLogEntry> recordRefWithLogEntries = DB.retrieveRowsOutOfTrx(
				completeSQL.toString(),
				completeParams.build(),
				RecordChangeLogEntryEntryLoader::createTableRefWithLogEntry);

		for (final RecordRefWithLogEntry recordRefWithLogEntry : recordRefWithLogEntries)
		{
			result.put(recordRefWithLogEntry.getRecordRef(), recordRefWithLogEntry.getRecordChangeLogEntry());
		}

		return result.build();
	}

	@Value
	private static class SqlWithParams
	{
		String sql;
		List<Object> sqlParams;
	}

	private static SqlWithParams createAD_ChangeLog_SQL(
			@NonNull final AdTableId tableId,
			@NonNull final List<Integer> recordIds)
	{
		final ArrayList<Object> sqlParams = new ArrayList<>();
		sqlParams.add(tableId.getRepoId());

		final String sql = "SELECT"
				+ " cl.AD_Table_ID"
				+ ", cl.Record_ID"
				+ ", cl.AD_Column_ID"
				+ ", c.AD_Reference_ID"
				+ ", c.ColumnName"
				+ ", c.Name as ColumnDisplayName"
				+ ", cl.Updated"
				+ ", cl.UpdatedBy"
				+ ", cl.OldValue"
				+ ", cl.NewValue "
				+ " FROM " + I_AD_ChangeLog.Table_Name + " cl"
				+ " INNER JOIN AD_Column c ON (c.AD_Column_ID=cl.AD_Column_ID)"
				+ " WHERE cl.AD_Table_ID=? AND cl." + DB.buildSqlList("Record_ID", recordIds, sqlParams);

		final SqlWithParams sqlWithParams = new SqlWithParams(sql, sqlParams);
		return sqlWithParams;
	}

	private static RecordRefWithLogEntry createTableRefWithLogEntry(@NonNull final ResultSet rs) throws SQLException
	{
		final int tableId = rs.getInt("AD_Table_ID");
		final int recordId = rs.getInt("Record_ID");

		final TableRecordReference recordRef = TableRecordReference.of(tableId, recordId);
		final RecordChangeLogEntry logEntry = retrieveLogEntry(rs, AdTableId.ofRepoId(tableId));

		return new RecordRefWithLogEntry(recordRef, logEntry);
	}

	private static RecordChangeLogEntry retrieveLogEntry(
			@NonNull final ResultSet rs,
			@NonNull final AdTableId tableId) throws SQLException
	{
		final POInfo poInfo = POInfo.getPOInfo(tableId);

		final RecordChangeLogEntryValuesResolver valueResolver = adTabled2RecordChangeLogEntryValuesResolver.getOrLoad(tableId, id -> RecordChangeLogEntryValuesResolver.of(poInfo));
		final POTrlInfo adColumnTrlInfo = adColumnPOInfo.getTrlInfo();

		final String columnName = rs.getString("ColumnName");
		final ITranslatableString columnDisplayName = retrieveColumnDisplayName(rs, adColumnTrlInfo);
		final Instant changeTimestamp = TimeUtil.asInstant(rs.getTimestamp("Updated"));
		final UserId changedByUserId = UserId.ofRepoId(rs.getInt("UpdatedBy"));
		final int displayType = rs.getInt("AD_Reference_ID");
		final Object valueOld = valueResolver.convertToDisplayValue(rs.getString("OldValue"), columnName);
		final Object valueNew = valueResolver.convertToDisplayValue(rs.getString("NewValue"), columnName);

		return RecordChangeLogEntry.builder()
				.columnName(columnName)
				.columnDisplayName(columnDisplayName)
				.displayType(displayType)
				.valueOld(valueOld)
				.valueNew(valueNew)
				.changedTimestamp(changeTimestamp)
				.changedByUserId(changedByUserId)
				.build();
	}

	private static ITranslatableString retrieveColumnDisplayName(
			@NonNull final ResultSet rs,
			@NonNull final POTrlInfo adColumnTrlInfo) throws SQLException
	{
		final int adColumnId = rs.getInt("AD_Column_ID");
		final String columnDisplayNameDefault = rs.getString("ColumnDisplayName");
		final IModelTranslationMap adColumnTrlMap = trlRepo.retrieveAll(adColumnTrlInfo, adColumnId);
		return adColumnTrlMap.getColumnTrl(I_AD_Column.COLUMNNAME_Name, columnDisplayNameDefault);
	}

	@Value
	private static class RecordRefWithLogEntry
	{
		TableRecordReference recordRef;
		RecordChangeLogEntry recordChangeLogEntry;
	}
}
