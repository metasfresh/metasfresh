package de.metas.ui.web.material.stockperweek;

import com.google.common.collect.ImmutableList;
import de.metas.material.dispo.model.I_MD_Stock_PerWeek_V;
import de.metas.security.IUserRolePermissions;
import de.metas.security.impl.AccessSqlStringExpression;
import de.metas.security.permissions.Access;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.AddRemoveChangedRowIdsCollector;
import de.metas.ui.web.view.SqlViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.view.ViewRowIdsOrderedSelectionFactory;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlAndParamsExpression;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.view.descriptor.SqlViewSelectionQueryBuilder;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.expression.api.IStringExpressionWrapper;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Function-backed selection factory for window 542159 ("Bestand pro Woche", view {@link I_MD_Stock_PerWeek_V}).
 * With a single-product filter it builds the selection from {@code MD_Stock_PerWeek_fn(product, warehouse)}
 * (filter pushed into the indexed {@code MD_Candidate} scan) instead of materializing the ~785k-row view;
 * output is byte-identical to the view. The applied product/warehouse are persisted in IntKey2/IntKey3 so the
 * page render can re-parameterize the function. Without a single-product filter it delegates to the standard
 * factory (preserving the {@code queryIfNoFilters=false} open-empty behavior).
 */
public class StockPerWeekSelectionFactory implements ViewRowIdsOrderedSelectionFactory
{
	public static final WindowId WINDOW_ID = WindowId.of(542159);

	private static final String FUNCTION_NAME = "MD_Stock_PerWeek_fn";
	private static final String FUNCTION_SOURCE_RELATION_SQL = FUNCTION_NAME + "(?,?)";
	private static final String KEY_COLUMN = I_MD_Stock_PerWeek_V.COLUMNNAME_MD_Stock_PerWeek_V_ID;
	private static final String FUNCTION_ALIAS = "fn";
	/** Window's default sort, used when {@code orderBys} is empty. */
	private static final ImmutableList<String> DEFAULT_ORDER_FIELD_NAMES = ImmutableList.of(
			I_MD_Stock_PerWeek_V.COLUMNNAME_WeekStartDate,
			I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID,
			I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID);

	@NonNull private final SqlViewBinding sqlViewBinding;
	@NonNull private final ViewRowIdsOrderedSelectionFactory delegate;

