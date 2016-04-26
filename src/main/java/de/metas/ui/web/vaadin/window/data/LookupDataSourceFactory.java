package de.metas.ui.web.vaadin.window.data;

import java.util.concurrent.ExecutionException;

import org.compiere.util.DisplayType;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class LookupDataSourceFactory
{
	private final LoadingCache<LookupDataSourceRequest, ILookupDataSource> lookupDataSources = CacheBuilder.newBuilder()
			.build(new CacheLoader<LookupDataSourceRequest, ILookupDataSource>()
			{

				@Override
				public ILookupDataSource load(final LookupDataSourceRequest dataSourceInfo)
				{
					return createLookupDataSouce(dataSourceInfo);
				}
			});

	public ILookupDataSource getLookupDataSource(final int windowNo, final String columnName, final int displayType, final int AD_Reference_Value_ID)
	{
		final LookupDataSourceRequest request = LookupDataSourceRequest.of(windowNo, columnName, displayType, AD_Reference_Value_ID);
		try
		{
			return lookupDataSources.get(request);
		}
		catch (final ExecutionException e)
		{
			throw Throwables.propagate(Throwables.getRootCause(e));
		}
	}

	private final ILookupDataSource createLookupDataSouce(final LookupDataSourceRequest request)
	{
		if (DisplayType.List == request.getDisplayType()
				|| DisplayType.Button == request.getDisplayType())
		{
			return new ADRefListLookupDataSource(request);
		}
		else
		{
			return new TableLookupDataSource(request);
		}
	}

}
