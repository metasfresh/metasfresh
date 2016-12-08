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
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.IQuery;

public class QueryBuilderDAO extends AbstractQueryBuilderDAO
{
	private final PlainQueryBuilderDAO memDAO = new PlainQueryBuilderDAO();

	@Override
	public <T> IQuery<T> create(final IQueryBuilder<T> builder)
	{
		//
		// Handle in memory queries
		final Class<T> modelClass = builder.getModelClass();
		final POJOLookupMap memDatabase = POJOLookupMap.getInMemoryDatabaseForModel(modelClass);
		if (memDatabase != null)
		{
			return memDAO.create(builder);
		}

		return super.create(builder);
	}

	@Override
	protected final <T> IQuery<T> createQuery(final QueryBuildContext<T> queryBuildCtx, final ISqlQueryFilter sqlFilters, final IQueryFilter<T> nonSqlFilters)
	{
		final Properties ctx = queryBuildCtx.getCtx();
		final String sqlWhereClause;
		final List<Object> sqlParams;
		if (sqlFilters != null)
		{
			sqlWhereClause = sqlFilters.getSql();
			sqlParams = sqlFilters.getSqlParams(ctx);
		}
		else
		{
			sqlWhereClause = null;
			sqlParams = null;
		}

		final String trxName = queryBuildCtx.getTrxName();
		final Class<T> modelClass = queryBuildCtx.getModelClass();
		final String modelTableName = queryBuildCtx.getModelTableName();
		final IQueryOrderBy queryOrderBy = queryBuildCtx.getQueryOrderBy();
		final int queryLimit = queryBuildCtx.getQueryLimit();
		final int queryOnlySelectionId = queryBuildCtx.getQueryOnlySelectionId();
		final Map<String, Object> queryOptions = queryBuildCtx.getQueryOptions();
		return new TypedSqlQuery<T>(ctx, modelClass, modelTableName, sqlWhereClause, trxName)
				.setParameters(sqlParams)
				.setPostQueryFilter(nonSqlFilters)
				.setOrderBy(queryOrderBy)
				.setLimit(queryLimit)
				.setOnlySelection(queryOnlySelectionId)
				.setOptions(queryOptions);
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
