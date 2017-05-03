package de.metas.ui.web.view.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.CompositeStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.impl.AccessSqlStringExpression;
import org.adempiere.util.Check;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFiltersBuilder;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.sql.SqlDocumentOrderByBuilder;

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
// TODO: used only in one place.. consider removing it from here
public final class SqlViewSelectionQueryBuilder
{
	public static final SqlViewSelectionQueryBuilder newInstance(final SqlEntityBinding entityBinding)
	{
		return new SqlViewSelectionQueryBuilder(entityBinding);
	}

	private final SqlEntityBinding entityBinding;

	private SqlViewSelectionQueryBuilder(final SqlEntityBinding entityBinding)
	{
		this.entityBinding = entityBinding;
	}

	private String getTableName()
	{
		return entityBinding.getTableName();
	}

	private String getTableAlias()
	{
		return entityBinding.getTableAlias();
	}

	public String getKeyColumnName()
	{
		return entityBinding.getKeyColumnName();
	}

	public IStringExpression getSqlWhereClause()
	{
		return entityBinding.getSqlWhereClause();
	}

	private IStringExpression getFieldOrderBy(final String fieldName)
	{
		return entityBinding.getFieldOrderBy(fieldName);
	}

	public String buildSqlCreateSelectionFrom( //
			final List<Object> sqlParams //
			, final ViewEvaluationCtx viewEvalCtx //
			, final ViewId newViewId //
			, final List<DocumentFilter> filters //
			, final List<DocumentQueryOrderBy> orderBys //
			, final int queryLimit //
	)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnNameFQ = getKeyColumnName();

		//
		// INSERT INTO T_WEBUI_ViewSelection (UUID, Line, Record_ID)
		final CompositeStringExpression.Builder sqlBuilder = IStringExpression.composer()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name + " ("
						+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID
						+ ")");

		//
		// SELECT ... FROM ... WHERE 1=1
		{
			IStringExpression sqlOrderBy = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy).buildSqlOrderBy(orderBys);
			if (sqlOrderBy == null || sqlOrderBy.isNullExpression())
			{
				sqlOrderBy = ConstantStringExpression.of(keyColumnNameFQ);
			}

			sqlBuilder.append(
					IStringExpression.composer()
							.append("\n SELECT ")
							.append("\n  ?") // UUID
							.append("\n, ").append("row_number() OVER (ORDER BY ").append(sqlOrderBy).append(")") // Line
							.append("\n, ").append(keyColumnNameFQ) // Record_ID
							.append("\n FROM ").append(sqlTableName).append(" ").append(sqlTableAlias)
							.append("\n WHERE 1=1 ")
							.wrap(AccessSqlStringExpression.wrapper(sqlTableAlias, IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO)) // security
			);
			sqlParams.add(newViewId.getViewId());
		}

		//
		// WHERE clause (from query)
		{
			final List<Object> sqlWhereClauseParams = new ArrayList<>();
			final IStringExpression sqlWhereClause = buildSqlWhereClause(sqlWhereClauseParams, filters);

			if (sqlWhereClause != null && !sqlWhereClause.isNullExpression())
			{
				sqlBuilder.append("\n AND (\n").append(sqlWhereClause).append("\n)");
				sqlParams.addAll(sqlWhereClauseParams);
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
		return sql;
	}

	private final IStringExpression buildSqlWhereClause(final List<Object> sqlParams, final List<DocumentFilter> filters)
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
		{
			final String sqlFilters = SqlDocumentFiltersBuilder.newInstance(entityBinding)
					.addFilters(filters)
					.buildSqlWhereClause(sqlParams);
			if (!Check.isEmpty(sqlFilters, true))
			{
				sqlWhereClauseBuilder.appendIfNotEmpty("\n AND ");
				sqlWhereClauseBuilder.append(" /* filters */ (\n").append(sqlFilters).append(")\n");
			}
		}

		return sqlWhereClauseBuilder.build();
	}

	public String buildSqlCreateSelectionFromSelection(final ViewEvaluationCtx viewEvalCtx, final List<DocumentQueryOrderBy> orderBys)
	{
		final String sqlTableName = getTableName();
		final String sqlTableAlias = getTableAlias();
		final String keyColumnName = getKeyColumnName();
		final String keyColumnNameFQ = sqlTableAlias + "." + keyColumnName;

		final String sqlOrderBys = SqlDocumentOrderByBuilder.newInstance(this::getFieldOrderBy)
				.buildSqlOrderBy(orderBys)
				.evaluate(viewEvalCtx.toEvaluatee(), OnVariableNotFound.Fail);

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

		return sqlBuilder.toString();
	}

	public String buildSqlWhereClause(final String selectionId, final Collection<DocumentId> rowIds)
	{
		final String sqlTableName = getTableName();
		final String sqlKeyColumnName = getKeyColumnName();

		final StringBuilder sqlWhereClause = new StringBuilder();
		sqlWhereClause.append("exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name + " sel "
				+ " where "
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId)
				+ " and sel." + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + "=" + sqlTableName + "." + sqlKeyColumnName
				+ ")");

		if (!Check.isEmpty(rowIds))
		{
			final Set<Integer> rowIdsAsInts = DocumentId.toIntSet(rowIds);
			sqlWhereClause.append(" AND ").append(sqlKeyColumnName).append(" IN ").append(DB.buildSqlList(rowIdsAsInts));
		}

		return sqlWhereClause.toString();

	}

	public String buildSqlDeleteRowIdsFromSelection(final String selectionId, final Collection<DocumentId> rowIds)
	{
		final Set<Integer> rowIdsAsInts = DocumentId.toIntSet(rowIds);
		if (rowIdsAsInts.isEmpty())
		{
			return null;
		}

		return "DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(selectionId)
				+ " AND " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + " IN " + DB.buildSqlList(rowIdsAsInts);
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
				+ ", (select max(Line) from T_WEBUI_ViewSelection z where z.UUID=?) as Line" // Line
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

	public String buildSqlCount(final List<Object> sqlParams, final String selectionId, final Collection<DocumentId> rowIds)
	{
		Check.assumeNotEmpty(selectionId, "selectionId is not empty");
		final Set<Integer> recordIds = DocumentId.toIntSet(rowIds);
		Check.assumeNotEmpty(recordIds, "recordIds is not empty");

		sqlParams.add(selectionId);
		return "SELECT COUNT(1) FROM " + I_T_WEBUI_ViewSelection.Table_Name + " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?"
				+ " AND " + DB.buildSqlList(I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID, rowIds, sqlParams);
	}

	public <T> IQueryFilter<T> buildInSelectionQueryFilter(final String selectionId)
	{
		final String tableName = entityBinding.getTableName();
		final String keyColumnName = entityBinding.getKeyColumnName();

		final String sql = "exists (select 1 from " + I_T_WEBUI_ViewSelection.Table_Name
				+ " where "
				+ " " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?"
				+ " and " + I_T_WEBUI_ViewSelection.COLUMNNAME_Record_ID + "=" + tableName + "." + keyColumnName
				+ ")";
		final List<Object> sqlParams = ImmutableList.of(selectionId);

		return new TypedSqlQueryFilter<>(sql, sqlParams);
	}

}
