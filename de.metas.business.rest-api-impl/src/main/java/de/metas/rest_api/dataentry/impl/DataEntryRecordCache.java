package de.metas.rest_api.dataentry.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.CacheIndex;
import de.metas.cache.CacheIndexDataAdapter;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordId;
import de.metas.dataentry.data.DataEntryRecordQuery;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
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

final class DataEntryRecordCache
{
	private final DataEntryRecordRepository recordsRepo;

	private final CacheIndex<DataEntryRecordId/* RK */, CacheKey/* CK */, DataEntryRecord/* V */> //
	cacheIndex = CacheIndex.of(new DataEntryRecordIdIndex());

	private final CCache<CacheKey, DataEntryRecord> cache;

	public DataEntryRecordCache(@NonNull final DataEntryRecordRepository recordsRepo, final int cacheCapacity)
	{
		this.recordsRepo = recordsRepo;
		this.cache = CCache.<CacheKey, DataEntryRecord> builder()
				.tableName(I_DataEntry_Record.Table_Name)
				.cacheMapType(CacheMapType.LRU)
				.initialCapacity(cacheCapacity)
				.invalidationKeysMapper(cacheIndex::computeCachingKeys)
				.removalListener(cacheIndex::remove)
				.additionListener(cacheIndex::add)
				.build();
	}

	public DataEntryRecordsMap get(
			final int recordId,
			@NonNull final Set<DataEntrySubTabId> subTabIds)
	{
		Check.assumeGreaterOrEqualToZero(recordId, "recordId");
		Check.assumeNotEmpty(subTabIds, "subTabIds is not empty");

		final Collection<DataEntryRecord> records = cache.getAllOrLoad(toCacheKeys(recordId, subTabIds), this::loadRecords);

		return DataEntryRecordsMap.of(records);
	}

	private static Set<CacheKey> toCacheKeys(
			final int recordId,
			@NonNull final Set<DataEntrySubTabId> subTabIds)
	{
		return subTabIds.stream()
				.map(subTabId -> CacheKey.of(recordId, subTabId))
				.collect(ImmutableSet.toImmutableSet());
	}

	private Map<CacheKey, DataEntryRecord> loadRecords(final Collection<CacheKey> keys)
	{
		final DataEntryRecordQuery query = toDataEntryRecordQuery(keys);
		final List<DataEntryRecord> records = recordsRepo.stream(query)
				.map(DataEntryRecord::copyAsImmutable)
				.collect(ImmutableList.toImmutableList());
		if (records.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<CacheKey, DataEntryRecord> recordsMap = Maps.uniqueIndex(
				records,
				record -> CollectionUtils.singleElement(cacheIndex.extractCacheKeys(record)));

		return recordsMap;
	}

	private static DataEntryRecordQuery toDataEntryRecordQuery(final Collection<CacheKey> keys)
	{
		final int mainRecordId = CollectionUtils.extractSingleElement(keys, CacheKey::getMainRecordId);
		final ImmutableSet<DataEntrySubTabId> subTabIds = extractSubTabIds(keys);

		return DataEntryRecordQuery.builder()
				.dataEntrySubTabIds(subTabIds)
				.recordId(mainRecordId)
				.build();
	}

	private static ImmutableSet<DataEntrySubTabId> extractSubTabIds(final Collection<CacheKey> keys)
	{
		final ImmutableSet<DataEntrySubTabId> subTabIds = keys.stream()
				.map(CacheKey::getSubTabId)
				.collect(ImmutableSet.toImmutableSet());
		return subTabIds;
	}

	@VisibleForTesting
	int getDataEntryRecordIdIndexSize()
	{
		return cacheIndex.size();
	}

	@Value(staticConstructor = "of")
	private static final class CacheKey
	{
		int mainRecordId;
		DataEntrySubTabId subTabId;
	}

	@ToString
	@VisibleForTesting
	static final class DataEntryRecordIdIndex implements CacheIndexDataAdapter<DataEntryRecordId, CacheKey, DataEntryRecord>
	{
		@Override
		public DataEntryRecordId extractDataItemId(final DataEntryRecord dataItem)
		{
			return dataItem.getId().get();
		}

		@Override
		public ImmutableSet<TableRecordReference> extractRecordRefs(final DataEntryRecord dataItem)
		{
			final DataEntryRecordId id = dataItem.getId().orElse(null);
			return id != null
					? ImmutableSet.of(TableRecordReference.of(I_DataEntry_Record.Table_Name, id))
					: ImmutableSet.of();
		}

		@Override
		public List<CacheKey> extractCacheKeys(final DataEntryRecord dataItem)
		{
			final CacheKey singleCacheKey = CacheKey.of(dataItem.getMainRecord().getRecord_ID(), dataItem.getDataEntrySubTabId());
			return ImmutableList.of(singleCacheKey);
		}
	}
}
