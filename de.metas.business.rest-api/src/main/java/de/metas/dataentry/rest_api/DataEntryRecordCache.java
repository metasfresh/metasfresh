package de.metas.dataentry.rest_api;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.dataentry.DataEntrySubTabId;
import de.metas.dataentry.data.DataEntryRecord;
import de.metas.dataentry.data.DataEntryRecordId;
import de.metas.dataentry.data.DataEntryRecordQuery;
import de.metas.dataentry.data.DataEntryRecordRepository;
import de.metas.dataentry.model.I_DataEntry_Record;
import de.metas.logging.LogManager;
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

	private final DataEntryRecordIdIndex dataEntryRecordIdIndex = new DataEntryRecordIdIndex();
	private final CCache<CacheKey, DataEntryRecord> cache = CCache.<CacheKey, DataEntryRecord> builder()
			.tableName(I_DataEntry_Record.Table_Name)
			.cacheMapType(CacheMapType.LRU)
			.initialCapacity(1000)
			.invalidationKeysMapper(dataEntryRecordIdIndex::getCacheKeysByTableRecordReference)
			.removalListener(dataEntryRecordIdIndex::remove)
			.build();

	public DataEntryRecordCache(@NonNull final DataEntryRecordRepository recordsRepo)
	{
		this.recordsRepo = recordsRepo;
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
		final List<DataEntryRecord> records = recordsRepo.list(query);
		if (records.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<CacheKey, DataEntryRecord> recordsMap = Maps.uniqueIndex(records, record -> extractCacheKey(record));

		dataEntryRecordIdIndex.add(records);

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

	private static CacheKey extractCacheKey(final DataEntryRecord record)
	{
		final int mainRecordId = record.getMainRecord().getRecord_ID();
		return CacheKey.of(mainRecordId, record.getDataEntrySubTabId());
	}

	//
	//
	//

	@VisibleForTesting
	int getDataEntryRecordIdIndexSize()
	{
		return dataEntryRecordIdIndex.size();
	}

	//
	//
	//

	@Value(staticConstructor = "of")
	private static final class CacheKey
	{
		int mainRecordId;
		DataEntrySubTabId subTabId;
	}

	@ToString
	@VisibleForTesting
	static final class DataEntryRecordIdIndex
	{
		private static final Logger logger = LogManager.getLogger(DataEntryRecordIdIndex.class);

		private final HashMultimap<DataEntryRecordId, CacheKey> map = HashMultimap.create();

		private synchronized Collection<CacheKey> getCacheKeys(final DataEntryRecordId entryRecordId)
		{
			final Set<CacheKey> cacheKeys = map.get(entryRecordId);
			logger.trace("Returning {} for {}", cacheKeys, entryRecordId);
			return cacheKeys;
		}

		private synchronized void add(final Multimap<? extends DataEntryRecordId, ? extends CacheKey> multimap)
		{
			logger.trace("Adding to index: {}", multimap);
			map.putAll(multimap);
		}

		private synchronized void remove(final DataEntryRecordId entryRecordId, final CacheKey key)
		{
			logger.trace("Removing pair from index: {}, {}", entryRecordId, key);
			map.remove(entryRecordId, key);
		}

		private synchronized int size()
		{
			return map.size();
		}

		//
		// ------------
		//

		public Collection<CacheKey> getCacheKeysByTableRecordReference(final TableRecordReference recordRef)
		{
			if (!I_DataEntry_Record.Table_Name.equals(recordRef.getTableName()))
			{
				logger.warn("Invalid {}. Returning no cache keys", recordRef);
				return ImmutableSet.of();
			}
			final DataEntryRecordId entryRecordId = DataEntryRecordId.ofRepoId(recordRef.getRecord_ID());

			return getCacheKeys(entryRecordId);
		}

		public void add(final Collection<DataEntryRecord> records)
		{
			if (records.isEmpty())
			{
				return;
			}

			final Multimap<? extends DataEntryRecordId, ? extends CacheKey> //
			multimap = records.stream()
					.collect(ImmutableSetMultimap.toImmutableSetMultimap(
							record -> record.getId().get(),
							record -> extractCacheKey(record)));

			add(multimap);
		}

		public void remove(final CacheKey key, final DataEntryRecord record)
		{
			final DataEntryRecordId entryRecordId = record.getId().get();
			remove(entryRecordId, key);
		}
	}
}
