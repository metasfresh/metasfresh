package de.metas.cache.model.impl;

import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.model.IMutableTableCacheConfig;
import de.metas.util.Check;

/* package */class MutableTableCacheConfig implements IMutableTableCacheConfig
{
	private final String tableName;
	private boolean enabled;
	private TrxLevel trxLevel;
	private CacheMapType cacheMapType;
	private int initialCapacity = 50;
	private int maxCapacity = -1;
	private int expireMinutes = EXPIREMINUTES_Never; // never expire

	public MutableTableCacheConfig(final String tableName)
	{
		super();

		Check.assumeNotEmpty(tableName, "tableName not empty");
		this.tableName = tableName;
		this.enabled = true; // enable it by default
		this.trxLevel = TrxLevel.All;
		this.cacheMapType = CacheMapType.HashMap;
	}

	@Override
	public String toString()
	{
		return "TableCacheConfig [tableName=" + tableName
				+ ", enabled=" + enabled
				+ ", trxLevel=" + trxLevel
				+ ", cacheMapType=" + cacheMapType
				+ ", initialCapacity=" + initialCapacity
				+ ", maxCapacity=" + maxCapacity
				+ ", expireMinutes=" + expireMinutes
				+ "]";
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public final boolean isEnabled()
	{
		return enabled;
	}

	@Override
	public void setEnabled(final boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	public TrxLevel getTrxLevel()
	{
		return this.trxLevel;
	}

	@Override
	public void setTrxLevel(final TrxLevel trxLevel)
	{
		Check.assumeNotNull(trxLevel, "trxLevel not null");
		this.trxLevel = trxLevel;
	}

	@Override
	public CacheMapType getCacheMapType()
	{
		return cacheMapType;
	}

	@Override
	public void setCacheMapType(CacheMapType cacheMapType)
	{
		Check.assumeNotNull(cacheMapType, "cacheMapType not null");
		this.cacheMapType = cacheMapType;
	}

	@Override
	public int getInitialCapacity()
	{
		return initialCapacity;
	}

	@Override
	public void setInitialCapacity(int initalCapacity)
	{
		Check.assume(initalCapacity > 0, "initialCapacity > 0");
		this.initialCapacity = initalCapacity;
	}

	@Override
	public int getMaxCapacity()
	{
		return maxCapacity;
	}

	@Override
	public void setMaxCapacity(int maxCapacity)
	{
		this.maxCapacity = maxCapacity;
	}

	@Override
	public int getExpireMinutes()
	{
		return expireMinutes;
	}

	@Override
	public void setExpireMinutes(int expireMinutes)
	{
		this.expireMinutes = expireMinutes > 0 ? expireMinutes : EXPIREMINUTES_Never;
	}

}
