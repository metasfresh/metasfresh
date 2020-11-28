package de.metas.cache;

import java.util.Collection;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import lombok.NonNull;

/**
 * An extension of {@link CCache} which is used to store models indexed by their primary key.
 *
 * NOTE: we use Object as cache key because in future i think we will move from Integer keys to something else (UUID, String etc).
 *
 * @author tsa
 *
 * @param <V>
 */
public class IDCache<V> extends CCache<Object, V>
{
	private static final CachingKeysMapper<Object> KEYS_MAPPER = new CachingKeysMapper<Object>()
	{
		@Override
		public Collection<Object> computeCachingKeys(@NonNull final TableRecordReference tableRecordReference)
		{
			return ImmutableList.of(tableRecordReference.getRecord_ID());
		}
	};

	public IDCache(final String tableName, final String trxName, final int initialCapacity, final int expireMinutes)
	{
		this(tableName, trxName, initialCapacity, expireMinutes, CacheMapType.HashMap);
	}

	public IDCache(final String tableName,
			final String trxName,
			final int initialCapacity,
			final int expireMinutes,
			final CacheMapType cacheMapType)
	{
		super(
				buildCacheName(tableName, trxName),
				tableName,
				null, // additionalTableNamesToResetFor
				initialCapacity,
				expireMinutes,
				cacheMapType,
				KEYS_MAPPER,
				(CacheRemovalListener<Object, V>)null,
				(CacheAdditionListener<Object, V>)null);

		Check.assumeNotEmpty(tableName, "tableName not empty");
	}

	private static final String buildCacheName(final String tableName, final String trxName)
	{
		// Build cache name
		// we use "tableName" as cacheName because we want our cache to be reseted when a record in that table is changed.
		final String cacheName = new StringBuilder(tableName)
				.append("#TrxName=").append(trxName)
				.toString();

		return cacheName;
	}
}
