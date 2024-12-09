package de.metas.cache;

<<<<<<< HEAD
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
=======
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;

import java.util.HashSet;
import java.util.Set;

/**
 * An extension of {@link CCache} which is used to store models indexed by their primary key.
 * <p>
 * NOTE: we use Object as cache key because in future i think we will move from Integer keys to something else (UUID, String etc).
 *
 * @param <V>
 * @author tsa
 */
public class IDCache<V> extends CCache<Object, V>
{
	private static final CachingKeysMapper<Object> KEYS_MAPPER = tableRecordReference -> ImmutableList.of(tableRecordReference.getRecord_ID());
	public static final CacheLabel LABEL_MODEL_CACHE = CacheLabel.ofString("MODEL_CACHE");
	public static final CacheLabel LABEL_MODEL_CACHE_IN_TRANSACTION = CacheLabel.ofString("MODEL_CACHE_IN_TRANSACTION");

	public IDCache(final String tableName,
				   final String trxName,
				   final int initialCapacity,
				   final int expireMinutes,
				   final CacheMapType cacheMapType)
	{
		super(
				tableName + "#TrxName=" + trxName,
				tableName,
				null, // additionalTableNamesToResetFor
				computeAdditionalLabels(trxName),
				initialCapacity,
				null,
				expireMinutes,
				cacheMapType,
				KEYS_MAPPER,
				null,
				null);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		Check.assumeNotEmpty(tableName, "tableName not empty");
	}

<<<<<<< HEAD
	private static final String buildCacheName(final String tableName, final String trxName)
	{
		// Build cache name
		// we use "tableName" as cacheName because we want our cache to be reseted when a record in that table is changed.
		final String cacheName = new StringBuilder(tableName)
				.append("#TrxName=").append(trxName)
				.toString();

		return cacheName;
	}
=======
	private static Set<CacheLabel> computeAdditionalLabels(final String trxName)
	{
		final HashSet<CacheLabel> additionalLabels = new HashSet<>();
		additionalLabels.add(LABEL_MODEL_CACHE);
		if (trxName != null)
		{
			additionalLabels.add(LABEL_MODEL_CACHE_IN_TRANSACTION);
		}

		return additionalLabels;
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
