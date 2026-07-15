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
 * Function-backed selection factory for the standalone "Bestand pro Woche" window (542159,
 * view {@link I_MD_Stock_PerWeek_V}).
 * <p>
 * When a product filter is applied, the row selection is built by scanning the parameterized function
 * {@code MD_Stock_PerWeek_fn(M_Product_ID, M_Warehouse_ID)} — which pushes the filter into the indexed
 * base {@code MD_Candidate} scan (partial index {@code md_candidate_perweek_pw_idx}) — instead of
 * materializing the entire {@link I_MD_Stock_PerWeek_V} (~785k rows) and filtering afterwards. The output
 * is byte-identical to the view for the same filter (same columns, same MD5 PK, same ATP semantics).
 * <p>
 * The applied product/warehouse are persisted alongside each selection row (IntKey2 / IntKey3) so that the
 * page render ({@code StockPerWeekViewDataRepository}) can re-parameterize the same function. Both
 * selection-creation entry points source from the function and carry these keys:
 * <ul>
 *   <li>{@link #createOrderedSelection} — initial selection when the user applies the product filter;</li>
 *   <li>{@link #createOrderedSelectionFromSelection} — re-sort (column header) and facet filtering, which
 *       derive a new selection from the current one. Without this, those interactions would fall back to the
 *       standard view-materializing builder AND drop IntKey2/IntKey3, leaving the render unable to
 *       re-parameterize the function (it would call the function with a null/zero product and render blank).</li>
 * </ul>
 * When no product filter is applied, {@link #createOrderedSelection} delegates to the standard factory, which
 * — together with the window's {@code queryIfNoFilters=false} binding customizer — keeps the "open empty /
 * please filter first" behavior intact.
 * <p>
 * The row add/remove operations are delegated unchanged: they are driven by change events on the view's own
 * table, and {@code MD_Stock_PerWeek_V} is a read-only computed view with no editable rows, so no such events
 * are ever published for this window and the path is not reachable here.
 */
public class StockPerWeekSelectionFactory implements ViewRowIdsOrderedSelectionFactory
{
	public static final WindowId WINDOW_ID = WindowId.of(542159);

	private static final String FUNCTION_NAME = "MD_Stock_PerWeek_fn";
	/** Function relation aliased with the view's table name so all field/key/display expressions resolve unchanged. */
	private static final String FUNCTION_SOURCE_RELATION_SQL = FUNCTION_NAME + "(?,?)";
	private static final String KEY_COLUMN = I_MD_Stock_PerWeek_V.COLUMNNAME_MD_Stock_PerWeek_V_ID;
	/** Alias used for the function relation in {@link #createOrderedSelection}'s hand-built SQL. */
	private static final String FUNCTION_ALIAS = "fn";
	/** Default row-numbering order (window's default sort), used when {@code orderBys} is empty. */
	private static final ImmutableList<String> DEFAULT_ORDER_FIELD_NAMES = ImmutableList.of(
			I_MD_Stock_PerWeek_V.COLUMNNAME_WeekStartDate,
			I_MD_Stock_PerWeek_V.COLUMNNAME_M_Warehouse_ID,
			I_MD_Stock_PerWeek_V.COLUMNNAME_M_Product_ID);

	@NonNull private final SqlViewBinding sqlViewBinding;
	/** Standard factory used for everything except building the (product-filtered) ordered selection. */
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
			// No product filter, OR the product filter has more than one value / is a range (see
			// extractFilterValue) => keep the standard behavior (incl. the queryIfNoFilters=false "please filter
			// first" open-empty guard, and any warehouse/week-only filtering the standard path supports).
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
				.wrap(securityRestrictionsWrapper(applySecurityRestrictions)); // security, same idiom as SqlViewSelectionQueryBuilder

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

	/**
	 * Mirrors {@code SqlViewSelectionQueryBuilder#securityRestrictionsWrapper} (same wrapper API, same
	 * {@link Access#READ} semantics) so the function-sourced selection gets the identical per-row
	 * client/org read-access filter the default view-selection path applies. The function output carries
	 * {@code AD_Client_ID}/{@code AD_Org_ID} exactly like the view, so {@link AccessSqlStringExpression}
	 * resolves against {@value #FUNCTION_ALIAS}'s columns unchanged.
	 */
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

	/**
	 * Builds the {@code ORDER BY} clause used to number ({@code row_number()}) the function-sourced rows
	 * before persisting them into {@code T_WEBUI_ViewSelection.Line}. Honors {@code orderBys} — the same
	 * order carried in the returned selection's metadata — instead of a hardcoded tuple, so the persisted
	 * row order never silently diverges from what the metadata advertises. Falls back to the window's
	 * default tuple (WeekStartDate, Warehouse, Product) when {@code orderBys} is empty, and always appends
	 * any of that default tuple's columns not already covered as tie-breakers, keeping the numbering fully
	 * deterministic.
	 */
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
			// Source selection is not function-backed (e.g. the open-empty selection has no rows) => standard behavior.
			return delegate.createOrderedSelectionFromSelection(viewEvalCtx, fromSelection, filters, orderBys, filterConverterCtx);
		}

		final ViewId newViewId = ViewId.random(fromSelection.getWindowId());

		// Re-sort / facet-filter the SAME product's rows, reading the sort/filter column values from the
		// function (fast) rather than re-materializing the view. The function is aliased with the view's table
		// name so the standard builder's field/order/filter SQL resolves unchanged.
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

		// Carry the applied product/warehouse onto the new selection rows so the page render can
		// re-parameterize the function (the standard builder only inserts UUID/Line/key columns).
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

	/**
	 * @return the single {@code EQUAL}-operator positive int value of {@code columnName} across the applied
	 *         filters, or {@code null} if no such filter parameter is present <b>or</b> the parameter is a
	 *         multi-value (e.g. {@code IN_ARRAY}, from a multi-select facet) or range ({@code BETWEEN})
	 *         filter that a single function parameter cannot represent. In the latter case {@code null} is
	 *         returned on purpose — never "the first value" — so the caller (createOrderedSelection) falls
	 *         back to the standard delegate path, which does handle multi-value/range filters correctly.
	 */
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
				// Multi-value or range filter on this column: MD_Stock_PerWeek_fn(product, warehouse) takes a
				// single product id, so picking "the first" value would silently drop the others and render
				// wrong/incomplete output. Bail out regardless of the window's actual filter cardinality.
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

	//
	// The row add/remove operations are delegated unchanged (see class Javadoc: unreachable for this
	// computed view). getSqlWhereClause / containsAnyOfRowIds / delete work purely on the persisted
	// T_WEBUI_ViewSelection by UUID / IntKey1 and are source-agnostic.
	//

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
