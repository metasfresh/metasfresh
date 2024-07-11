/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.view.descriptor;

import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.security.permissions.Access;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelectionLine;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.sql.SqlOrderByValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectDisplayValue;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder.SqlOrderByBindings;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.IStringExpressionWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Helper class used to build the SQL queries needed for creating and manipulating the view selections.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @see I_T_WEBUI_ViewSelection
 */
public final class SqlViewSelectionQueryBuilder
{
	private static final transient Logger logger = LogManager.getLogger(SqlViewSelectionQueryBuilder.class);

	private final SqlViewBinding _viewBinding;
	private boolean applySecurityRestrictions = true;
	private SqlDocumentFilterConverter _sqlDocumentFieldConverter; // lazy
	private boolean useColumnNameAlias = true;

	public static SqlViewSelectionQueryBuilder newInstance(final SqlViewBinding viewBinding)
	{
		return new SqlViewSelectionQueryBuilder(viewBinding);
	}

	private SqlViewSelectionQueryBuilder(@NonNull final SqlViewBinding viewBinding)
	{
		_viewBinding = viewBinding;
	}

	public SqlViewSelectionQueryBuilder applySecurityRestrictions(final boolean applySecurityRestrictions)
	{
		this.applySecurityRestrictions = applySecurityRestrictions;
		return this;
	}

	private String getTableName()
	{
		return _viewBinding.getTableName();
	}

	private String getTableAlias()
	{
		return _viewBinding.getTableAlias();
	}

	public IStringExpression getSqlWhereClause()
	{
		return _viewBinding.getSqlWhereClause();
	}

	private SqlViewKeyColumnNamesMap getSqlViewKeyColumnNamesMap()
	{
		return _viewBinding.getSqlViewKeyColumnNamesMap();
	}

	private SqlSelectValue getSqlSelectValue(final String fieldName)
	{
		return _viewBinding.getFieldByFieldName(fieldName).getSqlSelectValue();
	}

	private SqlSelectDisplayValue getSqlSelectDisplayValue(final String fieldName)
	{
		return _viewBinding.getFieldByFieldName(fieldName).getSqlSelectDisplayValue();
	}

	private SqlOrderByValue getFieldOrderBy(final String fieldName)
	{
		return _viewBinding.getFieldOrderBy(fieldName);
	}

	private Stream<DocumentQueryOrderBy> flatMapEffectiveFieldNames(final DocumentQueryOrderBy orderBy)
	{
		return _viewBinding.flatMapEffectiveFieldNames(orderBy);
	}

	private FilterSql toFilterSql(
			@NonNull final DocumentFilterList filters,
			@NonNull final SqlDocumentFilterConverterContext context,
			@NonNull final SqlOptions sqlOpts)
	{
		return getSqlDocumentFilterConverter().getSql(filters, sqlOpts, context);
	}

	private SqlDocumentFilterConverter getSqlDocumentFilterConverter()
	{
		if (_sqlDocumentFieldConverter == null)
		{
			_sqlDocumentFieldConverter = SqlDocumentFilterConverters.createEntityBindingEffectiveConverter(_viewBinding);
		}
		return _sqlDocumentFieldConverter;
	}

	private SqlViewRowIdsConverter getRowIdsConverter()
	{
		return _viewBinding.getRowIdsConverter();
	}

	private Set<String> getGroupByFieldNames()
	{
		return _viewBinding.getGroupByFieldNames();
	}

	private String getGroupByFieldNamesCommaSeparated()
	{
		return String.join(", ", getGroupByFieldNames());
	}

	private boolean isGroupBy(final String fieldName)
	{
		return _viewBinding.isGroupBy(fieldName);
	}

	public boolean hasGroupingFields()
	{
		return _viewBinding.hasGroupingFields();
	}

	@Nullable
	private SqlSelectValue getSqlAggregatedColumn(final String fieldName)
	{
		return _viewBinding.getSqlAggregatedColumn(fieldName);
	}

