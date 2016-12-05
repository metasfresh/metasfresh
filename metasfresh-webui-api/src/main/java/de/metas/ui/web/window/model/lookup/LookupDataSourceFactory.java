package de.metas.ui.web.window.model.lookup;

import java.util.Comparator;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CCacheStats;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.LookupDescriptor;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class LookupDataSourceFactory
{
	public static final transient LookupDataSourceFactory instance = new LookupDataSourceFactory();

	private static final Logger logger = LogManager.getLogger(LookupDataSourceFactory.class);

	private final CCache<LookupDescriptor, LookupDataSource> lookupDataSourcesCache = new CCache<>("LookupDataSourcesCache", 300);

	private LookupDataSourceFactory()
	{
		super();
	}

	public LookupDataSource getLookupDataSource(final LookupDescriptor lookupDescriptor)
	{
		return lookupDataSourcesCache.getOrLoad(lookupDescriptor, () -> createLookupDataSource(lookupDescriptor));
	}

	private LookupDataSource createLookupDataSource(final LookupDescriptor lookupDescriptor)
	{
		Check.assumeNotNull(lookupDescriptor, "Parameter lookupDescriptor is not null");
		
		final LookupDataSourceFetcher fetcher = lookupDescriptor.getLookupDataSourceFetcher();

		final LookupDataSource lookupDataSource;
		if (!lookupDescriptor.isHighVolume() && !lookupDescriptor.hasParameters())
		{
			lookupDataSource = FullyCachedLookupDataSource.of(fetcher);
		}
		else
		{
			lookupDataSource = PartitionCachedLookupDataSource.of(fetcher);
		}

		logger.debug("Creating lookup data source for {}: {}", lookupDescriptor, lookupDataSource);
		return lookupDataSource;
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
}
