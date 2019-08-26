package org.adempiere.ad.table;

import static de.metas.util.Check.assume;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOrNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.LogEntriesRepository.LogEntriesQuery;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_AD_ChangeLog;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Location;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.po.POTrlInfo;
import de.metas.i18n.po.POTrlRepository;
import de.metas.location.LocationId;
import de.metas.user.UserId;
import de.metas.util.Services;
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

public class RecordChangeLogEntryLoader
{
	private static final POTrlRepository trlRepo = POTrlRepository.instance;

	private static final CCache<AdTableId, RecordChangeLogEntryValuesResolver> adTabled2RecordChangeLogEntryValuesResolver = CCache
			.<AdTableId, RecordChangeLogEntryValuesResolver> builder()
			.cacheName("adTabled2RecordChangeLogEntryValuesResolver")
			.additionalTableNameToResetFor(I_AD_Table.Table_Name) // those table names are more or less here only for good measure; i didn't really analyze their need
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.additionalTableNameToResetFor(I_AD_Reference.Table_Name)
			.additionalTableNameToResetFor(I_AD_Ref_List.Table_Name)
			.additionalTableNameToResetFor(I_AD_Ref_Table.Table_Name)
			.build();

	private static final POInfo adColumnPOInfo = POInfo.getPOInfo(I_AD_Column.Table_Name);

