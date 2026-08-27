/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.pporder;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper;
import de.metas.document.engine.IDocument;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_M_Product;
import org.eevolution.api.CostCollectorType;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Cost_Collector;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_CostCollectorType;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_DocStatus;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_M_Product_ID;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_MovementQty;
import static org.eevolution.model.I_PP_Cost_Collector.COLUMNNAME_PP_Cost_Collector_ID;

public class PP_Cost_Collector_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final PP_Order_StepDefData ppOrderTable;
	private final PP_Cost_Collector_StepDefData ppCostCollectorTable;
	private final M_Product_StepDefData productTable;
	private final PP_Order_BOMLine_StepDefData bomLineTable;

	public PP_Cost_Collector_StepDef(
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final PP_Cost_Collector_StepDefData ppCostCollectorTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Order_BOMLine_StepDefData bomLineTable)
	{
		this.ppOrderTable = ppOrderTable;
		this.ppCostCollectorTable = ppCostCollectorTable;
		this.productTable = productTable;
		this.bomLineTable = bomLineTable;
	}

	/**
	 * Loads PP_Cost_Collector records from the database by matching the given DataTable rows,
	 * with a timeout to account for asynchronous cost collector creation.
	 *
	 * <p>Each DataTable row describes one PP_Cost_Collector to load and assert. The step waits up to
	 * {@code timeoutSec} for the record to appear in the database, then verifies its properties match
	 * the expected values.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code PP_Cost_Collector_ID.Identifier} — identifier to store the loaded record (for later reference)</li>
	 *   <li>{@code PP_Order_ID.Identifier} — identifier of the parent production order</li>
	 *   <li>{@code MovementQty} — expected movement quantity (must match the loaded record)</li>
	 *   <li>{@code DocStatus} — expected document status, e.g. "Drafted" or "Completed" (must match the loaded record)</li>
	 * </ul>
	 *
	 * <p>Optional columns:
	 * <ul>
	 *   <li>{@code M_Product_ID.Identifier} — identifier of the product; used to disambiguate when a single PP_Order
	 *   has multiple cost collectors with the same DocStatus (e.g., one for component issue and one for finished-good
	 *   receipt). If omitted, only PP_Order_ID and DocStatus are used to match the record.</li>
	 *   <li>{@code CostCollectorType} — (optional) {@link CostCollectorType} enum name; narrows the match when one
	 *   PP_Order has several completed cost collectors for the same product.</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * And after not more than 5s, PP_Cost_Collector are found:
	 *   | PP_Cost_Collector_ID.Identifier | PP_Order_ID.Identifier | M_Product_ID.Identifier | MovementQty | DocStatus |
	 *   | cc_1                            | order_1                | product_fg              | 100         | Completed |
	 *   | cc_2                            | order_1                | product_comp            | -50         | Drafted   |
	 * </pre>
	 *
	 * @param timeoutSec maximum seconds to wait for each PP_Cost_Collector record to appear
	 * @param dataTable the table defining PP_Cost_Collector records to load and assert
	 * @throws InterruptedException if the wait is interrupted
	 */
	@And("^after not more than (.*)s, PP_Cost_Collector are found:$")
	public void load_PP_Cost_Collector(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSec, 500, () -> loadPPCostCollector(tableRow));

			final String ppCostCollectorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PP_Cost_Collector_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Cost_Collector ppCostCollector = ppCostCollectorTable.get(ppCostCollectorIdentifier);
			assertThat(ppCostCollector).isNotNull();

			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (productIdentifier != null)
			{
				final I_M_Product product = productTable.get(productIdentifier);
				assertThat(product).isNotNull();
				assertThat(ppCostCollector.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
			}

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_MovementQty);
			final String status = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);

			assertThat(ppCostCollector.getMovementQty()).isEqualTo(movementQty);
			assertThat(ppCostCollector.getDocStatus()).isEqualTo(status);

			final String costCollectorTypeName = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_CostCollectorType);
			if (costCollectorTypeName != null)
			{
				assertThat(ppCostCollector.getCostCollectorType()).isEqualTo(CostCollectorType.valueOf(costCollectorTypeName).getCode());
			}
		}
	}

	/**
	 * Loads a single PP_Cost_Collector record from the database by matching the given table row.
	 *
	 * <p>Queries for a PP_Cost_Collector matching the PP_Order_ID and DocStatus from the row.
	 * If a product identifier is provided, further filters by product to disambiguate when one order
	 * has multiple cost collectors with the same status. Stores the found record in the
	 * {@code ppCostCollectorTable} for later reference.
	 *
	 * @param tableRow a single DataTable row with required columns:
	 *        {@code PP_Order_ID.Identifier}, {@code DocStatus}, and optional {@code M_Product_ID.Identifier}
	 * @return {@code true} if the record was found and stored; {@code false} if not found (caller will retry)
	 */
	@NonNull
	private Boolean loadPPCostCollector(@NonNull final Map<String, String> tableRow)
	{
		final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
		final String status = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocStatus);

		final IQueryBuilder<I_PP_Cost_Collector> queryBuilder = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(COLUMNNAME_DocStatus, status);

		// When a single order has several cost collectors with the same DocStatus (e.g. a component issue
		// and a finished-good receipt), disambiguate by the product so each row matches exactly one record.
		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (productIdentifier != null)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_M_Product_ID, productTable.get(productIdentifier).getM_Product_ID());
		}

		final String costCollectorTypeName = DataTableUtil.extractStringOrNullForColumnName(tableRow, COLUMNNAME_CostCollectorType);
		if (costCollectorTypeName != null)
		{
			queryBuilder.addEqualsFilter(COLUMNNAME_CostCollectorType, CostCollectorType.valueOf(costCollectorTypeName).getCode());
		}

		final Optional<I_PP_Cost_Collector> ppCostCollector = queryBuilder
				.create()
				.firstOnlyOptional(I_PP_Cost_Collector.class);

		if (!ppCostCollector.isPresent())
		{
			return false;
		}

		final String ppCostCollectorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PP_Cost_Collector_ID + "." + TABLECOLUMN_IDENTIFIER);
		ppCostCollectorTable.put(ppCostCollectorIdentifier, ppCostCollector.get());

		return true;
	}

	@And("validate I_PP_Cost_Collector")
	public void validate_cost_collector(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_PP_Order order = ppOrderTable.get(orderIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Product product = productTable.get(productIdentifier);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_MovementQty);

			final String bomLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order_BOMLine bomLine = bomLineIdentifier != null ? bomLineTable.get(bomLineIdentifier) : null;

			final I_PP_Cost_Collector costCollector = queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
					.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID())
					.addEqualsFilter(I_PP_Order.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
					.orderBy(I_PP_Cost_Collector.COLUMNNAME_Created)
					.create()
					.first();

			assertThat(costCollector).isNotNull();
			assertThat(costCollector.isProcessed()).isEqualTo(true);
			assertThat(costCollector.getMovementQty()).isEqualTo(movementQty);
			if (bomLine != null)
			{
				assertThat(costCollector.getPP_Order_BOMLine_ID()).isEqualTo(bomLine.getPP_Order_BOMLine_ID());
			}
		}
	}

	/**
	 * Asserts that all ActivityControl cost collectors of the given PP_Order post gracefully with no accounting facts.
	 *
	 * <p>Closing a manufacturing order reports its not-yet-started routing activities, which creates one
	 * ActivityControl cost collector per activity (see {@code closeAllActivities}). When the activity's resource
	 * carries no cost product - e.g. the "no resource" placeholder - there is no activity cost to book, so each
	 * such cost collector must still post successfully ({@code Posted='Y'}) and produce zero Fact_Acct rows
	 * instead of failing the posting pipeline.
	 *
	 * @param timeoutSec maximum seconds to wait for the cost collectors to appear and to become posted
	 * @param ppOrderIdentifier identifier of the parent production order (must be closed beforehand)
	 */
	@And("^after not more than (.*)s, all ActivityControl PP_Cost_Collector for PP_Order (.*) are posted with no Fact_Acct$")
	public void activityControlCostCollectors_arePostedWithNoFacts(
			final int timeoutSec,
			@NonNull final String ppOrderIdentifier) throws InterruptedException
	{
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
		assertThat(ppOrder).isNotNull();

		StepDefUtil.tryAndWait(timeoutSec, 500, () -> !queryCompletedActivityControlCostCollectors(ppOrder).isEmpty());

		final List<I_PP_Cost_Collector> activityControlCostCollectors = queryCompletedActivityControlCostCollectors(ppOrder);
		assertThat(activityControlCostCollectors).as("ActivityControl cost collectors for the closed PP_Order").isNotEmpty();

		final ImmutableSet<TableRecordReference> recordRefs = activityControlCostCollectors.stream()
				.map(TableRecordReference::of)
				.collect(ImmutableSet.toImmutableSet());

		// Posting must succeed for every ActivityControl cost collector.
		AccountingCucumberHelper.waitUtilPosted(recordRefs);

		// A no-cost ActivityControl cost collector must post zero Fact_Acct rows (graceful no-op).
		for (final TableRecordReference recordRef : recordRefs)
		{
			final int factAcctCount = queryBL.createQueryBuilder(I_Fact_Acct.class)
					.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
					.addEqualsFilter(I_Fact_Acct.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
					.create()
					.count();
			assertThat(factAcctCount).as("Fact_Acct rows for ActivityControl cost collector " + recordRef.getRecord_ID()).isZero();
		}
	}

	@NonNull
	private List<I_PP_Cost_Collector> queryCompletedActivityControlCostCollectors(@NonNull final I_PP_Order ppOrder)
	{
		return queryBL.createQueryBuilder(I_PP_Cost_Collector.class)
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Cost_Collector.COLUMNNAME_CostCollectorType, X_PP_Cost_Collector.COSTCOLLECTORTYPE_ActivityControl)
				.addEqualsFilter(COLUMNNAME_DocStatus, IDocument.STATUS_Completed)
				.create()
				.list(I_PP_Cost_Collector.class);
	}
}
