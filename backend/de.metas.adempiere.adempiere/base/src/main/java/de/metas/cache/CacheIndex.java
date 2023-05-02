package de.metas.cache;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Set;

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

/**
 * This class can help you caching domain objects for domain keys.
 * <p>
 * Example use case:
 * <li>you have a domain object that is based on C_BPartner and AD_User
 * <li>you have a cache for those domain objects
 * <li>if an AD_User record is updated in the UI, you want to invalidate in your cache <i>only</i> those instances that are based on that one AD_User record
 * <p>
 * To achive this goal, implement {@link CacheIndexDataAdapter} and create a {@link CacheIndex} instance.
 * To use that {@link CacheIndex} instance with your {@link CCache}, build the cache as follows:
 *
 * <pre>
 * CCache.&lt;YourKey, YourValue&gt;builder()
 * 		[...]
 * 		.invalidationKeysMapper(cacheIndex::computeCachingKeys)
 * 		.removalListener(cacheIndex::remove)
 * 		.additionListener(cacheIndex::add)
 * 		[...]
 * 		.build();
 * </pre>
 * <p>
 * Note: if you have a very straight 1:1 relation between your cache's key and value, then just implementing {@link CachingKeysMapper} might be sufficient for you.
 * <p>
 *
 * @param <DataItemId> data item's ID
 * @param <CacheKey> cache key that is used in your {@link CCache}. Might or might not be the same as {@code DataItemId}
 * @param <DataItem> the actually cached data item
 */
public final class CacheIndex<DataItemId, CacheKey, DataItem> implements CachingKeysMapper<CacheKey>
{
	public static <DataItemId, CacheKey, DataItem> CacheIndex<DataItemId, CacheKey, DataItem> of(@NonNull final CacheIndexDataAdapter<DataItemId, CacheKey, DataItem> adapter)
	{
		return new CacheIndex<>(adapter);
	}

	private static final Logger logger = LogManager.getLogger(CacheIndex.class);

	private final CacheIndexDataAdapter<DataItemId, CacheKey, DataItem> adapter;

	// NOTE: following maps shall be accessed from synchronized blocks
	private final SetMultimap<DataItemId, CacheKey> _dataItemId_to_cacheKey = HashMultimap.create();
	private final SetMultimap<TableRecordReference, DataItemId> _recordRef_to_dateItemId = HashMultimap.create();

	private CacheIndex(@NonNull final CacheIndexDataAdapter<DataItemId, CacheKey, DataItem> adapter)
	{
		this.adapter = adapter;
	}

	public synchronized int size()
	{
		return _dataItemId_to_cacheKey.size();
	}

	@Override
	public Collection<CacheKey> computeCachingKeys(@NonNull final TableRecordReference recordRef)
	{
		return getCacheKeys(recordRef);
	}

	private synchronized Collection<CacheKey> getCacheKeys(@NonNull final TableRecordReference recordRef)
	{
		final Set<DataItemId> dataItemIds = _recordRef_to_dateItemId.get(recordRef);
		if (dataItemIds.isEmpty())
		{
			logger.debug("computeCachingKeys: Returning no cache keys for {}", recordRef);
			return ImmutableSet.of();
		}

		final ImmutableSet<CacheKey> cacheKeys = dataItemIds.stream()
				.flatMap(dataItemId -> _dataItemId_to_cacheKey.get(dataItemId).stream())
				.collect(ImmutableSet.toImmutableSet());
		logger.debug("computeCachingKeys: Returning cacheKeys={} for {} (dataItemIds={})", cacheKeys, recordRef, dataItemIds);
		return cacheKeys;
	}

	public Collection<CacheKey> extractCacheKeys(final DataItem dataItem)
	{
		return adapter.extractCacheKeys(dataItem);
	}

	private DataItemId extractDataItemId(final DataItem dataItem)
	{
		return adapter.extractDataItemId(dataItem);
	}

	private Collection<TableRecordReference> extractRecordRefs(final DataItem dataItem)
	{
		return adapter.extractRecordRefs(dataItem);
	}

	/** Convenient method to be used as {@link CacheAdditionListener} */
	public void add(final CacheKey ignored, @NonNull final DataItem dataItem)
	{
		add(dataItem);
	}

	public void add(@NonNull final DataItem dataItem)
	{
		final DataItemId dataItemId = extractDataItemId(dataItem);
		final Collection<CacheKey> cacheKeys = extractCacheKeys(dataItem);
		final Collection<TableRecordReference> recordRefs = extractRecordRefs(dataItem);

		add(dataItemId, cacheKeys, recordRefs);
	}

	private synchronized void add(
			@NonNull final DataItemId dataItemId,
			@NonNull final Collection<CacheKey> cacheKeys,
			@NonNull final Collection<TableRecordReference> recordRefs)
	{
		logger.debug("Adding to index: {} -> {}", dataItemId, cacheKeys);
		_dataItemId_to_cacheKey.putAll(dataItemId, cacheKeys);

		logger.debug("Adding to index: {} -> {}", recordRefs, dataItemId);
		recordRefs.forEach(recordRef -> _recordRef_to_dateItemId.put(recordRef, dataItemId));
	}

	public void remove(final CacheKey cacheKey, final DataItem dataItem)
	{
		final DataItemId dataItemId = extractDataItemId(dataItem);
		final Collection<TableRecordReference> recordRefs = extractRecordRefs(dataItem);

		removeDataItemId(dataItemId, cacheKey, recordRefs);
	}

	private synchronized void removeDataItemId(
			final DataItemId dataItemId,
			final CacheKey cacheKey,
			final Collection<TableRecordReference> recordRefs)
	{
		logger.debug("Removing pair from index: {}, {}", dataItemId, cacheKey);
		_dataItemId_to_cacheKey.remove(dataItemId, cacheKey);

		logger.debug("Removing pairs from index: {}, {}", recordRefs, dataItemId);
		recordRefs.forEach(recordRef -> _recordRef_to_dateItemId.remove(recordRef, dataItemId));
	}

	@Override
	public boolean isResetAll(final TableRecordReference recordRef)
	{
		return adapter.isResetAll(recordRef);
	}
}
