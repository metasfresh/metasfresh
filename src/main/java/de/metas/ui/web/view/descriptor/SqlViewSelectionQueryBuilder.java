package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelectionLine;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverters;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder.SqlOrderByBindings;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Helper class used to build the SQL queries needed for creating and manipulating the view selections.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @see I_T_WEBUI_ViewSelection
 */
public final class SqlViewSelectionQueryBuilder
{
	private static final transient Logger logger = LogManager.getLogger(SqlViewSelectionQueryBuilder.class);

	private final SqlViewBinding _viewBinding;
	private SqlDocumentFilterConverter _sqlDocumentFieldConverter; // lazy

	public static final SqlViewSelectionQueryBuilder newInstance(final SqlViewBinding viewBinding)
	{
		return new SqlViewSelectionQueryBuilder(viewBinding);
	}

	private SqlViewSelectionQueryBuilder(@NonNull final SqlViewBinding viewBinding)
	{
		_viewBinding = viewBinding;
	}

	private String getTableName()
	{
		return _viewBinding.getTableName();
	}

	private String getTableAlias()
	{
		return _viewBinding.getTableAlias();
	}

	public String getKeyColumnName()
	{
		return _viewBinding.getKeyColumnName();
	}

	public IStringExpression getSqlWhereClause()
	{
		return _viewBinding.getSqlWhereClause();
	}

	private boolean isKeyFieldName(final String fieldName)
	{
		return _viewBinding.isKeyFieldName(fieldName);
	}

	private IStringExpression getFieldOrderBy(final String fieldName)
	{
		return _viewBinding.getFieldOrderBy(fieldName);
	}

	private Stream<DocumentQueryOrderBy> flatMapEffectiveFieldNames(final DocumentQueryOrderBy orderBy)
	{
		return _viewBinding.flatMapEffectiveFieldNames(orderBy);
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

	private boolean isGroupBy(final String fieldName)
	{
		return _viewBinding.isGroupBy(fieldName);
	}

	public boolean hasGroupingFields()
	{
		return _viewBinding.hasGroupingFields();
	}

	private String getSqlAggregatedColumn(final String fieldName)
	{
		return _viewBinding.getSqlAggregatedColumn(fieldName);
	}

	private boolean isAggregated(final String fieldName)
	{
		return _viewBinding.isAggregated(fieldName);
	}

	private String replaceTableNameWithTableAlias(final String sql)
	{
		return _viewBinding.replaceTableNameWithTableAlias(sql);
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
			final List<DocumentFilter> filters,
			final List<DocumentQueryOrderBy> orderBys,
			final int queryLimit)
	{
		if (!hasGroupingFields())
		{
			final SqlAndParams sqlCreateSelection = buildSqlCreateSelection_WithoutGrouping(viewEvalCtx, newViewId, filters, orderBys, queryLimit);
			return SqlCreateSelection.builder().sqlCreateSelection(sqlCreateSelection).build();
		}
		else
		{
			final SqlAndParams sqlCreateSelectionLines = buildSqlCreateSelectionLines_WithGrouping(viewEvalCtx, newViewId, filters, queryLimit);
			final SqlAndParams sqlCreateSelection = buildSqlCreateSelectionFromSelectionLines(viewEvalCtx, newViewId, orderBys);
			return SqlCreateSelection.builder().sqlCreateSelection(sqlCreateSelection).sqlCreateSelectionLines(sqlCreateSelectionLines).build();
		}
	}

	private SqlAndParams buildSqlCreateSelection_WithoutGrouping(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId newViewId,
			final List<DocumentFilter> filters,
			final List<DocumentQueryOrderBy> orderBys,
			final int queryLimit)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnName = getKeyColumnName();

		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer();

		//
		// INSERT INTO T_WEBUI_ViewSelection[Line] (...)
		sqlBuilder.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
				+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line // SeqNo
				+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
				+ ")");

