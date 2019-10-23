package de.metas.cache.model;

import de.metas.cache.CCache.CacheMapType;


public interface IMutableTableCacheConfig extends ITableCacheConfig
{
	@Override
	String getTableName();

	void setEnabled(final boolean enabled);

	@Override
	boolean isEnabled();

	void setTrxLevel(final TrxLevel trxLevel);

	@Override
	TrxLevel getTrxLevel();

	void setCacheMapType(CacheMapType cacheMapType);

	void setInitialCapacity(int initalCapacity);

	void setMaxCapacity(int maxCapacity);

	void setExpireMinutes(int expireMinutes);
}