	private boolean isAggregated(final String fieldName)
	{
		return _viewBinding.isAggregated(fieldName);
	}

	@Value
	@lombok.Builder
	public static class SqlCreateSelection
	{
		SqlAndParams sqlCreateSelectionLines;
		SqlAndParams sqlCreateSelection;
	}

	public SqlCreateSelection buildSqlCreateSelectionFrom(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId newViewId,
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final QueryLimit queryLimit,
			final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		if (!hasGroupingFields())
		{
			final SqlAndParams sqlCreateSelection = buildSqlCreateSelection_WithoutGrouping(viewEvalCtx, newViewId, filters, orderBys, queryLimit, filterConverterCtx);
			return SqlCreateSelection.builder().sqlCreateSelection(sqlCreateSelection).build();
		}
		else
		{
			final SqlAndParams sqlCreateSelectionLines = buildSqlCreateSelectionLines_WithGrouping(viewEvalCtx, newViewId, filters, queryLimit, filterConverterCtx);
			final SqlAndParams sqlCreateSelection = buildSqlCreateSelectionFromSelectionLines(viewEvalCtx, newViewId, orderBys);
			return SqlCreateSelection.builder().sqlCreateSelection(sqlCreateSelection).sqlCreateSelectionLines(sqlCreateSelectionLines).build();
		}
	}

	private SqlAndParams buildSqlCreateSelection_WithoutGrouping(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId newViewId,
			@Nullable final DocumentFilterList filters,
			@NonNull final DocumentQueryOrderByList orderBys,
			@NonNull final QueryLimit queryLimit,
			@NonNull final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();

		final FilterSqlExpression filterSqlExpression = buildFilterSqlExpression(
				filters,
				SqlOptions.usingTableAlias(sqlTableAlias),
				filterConverterCtx.withViewId(newViewId));

		SqlAndParams sqlOrderBy_FTS_Line = SqlAndParams.EMPTY;
		SqlAndParams sqlJoinFTSTable = SqlAndParams.EMPTY;
		if (filterSqlExpression.getFilterByFTS() != null)
		{
			sqlOrderBy_FTS_Line = filterSqlExpression.getFilterByFTS().buildOrderBy("fts");
			sqlJoinFTSTable = filterSqlExpression.getFilterByFTS().buildInnerJoinClause(sqlTableAlias, "fts");
		}

		final SqlAndParamsExpression sqlOrderBy = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
				.joinOnTableNameOrAlias(sqlTableAlias)
				.useColumnNameAlias(false)
				.beforeOrderBy(sqlOrderBy_FTS_Line)
				.beforeOrderBy(filterSqlExpression.getOrderBy())
				.buildSqlOrderBy(orderBys)
				.orElseGet(() -> SqlAndParamsExpression.of(keyColumnNamesMap.getKeyColumnNamesCommaSeparated(sqlTableAlias)));

		final SqlAndParamsExpression.Builder sqlInsert = SqlAndParamsExpression.builder()
				//
				// INSERT INTO T_WEBUI_ViewSelection[Line] (...)
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line // SeqNo
						+ ", " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated() // keys: IntKey1... StringKey1...
						+ ")")
				//
				// SELECT ... FROM ... WHERE 1=1
				.append(
						SqlAndParamsExpression.builder()
								.append("\n SELECT ")
								.append("\n  ?", newViewId.getViewId()) // UUID
								.append("\n, ").append("row_number() OVER (ORDER BY ").append(sqlOrderBy).append(")") // Line/SeqNo
								.append("\n, ").append(keyColumnNamesMap.getKeyColumnNamesCommaSeparated(sqlTableAlias)) // keys
								//
								.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
								.append(sqlJoinFTSTable)
								.append("\n WHERE 1=1 ")
								.wrap(securityRestrictionsWrapper(sqlTableAlias)) // security
				);

