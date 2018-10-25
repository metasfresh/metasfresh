package de.metas.cache;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.util.Check;

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
				cacheMapType);

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

	@Override
	public long resetForRecordId(final TableRecordReference recordRef)
	{
		//
		// Try matching by cache's TableName (if any)
		final String cacheTableName = getTableName();
		if (!cacheTableName.equals(recordRef.getTableName()))
		{
			return 0;
		}

		final int recordId = recordRef.getRecord_ID();
		final V valueOld = remove(recordId);
		return valueOld == null ? 0 : 1;
	}
}
