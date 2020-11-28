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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryAggregateColumnBuilder;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.lang.IAggregator;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.POInfo;
import org.compiere.util.DB;

import de.metas.util.StringUtils;
import lombok.NonNull;

public class QueryAggregateBuilder<SourceModelType, TargetModelType> implements IQueryAggregateBuilder<SourceModelType, TargetModelType>
{
	//
	private static final AtomicInteger uniqueIdGenerator = new AtomicInteger(0);
	private final int uniqueId = uniqueIdGenerator.incrementAndGet() % 10000; // unique Id used to create unique source column name aliases

	//
	// Parameters
	private final IQueryBuilder<SourceModelType> _sourceQueryBuilder;
	private final Properties ctx;
	private final String trxName;
	private final String _sourceColumnName;
	private final Class<TargetModelType> _targetModelType;
	private final String _targetTableName;
	private final String _targetJoinColumnName;
	//
	private final List<IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, ?>> aggregateBuilders = new ArrayList<>();

	public QueryAggregateBuilder(
			@NonNull final IQueryBuilder<SourceModelType> sourceQueryBuilder,
			@NonNull final String sourceColumnName,
			@NonNull final Class<TargetModelType> targetModelType)
	{
		this._sourceQueryBuilder = sourceQueryBuilder.copy();
		this.ctx = sourceQueryBuilder.getCtx();
		this.trxName = sourceQueryBuilder.getTrxName();

		this._targetModelType = targetModelType;
		this._targetTableName = InterfaceWrapperHelper.getTableName(_targetModelType);
		this._targetJoinColumnName = InterfaceWrapperHelper.getKeyColumnName(_targetTableName);

		this._sourceColumnName = sourceColumnName;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public List<TargetModelType> aggregate()
	{
		final boolean sqlSupported = !Adempiere.isUnitTestMode();

		if (sqlSupported)
		{
			return aggregate_SQL();
		}
		else
		{
			return aggregate_NonSql();
		}
	}

	private final List<TargetModelType> aggregate_SQL()
	{
		final List<Object> sqlParams = new ArrayList<>();
		final String sql = buildSql(sqlParams);

		final List<TargetModelType> result = new ArrayList<>();

		//
		// Execute the query and fetch the rows
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final TargetModelType model = retrieveTargetModel(rs);
				result.add(model);
			}
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql.toString(), sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		return result;
	}

	private final String buildSql(final List<Object> sqlParamsOut)
	{
		// Get all columns that we need to select on our target/aggregated model
		final POInfo poInfo = POInfo.getPOInfo(_targetTableName);
		final StringBuilder sqlSelectColumns = new StringBuilder(poInfo.getSqlSelectColumns());

		// Add the aggregated columns that we calculated in our "source query" at the end of our list of columns that we selected
		for (final IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, ?> aggregateBuilder : aggregateBuilders)
		{
			final String columnAlias = createColumnAlias(aggregateBuilder.getDynAttribute(), true);// useQuotes=true
			sqlSelectColumns.append("\n, ").append(columnAlias);
		}

		//
		// Create the final SQL query
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT")
				.append("\n ").append(sqlSelectColumns)
				.append("\n FROM ").append(_targetTableName)
				.append("\n ").append(buildSqlJoinSourceTable(sqlParamsOut)) // INNER JOIN (source table)
		// NOTE: there is no where clause around
		;

		return sql.toString();
	}