	public static ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> retrieveLogEntries(@NonNull final LogEntriesQuery logEntriesQuery)
	{
		final List<TableRecordReference> recordReferences = logEntriesQuery.getTableRecordReferences();
		if (recordReferences.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		final List<RecordRefWithLogEntry> recordRefWithLogEntries = loadLogEntriesFor(recordReferences);

		final Map<TableRecordReference, TreeSet<RecordChangeLogEntry>> intermediateResult = new HashMap<>();

		if (logEntriesQuery.isFollowLocationIdChanges() && POInfo.getPOInfo(I_C_Location.Table_Name).isChangeLog())
		{
			// separate from each other those change log entries that do and do not reference C_Location
			final Map<Boolean, List<RecordRefWithLogEntry>> partition = recordRefWithLogEntries.stream()
					.collect(Collectors.partitioningBy(RecordChangeLogEntryLoader::isReferencesLocationTable));

			final List<RecordRefWithLogEntry> entriesWithoutLocationId = partition.get(false);
			addAllToIntermediateResult(entriesWithoutLocationId, intermediateResult);

			final List<RecordRefWithLogEntry> entriesWithLocationId = partition.get(true);
			// instead of adding entriesWithLocationId, we derive C_Location-ChangeLog-Entries and add those
			final ImmutableListMultimap<TableRecordReference, LocationId> locationIds = extractLocationIds(entriesWithLocationId);
			final ImmutableListMultimap<TableRecordReference, I_C_Location> locationRecords = extractLocationRecords(locationIds);

			for (final TableRecordReference recordRef : locationRecords.keySet())
			{
				final List<RecordRefWithLogEntry> derivedLocationEntries = deriveLocationLogEntries(recordRef, locationRecords.get(recordRef));
				addAllToIntermediateResult(derivedLocationEntries, intermediateResult);
			}
		}
		else
		{
			addAllToIntermediateResult(recordRefWithLogEntries, intermediateResult);
		}

		final ImmutableListMultimap.Builder<TableRecordReference, RecordChangeLogEntry> result = ImmutableListMultimap.builder();
		for (final Entry<TableRecordReference, TreeSet<RecordChangeLogEntry>> entry : intermediateResult.entrySet())
		{
			result.putAll(entry.getKey(), entry.getValue());
		}
		return result.build();
	}

	private static void addAllToIntermediateResult(
			final List<RecordRefWithLogEntry> recordRefWithLogEntries,
			final Map<TableRecordReference, TreeSet<RecordChangeLogEntry>> intermediateResult)
	{
		for (final RecordRefWithLogEntry recordRefWithLogEntry : recordRefWithLogEntries)
		{
			final TableRecordReference recordRef = recordRefWithLogEntry.getRecordRef();
			final RecordChangeLogEntry logEntry = recordRefWithLogEntry.getRecordChangeLogEntry();

			final TreeSet<RecordChangeLogEntry> entriesForRecordRef = intermediateResult.computeIfAbsent(recordRef, k -> createNewTreeSet());
			entriesForRecordRef.add(logEntry);
		}
	}

	private static boolean isReferencesLocationTable(@NonNull final RecordRefWithLogEntry recordRefWithLogEntry)
	{
		final RecordChangeLogEntry logEntry = recordRefWithLogEntry.getRecordChangeLogEntry();
		final TableRecordReference recordRef = recordRefWithLogEntry.getRecordRef();

		final POInfo poInfo = POInfo.getPOInfo(recordRef.getAdTableId());
		final String columnName = logEntry.getColumnName();
		final String referencedTableName = poInfo.getReferencedTableNameOrNull(columnName);
		final boolean referencesLocationTable = Objects.equals(referencedTableName, I_C_Location.Table_Name);
		return referencesLocationTable;
	}

	private static ImmutableListMultimap<TableRecordReference, LocationId> extractLocationIds(
			@NonNull final List<RecordRefWithLogEntry> entriesWithLocationId)
	{
		final ImmutableListMultimap.Builder<TableRecordReference, LocationId> recordRef2LocationIds = ImmutableListMultimap.builder();
		if (entriesWithLocationId.isEmpty())
		{
			return recordRef2LocationIds.build();
		}

		// We need have the first changelog's *old* value for each C_Location-referencing column of every TableRecordRef!
		final ImmutableListMultimap<ArrayKey, RecordRefWithLogEntry> index = Multimaps.index(
				entriesWithLocationId,
				entry -> ArrayKey.of(entry.getRecordRef(), entry.getRecordChangeLogEntry().getColumnName()));
		for (final Collection<RecordRefWithLogEntry> values : index.asMap().values())
		{
			final Comparator<RecordRefWithLogEntry> comparator = Comparator
					.comparing(e -> e.getRecordChangeLogEntry().getChangedTimestamp());
			final RecordRefWithLogEntry firstRecordRefWithLogEntry = values.stream()
					.min(comparator)
					.get() /* the collection is not empty, so there must be a minimum */;

			final LocationId locationId = extractValueOldAsLocationId(firstRecordRefWithLogEntry);
			recordRef2LocationIds.put(firstRecordRefWithLogEntry.getRecordRef(), locationId);
		}

		// for all change logs including the first ones, we extract their new values
		// note that the order doesn't really matter; we will make sure the correct order later, after we got the actual C_Location records
		for (final RecordRefWithLogEntry recordRefWithLogEntry : entriesWithLocationId)
		{
			final LocationId locationId = extractValueNewAsLocationId(recordRefWithLogEntry);
			recordRef2LocationIds.put(recordRefWithLogEntry.getRecordRef(), locationId);
		}
		return recordRef2LocationIds.build();
	}

	private static ImmutableListMultimap<TableRecordReference, I_C_Location> extractLocationRecords(
			@NonNull final ImmutableListMultimap<TableRecordReference, LocationId> locationIds)
	{
		final ImmutableListMultimap.Builder<TableRecordReference, I_C_Location> recordRef2LocationRecords = ImmutableListMultimap.builder();
		if (locationIds.isEmpty())
		{
			return recordRef2LocationRecords.build(); // don't bother the database
		}

		final ImmutableList<LocationId> allLocationIds = locationIds.entries().stream().map(Entry::getValue).collect(ImmutableList.toImmutableList());

		final List<I_C_Location> locationRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Location.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Location.COLUMN_C_Location_ID, allLocationIds)
				.create()
				.list();
		final ImmutableMap<Integer, I_C_Location> repoId2LocationRecord = Maps.uniqueIndex(locationRecords, I_C_Location::getC_Location_ID);

		for (final Entry<TableRecordReference, LocationId> recordRefAndLocationId : locationIds.entries())
		{
			final I_C_Location locationRecord = repoId2LocationRecord.get(recordRefAndLocationId.getValue().getRepoId());
			recordRef2LocationRecords.put(recordRefAndLocationId.getKey(), locationRecord);
		}
		return recordRef2LocationRecords.build();
	}

