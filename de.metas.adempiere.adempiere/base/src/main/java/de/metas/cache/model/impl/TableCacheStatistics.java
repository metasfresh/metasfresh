package de.metas.cache.model.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import de.metas.cache.model.ITableCacheConfig;
import de.metas.cache.model.ITableCacheStatistics;

public class TableCacheStatistics implements ITableCacheStatistics
{
	private Date startDate;
	private final String tableName;
	private final AtomicLong hitCount = new AtomicLong(0);
	private final AtomicLong hitInTrxCount = new AtomicLong(0);
	private final AtomicLong missCount = new AtomicLong(0);
	private final AtomicLong missInTrxCount = new AtomicLong(0);
	private final ITableCacheConfig cacheConfig;

	public TableCacheStatistics(final String tableName, final ITableCacheConfig cacheConfig)
	{
		super();
		this.tableName = tableName;

		// cacheConfig can be null
		this.cacheConfig = cacheConfig;

		reset();
	}

	@Override
	public void reset()
	{
		startDate = new Date();
		hitCount.set(0);
		hitInTrxCount.set(0);
		missCount.set(0);
		missInTrxCount.set(0);
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public Date getStartDate()
	{
		return startDate;
	}

	@Override
	public long getHitCount()
	{
		return hitCount.longValue();
	}

	@Override
	public void incrementHitCount()
	{
		hitCount.incrementAndGet();
	}

	@Override
	public long getHitInTrxCount()
	{
		return hitInTrxCount.longValue();
	}

	@Override
	public void incrementHitInTrxCount()
	{
		hitInTrxCount.incrementAndGet();
	}

	@Override
	public long getMissCount()
	{
		return missCount.longValue();
	}

	@Override
	public void incrementMissCount()
	{
		missCount.incrementAndGet();
	}

	@Override
	public long getMissInTrxCount()
	{
		return missInTrxCount.longValue();
	}

	@Override
	public void incrementMissInTrxCount()
	{
		missInTrxCount.incrementAndGet();
	}

	@Override
	public boolean isCacheEnabled()
	{
		if (cacheConfig != null)
		{
			return cacheConfig.isEnabled();
		}

		// if no caching config is provided, it means we are dealing with an overall statistics
		// so we consider caching as Enabled
		return true;
	}
}
