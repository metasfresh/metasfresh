package de.metas.cache;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;

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
	private static final ImmutableSet<CacheLabel> ADDITIONAL_LABELS = ImmutableSet.of(CacheLabel.ofString("MODEL_CACHE"));

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
				ADDITIONAL_LABELS,
				initialCapacity,
				null,
				expireMinutes,
				cacheMapType,
				KEYS_MAPPER,
				null,
				null);

		Check.assumeNotEmpty(tableName, "tableName not empty");
	}

}