	/**
	 * @param unOrderedLocationRecords {@link I_C_Location} records that are referenced from the change log of {@code recordRef}; may or may not be ordered.
	 */
	private static ImmutableList<RecordRefWithLogEntry> deriveLocationLogEntries(
			@NonNull final TableRecordReference recordRef,
			@NonNull final ImmutableList<I_C_Location> unOrderedLocationRecords)
	{
		// Also if there was only only one C_Location_ID changelog, we have already 2 I_C_Locations, because we also got the first changelog's oldValue.
		assume(unOrderedLocationRecords.size() >= 2, "Parameter 'unOrderedLocationRecords' needs to contain at least 2 items; unOrderedLocationRecords={}", unOrderedLocationRecords);

		final POInfo poInfo = POInfo.getPOInfo(I_C_Location.Table_Name);
		final ImmutableList.Builder<RecordRefWithLogEntry> result = ImmutableList.builder();

		final ArrayList<I_C_Location> orderedLocationRecords = new ArrayList<>(unOrderedLocationRecords);
		Collections.sort(orderedLocationRecords, Comparator.comparing(I_C_Location::getCreated));

		for (int recordIdx = 1; recordIdx < orderedLocationRecords.size(); recordIdx++)
		{
			final I_C_Location oldRecord = orderedLocationRecords.get(recordIdx - 1);
			final I_C_Location newRecord = orderedLocationRecords.get(recordIdx);
			for (int columnIdx = 0; columnIdx < poInfo.getColumnCount(); columnIdx++)
			{
				final String columnName = poInfo.getColumnName(columnIdx);

				if (isSkipLocationColumnName(columnName))
				{
					continue;
				}

				final Object oldValue = getValueOrNull(oldRecord, columnName);
				final Object newValue = getValueOrNull(newRecord, columnName);
				if (Objects.equals(oldValue, newValue))
				{
					continue;
				}

				final POInfoColumn columnInfo = poInfo.getColumn(columnIdx);

				final IModelTranslationMap adColumnTrlMap = trlRepo.retrieveAll(
						adColumnPOInfo.getTrlInfo(),
						columnInfo.getAD_Column_ID());
				final ITranslatableString columnTrl = adColumnTrlMap.getColumnTrl(I_AD_Column.COLUMNNAME_Name, columnInfo.getColumnName());

				final RecordChangeLogEntry logEntry = RecordChangeLogEntry.builder()
						.changedByUserId(UserId.ofRepoId(newRecord.getCreatedBy()))
						.changedTimestamp(TimeUtil.asInstant(newRecord.getCreated()))
						.columnDisplayName(columnTrl)
						.columnName(columnInfo.getColumnName())
						.displayType(columnInfo.getDisplayType())
						.valueNew(newValue)
						.valueOld(oldValue)
						.build();
				result.add(new RecordRefWithLogEntry(recordRef, logEntry));
			}
		}
		return result.build();
	}

	private static boolean isSkipLocationColumnName(@NonNull final String columnName)
	{
		final POInfo poInfo = POInfo.getPOInfo(I_C_Location.Table_Name);
		final int columnIdx = poInfo.getColumnIndex(columnName);

		if (I_C_Location.COLUMNNAME_C_Location_ID.equals(columnName)
				|| I_C_Location.COLUMNNAME_Created.equals(columnName)
				|| I_C_Location.COLUMNNAME_CreatedBy.equals(columnName)
				|| I_C_Location.COLUMNNAME_Updated.equals(columnName)
				|| I_C_Location.COLUMNNAME_UpdatedBy.equals(columnName))
		{
			return true;
		}
		if (!poInfo.isAllowLogging(columnIdx))
		{
			return true;
		}

		return false;
	}

	private static TreeSet<RecordChangeLogEntry> createNewTreeSet()
	{
		final Comparator<RecordChangeLogEntry> comparator = Comparator
				.comparing(RecordChangeLogEntry::getChangedTimestamp)
				.thenComparing(RecordChangeLogEntry::getColumnName);
		return new TreeSet<RecordChangeLogEntry>(comparator);
	}