	public StockPerWeekSelectionFactory(@NonNull final SqlViewBinding sqlViewBinding)
	{
		this.sqlViewBinding = sqlViewBinding;
		this.delegate = SqlViewRowIdsOrderedSelectionFactory.of(sqlViewBinding);
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelection(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewId viewId,
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final boolean applySecurityRestrictions,
			final SqlDocumentFilterConverterContext context)
	{
		final Integer productId = extractFilterValue(filters, I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID);
		if (productId == null)
		{
			// no single-product filter (absent / multi-value / range) => standard path (open-empty guard + warehouse/week-only filtering)
			return delegate.createOrderedSelection(viewEvalCtx, viewId, filters, orderBys, applySecurityRestrictions, context);
		}

		final Integer warehouseId = extractFilterValue(filters, I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID);

		final String rowNumberOrderBySql = buildRowNumberOrderBySql(orderBys);

		final SqlAndParamsExpression.Builder sqlInsert = SqlAndParamsExpression.builder()
				.append("INSERT INTO " + I_T_WEBUI_ViewSelection.Table_Name
						+ " (" + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_Line
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey1
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey2
						+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey3 + ")\n")
				.append("SELECT ?", viewId.getViewId())
				.append(", row_number() OVER (ORDER BY " + rowNumberOrderBySql + ")"
						+ ", " + FUNCTION_ALIAS + "." + KEY_COLUMN
						+ ", " + FUNCTION_ALIAS + "." + I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID
						+ ", " + FUNCTION_ALIAS + "." + I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID + "\n"
						+ " FROM " + FUNCTION_NAME + "(?, ?) " + FUNCTION_ALIAS, productId, warehouseId)
				.append("\n WHERE 1=1 ")
				.wrap(securityRestrictionsWrapper(applySecurityRestrictions));

		final SqlAndParams sqlAndParams = sqlInsert.build().evaluate(viewEvalCtx.toEvaluatee());
		final long rowsCount = DB.executeUpdateAndThrowExceptionOnFail(
				sqlAndParams.getSql(), sqlAndParams.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);

		return ViewRowIdsOrderedSelection.builder()
				.viewId(viewId)
				.size(rowsCount)
				.orderBys(orderBys)
				.queryLimit(QueryLimit.NO_LIMIT)
				.build();
	}

	/** Same per-row client/org read-access filter the standard selection applies; the fn output carries AD_Client_ID/AD_Org_ID so it resolves against the fn alias. */
	private static IStringExpressionWrapper securityRestrictionsWrapper(final boolean applySecurityRestrictions)
	{
		if (applySecurityRestrictions)
		{
			return AccessSqlStringExpression.wrapper(FUNCTION_ALIAS, IUserRolePermissions.SQL_FULLYQUALIFIED, Access.READ);
		}
		else
		{
			return expression -> expression;
		}
	}

	/** ORDER BY for {@code row_number()}: honors {@code orderBys} (matching the selection metadata), falls back to the window default tuple, and appends any uncovered default columns as deterministic tie-breakers. */
	private static String buildRowNumberOrderBySql(@NonNull final DocumentQueryOrderByList orderBys)
	{
		final LinkedHashSet<String> coveredFieldNames = new LinkedHashSet<>();
		final StringBuilder sql = new StringBuilder();

		for (final DocumentQueryOrderBy orderBy : orderBys.toList())
		{
			final String fieldName = orderBy.getFieldName();
			if (!coveredFieldNames.add(fieldName))
			{
				continue; // duplicate field name within orderBys; already appended
			}
			if (sql.length() > 0)
			{
				sql.append(", ");
			}
			sql.append(FUNCTION_ALIAS).append('.').append(fieldName)
					.append(orderBy.isAscending() ? " ASC" : " DESC")
					.append(orderBy.isNullsLast() ? " NULLS LAST" : " NULLS FIRST");
		}

		for (final String fieldName : DEFAULT_ORDER_FIELD_NAMES)
		{
			if (!coveredFieldNames.add(fieldName))
			{
				continue; // already covered by orderBys above
			}
			if (sql.length() > 0)
			{
				sql.append(", ");
			}
			sql.append(FUNCTION_ALIAS).append('.').append(fieldName);
		}

		return sql.toString();
	}

	@Override
	public ViewRowIdsOrderedSelection createOrderedSelectionFromSelection(
			final ViewEvaluationCtx viewEvalCtx,
			final ViewRowIdsOrderedSelection fromSelection,
			final DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final SqlDocumentFilterConverterContext filterConverterCtx)
	{
		final AppliedFilter appliedFilter = readAppliedFilter(fromSelection.getSelectionId());
		if (appliedFilter == null)
		{
			// source selection not function-backed (e.g. open-empty, no rows) => standard path
			return delegate.createOrderedSelectionFromSelection(viewEvalCtx, fromSelection, filters, orderBys, filterConverterCtx);
		}

		final ViewId newViewId = ViewId.random(fromSelection.getWindowId());

		// re-sort / facet-filter the same product's rows via the function (not the view); fn aliased as the view so the standard builder's SQL resolves unchanged
		final SqlAndParams sqlCreateSelection = SqlViewSelectionQueryBuilder.newInstance(sqlViewBinding)
				.buildSqlCreateSelectionFromSelection(
						viewEvalCtx,
						newViewId,
						fromSelection.getSelectionId(),
						filters,
						orderBys,
						filterConverterCtx,
						FUNCTION_SOURCE_RELATION_SQL,
						appliedFilter.toFunctionParams());
		final long rowsCount = DB.executeUpdateAndThrowExceptionOnFail(
				sqlCreateSelection.getSql(), sqlCreateSelection.getSqlParamsArray(), ITrx.TRXNAME_ThreadInherited);

		// carry product/warehouse onto the new rows (the standard builder only inserts UUID/Line/key) so the render can re-parameterize the function
		if (rowsCount > 0)
		{
			carryAppliedFilter(newViewId.getViewId(), appliedFilter);
		}

		return ViewRowIdsOrderedSelection.builder()
				.viewId(newViewId)
				.size(rowsCount)
				.orderBys(orderBys)
				.queryLimit(fromSelection.getQueryLimit())
				.build();
	}

	/** Single {@code EQUAL} positive int value of {@code columnName}, or {@code null} if absent / multi-value ({@code IN_ARRAY}) / range ({@code BETWEEN}) — null (never "the first value") makes the caller take the standard path, which a single fn param cannot. */
	@Nullable
	private static Integer extractFilterValue(@NonNull final DocumentFilterList filters, @NonNull final String columnName)
	{
		for (final DocumentFilter filter : filters.toList())
		{
			final DocumentFilterParam param = filter.getParameterOrNull(columnName);
			if (param == null)
			{
				continue;
			}

			if (param.getOperator() != DocumentFilterParam.Operator.EQUAL)
			{
				// multi-value/range: fn takes one product; "the first" would silently drop the rest → bail to the standard path
				return null;
			}

			final int value = param.getValueAsInt(-1);
			if (value > 0)
			{
				return value;
			}
		}
		return null;
	}

	/**
	 * @return the product/warehouse persisted (IntKey2/IntKey3) with the given selection's rows, or
	 *         {@code null} if the selection has no rows or was not function-backed.
	 */
	@Nullable
	public static AppliedFilter readAppliedFilter(@NonNull final String selectionId)
	{
		final String sql = "SELECT " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey2
				+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey3
				+ " FROM " + I_T_WEBUI_ViewSelection.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?"
				+ " LIMIT 1";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, new Object[] { selectionId });
			rs = pstmt.executeQuery();
			if (!rs.next())
			{
				return null;
			}

			final int productId = rs.getInt(1);
			if (rs.wasNull() || productId <= 0)
			{
				return null;
			}
			final int warehouseIdRaw = rs.getInt(2);
			final Integer warehouseId = rs.wasNull() ? null : warehouseIdRaw;
			return new AppliedFilter(productId, warehouseId);
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, new Object[] { selectionId });
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static void carryAppliedFilter(@NonNull final String selectionId, @NonNull final AppliedFilter appliedFilter)
	{
		final String sql = "UPDATE " + I_T_WEBUI_ViewSelection.Table_Name
				+ " SET " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey2 + "=?"
				+ ", " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey3 + "=?"
				+ " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=?";
		DB.executeUpdateAndThrowExceptionOnFail(
				sql,
				new Object[] { appliedFilter.productId, appliedFilter.warehouseId, selectionId },
				ITrx.TRXNAME_ThreadInherited);
	}

