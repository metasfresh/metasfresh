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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderDAO;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.IQuery;

public class QueryBuilderDAO implements IQueryBuilderDAO
{
	private final PlainQueryBuilderDAO memDAO = new PlainQueryBuilderDAO();

	@Override
	public <T> IQuery<T> create(final IQueryBuilder<T> builder)
	{
		final Class<T> modelClass = builder.getModelClass();

		final POJOLookupMap memDatabase = POJOLookupMap.getInMemoryDatabaseForModel(modelClass);
		if (memDatabase != null)
		{
			return memDAO.create(builder);
		}

		final QueryBuilder<T> builderImpl = (QueryBuilder<T>)builder;

		final Properties ctx = builderImpl.getCtx();
		final String trxName = builderImpl.getTrxName();

		final IQueryFilter<T> filter = builderImpl.createFilter();

		//
		// Get the SQL and nonSQL part of the filter
		final ISqlQueryFilter sqlFilter;
		final IQueryFilter<T> nonSqlFilters;
		if (filter instanceof ICompositeQueryFilter)
		{
			final ICompositeQueryFilter<T> compositeFilter = (ICompositeQueryFilter<T>)filter;
			sqlFilter = compositeFilter.asPartialSqlQueryFilter();
			nonSqlFilters = compositeFilter.asPartialNonSqlFilterOrNull();
		}
		else if (filter instanceof ISqlQueryFilter)
		{
			sqlFilter = (ISqlQueryFilter)filter;
			nonSqlFilters = null;
		}
		else
		{
			sqlFilter = null;
			nonSqlFilters = filter;
		}

		//
		// Build SQL Where Clause and SQL Params
		final String sqlWhereClause;
		final List<Object> sqlParams;
		if (sqlFilter != null)
		{
			sqlWhereClause = sqlFilter.getSql();
			sqlParams = sqlFilter.getSqlParams(ctx);
		}
		else
		{
			sqlWhereClause = null;
			sqlParams = null;
		}

		return new TypedSqlQuery<T>(ctx, modelClass, sqlWhereClause, trxName)
				.setParameters(sqlParams)
				.setPostQueryFilter(nonSqlFilters)
				.setOrderBy(builderImpl.createQueryOrderBy())
				.setLimit(builderImpl.getLimit())
				.setOnlySelection(builderImpl.getSelectionId())
		//
		;
	}

	@Override
	public <T> String getSql(final Properties ctx, final ICompositeQueryFilter<T> filter, final List<Object> sqlParamsOut)
	{
		Check.assumeNotNull(ctx, "ctx not null");
		Check.assumeNotNull(filter, "filter not null");

		// Make sure given filter does not have nonSQL parts
		final IQueryFilter<T> nonSqlFilters = filter.asPartialNonSqlFilterOrNull();
		if (nonSqlFilters != null)
		{
			throw new DBException("Cannot convert filter to SQL because it contains nonSQL parts too: " + filter);
		}
		
		final ISqlQueryFilter sqlFilter = filter.asPartialSqlQueryFilter();
		final String filterSql = sqlFilter.getSql();
		final List<Object> filterSqlParams = sqlFilter.getSqlParams(ctx);
		
		if (!Check.isEmpty(filterSqlParams))
		{
			// NOTE: we enforce the sqlParamsOut to be not null, only if we really have some parameters to append
			Check.assumeNotNull(sqlParamsOut, "sqlParamsOut not null");
			sqlParamsOut.addAll(filterSqlParams);
		}
		return filterSql;
	}

}
