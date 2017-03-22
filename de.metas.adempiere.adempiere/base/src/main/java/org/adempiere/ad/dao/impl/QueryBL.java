package org.adempiere.ad.dao.impl;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderByBuilder;

public class QueryBL implements IQueryBL
{
	@Override
	public <T> IQueryBuilder<T> createQueryBuilder(final Class<T> modelClass, final Properties ctx, final String trxName)
	{
		return new QueryBuilder<>(modelClass, null)
				.setContext(ctx, trxName);
	}

	@Override
	public <T> IQueryBuilder<T> createQueryBuilder(final Class<T> modelClass, final Object contextProvider)
	{
		return new QueryBuilder<>(modelClass, null)
				.setContext(contextProvider);
	}

	@Override
	public <T> IQueryBuilder<T> createQueryBuilder(Class<T> modelClass, String tableName, Object contextProvider)
	{
		return new QueryBuilder<>(modelClass, tableName)
				.setContext(contextProvider);
	}

	@Override
	public IQueryBuilder<Object> createQueryBuilder(final String modelTableName, final Properties ctx, final String trxName)
	{
		return QueryBuilder.createForTableName(modelTableName)
				.setContext(ctx, trxName);
	}

	@Override
	public IQueryBuilder<Object> createQueryBuilder(final String modelTableName, final Object contextProvider)
	{
		return QueryBuilder.createForTableName(modelTableName)
				.setContext(contextProvider);
	}

	@Deprecated
	@Override
	public <T> IQueryOrderByBuilder<T> createQueryOrderByBuilder()
	{
		return new QueryOrderByBuilder<>();
	}

	@Override
	public <T> IQueryOrderByBuilder<T> createQueryOrderByBuilder(final Class<T> modelClass)
	{
		return new QueryOrderByBuilder<>();
	}

	@Override
	public IQueryOrderBy createSqlQueryOrderBy(final String orderBy)
	{
		return new SqlQueryOrderBy(orderBy);
	}

	@Override
	public <T> ICompositeQueryFilter<T> createCompositeQueryFilter(final Class<T> modelClass)
	{
		return new CompositeQueryFilter<>(modelClass);
	}

	@Override
	public ICompositeQueryFilter<Object> createCompositeQueryFilter(final String modelTableName)
	{
		return new CompositeQueryFilter<>(modelTableName);
	}

	@Override
	public <T> ICompositeQueryUpdater<T> createCompositeQueryUpdater(final Class<T> modelClass)
	{
		return new CompositeQueryUpdater<>(modelClass);
	}

	@Override
	public <T> String debugAccept(final IQueryFilter<T> filter, final T model)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("\n-------------------------------------------------------------------------------");
		sb.append("\nModel: " + model);
		final List<IQueryFilter<T>> filters = extractAllFilters(filter);
		for (final IQueryFilter<T> f : filters)
		{
			final boolean accept = f.accept(model);
			sb.append("\nFilter(accept=" + accept + "): " + f.toString());
		}
		sb.append("\n-------------------------------------------------------------------------------");

		return sb.toString();
	}

	private <T> List<IQueryFilter<T>> extractAllFilters(final IQueryFilter<T> filter)
	{
		if (filter == null)
		{
			return Collections.emptyList();
		}

		final List<IQueryFilter<T>> result = new ArrayList<>();

		result.add(filter);

		if (filter instanceof ICompositeQueryFilter)
		{
			final ICompositeQueryFilter<T> compositeFilter = (ICompositeQueryFilter<T>)filter;
			for (final IQueryFilter<T> f : compositeFilter.getFilters())
			{
				final List<IQueryFilter<T>> resultLocal = extractAllFilters(f);
				result.addAll(resultLocal);
			}
		}

		return result;
	}
}
