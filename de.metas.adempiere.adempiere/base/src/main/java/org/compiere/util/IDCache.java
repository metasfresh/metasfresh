package org.compiere.util;

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


import org.adempiere.util.Check;

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
				initialCapacity,
				expireMinutes,
				cacheMapType);

		Check.assumeNotEmpty(tableName, "tableName not empty");
	}

	private static final String buildCacheName(final String tableName, final String trxName)
	{
		// Build cache name
		// we use "tableName" as cacheName because we want our cache to be reseted when a record in that table is changed.
		final String cacheName = new StringBuilder(tableName)
				.append("#TrxName=").append(trxName)
				.toString();

		return cacheName;
	}

	@Override
	public int resetForRecordId(final String tableName, final Object recordId)
	{
		final String cacheTableName = getTableName();
		if (!cacheTableName.equals(tableName))
		{
			return 0;
		}

		final V valueOld = remove(recordId);
		return valueOld == null ? 0 : 1;
	}
}