	/**
	 *
	 * @param sqlParamsOut
	 * @return INNER JOIN (SELECT GrouppingColumn, AggregateExpression1, AggregateExpression2.... FROM Source Table WHERE .... GROUP BY GrouppingColumn) s ON
	 *         (TargetTable.GrouppingColumn=s.GrouppingColumn)
	 */
	private final String buildSqlJoinSourceTable(final List<Object> sqlParamsOut)
	{
		if (aggregateBuilders.isEmpty())
		{
			throw new AdempiereException("No aggregates were defined for " + this);
		}

		final String sourceTableAlias = "source";
		final List<String> sourceSqlColumns = new ArrayList<>();
		final List<String> sourceSqlGroupBys = new ArrayList<>();

		//
		// SOURCE: Add GROUP BY column
		final String sourceColumnName = _sourceColumnName;
		final String sourceColumnAlias = createSourceColumnAlias(sourceColumnName, true); // useQuotes=true
		sourceSqlColumns.add(sourceColumnName + " AS " + sourceColumnAlias);
		sourceSqlGroupBys.add(sourceColumnName);

		//
		// SOURCE: Add Aggregated columns
		// note: appends the SQL params
		for (final IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, ?> aggregateBuilder : aggregateBuilders)
		{
			final String aggregateSql = aggregateBuilder.getSql(ctx, sqlParamsOut);
			final String columnAlias = createColumnAlias(aggregateBuilder.getDynAttribute(), true); // useQuotes=true
			sourceSqlColumns.add(aggregateSql + " AS " + columnAlias);
		}

		//
		// SOURCE: SELECT ... FROM source table WHERE ... GROUP BY ...
		// note: appends the SQL params
		final StringBuilder sourceSql;
		{
			final IQuery<SourceModelType> sourceQuery = _sourceQueryBuilder.create();
			final TypedSqlQuery<SourceModelType> sourceSqlQuery = (TypedSqlQuery<SourceModelType>)sourceQuery;
			final StringBuilder sourceSqlSelect = StringUtils.toStringBuilder(sourceSqlColumns, "\n, ")
					.insert(0, "SELECT\n");

			final StringBuilder sourceSqlFrom = new StringBuilder("\nFROM ").append(sourceQuery.getTableName()).append("\n");

			sourceSql = new StringBuilder(sourceSqlQuery.buildSQL(sourceSqlSelect, sourceSqlFrom, null, false)); // useOrderBy=false (it's pointless)
			sqlParamsOut.addAll(sourceSqlQuery.getParametersEffective());

			// Append GROUP BY
			sourceSql.append("\n GROUP BY \n").append(StringUtils.toString(sourceSqlGroupBys, "\n, "));
		}

		//
		// INNER JOIN (source select sql) on (....)
		final StringBuilder sourceSqlJoin = new StringBuilder()
				.append("INNER JOIN (\n").append(sourceSql).append("\n) ").append(sourceTableAlias).append("\n")
				.append(" ON (").append(_targetTableName).append(".").append(_targetJoinColumnName)
				.append("=").append(sourceTableAlias).append(".").append(sourceColumnAlias)
				.append(")");

		//
		// Return the result
		return sourceSqlJoin.toString();
	}

	private final String createColumnAlias(final ModelDynAttributeAccessor<TargetModelType, ?> dynAttribute, final boolean useQuotes)
	{
		return createSourceColumnAlias(dynAttribute.getAttributeName(), useQuotes);
	}

	private final String createSourceColumnAlias(final String columnName, final boolean useQuotes)
	{
		final StringBuilder alias = new StringBuilder()
				// Source column unique prefix
				.append("AGG_SRC").append(uniqueId).append("_")
				// Source column name
				.append(columnName);

		// If we are asked to use quotes, add them
		if (useQuotes)
		{
			alias.insert(0, "\"").append("\"");
		}

		return alias.toString();
	}

	/**
	 * Retrieve target model from {@link ResultSet}.
	 *
	 * NOTE: this method will also load the aggregated values which were calculated and it will set them as dynamic attributes to loaded target model.
	 *
	 * @param rs
	 * @return loaded target model
	 * @throws SQLException
	 */
	private final TargetModelType retrieveTargetModel(final ResultSet rs) throws SQLException
	{
		//
		// Actually load the target model
		final TargetModelType model = TableModelLoader.instance.retrieveModel(ctx, _targetTableName, _targetModelType, rs, trxName);

		//
		// Load aggregated values and set them as dynamic attributes to the target model which we just loaded.
		for (final IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, ?> aggregateBuilder : aggregateBuilders)
		{
			final ModelDynAttributeAccessor<TargetModelType, ?> dynAttribute = aggregateBuilder.getDynAttribute();
			loadDynAttribute(model, dynAttribute, rs);
		}

		return model;
	}

	/**
	 * Load the given <code>dynAttribute</code> from {@link ResultSet} and set it's value to given target model.
	 *
	 * @param model target model
	 * @param dynAttribute dynamic attribute
	 * @param rs result set
	 * @throws SQLException
	 */
	private final <ValueType> void loadDynAttribute(final TargetModelType model, final ModelDynAttributeAccessor<TargetModelType, ValueType> dynAttribute, final ResultSet rs) throws SQLException
	{
		final String columnName = createColumnAlias(dynAttribute, false);
		final Class<ValueType> returnType = dynAttribute.getAttributeType();

		final ValueType value = DB.retrieveValue(rs, columnName, returnType);
		dynAttribute.setValue(model, value);
	}

