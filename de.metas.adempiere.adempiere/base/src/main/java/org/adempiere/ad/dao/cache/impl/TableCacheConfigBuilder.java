package org.adempiere.ad.dao.cache.impl;

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


import org.adempiere.ad.dao.cache.IMutableTableCacheConfig;
import org.adempiere.ad.dao.cache.ITableCacheConfig;
import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.adempiere.ad.dao.cache.ITableCacheConfigBuilder;
import org.compiere.util.CCache.CacheMapType;

import de.metas.util.Check;

/* package */class TableCacheConfigBuilder implements ITableCacheConfigBuilder
{
	private final ModelCacheService cacheService;
	private ITableCacheConfig template;
	private final String tableName;
	private Boolean enabled = null;
	private TrxLevel trxLevel = null;
	private CacheMapType cacheMapType = CacheMapType.HashMap;
	private int initialCapacity = 50;
	private int maxCapacity = -1;
	private int expireMinutes = ITableCacheConfig.EXPIREMINUTES_Never;

	public TableCacheConfigBuilder(final ModelCacheService cacheService, final String tableName)
	{
		super();

		Check.assumeNotNull(cacheService, "cacheService not null");
		this.cacheService = cacheService;

		this.tableName = tableName;
	}

	@Override
	public void register()
	{
		final ITableCacheConfig cacheConfig = create();

		final boolean override = true;
		cacheService.addTableCacheConfig(cacheConfig, override);
	}

	@Override
	public void registerIfAbsent()
	{
		final ITableCacheConfig cacheConfig = create();

		final boolean override = false;
		cacheService.addTableCacheConfig(cacheConfig, override);
	}

	@Override
	public ITableCacheConfig create()
	{
		final IMutableTableCacheConfig cacheConfig = new MutableTableCacheConfig(tableName);
		cacheConfig.setEnabled(isEnabled());
		cacheConfig.setTrxLevel(getTrxLevel());
		cacheConfig.setCacheMapType(getCacheMapType());
		cacheConfig.setExpireMinutes(getExpireMinutes());
		cacheConfig.setInitialCapacity(getInitialCapacity());
		cacheConfig.setMaxCapacity(getMaxCapacity());

		return cacheConfig;
	}

	@Override
	public ITableCacheConfigBuilder setTemplate(final ITableCacheConfig template)
	{
		this.template = template;
		return this;
	}

	@Override
	public ITableCacheConfigBuilder setEnabled(final boolean enabled)
	{
		this.enabled = enabled;
		return this;
	}

	public boolean isEnabled()
	{
		if (enabled != null)
		{
			return enabled;
		}
		if (template != null)
		{
			return template.isEnabled();
		}

		throw new IllegalStateException("Cannot get IsEnabled");
	}

	@Override
	public ITableCacheConfigBuilder setTrxLevel(final TrxLevel trxLevel)
	{
		this.trxLevel = trxLevel;
		return this;
	}

	public TrxLevel getTrxLevel()
	{
		if (trxLevel != null)
		{
			return trxLevel;
		}
		if (template != null)
		{
			return template.getTrxLevel();
		}

		throw new IllegalStateException("Cannot get TrxLevel");
	}

	@Override
	public ITableCacheConfigBuilder setCacheMapType(final CacheMapType cacheMapType)
	{
		this.cacheMapType = cacheMapType;
		return this;
	}

	public CacheMapType getCacheMapType()
	{
		if (cacheMapType != null)
		{
			return cacheMapType;
		}
		if (template != null)
		{
			return template.getCacheMapType();
		}

		throw new IllegalStateException("Cannot get CacheMapType");
	}

	public int getInitialCapacity()
	{
		if (initialCapacity > 0)
		{
			return initialCapacity;
		}
		else if (template != null)
		{
			return template.getInitialCapacity();
		}

		throw new IllegalStateException("Cannot get InitialCapacity");
	}

	@Override
	public ITableCacheConfigBuilder setInitialCapacity(final int initialCapacity)
	{
		this.initialCapacity = initialCapacity;
		return this;
	}

	public int getMaxCapacity()
	{
		if (maxCapacity > 0)
		{
			return maxCapacity;
		}
		else if (template != null)
		{
			return template.getMaxCapacity();
		}

		return -1;
	}

	@Override
	public ITableCacheConfigBuilder setMaxCapacity(final int maxCapacity)
	{
		this.maxCapacity = maxCapacity;
		return this;
	}

	public int getExpireMinutes()
	{
		if (expireMinutes > 0 || expireMinutes == ITableCacheConfig.EXPIREMINUTES_Never)
		{
			return expireMinutes;
		}
		else if (template != null)
		{
			return template.getExpireMinutes();
		}

		throw new IllegalStateException("Cannot get ExpireMinutes");
	}

	@Override
	public ITableCacheConfigBuilder setExpireMinutes(final int expireMinutes)
	{
		this.expireMinutes = expireMinutes;
		return this;
	}

}