		//
		// SELECT ... FROM ... WHERE 1=1
		final List<Object> sqlParams = new ArrayList<>();
		{
			IStringExpression sqlOrderBy = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy).buildSqlOrderBy(orderBys);
			if (sqlOrderBy == null || sqlOrderBy.isNullExpression())
			{
				sqlOrderBy = ConstantStringExpression.of(keyColumnName);
			}

			final IStringExpression sqlSeqNo = IStringExpression.composer()
					.append("row_number() OVER (ORDER BY ").append(sqlOrderBy).append(")")
					.build();
			final IStringExpression sqlRecordId = ConstantStringExpression.of(keyColumnName);

			sqlBuilder.append(
					IStringExpression.composer()
							.append("\n SELECT ")
							.append("\n  ?") // UUID
							.append("\n, ").append(sqlSeqNo) // Line/SeqNo
							.append("\n, ").append(sqlRecordId) // Record_ID
							//
							.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
							.append("\n WHERE 1=1 ")
							.wrap(AccessSqlStringExpression.wrapper(sqlTableAlias, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
			);
			sqlParams.add(newViewId.getViewId());
		}

		//
		// WHERE clause (from query)
		{
			final SqlParamsCollector sqlWhereClauseParams = SqlParamsCollector.newInstance();
			final IStringExpression sqlWhereClause = buildSqlWhereClause(sqlWhereClauseParams, filters, SqlOptions.usingTableAlias(sqlTableAlias));

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
		return new SqlAndParams(sql, sqlParams);
	}

	private SqlAndParams buildSqlCreateSelectionLines_WithGrouping(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId newViewId,
			final List<DocumentFilter> filters,
			final int queryLimit)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnName = getKeyColumnName();

		final Set<String> groupByFieldNames = getGroupByFieldNames();
		Check.assumeNotEmpty(groupByFieldNames, "groupByFieldNames is not empty"); // shall not happen

		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer();

		//
		// INSERT INTO T_WEBUI_ViewSelectionLine (...)
		sqlBuilder.append("INSERT INTO " + I_T_WEBUI_ViewSelectionLine.Table_Name + " ("
				+ " " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ ", " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID
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
							.wrap(AccessSqlStringExpression.wrapper(sqlTableAlias, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
			);
			sqlParams.add(newViewId.getViewId());
		}

		//
		// WHERE clause (from query)
		{
			final SqlParamsCollector sqlWhereClauseParams = SqlParamsCollector.newInstance();
			final IStringExpression sqlWhereClause = buildSqlWhereClause(sqlWhereClauseParams, filters, SqlOptions.usingTableAlias(sqlTableAlias));

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

		return new SqlAndParams(sql, sqlParams);
	}

	public SqlAndParams buildSqlCreateSelectionFromSelectionLines(final ViewEvaluationCtx viewEvalCtx, final ViewId newViewId, final List<DocumentQueryOrderBy> orderBys)
	{
		final String lineTableName = getTableName();
		final String lineTableAlias = getTableAlias();
		final String lineKeyColumnName = getKeyColumnName();

		final SqlOrderByBindings sqlOrderByBindings = fieldName -> {
			if (isKeyFieldName(fieldName))
			{
				return ConstantStringExpression.of("sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID);
			}
			else if (isGroupBy(fieldName))
			{
				return getFieldOrderBy(fieldName);
			}
			else if (isAggregated(fieldName))
			{
				return ConstantStringExpression.of(getSqlAggregatedColumn(fieldName));
			}
			else
			{
				// shall not happen
				return null;
			}
		};

		final List<DocumentQueryOrderBy> orderBysEffective = orderBys.stream()
				.flatMap(this::flatMapEffectiveFieldNames)
				.filter(orderBy -> isKeyFieldName(orderBy.getFieldName()) || isGroupBy(orderBy.getFieldName()) || isAggregated(orderBy.getFieldName()))
				.collect(ImmutableList.toImmutableList());

		final IStringExpression sqlOrderByExpr = SqlDocumentOrderByBuilder.newInstance(sqlOrderByBindings).buildSqlOrderBy(orderBysEffective);
		final String sqlOrderBy;
		if (sqlOrderByExpr == null || sqlOrderByExpr.isNullExpression())
		{
			sqlOrderBy = "sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID;
		}
		else
		{
			sqlOrderBy = sqlOrderByExpr.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);
		}

		final String sqlFrom = "SELECT "
				+ "\n sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ "\n, sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID
				+ "\n FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name + " sl "
				+ "\n INNER JOIN " + lineTableName + " " + lineTableAlias + " ON (" + lineTableAlias + "." + lineKeyColumnName + " = sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID + ")" // join lines
				+ "\n WHERE " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=?"
				+ "\n GROUP BY "
				+ "\n sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ "\n, sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID
				+ "\n, " + getGroupByFieldNames().stream().collect(Collectors.joining(", "))
				+ "\n ORDER BY " + sqlOrderBy;

		final String sqlCreateSelectionFromLines = "INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + "("
				+ "\n " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
				+ "\n, " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
				+ "\n, " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line // SeqNo
				+ "\n)"
				+ "\n SELECT "
				+ "\n   sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
				+ "\n , sl." + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID
				+ "\n , row_number() OVER ()" // SeqNo
				+ "\n FROM (" + sqlFrom + ") sl";

		final List<Object> sqlCreateSelectionFromLinesParams = Arrays.asList(newViewId.getViewId());

		return new SqlAndParams(sqlCreateSelectionFromLines, sqlCreateSelectionFromLinesParams);
	}

	private final IStringExpression buildSqlWhereClause(final SqlParamsCollector sqlParams, @Nullable final List<DocumentFilter> filters, final SqlOptions sqlOpts)
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
			final String sqlFilters = getSqlDocumentFilterConverter().getSql(sqlParams, filters, sqlOpts);
			if (!Check.isEmpty(sqlFilters, true))
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* filters */ (\n").append(sqlFilters).append(")\n");
			}
		}

