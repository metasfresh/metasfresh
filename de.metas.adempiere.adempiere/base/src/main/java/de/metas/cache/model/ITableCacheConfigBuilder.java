package de.metas.cache.model;

import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.model.ITableCacheConfig.TrxLevel;

public interface ITableCacheConfigBuilder
{
	/** Creates and register the cache config */
	void register();

	/** Creates and register the cache config, ONLY if there is no configuration already registered for this table */
	void registerIfAbsent();

	/** Creates and returns the cache config */
	ITableCacheConfig create();

	ITableCacheConfigBuilder setTemplate(ITableCacheConfig template);

	ITableCacheConfigBuilder setEnabled(final boolean enabled);

	/**
	 * When you do {@link TrxLevel#InTransactionOnly}, then distributed cache invalidation is pointless.<br>
	 * Using trx-only caching can make sense if some short lived is queried over and over again within one transaction.
	 * 
	 * @param trxLevel
	 * @return
	 */
	ITableCacheConfigBuilder setTrxLevel(final TrxLevel trxLevel);

	ITableCacheConfigBuilder setCacheMapType(CacheMapType cacheMapType);

	ITableCacheConfigBuilder setInitialCapacity(int initialCapacity);

	ITableCacheConfigBuilder setMaxCapacity(int maxCapacity);

	/**
	 * 
	 * @param expireMinutes ..or {@link ITableCacheConfig#EXPIREMINUTES_Never}
	 * @return
	 */
	ITableCacheConfigBuilder setExpireMinutes(int expireMinutes);
}
