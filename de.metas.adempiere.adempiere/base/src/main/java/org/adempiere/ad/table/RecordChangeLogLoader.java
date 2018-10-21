package org.adempiere.ad.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.table.RecordChangeLog.RecordChangeLogBuilder;
import org.adempiere.user.UserId;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Column;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.po.POTrlInfo;
import de.metas.i18n.po.POTrlRepository;
import de.metas.util.Check;
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

final class RecordChangeLogLoader
{
	public static final RecordChangeLogLoader ofAdTableId(final int adTableId)
	{
		return new RecordChangeLogLoader(adTableId);
	}

	private final POTrlRepository trlRepo;

	private final POInfo poInfo;
	private final RecordChangeLogEntryValuesResolver valueResolver;

	private final POTrlInfo adColumnTrlInfo;

	private RecordChangeLogLoader(final int adTableId)
	{
		Check.assumeGreaterThanZero(adTableId, "adTableId");

		trlRepo = POTrlRepository.instance;

		poInfo = POInfo.getPOInfo(adTableId);
		Check.assumeNotNull(poInfo, "poInfo is not null for adTableId={}", adTableId);

		valueResolver = RecordChangeLogEntryValuesResolver.of(poInfo);

		final POInfo adColumnPOInfo = POInfo.getPOInfo(I_AD_Column.Table_Name);
		adColumnTrlInfo = adColumnPOInfo.getTrlInfo();
	}

	public RecordChangeLog getByRecordId(@NonNull final ComposedRecordId recordId)
	{
		final RecordChangeLogBuilder changeLogsBuilder = RecordChangeLog.builder()
				.tableName(poInfo.getTableName())
				.recordId(recordId);

		loadRecordSummary(changeLogsBuilder, recordId);

		final List<RecordChangeLogEntry> logEntries = retrieveLogEntries(recordId);
		changeLogsBuilder.entries(logEntries);

		return changeLogsBuilder.build();
	}

	public RecordChangeLog getSummaryByRecordId(@NonNull final ComposedRecordId recordId)
	{
		final RecordChangeLogBuilder changeLogsBuilder = RecordChangeLog.builder()
				.tableName(poInfo.getTableName())
				.recordId(recordId);

		loadRecordSummary(changeLogsBuilder, recordId);

		return changeLogsBuilder.build();
	}

	private void loadRecordSummary(final RecordChangeLogBuilder changeLogsBuilder, final ComposedRecordId recordId)
	{
		final String sql = new StringBuilder()
				.append("SELECT Created, CreatedBy, Updated, UpdatedBy FROM ").append(poInfo.getTableName())
				.append(" WHERE ").append(poInfo.getSqlWhereClauseByKeys())
				.toString();

		final List<Object> sqlParams = recordId.getKeysAsList(poInfo.getKeyColumnNames());

		DB.retrieveFirstRowOrNull(sql, sqlParams, rs -> {
			changeLogsBuilder
					.createdByUserId(UserId.ofRepoIdOrNull(rs.getInt("CreatedBy")))
					.createdTimestamp(TimeUtil.asZonedDateTime(rs.getTimestamp("Created")))
					.lastChangedByUserId(UserId.ofRepoIdOrNull(rs.getInt("UpdatedBy")))
					.lastChangedTimestamp(TimeUtil.asZonedDateTime(rs.getTimestamp("Updated")));
			return null;
		});
	}

	private List<RecordChangeLogEntry> retrieveLogEntries(final ComposedRecordId recordId)
	{
		if (!recordId.isSingleKey())
		{
			return ImmutableList.of();
		}
		final int singleRecordId = recordId.getSingleRecordId().orElse(-1);

		final String sql = "SELECT"
				+ " cl.AD_Column_ID"
				+ ", c.AD_Reference_ID"
				+ ", c.ColumnName"
				+ ", c.Name as ColumnDisplayName"
				+ ", cl.Updated"
				+ ", cl.UpdatedBy"
				+ ", cl.OldValue"
				+ ", cl.NewValue "
				+ " FROM " + I_AD_ChangeLog.Table_Name + " cl"
				+ " INNER JOIN AD_Column c ON (c.AD_Column_ID=cl.AD_Column_ID)"
				+ " WHERE cl.AD_Table_ID=? AND cl.Record_ID=? "
				+ " ORDER BY cl.Updated DESC";
		final List<Object> sqlParams = Arrays.<Object> asList(poInfo.getAD_Table_ID(), singleRecordId);

		return DB.retrieveRowsOutOfTrx(sql, sqlParams, this::retrieveLogEntry);
	}

	private RecordChangeLogEntry retrieveLogEntry(final ResultSet rs) throws SQLException
	{
		final String columnName = rs.getString("ColumnName");
		final ITranslatableString columnDisplayName = retrieveColumnDisplayName(rs);
		final ZonedDateTime changeTimestamp = TimeUtil.asZonedDateTime(rs.getTimestamp("Updated"));
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

	private ITranslatableString retrieveColumnDisplayName(final ResultSet rs) throws SQLException
	{
		final int adColumnId = rs.getInt("AD_Column_ID");
		final String columnDisplayNameDefault = rs.getString("ColumnDisplayName");
		final IModelTranslationMap adColumnTrlMap = trlRepo.retrieveAll(adColumnTrlInfo, adColumnId);
		return adColumnTrlMap.getColumnTrl(I_AD_Column.COLUMNNAME_Name, columnDisplayNameDefault);
	}

}
