package de.metas.ui.web.material.stockperweek;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.UserRolePermissionsKey;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowFieldBinding;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.user.UserId;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
 * Covers {@link StockPerWeekSelectionFactory#buildCreateSelectionSql} — the layer that decides fast-vs-slow and
 * builds the fast-path SQL, without executing it (no DB). A full RelationType-driven zoom is not triggerable here
 * (de.metas.cucumber, the only Postgres-backed suite, has no ui.web.base classes; ui.web.base unit tests run on the
 * in-memory POJO map with no SQL engine), so we feed the <b>exact {@code DocumentFilterParam.ofSqlWhereClause(...)}
 * the order-line zoom provider emits</b> and assert the factory takes the {@code MD_Stock_PerWeek_fn} fast path.
 * Row-equivalence against the slow view output and the index-scan EXPLAIN are verified separately against a live
 * stack, not here.
 * <p>
 * RED (before the fix): the zoom's SQL-where param is hidden from by-field-name lookup, so no product is
 * resolved and {@code buildCreateSelectionSql} returns {@code null} (slow delegate path) — the fast-path
 * assertions fail. GREEN (after): the product is parsed out of the clause and the function fast path is built.
 */
class StockPerWeekSelectionFactoryTest
{
	private static final WindowId WINDOW_ID = StockPerWeekSelectionFactory.WINDOW_ID;
	private static final int PRODUCT_ID = 1_000_042;
	private static final int ORDER_ID = 5_000_007;

	private ViewEvaluationCtx viewEvalCtx;
	private StockPerWeekSelectionFactory factory;

	@BeforeEach
	void beforeEach()
	{
		viewEvalCtx = ViewEvaluationCtx._builder()
				.loggedUserId(Optional.of(UserId.METASFRESH))
				.orgId(OrgId.ofRepoId(1000000))
				.adLanguage("en_US")
				.timeZone(ZoneId.of("Europe/Berlin"))
				.permissionsKey(UserRolePermissionsKey.of(
						RoleId.SYSTEM,
						UserId.METASFRESH,
						ClientId.SYSTEM,
						LocalDate.of(2026, 3, 22)))
				.build();
		factory = new StockPerWeekSelectionFactory(minimalBinding());
	}

	/** The exact single opaque SQL-where param the order-line RelationType zoom pushes in. */
	private static DocumentFilterList zoomFilter()
	{
		final String sqlWhere = "M_Product_ID = " + PRODUCT_ID
				+ " AND M_Warehouse_ID = MD_getStockWarehouse( (SELECT o.M_Warehouse_ID FROM C_Order o WHERE o.C_Order_ID = " + ORDER_ID + ") )"
				+ " AND WeekStartDate >= date_trunc('week', COALESCE( (SELECT o.PreparationDate FROM C_Order o WHERE o.C_Order_ID = " + ORDER_ID + "), now()))::date";
		return DocumentFilterList.of(DocumentFilter.builder()
				.setFilterId("MQuery-" + UUID.randomUUID())
				.addParameter(DocumentFilterParam.ofSqlWhereClause(sqlWhere))
				.build());
	}

	@Test
	void orderLineZoom_sqlWhereFilter_takesFunctionFastPath_withResidualWhere()
	{
		final SqlAndParams sql = buildSql(zoomFilter());

		// fast path used (RED before the fix: unrecognized zoom param => null => slow delegate path)
		assertThat(sql).as("order-line zoom must take the MD_Stock_PerWeek_fn fast path").isNotNull();

		final String sqlText = sql.getSql();
		// sourced from the function; product is the 1st fn param, warehouse (2nd) is NULL = all warehouses
		assertThat(sqlText).contains("MD_Stock_PerWeek_fn(?, ?) fn");
		assertThat(sql.getSqlParams()).containsSubsequence(PRODUCT_ID, null);
		// product persisted into IntKey2; IntKey3 = the (null, zoom) warehouse filter param -> render sources fn(product, NULL)
		assertThat(sqlText).contains(", fn.M_Product_ID, CAST(? AS numeric)\n FROM MD_Stock_PerWeek_fn(?, ?) fn");
		// the clause's residual warehouse-resolution + week floor are applied against the fn output, not dropped
		assertThat(sqlText).contains("MD_getStockWarehouse");
		assertThat(sqlText).contains("WeekStartDate >=");
	}

	@Test
	void directProductEqualsFacet_takesFunctionFastPath_withoutResidualWhere()
	{
		// existing direct-filter fast path must stay intact: product+warehouse as the two fn params, WHERE 1=1
		final SqlAndParams sql = buildSql(DocumentFilterList.of(DocumentFilter.equalsFilter("M_Product_ID", PRODUCT_ID)));

		assertThat(sql).isNotNull();
		assertThat(sql.getSql()).contains("MD_Stock_PerWeek_fn(?, ?) fn");
		assertThat(sql.getSql()).doesNotContain("MD_getStockWarehouse");
		assertThat(sql.getSql()).doesNotContain("\n AND (\n"); // no residual WHERE appended
	}

	@Test
	void directProductAndWarehouseFacet_bothBecomeFunctionParams()
	{
		// product + warehouse EQUAL facets => both carried as the two fn params (warehouse non-null), no residual WHERE
		final int warehouseId = 1_000_110;
		final SqlAndParams sql = buildSql(DocumentFilterList.of(
				DocumentFilter.equalsFilter("M_Product_ID", PRODUCT_ID),
				DocumentFilter.equalsFilter("M_Warehouse_ID", warehouseId)));

		assertThat(sql).isNotNull();
		assertThat(sql.getSql()).contains(", fn.M_Product_ID, CAST(? AS numeric)\n FROM MD_Stock_PerWeek_fn(?, ?) fn");
		// IntKey3 persists the warehouse FILTER (=warehouseId), then product+warehouse are the two fn params
		assertThat(sql.getSqlParams()).containsSubsequence(warehouseId, PRODUCT_ID, warehouseId);
		assertThat(sql.getSql()).doesNotContain("\n AND (\n"); // direct facet => no residual WHERE
	}

	@Test
	void productOnlyFacet_persistsNullWarehouseFilter_soRenderSpansAllWarehouses()
	{
		// Product-only selection: IntKey3 persists the (null) warehouse filter, not the per-row warehouse,
		// so the render sources fn(product, NULL) and its join to the selection restores every warehouse.
		final SqlAndParams sql = buildSql(DocumentFilterList.of(DocumentFilter.equalsFilter("M_Product_ID", PRODUCT_ID)));

		assertThat(sql).isNotNull();
		assertThat(sql.getSql()).contains(", fn.M_Product_ID, CAST(? AS numeric)\n FROM MD_Stock_PerWeek_fn(?, ?) fn");
	}

	@Test
	void multiValueProductFacet_delegatesToSlowPath()
	{
		// IN_ARRAY facet (a single fn param can't carry N products) => slow delegate path preserved
		final SqlAndParams sql = buildSql(DocumentFilterList.of(
				DocumentFilter.inArrayFilter("prod", "M_Product_ID", ImmutableList.of(PRODUCT_ID, PRODUCT_ID + 1))));

		assertThat(sql).isNull();
	}

	@Test
	void ambiguousZoomClause_failsClosedToSlowPath()
	{
		// two distinct product literals => never guess a product => slow delegate path
		final DocumentFilterList filters = DocumentFilterList.of(DocumentFilter.builder()
				.setFilterId("MQuery-" + UUID.randomUUID())
				.addParameter(DocumentFilterParam.ofSqlWhereClause(
						"M_Product_ID = " + PRODUCT_ID + " OR M_Product_ID = " + (PRODUCT_ID + 1)))
				.build());

		assertThat(buildSql(filters)).isNull();
	}

	@Test
	void noFilter_delegatesToSlowPath()
	{
		assertThat(buildSql(DocumentFilterList.EMPTY)).isNull();
	}

	private SqlAndParams buildSql(final DocumentFilterList filters)
	{
		return factory.buildCreateSelectionSql(
				viewEvalCtx,
				ViewId.random(WINDOW_ID),
				filters,
				DocumentQueryOrderByList.EMPTY,
				false, // no security restrictions => no live permissions lookup needed in-test
				SqlDocumentFilterConverterContext.builder().build());
	}

	private static SqlViewBinding minimalBinding()
	{
		final SqlViewRowFieldBinding keyField = SqlViewRowFieldBinding.builder()
				.fieldName("MD_Stock_PerWeek_V_ID")
				.widgetType(DocumentFieldWidgetType.Integer)
				.sqlValueClass(Integer.class)
				.keyColumn(true)
				.fieldLoader((rs, adLanguage) -> null)
				.sqlSelectValue(SqlSelectValue.builder()
						.columnName("MD_Stock_PerWeek_V_ID")
						.columnNameAlias("MD_Stock_PerWeek_V_ID")
						.build())
				.build();

		return SqlViewBinding.builder()
				.tableName("MD_Stock_PerWeek_V")
				.field(keyField)
				.displayFieldNames("MD_Stock_PerWeek_V_ID")
				.build();
	}
}
