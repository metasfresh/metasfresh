package org.adempiere.ad.dao.cache;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.dao.cache.ITableCacheConfig.TrxLevel;
import org.compiere.util.CCache.CacheMapType;

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
