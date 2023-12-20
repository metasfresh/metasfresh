/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cache;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import org.adempiere.util.lang.IAutoCloseable;

public final class ThreadLocalCacheController
{
	public static final ThreadLocalCacheController instance = new ThreadLocalCacheController();

	public static boolean computeAllowDisablingCache(@NonNull final String cacheName, @NonNull final ImmutableSet<CacheLabel> labels)
	{
		return !isApplicationDictionaryCache(cacheName, labels);
	}

	@NonNull
	private final ThreadLocal<Boolean> noCacheRef = new ThreadLocal<>();

	private ThreadLocalCacheController() {}

	public boolean isNoCache()
	{
		final Boolean noCache = noCacheRef.get();
		return noCache != null && noCache;
	}

	@NonNull
	public IAutoCloseable temporaryDisableCache()
	{
		final Boolean noCachePrev = noCacheRef.get();

		noCacheRef.set(Boolean.TRUE);

		return new IAutoCloseable()
		{
			boolean closed = false;

			@Override
			public void close()
			{
				if (closed)
				{
					return;
				}
				closed = true;

				noCacheRef.set(noCachePrev);
			}
		};
	}

	private static boolean isApplicationDictionaryCache(@NonNull final String cacheName, @NonNull final ImmutableSet<CacheLabel> labels)
	{
		final boolean anyApplicationDictionaryTableNames = labels.stream().anyMatch(CacheLabel::isApplicationDictionaryTableName);
		if (!anyApplicationDictionaryTableNames)
		{
			return false;
		}

		final boolean anyNonApplicationDictionaryTableNames = labels.stream().anyMatch(label -> isNonApplicationDictionaryTableName(label, cacheName));
		return !anyNonApplicationDictionaryTableNames;
	}

	private static boolean isNonApplicationDictionaryTableName(@NonNull final CacheLabel label, @NonNull final String cacheName)
	{
		return !label.equalsByName(cacheName) //ignore the label created from this.cacheName as it's not necessary a table name
				&& !label.containsNoTableNameMarker()
				&& !label.isApplicationDictionaryTableName();
	}
}