package de.metas.cache.model;

import de.metas.cache.CCache.CacheMapType;

/**
 * Table level caching configuration
 * 
 * @author tsa
 * 
 */
public interface ITableCacheConfig
{
	enum TrxLevel
	{
		/**
		 * Only cache when object is retrieved out of transaction.
		 */
		OutOfTransactionOnly,
		
		/**
		 * Only cache when object is retrieved within a transaction.
		 */
		InTransactionOnly,
		
		/**
		 * Always cache.
		 */
		All,
	};
	
	int EXPIREMINUTES_Never = 0;

	public String getTableName();

	public boolean isEnabled();

	/**
	 * Enable caching on transaction level (i.e. objects which are in a transaction)
	 * 
	 * @return true
	 */
	public TrxLevel getTrxLevel();
	
	public CacheMapType getCacheMapType();
	
	public int getInitialCapacity();
	
	public int getMaxCapacity();
	
	public int getExpireMinutes();
}