	private static LocationId extractValueOldAsLocationId(@NonNull final RecordRefWithLogEntry recordRefWithLogEntry)
	{
		final RecordChangeLogEntry logEntry = recordRefWithLogEntry.getRecordChangeLogEntry();

		final Object valueOld = logEntry.getValueOld();
		if (valueOld == null || !(valueOld instanceof KeyNamePair))
		{
			throw new AdempiereException("The RecordChangeLogEntry's column references C_Location, so its valueOld needs to be KeyNamePair and not-null")
					.appendParametersToMessage()
					.setParameter("valueOld", valueOld)
					.setParameter("recordRefWithLogEntry", recordRefWithLogEntry);
		}

		// because C_Location is not mutated by the system, we can also safely assume that no locationId will show up more than once.
		final LocationId locationId = LocationId.ofRepoId(((KeyNamePair)valueOld).getKey());
		return locationId;
	}

	private static LocationId extractValueNewAsLocationId(@NonNull final RecordRefWithLogEntry recordRefWithLogEntry)
	{
		final RecordChangeLogEntry logEntry = recordRefWithLogEntry.getRecordChangeLogEntry();

		final Object valueNew = logEntry.getValueNew();
		if (valueNew == null || !(valueNew instanceof KeyNamePair))
		{
			throw new AdempiereException("The RecordChangeLogEntry's column references C_Location, so its valueNew needs to be KeyNamePair and not-null")
					.appendParametersToMessage()
					.setParameter("valueNew", valueNew)
					.setParameter("recordRefWithLogEntry", recordRefWithLogEntry);
		}

		// because C_Location is not mutated by the system, we can also safely assume that no locationId will show up more than once.
		final LocationId locationId = LocationId.ofRepoId(((KeyNamePair)valueNew).getKey());
		return locationId;
	}

	private static List<RecordRefWithLogEntry> loadLogEntriesFor(@NonNull final List<TableRecordReference> recordReferences)
	{
		SqlWithParams completeSQL = SqlWithParams.createEmpty();

		final ListMultimap<AdTableId, Integer> tableId2RecordIds = TableRecordReferenceSet.of(recordReferences).extractTableId2RecordIds();

		for (final AdTableId tableId : tableId2RecordIds.keySet())
		{
			// get the current tableId's changelogs' SQL
			final SqlWithParams sqlWithParams = createAD_ChangeLog_SQL(tableId, tableId2RecordIds.get(tableId));
			completeSQL = completeSQL.add(sqlWithParams);
		}

		completeSQL = completeSQL.withFinalOrderByClause("ORDER BY Updated"); // oldes first

		final List<RecordRefWithLogEntry> recordRefWithLogEntries = DB.retrieveRowsOutOfTrx(
				completeSQL.getSql(),
				completeSQL.getSqlParams(),
				RecordChangeLogEntryLoader::createTableRefWithLogEntry);
		return recordRefWithLogEntries;
	}

	@Value
	@VisibleForTesting
	static class SqlWithParams
	{
		@NonNull
		String sql;

		@NonNull
		ImmutableList<Object> sqlParams;

		@VisibleForTesting
		static SqlWithParams createEmpty()
		{
			return new SqlWithParams("", ImmutableList.of());
		}

		@VisibleForTesting
		SqlWithParams add(@NonNull final SqlWithParams sqlWithParams)
		{
			StringBuilder completeSQL = new StringBuilder(sql);
			ImmutableList.Builder<Object> completeParams = ImmutableList.builder().addAll(sqlParams);

			final boolean firstSQL = completeSQL.length() == 0;
			if (!firstSQL)
			{
				completeSQL.append(" UNION\n");
			}

			completeSQL.append(sqlWithParams.getSql());
			completeSQL.append("\n");

			completeParams.addAll(sqlWithParams.getSqlParams());

			return new SqlWithParams(completeSQL.toString(), completeParams.build());
		}

		@VisibleForTesting
		SqlWithParams withFinalOrderByClause(@NonNull final String orderBy)
		{
			final StringBuilder completeSQL = new StringBuilder(sql).append(orderBy);
			return new SqlWithParams(completeSQL.toString(), sqlParams);
		}
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

		final SqlWithParams sqlWithParams = new SqlWithParams(sql, ImmutableList.copyOf(sqlParams));
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
		@NonNull
		TableRecordReference recordRef;
		@NonNull
		RecordChangeLogEntry recordChangeLogEntry;
	}
}
