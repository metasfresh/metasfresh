package de.metas.ui.web.window.model.lookup;

import org.compiere.util.CCache;

import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;

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
		if (lookupDescriptor instanceof SqlLookupDescriptor)
		{
			final SqlLookupDescriptor sqlLookupDescriptor = (SqlLookupDescriptor)lookupDescriptor;

			final GenericSqlLookupDataSourceFetcher fetcher = GenericSqlLookupDataSourceFetcher.of(sqlLookupDescriptor);

			if (!sqlLookupDescriptor.isHighVolume()
					&& sqlLookupDescriptor.getSqlForFetchingExpression().getParameters().isEmpty()
					&& sqlLookupDescriptor.getPostQueryPredicate().getParameters().isEmpty())
			{
				return InMemoryLookupDataSource.of(fetcher);
			}

			return LocallyCachedLookupDataSource.of(fetcher);
		}
		else
		{
			throw new IllegalArgumentException("Unsupported lookup descriptor type: " + lookupDescriptor);
		}

	}
}