	private final List<TargetModelType> aggregate_NonSql()
	{
		final String sourceColumnName = _sourceColumnName;
		final Class<TargetModelType> targetModelType = _targetModelType;

		//
		// Retrieve source models
		final IQueryBuilder<SourceModelType> sourceQueryBuilder = _sourceQueryBuilder.copy();
		// Make sure we are sorting by the column on which we a grouping
		sourceQueryBuilder.orderBy()
				.clear()
				.addColumn(sourceColumnName, Direction.Ascending, Nulls.Last);
		final List<SourceModelType> sourceModels = sourceQueryBuilder.create().list();

		//
		// Load target models(grouping column).
		// Aggregate the source models and set the result as a dynamic attribute in target model.
		final List<TargetModelType> targetModels = new ArrayList<>();
		Object lastTargetModelId = null;
		TargetModelType currentTargetModel = null;
		final List<IAggregator<?, SourceModelType>> currentAggregators = new ArrayList<>();
		for (final SourceModelType sourceModel : sourceModels)
		{
			// Skip null source models (shall not happen)
			if (sourceModel == null)
			{
				continue;
			}

			//
			// Get current taget model Id
			final Object targetModelId = InterfaceWrapperHelper.getValueOrNull(sourceModel, sourceColumnName);
			if (targetModelId == null)
			{
				// skip null values
				continue;
			}

			//
			// If target model changed,
			// * retrieve the new target model
			// * initialize the aggregators
			if (lastTargetModelId == null || !Objects.equals(lastTargetModelId, targetModelId))
			{
				currentTargetModel = InterfaceWrapperHelper.getModelValue(sourceModel, sourceColumnName, targetModelType);
				if (currentTargetModel == null)
				{
					// Shall not happen because we have a not null ID value. So it means that for some reason the model for that ID was not found...
					throw new AdempiereException("No model value was found in source model even though the value exists."
							+ "\n sourceModel=" + sourceModel
							+ "\n sourceColumnName=" + sourceColumnName
							+ "\n targetModelId=" + targetModelId
							+ "\n targetModelType=" + targetModelType);
				}
				targetModels.add(currentTargetModel);

				// Create aggregators
				currentAggregators.clear();
				for (final IQueryAggregateColumnBuilder<SourceModelType, TargetModelType, ?> aggregateBuilder : aggregateBuilders)
				{
					final IAggregator<?, SourceModelType> aggregator = aggregateBuilder.createAggregator(currentTargetModel);
					currentAggregators.add(aggregator);
				}

			}
			lastTargetModelId = targetModelId;

			// Aggregate current source model to all aggregators of current target model
			for (final IAggregator<?, SourceModelType> aggregator : currentAggregators)
			{
				aggregator.add(sourceModel);
				// NOTE: current aggregated values will be automatically pushed to target model each time
			}
		}

		return targetModels;
	}

	@Override
	public CountIfQueryAggregateColumnBuilder<SourceModelType, TargetModelType> countIf(final ModelDynAttributeAccessor<TargetModelType, Integer> dynAttribute)
	{
		final CountIfQueryAggregateColumnBuilder<SourceModelType, TargetModelType> aggregateBuilder = new CountIfQueryAggregateColumnBuilder<>();

		aggregateBuilder.setDynAttribute(dynAttribute);
		aggregateBuilders.add(aggregateBuilder);
		return aggregateBuilder;
	}

	@Override
	public SumQueryAggregateColumnBuilder<SourceModelType, TargetModelType> sum(
			final ModelDynAttributeAccessor<TargetModelType, BigDecimal> dynAttribute,
			final ModelColumn<SourceModelType, ?> amountColumn)
	{
		final SumQueryAggregateColumnBuilder<SourceModelType, TargetModelType> aggregateBuilder = new SumQueryAggregateColumnBuilder<>(amountColumn);

		aggregateBuilder.setDynAttribute(dynAttribute);
		aggregateBuilders.add(aggregateBuilder);
		return aggregateBuilder;
	}
}