		//
		// WHERE clause (from query)
		{
			final SqlAndParamsExpression sqlWhereClause = filterSqlExpression.getWhereClauseConsideringAlwaysIncludeSqls();
			if (sqlWhereClause != null && !sqlWhereClause.isEmpty())
			{
				sqlInsert.append("\n AND (\n").append(sqlWhereClause).append("\n)");
			}
		}

		//
		// Enforce a LIMIT, to not affect server performances on huge tables
		if (queryLimit.isLimited())
		{
			sqlInsert.append("\n LIMIT ?", queryLimit.toInt());
		}

		//
		// Evaluate the final SQL query
		return sqlInsert.build().evaluate(viewEvalCtx.toEvaluatee());
	}

	private SqlAndParams buildSqlCreateSelectionLines_WithGrouping(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId newViewId,
			@Nullable final DocumentFilterList filters,
			@NonNull final QueryLimit queryLimit,
			@NonNull final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();
		final String keyColumnName = keyColumnNamesMap.getSingleKeyColumnName();

		final Set<String> groupByFieldNames = getGroupByFieldNames();
		Check.assumeNotEmpty(groupByFieldNames, "groupByFieldNames is not empty"); // shall not happen

		final FilterSqlExpression filterSqlExpression = buildFilterSqlExpression(
				filters,
				SqlOptions.usingTableAlias(sqlTableAlias),
				filterConverterCtx.withViewId(newViewId));
		if (filterSqlExpression.getFilterByFTS() != null)
		{
			throw new AdempiereException("Full Text Search filtering not supported when grouping" + filterSqlExpression);
		}
		if (filterSqlExpression.getOrderBy() != null)
		{
			throw new AdempiereException("Filter ORDER BY not supported when grouping: " + filterSqlExpression);
		}

		final SqlAndParamsExpression.Builder sqlInsert = SqlAndParamsExpression.builder()
				//
				// INSERT INTO T_WEBUI_ViewSelectionLine (...)
				.append("INSERT INTO " + I_T_WEBUI_ViewSelectionLine.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
						+ ", " + keyColumnNamesMap.getSingleWebuiSelectionColumnName()
						+ ", " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID
						+ ")")
				//
				// SELECT ... FROM ... WHERE 1=1
				.append(
						SqlAndParamsExpression.builder()
								.append("\n SELECT ")
								.append("\n  ?", newViewId.getViewId()) // UUID
								.append("\n, ").append("MIN(" + keyColumnName + ") OVER agg") // Record_ID
								.append("\n ,").append(keyColumnName) // Line_ID
								//
								.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
								.append("\n WHERE 1=1 ")
								.wrap(securityRestrictionsWrapper(sqlTableAlias))); // security

		//
		// WHERE clause (from query)
		{
			final SqlAndParamsExpression sqlWhereClause = filterSqlExpression.getWhereClauseConsideringAlwaysIncludeSqls();
			if (sqlWhereClause != null && !sqlWhereClause.isEmpty())
			{
				sqlInsert.append("\n AND (\n").append(sqlWhereClause).append("\n)");
			}
		}

		//
		// WINDOW "agg" definition
		sqlInsert.append("\n WINDOW agg AS (PARTITION BY ").append(String.join(", ", groupByFieldNames)).append(")");

		//
		// Enforce a LIMIT, to not affect server performances on huge tables
		if (queryLimit.isLimited())
		{
			sqlInsert.append("\n LIMIT ?", queryLimit.toInt());
		}

		//
		// Evaluate the final SQL query
		return sqlInsert.build().evaluate(viewEvalCtx.toEvaluatee());
	}

	public SqlAndParams buildSqlCreateSelectionFromSelectionLines(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId newViewId,
			@NonNull final DocumentQueryOrderByList orderBys)
	{
		final String lineTableName = getTableName();
		final String lineTableAlias = getTableAlias();
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();
		final String lineKeyColumnName = keyColumnNamesMap.getSingleKeyColumnName();

		final SqlOrderByBindings sqlOrderByBindings = fieldName -> {
			if (keyColumnNamesMap.isKeyPartFieldName(fieldName))
			{
				return SqlOrderByValue.ofColumnName("sl", keyColumnNamesMap.getWebuiSelectionColumnNameForKeyColumnName(fieldName));
			}
			else if (isGroupBy(fieldName))
			{
				return getFieldOrderBy(fieldName);
			}
			else if (isAggregated(fieldName))
			{
				return SqlOrderByValue.builder().sqlSelectValue(getSqlAggregatedColumn(fieldName)).build();
			}
			else
			{
				// shall not happen
				return null;
			}
		};

		final DocumentQueryOrderByList orderBysEffective = orderBys.stream()
				.flatMap(this::flatMapEffectiveFieldNames)
				.filter(orderBy -> keyColumnNamesMap.isKeyPartFieldName(orderBy.getFieldName()) || isGroupBy(orderBy.getFieldName()) || isAggregated(orderBy.getFieldName()))
				.collect(DocumentQueryOrderByList.toDocumentQueryOrderByList());

		final SqlAndParams sqlOrderBy = SqlDocumentOrderByBuilder.newInstance(sqlOrderByBindings)
				.joinOnTableNameOrAlias(lineTableAlias)
				.useColumnNameAlias(false)
				.buildSqlOrderBy(orderBysEffective)
				.map(sqlOrderByExpr -> sqlOrderByExpr.evaluate(viewEvalCtx.toEvaluatee()))
				.orElseGet(() -> SqlAndParams.of(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")));

		final SqlAndParams sqlFrom0 = SqlAndParams.builder()
				.append("SELECT "
						+ "\n sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
						+ "\n, " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")
						+ "\n FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name + " sl "
						+ "\n INNER JOIN " + lineTableName + " " + lineTableAlias + " ON (" + lineTableAlias + "." + lineKeyColumnName + " = sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID + ")") // join lines
				.append("\n WHERE " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=?", newViewId.getViewId())
				.append("\n GROUP BY "
						+ "\n sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
						+ "\n, " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")
						+ "\n, " + getGroupByFieldNamesCommaSeparated())
				.append("\n ORDER BY ").append(sqlOrderBy)
				.build();

		return SqlAndParams.builder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + "("
						+ "\n " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ "\n, " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated()
						+ "\n, " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line // SeqNo
						+ "\n)"
						+ "\n SELECT "
						+ "\n   sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
						+ "\n , " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")
						+ "\n , row_number() OVER ()" // SeqNo
						+ "\n FROM (").append(sqlFrom0).append(") sl")
				.build();
	}

	private FilterSqlExpression buildFilterSqlExpression(
			@Nullable final DocumentFilterList filters,
			@NonNull final SqlOptions sqlOpts,
			@NonNull final SqlDocumentFilterConverterContext context)
	{

		final SqlAndParamsExpression.Builder sqlWhereClauseBuilder = SqlAndParamsExpression.builder();

		//
		// Entity's WHERE clause
		{
			final IStringExpression entityWhereClauseExpression = getSqlWhereClause();
			if (entityWhereClauseExpression != null && !entityWhereClauseExpression.isNullExpression())
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* entity where clause */ (").append(entityWhereClauseExpression).append(")");
			}
		}

		//
		// Document filters
		FilterSql.FullTextSearchResult filterByFTS = null;
		FilterSql.RecordsToAlwaysIncludeSql alwaysIncludeSql = null;
		SqlAndParams sqlOrderBy = null;
		if (filters != null && !filters.isEmpty())
		{
			final FilterSql filtersSql = toFilterSql(filters, context, sqlOpts);
			if (filtersSql.getWhereClause() != null && !filtersSql.getWhereClause().isEmpty())
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* filters */ (\n").append(filtersSql.getWhereClause()).append(")\n");
			}

			filterByFTS = filtersSql.getFilterByFTS();
			alwaysIncludeSql = filtersSql.getAlwaysIncludeSql();
			sqlOrderBy = filtersSql.getOrderBy();
		}

		return FilterSqlExpression.builder()
				.whereClause(sqlWhereClauseBuilder.build())
				.filterByFTS(filterByFTS)
				.alwaysIncludeSql(alwaysIncludeSql)
				.orderBy(sqlOrderBy)
				.build();
	}

	@Value
	@Builder
	private static class FilterSqlExpression
	{
		@Getter(AccessLevel.NONE)
		@Nullable SqlAndParamsExpression whereClause;

		@Nullable FilterSql.FullTextSearchResult filterByFTS;

		@Getter(AccessLevel.NONE)
		@Nullable FilterSql.RecordsToAlwaysIncludeSql alwaysIncludeSql;

		@Nullable SqlAndParams orderBy;

		@Nullable
		public SqlAndParamsExpression getWhereClauseConsideringAlwaysIncludeSqls()
		{
			if (whereClause == null || whereClause.isEmpty())
			{
				return null;
			}

			final SqlAndParams alwaysIncludeSql = this.alwaysIncludeSql != null ? this.alwaysIncludeSql.toSqlAndParams() : null;
			if (alwaysIncludeSql == null || alwaysIncludeSql.isEmpty())
			{
				return whereClause;
			}

			return SqlAndParamsExpression.builder()
					.append(whereClause)
					.append("\n OR (").append(alwaysIncludeSql).append(")")
					.build();
		}
	}

	/**
	 * @return <pre>
	 * INSERT INTO T_WEBUI_ViewSelection (UUID, Line, keys)
	 * SELECT ... FROM T_WEBUI_ViewSelection sel INNER JOIN ourTable WHERE sel.UUID=[fromUUID]
	 *         </pre>
	 */
	public SqlAndParams buildSqlCreateSelectionFromSelection(
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId newViewId,
			@NonNull final String fromSelectionId,
			@NonNull final DocumentFilterList filters,
			@NonNull final DocumentQueryOrderByList orderBys,
			@NonNull final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		final String sqlTableAlias = getTableAlias();
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();
		final FilterSql filterSql = toFilterSql(
				filters,
				filterConverterCtx.withUserRolePermissionsKey(viewEvalCtx.getPermissionsKey()),
				SqlOptions.usingTableName(getTableName()));

		final DocumentQueryOrderByList orderBysEffective = orderBys.stream()
				.flatMap(this::flatMapEffectiveFieldNames)
				.collect(DocumentQueryOrderByList.toDocumentQueryOrderByList());

		//
		// Build the table we will join (i.e. a sub-select from source table)
		final SqlAndParams sqlSourceTable;
		{
			final Set<String> addedFieldNames = new HashSet<>();

			final SqlAndParams.Builder sqlKeyColumnNames;
			{
				sqlKeyColumnNames = SqlAndParams.builder();
				for (final String keyColumnName : keyColumnNamesMap.getKeyColumnNames())
				{
					if (!addedFieldNames.add(keyColumnName))
					{
						continue;
					}

					if (sqlKeyColumnNames.length() > 0)
					{
						sqlKeyColumnNames.append("\n, ");
					}
					sqlKeyColumnNames.append(getSqlSelectValue(keyColumnName).withColumnNameAlias(keyColumnName).toSqlStringWithColumnNameAlias());
				}
			}

			final SqlAndParams.Builder sqlSourceTableBuilder = SqlAndParams.builder();
			sqlSourceTableBuilder.append("(SELECT ").append(sqlKeyColumnNames);

			for (final DocumentQueryOrderBy orderBy : orderBysEffective.toList())
			{
				final String fieldName = orderBy.getFieldName();

				final SqlSelectDisplayValue sqlSelectDisplayValue = getSqlSelectDisplayValue(fieldName);
				final SqlSelectValue sqlSelectValue = getSqlSelectValue(fieldName);

				if (sqlSelectValue.isVirtualColumn() && sqlSelectDisplayValue != null)
				{
					useColumnNameAlias = false;
				}

				if (sqlSelectDisplayValue != null && addedFieldNames.add(sqlSelectDisplayValue.getColumnNameAlias()) && !sqlSelectValue.isVirtualColumn())
				{
					sqlSourceTableBuilder.append("\n, ")
							.append(sqlSelectDisplayValue
									.withJoinOnTableNameOrAlias(getTableName())
									.toSqlStringWithColumnNameAlias(viewEvalCtx.toEvaluatee()));
				}

				if (addedFieldNames.add(sqlSelectValue.getColumnNameAlias()))
				{
					sqlSourceTableBuilder.append("\n, ").append(sqlSelectValue
							.withJoinOnTableNameOrAlias(getTableName())
							.toSqlStringWithColumnNameAlias());
				}
			}

			if (filterSql.getFilterByFTS() != null)
			{
				sqlSourceTableBuilder.append(", ").append(filterSql.getFilterByFTS().buildOrderBy("fts")).append(" AS _fts_line");
			}

			sqlSourceTableBuilder.append("\n FROM ").append(getTableName());

			if (filterSql.getFilterByFTS() != null)
			{
				sqlSourceTableBuilder.append(filterSql.getFilterByFTS().buildInnerJoinClause(getTableName(), "fts"));
			}

			if (filterSql.getWhereClause() != null && !filterSql.getWhereClause().isEmpty())
			{
				sqlSourceTableBuilder.append("\n WHERE ").append(filterSql.getWhereClause());
			}

			sqlSourceTableBuilder.append(")");

			sqlSourceTable = sqlSourceTableBuilder.build();
		}

		//
		// ORDER BY clause (including ORDER BY prefix)
		// or empty if there is no ORDER BY
		final SqlAndParams sqlOrderBys = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
				.joinOnTableNameOrAlias(sqlTableAlias)
				.useColumnNameAlias(useColumnNameAlias)
				.beforeOrderBy(filterSql.getFilterByFTS() != null ? "_fts_line" : null)
				.beforeOrderBy(filterSql.getOrderBy())
				.buildSqlOrderBy(orderBysEffective)
				.map(sqlOrderBysExpr -> sqlOrderBysExpr.evaluate(viewEvalCtx.toEvaluatee()))
				.filter(sql -> !sql.isEmpty())
				.map(sql -> _viewBinding.replaceTableNameWithTableAlias(sql, sqlTableAlias))
				.map(sql -> SqlAndParams.builder().append("ORDER BY ").append(sql).build()) // prefix with ORDER BY keyword
				.orElse(SqlAndParams.EMPTY);

		//
		return SqlAndParams.builder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " (")
				.append(" ").append(I_T_WEBUI_ViewSelection.COLUMNNAME_UUID)
				.append(", ").append(I_T_WEBUI_ViewSelection.COLUMNNAME_Line)
				.append(", ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated())
				.append(")")
				.append("\n SELECT ")
				.append("\n  ?", newViewId.getViewId()) // newUUID
				.append("\n, ").append("row_number() OVER (").append(sqlOrderBys).append(")") // Line
				.append("\n, ").append(keyColumnNamesMap.getKeyColumnNamesCommaSeparated()) // keys
				.append("\n FROM ").append(I_T_WEBUI_ViewSelection.Table_Name).append(" sel")
				.append("\n INNER JOIN ").append(sqlSourceTable).append(" ").append(sqlTableAlias).append(" ON (")
				.append(keyColumnNamesMap.getSqlJoinCondition(sqlTableAlias, "sel"))
				.append(")")
				.append("\n WHERE sel.").append(I_T_WEBUI_ViewSelection.COLUMNNAME_UUID).append("=?", fromSelectionId) // fromUUID
				.build();
	}

	/**
	 * @return <pre>
	 * 	INSERT INTO T_WEBUI_ViewSelectionLine (UUID, Line, keys, Line_ID) ...
	 * 	SELECT ... FROM T_WEBUI_ViewSelectionLine sl INNER JOIN ourTable on (Line_ID)  WHERE sel.UUID=[fromUUID]
	 *         </pre>
	 */
	public SqlAndParams buildSqlCreateSelectionLinesFromSelectionLines(
			@NonNull final ViewId newViewId,
			@NonNull final String fromSelectionId)
	{
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();

		final SqlAndParams.Builder sqlBuilder = SqlAndParams.builder()
				//
				// INSERT INTO T_WEBUI_ViewSelectionLine (UUID, keys, Line_ID)
				.append("INSERT INTO ").append(I_T_WEBUI_ViewSelectionLine.Table_Name).append(" (")
				.append(" ").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID)
				.append(", ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated())
				.append(", ").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID)
				.append(")")
				//
				// SELECT ... FROM T_WEBUI_ViewSelectionLine sl INNER JOIN ourTable on (Line_ID) WHERE sel.UUID=[fromUUID]
				.append("\n SELECT ")
				.append("\n  ?", newViewId.getViewId()) // newUUID
				.append("\n, ").append(keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")) // keys
				.append("\n, sl.").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID) // Line_ID
				.append("\n FROM ").append(I_T_WEBUI_ViewSelectionLine.Table_Name).append(" sl ")
				.append("\n WHERE sl.").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID).append("=?", fromSelectionId); // fromUUID

		//
		return sqlBuilder.build();
	}

	public SqlViewRowsWhereClause buildSqlWhereClause(
			@NonNull final String selectionId,
			@NonNull final DocumentIdsSelection rowIds)
	{
		final String sqlTableName = getTableName();
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();
		final SqlViewRowIdsConverter rowIdsConverter = getRowIdsConverter();
		return buildSqlWhereClause(sqlTableName, keyColumnNamesMap, selectionId, rowIds, rowIdsConverter);
	}

	@lombok.Builder(builderMethodName = "prepareSqlWhereClause", builderClassName = "SqlWhereClauseBuilder")
	private static SqlViewRowsWhereClause buildSqlWhereClause(
			@NonNull final String sqlTableAlias,
			@NonNull final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			@NonNull final String selectionId,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final SqlViewRowIdsConverter rowIdsConverter)
	{
		if (rowIds.isEmpty())
		{
			//noinspection ThrowableNotThrown
			new AdempiereException("got empty rowIds").throwIfDeveloperModeOrLogWarningElse(logger);
			return SqlViewRowsWhereClause.noRecords();
		}

		final SqlAndParams rowsPresentInViewSelection = SqlAndParams.of(
				"exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " sel "
						+ " where "
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId)
						+ " and " + keyColumnNamesMap.getSqlJoinCondition(sqlTableAlias, "sel")
						+ ")");

		final SqlAndParams rowsPresentInTable;
		if (!rowIds.isAll())
		{
			rowsPresentInTable = keyColumnNamesMap.prepareSqlFilterByRowIds()
					.sqlColumnPrefix(sqlTableAlias + ".")
					.mappingType(SqlViewKeyColumnNamesMap.MappingType.SOURCE_TABLE)
					.rowIds(rowIds)
					.rowIdsConverter(rowIdsConverter)
					.embedSqlParams(true)
					.build();
		}
		else
		{
			rowsPresentInTable = null;
		}

		return SqlViewRowsWhereClause.builder()
				.rowsPresentInViewSelection(rowsPresentInViewSelection)
				.rowsPresentInTable(rowsPresentInTable)
				.build();
	}

	@Nullable
	public SqlAndParams buildSqlDeleteRowIdsFromSelection(@NonNull final String selectionId, @NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return null;
		}

		final SqlAndParams.Builder sql = SqlAndParams.builder();
		sql.append("DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name + " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?", selectionId);

		if (!rowIds.isAll())
		{
			final SqlAndParams sqlFilterByRowIds = getSqlViewKeyColumnNamesMap()
					.prepareSqlFilterByRowIds()
					.rowIds(rowIds)
					.build();

			sql.append("\n AND (").append(sqlFilterByRowIds).append(")");
		}

		return sql.build();
	}

	public SqlAndParams buildSqlAddRowIdsFromSelection(final String selectionId, final DocumentId rowId)
	{
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();

		// TODO: we should also validate if the rowId is allowed to be part of this selection (e.g. enforce entity binding's SQL where clause)

		return SqlAndParams.builder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated()
						+ ")"
						+ " SELECT ")
				.append(" ? as UUID ", selectionId) // UUID
				.append(", (select coalesce(max(Line), 0) + 1 from T_WEBUI_ViewSelection z where z.UUID=?) as Line", selectionId) // Line
				.append(", ").append(keyColumnNamesMap.getSqlValuesCommaSeparated(rowId)) // keys
				.append(" WHERE ")
				.append(" NOT EXISTS(select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " z where z.UUID=?", selectionId)
				.append(" and ").append(keyColumnNamesMap.prepareSqlFilterByRowIds()
						.sqlColumnPrefix("z.")
						.rowIds(DocumentIdsSelection.fromNullable(rowId))
						.build())
				.append(")")
				.build();
	}

	public SqlAndParams buildSqlRetrieveSize(final String selectionId)
	{
		Check.assumeNotEmpty(selectionId, "selectionId is not empty");

		return SqlAndParams.of("SELECT COUNT(1) FROM " + I_T_WEBUI_ViewSelection.Table_Name + " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?", selectionId);
	}

	public SqlAndParams buildSqlCount(final String selectionId, final DocumentIdsSelection rowIds)
	{
		Check.assumeNotEmpty(selectionId, "selectionId is not empty");

		final SqlAndParams.Builder sql = SqlAndParams.builder()
				.append("SELECT COUNT(1) FROM " + I_T_WEBUI_ViewSelection.Table_Name + " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?", selectionId);

		if (rowIds.isAll())
		{
			return sql.build();
		}
		else if (rowIds.isEmpty())
		{
			throw new IllegalArgumentException("empty rowIds is not allowed");
		}
		else
		{
			return sql.append(" AND (")
					.append(getSqlViewKeyColumnNamesMap()
							.prepareSqlFilterByRowIds()
							.rowIds(rowIds)
							.build())
					.append(")")
					.build();
		}
	}

	public SqlAndParams buildSqlDeleteSelection(@NonNull final Set<String> selectionIds)
	{
		final ArrayList<Object> sqlParams = new ArrayList<>(selectionIds.size());
		final String sql = "DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name
				+ " WHERE " + DB.buildSqlList(I_T_WEBUI_ViewSelection.COLUMNNAME_UUID, selectionIds, sqlParams);
		return SqlAndParams.of(sql, sqlParams);
	}

	public SqlAndParams buildSqlDeleteSelectionLines(@NonNull final Set<String> selectionIds)
	{
		final ArrayList<Object> sqlParams = new ArrayList<>(selectionIds.size());
		final String sql = "DELETE FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name
				+ " WHERE " + DB.buildSqlList(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID, selectionIds, sqlParams);
		return SqlAndParams.of(sql, sqlParams);
	}

	public static SqlAndParams buildSqlSelectRowIdsForLineIds(
			@NonNull final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			@NonNull final String selectionId,
			final Collection<Integer> lineIds)
	{
		Check.assumeNotEmpty(lineIds, "lineIds is not empty");

		final List<Object> sqlParams = new ArrayList<>();
		sqlParams.add(selectionId);

		final String sql = "SELECT "
				+ keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated()
				+ " FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=?"
				+ " AND " + DB.buildSqlList(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID, lineIds, sqlParams);

		return SqlAndParams.of(sql, sqlParams);
	}

	private IStringExpressionWrapper securityRestrictionsWrapper(final String sqlTableAlias)
	{
		if (applySecurityRestrictions)
		{
			return AccessSqlStringExpression.wrapper(sqlTableAlias, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
		}
		else
		{
			return expression -> expression;
		}
	}
}
