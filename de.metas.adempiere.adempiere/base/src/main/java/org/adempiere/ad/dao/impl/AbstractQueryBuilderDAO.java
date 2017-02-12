package org.adempiere.ad.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderDAO;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.IQuery;

import com.google.common.collect.ImmutableMap;

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

/**
 * Base implementation for {@link IQueryBuilderDAO}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class AbstractQueryBuilderDAO implements IQueryBuilderDAO
{
	@Override
	@OverridingMethodsMustInvokeSuper
	public <T> IQuery<T> create(final IQueryBuilder<T> builder)
	{
		final QueryBuilder<T> builderImpl = (QueryBuilder<T>)builder;
		final QueryBuildContext<T> queryBuildCtx = new QueryBuildContext<T>(builderImpl);
		return create(queryBuildCtx);
	}

	private final <T> IQuery<T> create(final QueryBuildContext<T> queryBuildCtx)
	{
		final boolean explodeORJoinsToUnions = queryBuildCtx.isExplodeORJoinsToUnions();
		final IQueryFilter<T> mainFilter = queryBuildCtx.getMainFilter();
		final ICompositeQueryFilter<T> mainFilterAsComposite = mainFilter instanceof ICompositeQueryFilter ? (ICompositeQueryFilter<T>)mainFilter : null;

		//
		// Get the SQL and nonSQL part of the filter
		// Explode composite filters which are JOIN=OR to unions (if asked).
		final IPair<ISqlQueryFilter, IQueryFilter<T>> sqlAndNonSqlFilters;
		final List<IQuery<T>> queryUnions = new ArrayList<>();

		//
		// Case: we were asked to explode composite filters which are joined by ORs into UNIONs
		if (mainFilterAsComposite != null // we deal with a composite filter
				&& explodeORJoinsToUnions // we were asked to explode the filter
				&& mainFilterAsComposite.isJoinOr() // our composite it's joining it's filters by OR
				&& mainFilterAsComposite.getFiltersCount() >= 2 // we have at least 2 included filters (because else it does not make sense to explode)
		)
		{
			final List<IQueryFilter<T>> subFilters = mainFilterAsComposite.getFilters();

			//
			// For the main query we use only the first filter from our composite.
			final IQueryFilter<T> firstSubFilter = subFilters.get(0);
			sqlAndNonSqlFilters = extractSqlAndNonSqlFilters(firstSubFilter);

			//
			// Create union queries, starting from second sub-filter.
			for (int subFilterIndex = 1, subFiltersCount = subFilters.size(); subFilterIndex < subFiltersCount; subFilterIndex++)
			{
				final IQueryFilter<T> subFilter = subFilters.get(subFilterIndex);
				final QueryBuildContext<T> subQueryCtx = queryBuildCtx.deriveForSubFilter(subFilter);
				final IQuery<T> unionQuery = create(subQueryCtx);
				queryUnions.add(unionQuery);
			}
		}
		//
		// Standard case
		else
		{
			sqlAndNonSqlFilters = extractSqlAndNonSqlFilters(mainFilter);
		}

		//
		// Create the query
		final ISqlQueryFilter sqlFilters = sqlAndNonSqlFilters.getLeft();
		final IQueryFilter<T> nonSqlFilters = sqlAndNonSqlFilters.getRight();
		final IQuery<T> query = createQuery(queryBuildCtx, sqlFilters, nonSqlFilters);

		//
		// Add the unions to query
		for (final IQuery<T> queryUnion : queryUnions)
		{
			final boolean distinct = true;
			query.addUnion(queryUnion, distinct);
		}

		return query;
	}

	/**
	 * Extracts the {@link ISqlQueryFilter} part and the nonSQL part of given filter.
	 *
	 * @param filter
	 * @return pair of SQL filter and nonSQL filter
	 */
	protected <T> IPair<ISqlQueryFilter, IQueryFilter<T>> extractSqlAndNonSqlFilters(final IQueryFilter<T> filter)
	{
		final ISqlQueryFilter sqlFilters;
		final IQueryFilter<T> nonSqlFilters;
		if (filter instanceof ICompositeQueryFilter)
		{
			final ICompositeQueryFilter<T> compositeSubFilter = (ICompositeQueryFilter<T>)filter;
			sqlFilters = compositeSubFilter.asPartialSqlQueryFilter();
			nonSqlFilters = compositeSubFilter.asPartialNonSqlFilterOrNull();
		}
		else if (filter instanceof ISqlQueryFilter)
		{
			sqlFilters = (ISqlQueryFilter)filter;
			nonSqlFilters = null;
		}
		else
		{
			sqlFilters = null;
			nonSqlFilters = filter;
		}

		return ImmutablePair.of(sqlFilters, nonSqlFilters);
	}

	/**
	 * Actually creates the {@link IQuery} instance for given context.
	 *
	 * @param queryBuildCtx
	 * @param sqlFilters SQL filters part
	 * @param nonSqlFilters nonSQL filters part
	 * @return
	 */
	protected abstract <T> IQuery<T> createQuery(final QueryBuildContext<T> queryBuildCtx, final ISqlQueryFilter sqlFilters, final IQueryFilter<T> nonSqlFilters);

	/**
	 * Context object used while the {@link IQuery} is generated, just to pass the common parameters from one method to the other.
	 *
	 * @param <T> model type
	 */
	static final class QueryBuildContext<T>
	{
		private final Properties ctx;
		private final String trxName;
		private final Class<T> modelClass;
		private final String modelTableName;
		private final ImmutableMap<String, Object> queryOptions;
		//
		private final int queryOnlySelectionId;
		private IQueryFilter<T> mainFilter;
		//
		private IQueryOrderBy queryOrderBy;
		private int queryLimit;
		//
		private final boolean explodeORJoinsToUnions;

		public QueryBuildContext(final QueryBuilder<T> builderImpl)
		{
			super();
			this.ctx = builderImpl.getCtx();
			this.trxName = builderImpl.getTrxName();
			this.modelClass = builderImpl.getModelClass();
			this.modelTableName = builderImpl.getModelTableName();
			this.queryOptions = ImmutableMap.copyOf(builderImpl.getOptions());
			//
			this.queryOnlySelectionId = builderImpl.getSelectionId();
			this.mainFilter = builderImpl.createFilter();
			//
			this.queryOrderBy = builderImpl.createQueryOrderBy();
			this.queryLimit = builderImpl.getLimit();
			//
			this.explodeORJoinsToUnions = Boolean.TRUE.equals(queryOptions.get(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions));
		}

		private QueryBuildContext(final QueryBuildContext<T> parent)
		{
			super();
			// Final attributes:
			this.ctx = parent.ctx;
			this.trxName = parent.trxName;
			this.modelClass = parent.modelClass;
			this.modelTableName = parent.modelTableName;
			this.queryOptions = parent.queryOptions;
			//
			this.queryOnlySelectionId = parent.queryOnlySelectionId;
			this.mainFilter = parent.mainFilter;
			//
			this.queryOrderBy = parent.queryOrderBy;
			this.queryLimit = parent.queryLimit;
			//
			this.explodeORJoinsToUnions = parent.explodeORJoinsToUnions;
		}

		public QueryBuildContext<T> deriveForSubFilter(final IQueryFilter<T> subFilter)
		{
			final QueryBuildContext<T> subQueryCtx = new QueryBuildContext<>(this);
			subQueryCtx.mainFilter = subFilter;
			subQueryCtx.queryOrderBy = null;
			subQueryCtx.queryLimit = IQuery.NO_LIMIT;

			return subQueryCtx;
		}

		public Properties getCtx()
		{
			return ctx;
		}

		public String getTrxName()
		{
			return trxName;
		}

		public Class<T> getModelClass()
		{
			return modelClass;
		}

		public String getModelTableName()
		{
			return modelTableName;
		}

		public ImmutableMap<String, Object> getQueryOptions()
		{
			return queryOptions;
		}

		public int getQueryOnlySelectionId()
		{
			return queryOnlySelectionId;
		}

		public IQueryFilter<T> getMainFilter()
		{
			return mainFilter;
		}

		public IQueryOrderBy getQueryOrderBy()
		{
			return queryOrderBy;
		}

		public void setQueryOrderBy(IQueryOrderBy queryOrderBy)
		{
			this.queryOrderBy = queryOrderBy;
		}

		public int getQueryLimit()
		{
			return queryLimit;
		}

		public void setQueryLimit(int queryLimit)
		{
			this.queryLimit = queryLimit;
		}

		public boolean isExplodeORJoinsToUnions()
		{
			return explodeORJoinsToUnions;
		}
	}

}
