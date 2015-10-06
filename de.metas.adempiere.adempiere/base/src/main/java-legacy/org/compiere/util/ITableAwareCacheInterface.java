package org.compiere.util;

/**
 * An {@link CacheInterface} which is storing table records.
 * 
 * @author tsa
 *
 */
public interface ITableAwareCacheInterface extends CacheInterface
{
	/**
	 * Gets Cache Name.
	 * 
	 * In some cases this can be the same as Cache Table Name but is not guaranteed. If you want to get cache's tableName please use {@link #getTableName()}.
	 * 
	 * @return cache name
	 */
	String getName();

	/**
	 * Gets cache TableName (if any)
	 * 
	 * @return tableName or null
	 */
	String getTableName();

	/**
	 * Invalidate the cache for given tableName and cache key (which in most of the cases is the record ID).
	 * 
	 * @param tableName
	 * @param key cache key
	 * @return how many cache entries were invalidated
	 */
	int resetForRecordId(String tableName, Object key);
}