	// remaining operations are source-agnostic (persisted T_WEBUI_ViewSelection by UUID/IntKey1) => delegated unchanged
	@Override
	public SqlViewRowsWhereClause getSqlWhereClause(final ViewId viewId, final DocumentIdsSelection rowIds)
	{
		return delegate.getSqlWhereClause(viewId, rowIds);
	}

	@Override
	public ViewRowIdsOrderedSelection addRowIdsToSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		return delegate.addRowIdsToSelection(selection, rowIds);
	}

	@Override
	public ViewRowIdsOrderedSelection removeRowIdsFromSelection(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		return delegate.removeRowIdsFromSelection(selection, rowIds);
	}

	@Override
	public ViewRowIdsOrderedSelection removeAndAddRowIdsFromSelection(
			@NonNull final ViewRowIdsOrderedSelection selection,
			@NonNull final DocumentIdsSelection rowIdsToRemove,
			@NonNull final DocumentIdsSelection rowIdsToAdd,
			@NonNull final AddRemoveChangedRowIdsCollector changesCollector)
	{
		return delegate.removeAndAddRowIdsFromSelection(selection, rowIdsToRemove, rowIdsToAdd, changesCollector);
	}

	@Override
	public boolean containsAnyOfRowIds(final ViewRowIdsOrderedSelection selection, final DocumentIdsSelection rowIds)
	{
		return delegate.containsAnyOfRowIds(selection, rowIds);
	}

	@Override
	public void deleteSelections(final Set<String> selectionIds)
	{
		delegate.deleteSelections(selectionIds);
	}

	@Override
	public void scheduleDeleteSelections(final Set<String> selectionIds)
	{
		delegate.scheduleDeleteSelections(selectionIds);
	}

	/** The product (+ optional warehouse) filter that produced a selection, persisted in IntKey2/IntKey3. */
	public static final class AppliedFilter
	{
		private final int productId;
		@Nullable private final Integer warehouseId;

		private AppliedFilter(final int productId, @Nullable final Integer warehouseId)
		{
			this.productId = productId;
			this.warehouseId = warehouseId;
		}

		/** Parameters for {@code MD_Stock_PerWeek_fn(product, warehouse)} — warehouse null = all warehouses. */
		public List<Object> toFunctionParams()
		{
			return Arrays.asList((Object)productId, warehouseId);
		}
	}
}
