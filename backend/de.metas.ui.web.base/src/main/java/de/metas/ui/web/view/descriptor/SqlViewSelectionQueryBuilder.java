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
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.IStringExpressionWrapper;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

	private SqlAndParams buildSqlFiltersOrNull(
			@NonNull final DocumentFilterList filters,
			@NonNull final SqlDocumentFilterConverterContext context,
			@NonNull final SqlOptions sqlOpts)
	{
		if (filters.isEmpty())
		{
			return null;
		}

		final SqlParamsCollector sqlParamsOut = SqlParamsCollector.newInstance();
		final String sql = getSqlDocumentFilterConverter().getSql(sqlParamsOut, filters, sqlOpts, context);
		return Check.isNotBlank(sql)
				? SqlAndParams.of(sql, sqlParamsOut.toList())
				: null;
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
		return getGroupByFieldNames().stream().collect(Collectors.joining(", "));
	}

	private boolean isGroupBy(final String fieldName)
	{
		return _viewBinding.isGroupBy(fieldName);
	}

	public boolean hasGroupingFields()
	{
		return _viewBinding.hasGroupingFields();
	}

	private SqlSelectValue getSqlAggregatedColumn(final String fieldName)
	{
		return _viewBinding.getSqlAggregatedColumn(fieldName);
	}

	private boolean isAggregated(final String fieldName)
	{
		return _viewBinding.isAggregated(fieldName);
	}

	@Value
	@Builder
	public static final class SqlCreateSelection
	{
		private final SqlAndParams sqlCreateSelectionLines;
		private final SqlAndParams sqlCreateSelection;
	}

	public SqlCreateSelection buildSqlCreateSelectionFrom(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId newViewId,
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final int queryLimit,
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
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final int queryLimit,
			final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();

		//
		// INSERT INTO T_WEBUI_ViewSelection[Line] (...)
		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer();
		sqlBuilder.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
				+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line // SeqNo
				+ ", " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated() // keys: IntKey1... StringKey1...
				+ ")");

		//
		// SELECT ... FROM ... WHERE 1=1
		final ArrayList<Object> sqlParams = new ArrayList<>();
		{
			final IStringExpression sqlOrderBy = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
					.joinOnTableNameOrAlias(sqlTableAlias)
					.useColumnNameAlias(false)
					.buildSqlOrderBy(orderBys)
					.orElseGet(() -> ConstantStringExpression.of(keyColumnNamesMap.getKeyColumnNamesCommaSeparated(sqlTableAlias)));

			final IStringExpression sqlSeqNo = IStringExpression.composer()
					.append("row_number() OVER (ORDER BY ").append(sqlOrderBy).append(")")
					.build();

			sqlBuilder.append(
					IStringExpression.composer()
							.append("\n SELECT ")
							.append("\n  ?") // UUID
							.append("\n, ").append(sqlSeqNo) // Line/SeqNo
							.append("\n, ").append(keyColumnNamesMap.getKeyColumnNamesCommaSeparated(sqlTableAlias)) // keys
							//
							.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
							.append("\n WHERE 1=1 ")
							.wrap(securityRestrictionsWrapper(sqlTableAlias)) // security
			);
			sqlParams.add(newViewId.getViewId());
		}

		//
		// WHERE clause (from query)
		{
			final SqlParamsCollector sqlWhereClauseParams = SqlParamsCollector.newInstance();
			final IStringExpression sqlWhereClause = buildSqlWhereClause(sqlWhereClauseParams, filters, SqlOptions.usingTableAlias(sqlTableAlias), filterConverterCtx);

			if (sqlWhereClause != null && !sqlWhereClause.isNullExpression())
			{
				sqlBuilder.append("\n AND (\n").append(sqlWhereClause).append("\n)");
				sqlParams.addAll(sqlWhereClauseParams.toList());
			}
		}

		//
		// Enforce a LIMIT, to not affect server performances on huge tables
		if (queryLimit > 0)
		{
			sqlBuilder.append("\n LIMIT ?");
			sqlParams.add(queryLimit);
		}

		//
		// Evaluate the final SQL query
		final String sql = sqlBuilder.build().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		//
		//
		return SqlAndParams.of(sql, sqlParams);
	}

	private SqlAndParams buildSqlCreateSelectionLines_WithGrouping(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId newViewId,
			final DocumentFilterList filters,
			final int queryLimit,
			final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();
		final String keyColumnName = keyColumnNamesMap.getSingleKeyColumnName();

		final Set<String> groupByFieldNames = getGroupByFieldNames();
		Check.assumeNotEmpty(groupByFieldNames, "groupByFieldNames is not empty"); // shall not happen

		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer();

		//
		// INSERT INTO T_WEBUI_ViewSelectionLine (...)
		sqlBuilder.append("INSERT INTO " + I_T_WEBUI_ViewSelectionLine.Table_Name + " ("
				+ " " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ ", " + keyColumnNamesMap.getSingleWebuiSelectionColumnName()
				+ ", " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID
				+ ")");

		//
		// SELECT ... FROM ... WHERE 1=1
		final List<Object> sqlParams = new ArrayList<>();
		{
			final IStringExpression sqlRecordId = ConstantStringExpression.of("MIN(" + keyColumnName + ") OVER agg");
			final IStringExpression sqlLineId = ConstantStringExpression.of(keyColumnName);

			sqlBuilder.append(
					IStringExpression.composer()
							.append("\n SELECT ")
							.append("\n  ?") // UUID
							.append("\n, ").append(sqlRecordId) // Record_ID
							.append("\n ,").append(sqlLineId) // Line_ID
							//
							.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
							.append("\n WHERE 1=1 ")
							.wrap(securityRestrictionsWrapper(sqlTableAlias)) // security
			);
			sqlParams.add(newViewId.getViewId());
		}

		//
		// WHERE clause (from query)
		{
			final SqlParamsCollector sqlWhereClauseParams = SqlParamsCollector.newInstance();
			final IStringExpression sqlWhereClause = buildSqlWhereClause(sqlWhereClauseParams, filters, SqlOptions.usingTableAlias(sqlTableAlias), filterConverterCtx);

			if (sqlWhereClause != null && !sqlWhereClause.isNullExpression())
			{
				sqlBuilder.append("\n AND (\n").append(sqlWhereClause).append("\n)");
				sqlParams.addAll(sqlWhereClauseParams.toList());
			}
		}

		//
		// WINDOW "agg" definition
		{
			final IStringExpression sqlAggregateWindowDef = IStringExpression.composer()
					.append("agg AS (PARTITION BY ")
					.append(groupByFieldNames.stream().collect(Collectors.joining(", ")))
					.append(")")
					.build();
			sqlBuilder.append("\n WINDOW ").append(sqlAggregateWindowDef);
		}

		//
		// Enforce a LIMIT, to not affect server performances on huge tables
		if (queryLimit > 0)
		{
			sqlBuilder.append("\n LIMIT ?");
			sqlParams.add(queryLimit);
		}

		//
		// Evaluate the final SQL query
		final String sql = sqlBuilder.build().evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

		return SqlAndParams.of(sql, sqlParams);
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

		final String sqlOrderBy = SqlDocumentOrderByBuilder.newInstance(sqlOrderByBindings)
				.joinOnTableNameOrAlias(lineTableAlias)
				.useColumnNameAlias(false)
				.buildSqlOrderBy(orderBysEffective)
				.map(sqlOrderByExpr -> sqlOrderByExpr.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail))
				.orElseGet(() -> keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl"));

		final String sqlFrom = "SELECT "
				+ "\n sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ "\n, " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")
				+ "\n FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name + " sl "
				+ "\n INNER JOIN " + lineTableName + " " + lineTableAlias + " ON (" + lineTableAlias + "." + lineKeyColumnName + " = sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID + ")" // join lines
				+ "\n WHERE " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=?"
				+ "\n GROUP BY "
				+ "\n sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ "\n, " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")
				+ "\n, " + getGroupByFieldNamesCommaSeparated()
				+ "\n ORDER BY " + sqlOrderBy;

		final String sqlCreateSelectionFromLines = "INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + "("
				+ "\n " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
				+ "\n, " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated()
				+ "\n, " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line // SeqNo
				+ "\n)"
				+ "\n SELECT "
				+ "\n   sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ "\n , " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated("sl")
				+ "\n , row_number() OVER ()" // SeqNo
				+ "\n FROM (" + sqlFrom + ") sl";

		final List<Object> sqlCreateSelectionFromLinesParams = Arrays.asList(newViewId.getViewId());

		return SqlAndParams.of(sqlCreateSelectionFromLines, sqlCreateSelectionFromLinesParams);
	}

	private IStringExpression buildSqlWhereClause(
			final SqlParamsCollector sqlParams,
			@Nullable final DocumentFilterList filters,
			final SqlOptions sqlOpts,
			final SqlDocumentFilterConverterContext context)
	{
		final CompositeStringExpression.Builder sqlWhereClauseBuilder = IStringExpression.composer();

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
		if (filters != null && !filters.isEmpty())
		{
			final SqlAndParams sqlFilters = buildSqlFiltersOrNull(filters, context, sqlOpts);
			if (sqlFilters != null)
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* filters */ (\n").append(sqlFilters.getSql()).append(")\n");
				sqlParams.collectAll(sqlFilters.getSqlParams());
			}
		}

		return sqlWhereClauseBuilder.build();
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

		final DocumentQueryOrderByList orderBysEffective = orderBys.stream()
				.flatMap(this::flatMapEffectiveFieldNames)
				.collect(DocumentQueryOrderByList.toDocumentQueryOrderByList());

		//
		// Build the table we will join.
		final SqlAndParams sqlSourceTable;
		{
			final Set<String> addedFieldNames = new HashSet<>();

			final StringBuilder sqlKeyColumnNames;
			{
				sqlKeyColumnNames = new StringBuilder();
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
					sqlKeyColumnNames.append(getSqlSelectValue(keyColumnName)
							.withColumnNameAlias(keyColumnName)
							.toSqlStringWithColumnNameAlias());
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
					sqlSourceTableBuilder.append("\n, ").append(sqlSelectDisplayValue
							.withJoinOnTableNameOrAlias(getTableName())
							.toSqlStringWithColumnNameAlias(viewEvalCtx.toEvaluatee()));
				}

				if (sqlSelectValue != null && addedFieldNames.add(sqlSelectValue.getColumnNameAlias()))
				{
					sqlSourceTableBuilder.append("\n, ").append(sqlSelectValue
							.withJoinOnTableNameOrAlias(getTableName())
							.toSqlStringWithColumnNameAlias());
				}
			}

			sqlSourceTableBuilder.append("\n FROM ").append(getTableName());

			final SqlAndParams sqlFilters = buildSqlFiltersOrNull(filters, filterConverterCtx, SqlOptions.usingTableName(getTableName()));
			if (sqlFilters != null)
			{
				sqlSourceTableBuilder.append("\n WHERE ").append(sqlFilters);
			}

			sqlSourceTableBuilder.append(")");

			sqlSourceTable = sqlSourceTableBuilder.build();
		}

		//
		// Order BY
		final String sqlOrderBys = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
				.joinOnTableNameOrAlias(sqlTableAlias)
				.useColumnNameAlias(useColumnNameAlias)
				.buildSqlOrderBy(orderBysEffective)
				.map(sqlOrderBysExpr -> sqlOrderBysExpr.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail))
				.map(sql -> _viewBinding.replaceTableNameWithTableAlias(sql, sqlTableAlias))
				.orElse(null);

		//
		final String sqlJoinCondition = keyColumnNamesMap.getSqlJoinCondition(sqlTableAlias, "sel");

		//
		return SqlAndParams.builder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated()
						+ ")")
				.append("\n SELECT ")
				.append("\n  ?", newViewId.getViewId()) // newUUID
				.append("\n, ").append("row_number() OVER (").append(sqlOrderBys != null ? "ORDER BY " + sqlOrderBys : "").append(")") // Line
				.append("\n, ").append(keyColumnNamesMap.getKeyColumnNamesCommaSeparated()) // keys
				.append("\n FROM ").append(I_T_WEBUI_ViewSelection.Table_Name).append(" sel")
				.append("\n INNER JOIN ").append(sqlSourceTable).append(" ").append(sqlTableAlias).append(" ON (").append(sqlJoinCondition).append(")")
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
			@NonNull final ViewEvaluationCtx viewEvalCtx,
			@NonNull final ViewId newViewId,
			@NonNull final String fromSelectionId)
	{
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = getSqlViewKeyColumnNamesMap();

		//
		// INSERT INTO T_WEBUI_ViewSelectionLine (UUID, keys, Line_ID)
		final StringBuilder sqlBuilder = new StringBuilder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelectionLine.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
						+ ", " + keyColumnNamesMap.getWebuiSelectionColumnNamesCommaSeparated()
						+ ", " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID
						+ ")");

		//
		// SELECT ... FROM T_WEBUI_ViewSelectionLine sl INNER JOIN ourTable on (Line_ID) WHERE sel.UUID=[fromUUID]
		{
			sqlBuilder
					.append("\n SELECT ")
					.append("\n  ?") // newUUID
					.append("\n, ").append(keyColumnNamesMap.getWebuiSelectionColumnNameForKeyColumnName("sl")) // keys
					.append("\n, sl.").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID) // Line_ID
					.append("\n FROM ").append(I_T_WEBUI_ViewSelectionLine.Table_Name).append(" sl ")
					.append("\n WHERE sl.").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID).append("=?") // fromUUID
			;
		}

		//
		final String sql = sqlBuilder.toString();
		return SqlAndParams.of(sql, newViewId.getViewId(), fromSelectionId);
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

	@Builder(builderMethodName = "prepareSqlWhereClause", builderClassName = "SqlWhereClauseBuilder")
	private static SqlViewRowsWhereClause buildSqlWhereClause(
			@NonNull final String sqlTableAlias,
			@NonNull final SqlViewKeyColumnNamesMap keyColumnNamesMap,
			@NonNull final String selectionId,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final SqlViewRowIdsConverter rowIdsConverter)
	{
		if (rowIds.isEmpty())
		{
			new AdempiereException("got empty rowIds")
					.throwIfDeveloperModeOrLogWarningElse(logger);
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

	public SqlAndParams buildSqlDeleteRowIdsFromSelection(@NonNull final String selectionId, @NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return null;
		}

		final List<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name + " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?");
		sqlParams.add(selectionId);

		if (rowIds.isAll())
		{
			// nothing
		}
		else
		{
			final SqlAndParams sqlFilterByRowIds = getSqlViewKeyColumnNamesMap()
					.prepareSqlFilterByRowIds()
					.rowIds(rowIds)
					.build();

			sql.append("\n AND (").append(sqlFilterByRowIds.getSql()).append(")");
			sqlParams.addAll(sqlFilterByRowIds.getSqlParams());
		}

		return SqlAndParams.of(sql.toString(), sqlParams);
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
			@NonNull SqlViewKeyColumnNamesMap keyColumnNamesMap,
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