		return sqlWhereClauseBuilder.build();
	}

	/**
	 * @return
	 *
	 *         <pre>
	 * INSERT INTO T_WEBUI_ViewSelection (UUID, Line, Record_ID)
	 * SELECT ... FROM T_WEBUI_ViewSelection sel INNER JOIN ourTable WHERE sel.UUID=[fromUUID]
	 *         </pre>
	 */
	public SqlAndParams buildSqlCreateSelectionFromSelection(final ViewEvaluationCtx viewEvalCtx,
			final ViewId newViewId,
			final String fromSelectionId,
			final List<DocumentQueryOrderBy> orderBys)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnName = getKeyColumnName();
		final String keyColumnNameFQ = sqlTableAlias + "." + keyColumnName;

		final String sqlOrderBys = replaceTableNameWithTableAlias(
				SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
						.buildSqlOrderBy(orderBys.stream()
								.flatMap(this::flatMapEffectiveFieldNames)
								.collect(ImmutableList.toImmutableList()))
						.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail));

		//
		// INSERT INTO T_WEBUI_ViewSelection (UUID, Line, Record_ID)
		final StringBuilder sqlBuilder = new StringBuilder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
						+ ")");

		//
		// SELECT ... FROM T_WEBUI_ViewSelection sel INNER JOIN ourTable WHERE sel.UUID=[fromUUID]
		{
			sqlBuilder
					.append("\n SELECT ")
					.append("\n  ?") // newUUID
					.append("\n, ").append("row_number() OVER (ORDER BY ").append(sqlOrderBys).append(")") // Line
					.append("\n, ").append(keyColumnNameFQ) // Record_ID
					.append("\n FROM ").append(I_T_WEBUI_ViewSelection.Table_Name).append(" sel")
					.append("\n LEFT OUTER JOIN ").append(sqlTableName).append(" ").append(sqlTableAlias).append(" ON (").append(keyColumnNameFQ).append("=").append("sel.")
					.append(I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID).append(")")
					.append("\n WHERE sel.").append(I_T_WEBUI_ViewSelection.COLUMNNAME_UUID).append("=?") // fromUUID
			;
		}

		//
		final String sql = sqlBuilder.toString();
		return new SqlAndParams(sql, ImmutableList.of(newViewId.getViewId(), fromSelectionId));
	}

	/**
	 * @return
	 *
	 *         <pre>
	 * 	INSERT INTO T_WEBUI_ViewSelectionLine (UUID, Line, Record_ID, Line_ID) ...
	 *	SELECT ... FROM T_WEBUI_ViewSelectionLine sl INNER JOIN ourTable on (Line_ID)  WHERE sel.UUID=[fromUUID]
	 *         </pre>
	 */
	public SqlAndParams buildSqlCreateSelectionLinesFromSelectionLines(final ViewEvaluationCtx viewEvalCtx,
			final ViewId newViewId,
			final String fromSelectionId)
	{
		//
		// INSERT INTO T_WEBUI_ViewSelectionLine (UUID, Line, Record_ID, Line_ID)
		final StringBuilder sqlBuilder = new StringBuilder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelectionLine.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID
						// + ", " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line // SeqNo
						+ ", " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID
						+ ", " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID
						+ ")");

		//
		// SELECT ... FROM T_WEBUI_ViewSelectionLine sl INNER JOIN ourTable on (Line_ID) WHERE sel.UUID=[fromUUID]
		{
			sqlBuilder
					.append("\n SELECT ")
					.append("\n  ?") // newUUID
					.append("\n, sl.").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID) // Record_ID
					.append("\n, sl.").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID) // Line_ID
					.append("\n FROM ").append(I_T_WEBUI_ViewSelectionLine.Table_Name).append(" sl ")
					.append("\n WHERE sl.").append(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID).append("=?") // fromUUID
			;
		}

		//
		final String sql = sqlBuilder.toString();
		return new SqlAndParams(sql, ImmutableList.of(newViewId.getViewId(), fromSelectionId));
	}

	public String buildSqlWhereClause(final String selectionId, final DocumentIdsSelection rowIds)
	{
		final String sqlTableName = getTableName();
		final String sqlKeyColumnName = getKeyColumnName();
		final String sqlKeyColumnNameFK = sqlTableName + "." + sqlKeyColumnName;
		final SqlViewRowIdsConverter rowIdsConverter = getRowIdsConverter();
		return buildSqlWhereClause(sqlKeyColumnNameFK, selectionId, rowIds, rowIdsConverter);
	}

	public static String buildSqlWhereClause(
			@NonNull final String sqlKeyColumnNameFK,
			@NonNull final String selectionId,
			@NonNull final DocumentIdsSelection rowIds,
			@NonNull final SqlViewRowIdsConverter rowIdsConverter)
	{
		if (rowIds.isEmpty())
		{
			new AdempiereException("got empty rowIds")
					.throwIfDeveloperModeOrLogWarningElse(logger);
			return "1=0";
		}

		final StringBuilder sqlWhereClause = new StringBuilder();
		sqlWhereClause.append("exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " sel "
				+ " where "
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId)
				+ " and sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + "=" + sqlKeyColumnNameFK
				+ ")");

		if (!rowIds.isAll())
		{
			final Set<Integer> recordIds = rowIdsConverter.convertToRecordIds(rowIds);
			sqlWhereClause.append(" AND ").append(sqlKeyColumnNameFK).append(" IN ").append(DB.buildSqlList(recordIds));
		}

		return sqlWhereClause.toString();
	}

	public String buildSqlDeleteRowIdsFromSelection(@NonNull final String selectionId, @NonNull final DocumentIdsSelection rowIds)
	{
		if (rowIds.isEmpty())
		{
			return null;
		}

		final StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId));

		if (rowIds.isAll())
		{
			// nothing
		}
		else
		{
			final Set<Integer> recordIds = rowIds.toIntSet();
			Check.assumeNotEmpty(recordIds, "recordIds is not empty"); // shall not happen
			sql.append(" AND " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + " IN " + DB.buildSqlList(recordIds));
		}

		return sql.toString();
	}

	public String buildSqlAddRowIdsFromSelection(final List<Object> sqlParams, final String selectionId, final DocumentId rowId)
	{
		final int rowIdInt = rowId.toInt();
		final String sql = "INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
				+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
				+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
				+ ")"
				+ " SELECT "
				+ " ? as UUID " // UUID
				+ ", (select coalesce(max(Line), 0) + 1 from T_WEBUI_ViewSelection z where z.UUID=?) as Line" // Line
				+ ", ? as Record_ID"  // Record_ID
				+ " WHERE "
				+ " NOT EXISTS(select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " z where z.UUID=? and z.Record_ID=?)";
		// TODO: we should also validate if the rowId is allowed to be part of this selection (e.g. enforce entity binding's SQL where clause)

		sqlParams.add(selectionId); // UUID
		sqlParams.add(selectionId); // for Line
		sqlParams.add(rowIdInt); // Record_ID
		sqlParams.add(selectionId); // for NOT EXISTS
		sqlParams.add(rowIdInt); // for NOT EXISTS

		return sql;
	}

	public String buildSqlRetrieveSize(final List<Object> sqlParams, final String selectionId)
	{
		Check.assumeNotEmpty(selectionId, "selectionId is not empty");
		sqlParams.add(selectionId);
		return "SELECT COUNT(1) FROM " + I_T_WEBUI_ViewSelection.Table_Name + " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?";
	}

	public String buildSqlCount(final List<Object> sqlParams, final String selectionId, final DocumentIdsSelection rowIds)
	{
		Check.assumeNotEmpty(selectionId, "selectionId is not empty");

		sqlParams.add(selectionId);
		final StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(1) FROM " + I_T_WEBUI_ViewSelection.Table_Name + " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?");

		if (rowIds.isAll())
		{
			// nothing
		}
		else if (rowIds.isEmpty())
		{
			throw new IllegalArgumentException("empty rowIds is not allowed");
		}
		else
		{
			final Set<Integer> recordIds = rowIds.toIntSet();
			sql.append(" AND ").append(DB.buildSqlList(I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID, recordIds, sqlParams));
		}

		return sql.toString();
	}

	public <T> IQueryFilter<T> buildInSelectionQueryFilter(final String selectionId)
	{
		final String tableName = getTableName();
		final String keyColumnName = getKeyColumnName();

		final String sql = "exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name
				+ " where "
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?"
				+ " and " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + "=" + tableName + "." + keyColumnName
				+ ")";
		final List<Object> sqlParams = ImmutableList.of(selectionId);

		return TypedSqlQueryFilter.of(sql, sqlParams);
	}

	public String buildSqlDeleteSelection(@NonNull final String selectionId)
	{
		return "DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId);
	}

	public String buildSqlDeleteSelectionLines(@NonNull final String selectionId)
	{
		return "DELETE FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId);
	}

	public static SqlAndParams buildSqlSelectRecordIdsForLineIds(@NonNull final String selectionId, final Collection<Integer> lineIds)
	{
		Check.assumeNotEmpty(lineIds, "lineIds is not empty");

		final List<Object> sqlParams = new ArrayList<>();
		sqlParams.add(selectionId);

		final String sql = "SELECT " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Record_ID
				+ " FROM " + I_T_WEBUI_ViewSelectionLine.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelectionLine.COLUMNNAME_UUID + "=?"
				+ " AND " + DB.buildSqlList(I_T_WEBUI_ViewSelectionLine.COLUMNNAME_Line_ID, lineIds, sqlParams);

		return SqlAndParams.of(sql, sqlParams);
	}
}
