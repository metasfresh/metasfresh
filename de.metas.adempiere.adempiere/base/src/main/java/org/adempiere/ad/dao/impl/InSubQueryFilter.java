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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Filters out only records which are present in sub-query.
 *
 * @author tsa
 *
 * @param <T>
 */
public class InSubQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	public static <T> InSubQueryFilter<T> of(final ModelColumn<T, ?> column, final String subQueryColumnName, final IQuery<?> subQuery)
	{
		final Builder<T> builder = builder();
		return builder
				.tableName(column.getTableName())
				.subQuery(subQuery)
				.matchingColumnNames(column.getColumnName(), subQueryColumnName)
				.build();
	}

	public static <T> Builder<T> builder()
	{
		return new Builder<>();
	}

	private final String tableName;
	private final IQuery<?> subQuery;
	private final List<ColumnNamePair> matchers;

	//
	private boolean sqlBuilt = false;
	private String sqlWhereClause = null;
	private List<Object> sqlParams = null;
	private List<Map<ColumnNamePair, Object>> _subQueryValues = null;

	private static final transient Logger logger = LogManager.getLogger(InSubQueryFilter.class);

	private InSubQueryFilter(final Builder<T> builder)
	{
		Check.assumeNotEmpty(builder.tableName, "builder.tableName is not empty");
		Check.assumeNotNull(builder.subQuery, "Parameter builder.subQuery is not null");
		Check.assumeNotEmpty(builder.matchers, "builder.matchers is not empty");

		this.tableName = builder.tableName;
		this.subQuery = builder.subQuery;
		this.matchers = ImmutableList.copyOf(builder.matchers);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", tableName)
				.add("subQuery", subQuery)
				.add("matchers", matchers)
				//
				.add("sqlBuilt", sqlBuilt)
				.add("sqlWhereClause", sqlWhereClause)
				.add("sqlParams", sqlParams)
				.add("_subQueryValues", _subQueryValues)
				.toString();
	}

	@Override
	public String getSql()
	{
		buildSql();
		return sqlWhereClause;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		buildSql();
		return sqlParams;
	}

	/**
	 * Build the sub Query SQL using EXISTS if the table names of the query and the subQuery are different or using IN otherwise
	 */
	private final void buildSql()
	{
		if (sqlBuilt)
		{
			return;
		}

		final TypedSqlQuery<?> subQueryImpl = TypedSqlQuery.cast(subQuery);

		//
		// Decide if we will render the SQL using "EXISTS (...)" (preferred option, at least of postresql) or "IN (...)".
		final boolean useIN;
		if (tableName == null || tableName.equals(subQuery.getTableName()))
		{
			// task 08957
			// In case the sub query is done on the same table as the parent table, we can't write it with "EXISTS"
			// Because we don't have aliases.
			// Therefore, in such cases, we have to keep the writing with "IN"
			// logDevelopmentWarn("The query has to be written with IN instead of EXISTS because the tablename is the same for both query and sub query.");
			useIN = true;
		}
		else if (subQueryImpl.hasLimitOrOffset())
		{
			// In case the sub-query is using LIMIT/OFFSET we cannot go with "EXISTS" because that one will not apply the LIMIT and ORDER BY, so we will get the wrong data.
			useIN = true;
		}
		else
		{
			useIN = false;
		}

		//
		// Build the final SQL and SQL parameters list
		final String subWhereClause;
		if (useIN)
		{
			subWhereClause = buildSql_UsingIN(subQueryImpl);
		}
		else
		{
			// For all the other cases it is more efficient to write the sub-query using "EXISTS"
			subWhereClause = buildSql_UsingEXISTS(subQueryImpl);
		}
		final List<Object> subQueryParams = subQueryImpl.getParametersEffective();

		//
		// Assign them
		this.sqlWhereClause = subWhereClause.toString();
		this.sqlParams = subQueryParams;
		this.sqlBuilt = true;
	}

	/**
	 * Build the filter SQL using EXISTS.
	 *
	 * e.g. EXISTS (SELECT 1 FROM SubTable WHERE ParentTable.JoinColumn=SubTable.JoinColumn AND .....)
	 *
	 * @param subQueryImpl
	 * @return sql
	 */
	private String buildSql_UsingEXISTS(final TypedSqlQuery<?> subQueryImpl)
	{
		//
		// Build the new sub-query's SELECT FROM
		final StringBuilder subQuerySelectClause = new StringBuilder("SELECT 1 ");
		final StringBuilder subQueryFromClause = new StringBuilder(" FROM ").append(subQueryImpl.getTableName());

		final boolean subQueryUseOrderByClause = false;

		//
		// Build the new sub-query's where clause
		final StringBuilder subQueryWhereClauseNew = new StringBuilder();

		// Linking where clause
		for (final ColumnNamePair matcher : matchers)
		{
			if (subQueryWhereClauseNew.length() > 0)
			{
				subQueryWhereClauseNew.append(" and ");
			}
			subQueryWhereClauseNew
					.append(subQuery.getTableName() + "." + matcher.getSubQueryColumnName())
					.append(" = ")
					.append(this.tableName + "." + matcher.getColumnName());
		}

		//
		// Make sure the ID column for which we are search for is NOT NULL.
		// Otherwise, if there is any row where this column is null, PostgreSQL will return false no matter what (fuck you PG for that, btw).
		for (final ColumnNamePair matcher : matchers)
		{
			final IQueryFilterModifier modifier = matcher.getModifier();
			final String subQueryColumnName = matcher.getSubQueryColumnName();
			final String subQueryColumnNameWithModifier = modifier.getColumnSql(subQueryColumnName);

			if (subQueryWhereClauseNew.length() > 0)
			{
				subQueryWhereClauseNew.append(" and ");
			}
			subQueryWhereClauseNew.append(subQueryColumnNameWithModifier).append(" is not null");
		}

		// Sub-query's initial where clause
		{
			final String subQueryWhereClauseInitial = subQueryImpl.getWhereClause();
			subQueryWhereClauseNew.append(" AND (").append(subQueryWhereClauseInitial).append(")");
		}

		//
		// Build the new sub-query SQL
		final TypedSqlQuery<?> subQueryImpNew = subQueryImpl.setWhereClause(subQueryWhereClauseNew.toString());
		final String subQuerySql = subQueryImpNew.buildSQL(
				subQuerySelectClause,
				subQueryFromClause,
				null/* groupByClause */,
				subQueryUseOrderByClause);

		//
		// Wrap the sub-query SQL in an EXISTS and return it
		return new StringBuilder()
				.append(" EXISTS (")
				.append(subQuerySql)
				.append(")")
				.toString();
	}

	/**
	 * Build the filter SQL using IN.
	 *
	 * e.g. ParentTable.JoinColumn IN (SELECT 1 FROM SubTable WHERE ...)
	 *
	 * @param subQueryImpl
	 * @return sql
	 */
	private String buildSql_UsingIN(final TypedSqlQuery<?> subQueryImpl)
	{
		if (matchers.size() > 1)
		{
			throw new AdempiereException("More than one matchers are not supported when generating subqueries using IN keyword.")
					.appendParametersToMessage()
					.setParameter("filter", this);
		}

		final ColumnNamePair matcher = matchers.get(0);
		final String columnName = matcher.getColumnName();
		final String subQueryColumnName = matcher.getSubQueryColumnName();
		final IQueryFilterModifier modifier = matcher.getModifier();

		final String subQueryColumnNameWithModifier = modifier.getColumnSql(subQueryColumnName);

		//
		// Build the new sub-query's SELECT FROM
		final StringBuilder subQuerySelectClause = new StringBuilder("SELECT ").append(subQueryColumnNameWithModifier);
		final StringBuilder subQueryFromClause = new StringBuilder(" FROM ").append(subQueryImpl.getTableName());

		// We shall have ORDER BY in sub-query if the sub-query has a LIMIT/OFFSET set
		final boolean subQueryUseOrderByClause = subQueryImpl.hasLimitOrOffset();

		//
		// Build the new sub-query's where clause
		final StringBuilder subQueryWhereClauseNew = new StringBuilder("true");
		// Sub-query's initial where clause
		{
			final String subQueryWhereClauseInitial = subQueryImpl.getWhereClause();
			subQueryWhereClauseNew.append(" AND (").append(subQueryWhereClauseInitial).append(")");
		}
		//
		// Make sure the ID column for which we are search for is NOT NULL.
		// Otherwise, if there is any row where this column is null, PostgreSQL will return false no matter what (fuck you PG for that, btw).
		{
			subQueryWhereClauseNew.append(" and " + subQueryColumnNameWithModifier + " is not null");
		}

		//
		// Build the new sub-query SQL
		final TypedSqlQuery<?> subQueryImpNew = subQueryImpl.setWhereClause(subQueryWhereClauseNew.toString());
		final String subQuerySql = subQueryImpNew.buildSQL(
				subQuerySelectClause,
				subQueryFromClause,
				null/*groupByClause*/,
				subQueryUseOrderByClause);

		//
		// Wrap the sub-query SQL in an IN (SELECT ...) and return it
		final String columnNameWithModifier = modifier.getColumnSql(columnName);
		return new StringBuilder()
				.append(columnNameWithModifier).append(" IN (")
				.append(subQuerySql)
				.append(")")
				.toString();
	}

	@SuppressWarnings("unused")
	private final void logDevelopmentWarn(final String message)
	{
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final AdempiereException e = new AdempiereException(message + "\n Filter: " + this);
			logger.warn(e.getLocalizedMessage(), e);
		}
		logger.debug(message + "\n Filter: {}", this);
	}

	@Override
	public boolean accept(final T model)
	{
		final List<Map<ColumnNamePair, Object>> subQueryValuesList = getSubQueryValues(model);
		if (subQueryValuesList.isEmpty())
		{
			return false;
		}

		final Map<ColumnNamePair, Object> modelValues = extractModelValues(model);

		return subQueryValuesList.contains(modelValues);
	}

	private Map<ColumnNamePair, Object> extractModelValues(final Object model)
	{
		final Map<ColumnNamePair, Object> modelValues = new HashMap<>();
		for (final ColumnNamePair matcher : matchers)
		{
			final String columnName = matcher.getColumnName();
			final IQueryFilterModifier modifier = matcher.getModifier();

			final Object modelValue0 = InterfaceWrapperHelper.getValue(model, columnName).orElse(null);
			final Object modelValue = modifier.convertValue(columnName, modelValue0, model);

			modelValues.put(matcher, modelValue);
		}

		return modelValues;
	}

	private List<Map<ColumnNamePair, Object>> getSubQueryValues(final T contextModel)
	{
		if (_subQueryValues != null)
		{
			return _subQueryValues;
		}

		final List<?> subQueryResult = subQuery.list();

		final List<Map<ColumnNamePair, Object>> subQueryValues = new ArrayList<>(subQueryResult.size());
		for (final Object subModel : subQueryResult)
		{
			final Map<ColumnNamePair, Object> subModelValues = new HashMap<>();
			for (final ColumnNamePair matcher : matchers)
			{
				final String subQueryColumnName = matcher.getSubQueryColumnName();
				final IQueryFilterModifier modifier = matcher.getModifier();

				final Object value0 = InterfaceWrapperHelper.getValue(subModel, subQueryColumnName).orElse(null);
				final Object value = modifier.convertValue(IQueryFilterModifier.COLUMNNAME_Constant, value0, contextModel);

				subModelValues.put(matcher, value);
			}

			subQueryValues.add(subModelValues);
		}

		this._subQueryValues = subQueryValues;
		return subQueryValues;
	}

	@lombok.Value
	@lombok.Builder
	private static final class ColumnNamePair
	{
		@lombok.NonNull
		String columnName;
		@lombok.NonNull
		String subQueryColumnName;
		@lombok.NonNull
		IQueryFilterModifier modifier;
	}

	public static final class Builder<T>
	{
		private String tableName;
		private final List<ColumnNamePair> matchers = new ArrayList<>();
		private IQuery<?> subQuery;

		private Builder()
		{
		}

		public InSubQueryFilter<T> build()
		{
			return new InSubQueryFilter<>(this);
		}

		public Builder<T> tableName(final String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		public Builder<T> subQuery(final IQuery<?> subQuery)
		{
			this.subQuery = subQuery;
			return this;
		}

		public Builder<T> matchingColumnNames(final String columnName, final String subQueryColumnName, final IQueryFilterModifier modifier)
		{
			matchers.add(ColumnNamePair.builder()
					.columnName(columnName)
					.subQueryColumnName(subQueryColumnName)
					.modifier(modifier)
					.build());
			return this;
		}

		public Builder<T> matchingColumnNames(final String columnName, final String subQueryColumnName)
		{
			return matchingColumnNames(columnName, subQueryColumnName, NullQueryFilterModifier.instance);
		}
	}
}
