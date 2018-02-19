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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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
		final QueryBuildContext<T> queryBuildCtx = new QueryBuildContext<>(builderImpl);
		return create(queryBuildCtx);
	}

	private final <T> IQuery<T> create(final QueryBuildContext<T> queryBuildCtx)
	{
		//
		// Case: we were asked to explode composite filters which are joined by ORs into UNIONs
		if (queryBuildCtx.isExplodeORJoinsToUnions())
		{
			IQuery<T> query = createQuery_ExplodingORJoinsToUnions_FirstLevelCompositeOR(queryBuildCtx);
			if (query == null)
			{
				query = createQuery_ExplodingORJoinsToUnions_SecondLevelCompositeOR(queryBuildCtx);
			}
			//
			if (query != null)
			{
				return query;
			}
		}

		//
		// Standard case / fallback
		{
			final IQueryFilter<T> mainFilter = queryBuildCtx.getMainFilter();
			final IPair<ISqlQueryFilter, IQueryFilter<T>> sqlAndNonSqlFilters = extractSqlAndNonSqlFilters(mainFilter);
			final ISqlQueryFilter sqlFilters = sqlAndNonSqlFilters.getLeft();
			final IQueryFilter<T> nonSqlFilters = sqlAndNonSqlFilters.getRight();
			return createQuery(queryBuildCtx, sqlFilters, nonSqlFilters);
		}

	}

	/**
	 * Explodes SQLs like:
	 * 
	 * <pre>
	 * SELECT .. FROM MyTable WHERE Value1=1 OR Value2=2 OR Value3=3
	 * </pre>
	 * 
	 * to
	 * 
	 * <pre>
	 * SELECT .. FROM MyTable WHERE Value1=1
	 * UNION
	 * SELECT .. FROM MyTable WHERE Value2=2
	 * UNION
	 * SELECT .. FROM MyTable WHERE Value3=3
	 * </pre>
	 */
	private <T> IQuery<T> createQuery_ExplodingORJoinsToUnions_FirstLevelCompositeOR(final QueryBuildContext<T> queryBuildCtx)
	{
		final ICompositeQueryFilter<T> mainFilterAsComposite = queryBuildCtx.getMainFilterAsCompositeOrNull();
		if (mainFilterAsComposite == null)
		{
			return null;
		}
		if (!mainFilterAsComposite.isJoinOr())
		{
			return null;
		}
		// we shall have at least 2 included filters (because else it does not make sense to explode)
		if (mainFilterAsComposite.getFiltersCount() < 2)
		{
			return null;
		}

		final List<IQueryFilter<T>> subFilters = mainFilterAsComposite.getFilters();

		//
		// For the main query we use only the first filter from our composite.
		final IQueryFilter<T> firstSubFilter = subFilters.get(0);
		final IPair<ISqlQueryFilter, IQueryFilter<T>> sqlAndNonSqlFilters = extractSqlAndNonSqlFilters(firstSubFilter);

		//
		// Create union queries, starting from second sub-filter.
		final List<IQuery<T>> queryUnions = new ArrayList<>();
		for (int subFilterIndex = 1, subFiltersCount = subFilters.size(); subFilterIndex < subFiltersCount; subFilterIndex++)
		{
			final IQueryFilter<T> subFilter = subFilters.get(subFilterIndex);
			final QueryBuildContext<T> subQueryCtx = queryBuildCtx.deriveForSubFilter(subFilter);
			final IQuery<T> unionQuery = create(subQueryCtx);
			queryUnions.add(unionQuery);
		}

		//
		// Create the query
		final ISqlQueryFilter sqlFilters = sqlAndNonSqlFilters.getLeft();
		final IQueryFilter<T> nonSqlFilters = sqlAndNonSqlFilters.getRight();
		return createQuery(queryBuildCtx, sqlFilters, nonSqlFilters)
				.addUnions(queryUnions, /* distinct */true);
	}

	/**
	 * Explodes SQLs like:
	 * 
	 * <pre>
	 * SELECT .. FROM MyTable WHERE AD_Client_ID=1 and IsActive=2 and (Value1=1 OR Value2=2 OR Value3=3)
	 * </pre>
	 * 
	 * to
	 * 
	 * <pre>
	 * SELECT .. FROM MyTable WHERE AD_Client_ID=1 and IsActive=2 and Value1=1
	 * UNION
	 * SELECT .. FROM MyTable WHERE AD_Client_ID=1 and IsActive=2 and Value2=2
	 * UNION
	 * SELECT .. FROM MyTable WHERE AD_Client_ID=1 and IsActive=2 and Value3=3
	 * </pre>
	 */
	private <T> IQuery<T> createQuery_ExplodingORJoinsToUnions_SecondLevelCompositeOR(final QueryBuildContext<T> queryBuildCtx)
	{
		final ICompositeQueryFilter<T> mainFilterAsComposite = queryBuildCtx.getMainFilterAsCompositeOrNull();
		if (mainFilterAsComposite == null)
		{
			return null;
		}
		if (mainFilterAsComposite.getFiltersCount() <= 0)
		{
			return null;
		}
		if(!mainFilterAsComposite.isJoinAnd())
		{
			return null;
		}

		//
		// Find second level composite-OR
		final List<IQueryFilter<T>> otherFilters = new ArrayList<>();
		ICompositeQueryFilter<T> compositeFilterToExplode = null;
		for (final IQueryFilter<T> filter : mainFilterAsComposite.getFilters())
		{
			if (compositeFilterToExplode == null
					&& filter instanceof ICompositeQueryFilter)
			{
				final ICompositeQueryFilter<T> compositeFilter = (ICompositeQueryFilter<T>)filter;
				if (compositeFilter.isJoinOr() && compositeFilter.getFiltersCount() >= 2)
				{
					compositeFilterToExplode = compositeFilter;
					continue;
				}
			}

			otherFilters.add(filter);
		}

		if (compositeFilterToExplode == null)
		{
			return null;
		}

		final List<IQueryFilter<T>> subFilters = compositeFilterToExplode.getFilters();

		//
		// For the main query we use only the first filter from our composite.
		final IQuery<T> query;
		{
			final IQueryFilter<T> firstSubFilter = subFilters.get(0);
			final IPair<ISqlQueryFilter, IQueryFilter<T>> sqlAndNonSqlFilters = extractSqlAndNonSqlFilters(new CompositeQueryFilter<T>(queryBuildCtx.getModelTableName())
					.addFilters(otherFilters)
					.addFilter(firstSubFilter));
			final ISqlQueryFilter sqlFilters = sqlAndNonSqlFilters.getLeft();
			final IQueryFilter<T> nonSqlFilters = sqlAndNonSqlFilters.getRight();
			query = createQuery(queryBuildCtx, sqlFilters, nonSqlFilters);
		}

		//
		// Create union queries, starting from second sub-filter.
		for (int subFilterIndex = 1, subFiltersCount = subFilters.size(); subFilterIndex < subFiltersCount; subFilterIndex++)
		{
			final IQueryFilter<T> subFilter = subFilters.get(subFilterIndex);
			final QueryBuildContext<T> subQueryCtx = queryBuildCtx.deriveForSubFilter(new CompositeQueryFilter<T>(queryBuildCtx.getModelTableName())
					.addFilters(otherFilters)
					.addFilter(subFilter));
			final IQuery<T> unionQuery = create(subQueryCtx);
			query.addUnion(unionQuery, /* distinct */true);
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
			sqlFilters = ISqlQueryFilter.cast(filter);
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

		public ICompositeQueryFilter<T> getMainFilterAsComposite()
		{
			return (ICompositeQueryFilter<T>)mainFilter;
		}

		public ICompositeQueryFilter<T> getMainFilterAsCompositeOrNull()
		{
			return mainFilter instanceof ICompositeQueryFilter ? (ICompositeQueryFilter<T>)mainFilter : null;
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
