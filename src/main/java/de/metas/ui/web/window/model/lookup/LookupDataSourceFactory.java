package de.metas.ui.web.window.model.lookup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.util.GuavaCollectors;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CCacheStats;
import org.slf4j.Logger;

import com.google.common.base.Predicates;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class LookupDataSourceFactory
{
	public static final transient LookupDataSourceFactory instance = new LookupDataSourceFactory();

	private static final Logger logger = LogManager.getLogger(LookupDataSourceFactory.class);

	private final CCache<LookupDescriptor, LookupDataSource> lookupDataSourcesCache = new CCache<>("LookupDataSourcesCache", 300);
	private final ConcurrentHashMap<String, CacheInvalidationGroup> cacheInvalidationGroupsByTableName = new ConcurrentHashMap<>();

	private LookupDataSourceFactory()
	{
	}

	public LookupDataSource searchInTableLookup(final String tableName)
	{
		final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.searchInTable(tableName)
				.provideForScope(LookupScope.DocumentField);
		return getLookupDataSource(lookupDescriptor);
	}

	public LookupDataSource getLookupDataSource(final LookupDescriptor lookupDescriptor)
	{
		return lookupDataSourcesCache.getOrLoad(lookupDescriptor, () -> createLookupDataSource(lookupDescriptor));
	}

	public LookupDataSource createLookupDataSource(@NonNull final LookupDescriptor lookupDescriptor)
	{
		final LookupDataSourceFetcher fetcher = lookupDescriptor.getLookupDataSourceFetcher();

		final LookupDataSource lookupDataSource;
		if (fetcher.isCached())
		{
			lookupDataSource = LookupDataSourceAdapter.of(fetcher);
		}
		else if (!lookupDescriptor.isHighVolume() && !lookupDescriptor.hasParameters())
		{
			lookupDataSource = FullyCachedLookupDataSource.of(fetcher);
		}
		else
		{
			final CachedLookupDataSourceFetcherAdapter cachedFetcher = CachedLookupDataSourceFetcherAdapter.of(fetcher);
			lookupDataSource = LookupDataSourceAdapter.of(cachedFetcher);
		}

		//
		// Register cache invalidation on depending table changed
		lookupDescriptor.getDependsOnTableNames()
				.stream()
				.map(this::getCacheInvalidationGroupByTableName)
				.forEach(cacheInvalidationGroup -> cacheInvalidationGroup.addLookupDataSource(lookupDataSource));

		logger.debug("Creating lookup data source for {}: {}", lookupDescriptor, lookupDataSource);
		return lookupDataSource;
	}

	private CacheInvalidationGroup getCacheInvalidationGroupByTableName(final String tableName)
	{
		return cacheInvalidationGroupsByTableName.computeIfAbsent(tableName, CacheInvalidationGroup::new);
	}

	public void cacheInvalidateOnRecordsChanged(final Set<String> tableNames)
	{
		tableNames.stream()
				.map(cacheInvalidationGroupsByTableName::get)
				.filter(Predicates.notNull())
				.forEach(CacheInvalidationGroup::cacheInvalidate);
	}

	public List<CCacheStats> getCacheStats()
	{
		return lookupDataSourcesCache
				.values()
				.stream()
				.flatMap(dataSource -> dataSource.getCacheStats().stream())
				.distinct()
				.sorted(Comparator.comparing(CCacheStats::getName))
				.collect(GuavaCollectors.toImmutableList());
	}

	@ToString(exclude = "lookupDataSources")
	private static final class CacheInvalidationGroup
	{
		private final String tableName;
		private final List<WeakReference<LookupDataSource>> lookupDataSources = new ArrayList<>();

		public CacheInvalidationGroup(final String tableName)
		{
			this.tableName = tableName;
		}

		public void addLookupDataSource(@NonNull final LookupDataSource lookupDataSource)
		{
			synchronized (lookupDataSources)
			{
				lookupDataSources.add(new WeakReference<>(lookupDataSource));
			}
		}

		private List<LookupDataSource> purgeAndGet()
		{
			synchronized (lookupDataSources)
			{
				final List<LookupDataSource> result = new ArrayList<>(lookupDataSources.size());
				for (final Iterator<WeakReference<LookupDataSource>> it = lookupDataSources.iterator(); it.hasNext();)
				{
					final LookupDataSource lookupDataSource = it.next().get();
					if (lookupDataSource == null)
					{
						it.remove();
						continue;
					}

					result.add(lookupDataSource);
				}

				return result;
			}

		}

		public void cacheInvalidate()
		{
			for (final LookupDataSource lookupDataSource : purgeAndGet())
			{
				try
				{
					lookupDataSource.cacheInvalidate();
					logger.debug("Cache invalidated {} on {} record changed", lookupDataSource, tableName);
				}
				catch (final Exception ex)
				{
					logger.warn("Failed invalidating {}. Skipped", lookupDataSource, ex);
				}
			}
		}
	}
}
