package de.metas.cache;

import java.util.Collection;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;

import de.metas.logging.LogManager;
import lombok.Getter;
import lombok.NonNull;

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
 * @param <RK> "actual" key/id of the cached data ("record key")
 * @param <CK> cache key that is used in your {@link CCache}. Might or migh not be the same as {@code RK}
 * @param <V> the actually cached data ("value")
 */
public final class CacheIndex<RK, CK, V> implements CachingKeysMapper<CK>
{
	private static final Logger logger = LogManager.getLogger(CacheIndex.class);

	private final HashMultimap<RK, CK> map = HashMultimap.create();

	@Getter
	private final CacheIndexDataAdapter<RK, CK, V> adapter;

	public CacheIndex(@NonNull final CacheIndexDataAdapter<RK, CK, V> adapter)
	{
		this.adapter = adapter;
	}

	private synchronized Collection<CK> getCKs(final RK entryRecordId)
	{
		final Set<CK> CKs = map.get(entryRecordId);
		logger.trace("Returning {} for {}", CKs, entryRecordId);
		return CKs;
	}

	private synchronized void add(final Multimap<? extends RK, ? extends CK> multimap)
	{
		logger.trace("Adding to index: {}", multimap);
		map.putAll(multimap);
	}

	private synchronized void removeRecordKey(final RK entryRecordId, final CK key)
	{
		logger.trace("Removing pair from index: {}, {}", entryRecordId, key);
		map.remove(entryRecordId, key);
	}

	public synchronized int size()
	{
		return map.size();
	}

	//
	// ------------
	//

	@Override
	public Collection<CK> computeCachingKeys(@NonNull final TableRecordReference recordRef)
	{
		final RK recordId = adapter.extractRK(recordRef);
		return getCKs(recordId);
	}

	/** @return the caching keys for the given record. */
	public Collection<CK> extractCKs(@NonNull final V record)
	{
		return adapter.extractCKs(record);
	}

	public RK extractRK(@NonNull final V record)
	{
		return adapter.extractRK(record);
	}

	public RK extractRK(@NonNull final TableRecordReference recordRef)
	{
		return adapter.extractRK(recordRef);
	}

	public void add(@NonNull final CK ignored, @NonNull final V record)
	{
		final ImmutableSetMultimap.Builder<RK, CK> multimap = ImmutableSetMultimap.builder();

		multimap.putAll(
				adapter.extractRK(record),
				adapter.extractCKs(record));

		add(multimap.build());
	}

	public void remove(final CK key, final V record)
	{
		final RK recordKey = adapter.extractRK(record);
		removeRecordKey(recordKey, key);
	}
}
