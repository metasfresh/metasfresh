/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.M_ReceiptSchedule_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_RV_ReceiptLogistics;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Asserts what the receipt-logistics window would list for one purchase order.
 * <p>
 * {@code RV_ReceiptLogistics} is a UNION of two branches and the whole point of the window is which of them a
 * given order lands on: a <b>planned</b> row (one active {@code Incoming} delivery planning carrying a receipt
 * schedule) or an <b>unplanned</b> row (a receipt schedule no active planning refers to). The two are told apart
 * by {@code M_Delivery_Planning_ID} being set or null, and each carries a different key - the planning id on the
 * planned branch, {@code 1000000000 + M_ReceiptSchedule_ID} on the unplanned one. This step-def asserts that key
 * relationship on every row rather than taking the caller's word for which branch a row came from: a row that
 * claims to be planned but keys like an unplanned one is a defect in the view, not a detail.
 */
@RequiredArgsConstructor
public class RV_ReceiptLogistics_StepDef
{
	/** The offset branch two adds to the receipt-schedule id to keep the two branches' keys disjoint. */
	private static final int UNPLANNED_KEY_OFFSET = 1_000_000_000;

	@NonNull private final RV_ReceiptLogistics_StepDefData receiptLogisticsTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * The rows the receipt-logistics window lists for one order: the given ones and nothing else.
	 * <p>
	 * Waits for the expected number of rows first, because the delivery planning behind a planned row is
	 * generated asynchronously after the order completes; a timeout here means the row never appeared.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>RV_ReceiptLogistics_ID</b> — (required, identifier-ref) alias to store the loaded row under<br>
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning this row was built from, or the
	 *   {@code null} placeholder for an unplanned row<br>
	 *   <b>M_ReceiptSchedule_ID</b> — (required, identifier-ref) the receipt schedule behind the row<br>
	 *   <b>OPT.ETA</b> — (optional) expected {@code ETA}, as a date<br>
	 *   <b>OPT.DatePromised_Effective</b> — (optional) expected {@code DatePromised_Effective}, as a date<br>
	 *   <b>OPT.QtyOrdered</b> — (optional) expected {@code QtyOrdered}<br>
	 *   <b>OPT.C_BPartner_ID</b> — (optional, identifier-ref) expected business partner<br>
	 *   <b>OPT.M_Product_ID</b> — (optional, identifier-ref) expected product<br>
	 *   <b>OPT.M_Warehouse_ID</b> — (optional, identifier-ref) expected warehouse<br>
	 *   <b>OPT.POReference</b> — (optional) expected {@code POReference}<br>
	 * @cucumber.depends StepDefData: RV_ReceiptLogistics_StepDefData, C_Order_StepDefData,
	 * M_Delivery_Planning_StepDefData, M_ReceiptSchedule_StepDefData, C_BPartner_StepDefData,
	 * M_Product_StepDefData, M_Warehouse_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 30s, the C_Order identified by order_PO has exactly the following rows in RV_ReceiptLogistics:
	 *   | RV_ReceiptLogistics_ID | M_Delivery_Planning_ID | M_ReceiptSchedule_ID | OPT.ETA    |
	 *   | receiptLogisticsRow    | deliveryPlanning_PO    | receiptSchedule      | 2023-02-20 |
	 * </pre>
	 */
	@Then("^after not more than (.*)s, the C_Order identified by (.*) has exactly the following rows in RV_ReceiptLogistics:$")
	public void validate_RV_ReceiptLogistics(
			final int timeoutSec,
			@NonNull final String orderIdentifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		final ImmutableList<DataTableRow> expectedRows = DataTableRows.of(dataTable).stream().collect(ImmutableList.toImmutableList());

		final Supplier<Boolean> rowsArrived = () -> queryRows(order).size() == expectedRows.size();
		StepDefUtil.tryAndWait(timeoutSec, 500, rowsArrived);

		final List<I_RV_ReceiptLogistics> actualRows = queryRows(order);
		assertThat(actualRows)
				.as("rows of RV_ReceiptLogistics for C_Order %s", orderIdentifier)
				.hasSize(expectedRows.size());

		for (int i = 0; i < expectedRows.size(); i++)
		{
			validateRow(expectedRows.get(i), actualRows.get(i));
		}
	}

	/**
	 * The receipt-logistics window lists NOTHING for this order.
	 * <p>
	 * Asserts immediately and deliberately: an order whose planning must not surface has to be driven far enough
	 * that the planning EXISTS before this step runs (see {@code load created M_Delivery_Planning}), or the step
	 * would pass on nothing more than the generation not having happened yet.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then RV_ReceiptLogistics has no row for the C_Order identified by order_SO
	 * </pre>
	 */
	@Then("^RV_ReceiptLogistics has no row for the C_Order identified by (.*)$")
	public void validate_no_RV_ReceiptLogistics_row(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);

		assertThat(queryRows(order))
				.as("rows of RV_ReceiptLogistics for C_Order %s", orderIdentifier)
				.isEmpty();
	}

	private List<I_RV_ReceiptLogistics> queryRows(@NonNull final I_C_Order order)
	{
		return queryBL.createQueryBuilder(I_RV_ReceiptLogistics.class)
				.addEqualsFilter(I_RV_ReceiptLogistics.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.orderBy()
				.addColumn(I_RV_ReceiptLogistics.COLUMNNAME_RV_ReceiptLogistics_ID)
				.endOrderBy()
				.create()
				.list();
	}

	private void validateRow(@NonNull final DataTableRow expected, @NonNull final I_RV_ReceiptLogistics actual)
	{
		final SoftAssertions softly = new SoftAssertions();

		final StepDefDataIdentifier planningIdentifier = expected.getAsIdentifier(I_RV_ReceiptLogistics.COLUMNNAME_M_Delivery_Planning_ID);
		final I_M_ReceiptSchedule receiptSchedule = expected.getAsIdentifier(I_RV_ReceiptLogistics.COLUMNNAME_M_ReceiptSchedule_ID)
				.lookupNotNullIn(receiptScheduleTable);

		softly.assertThat(actual.getM_ReceiptSchedule_ID())
				.as(I_RV_ReceiptLogistics.COLUMNNAME_M_ReceiptSchedule_ID)
				.isEqualTo(receiptSchedule.getM_ReceiptSchedule_ID());

		if (planningIdentifier.isNullPlaceholder())
		{
			// unplanned row: no planning, and the key is the schedule id lifted into the second branch's range
			softly.assertThat(actual.getM_Delivery_Planning_ID())
					.as("%s of an unplanned row", I_RV_ReceiptLogistics.COLUMNNAME_M_Delivery_Planning_ID)
					.isZero();
			softly.assertThat(actual.getRV_ReceiptLogistics_ID())
					.as("%s of an unplanned row", I_RV_ReceiptLogistics.COLUMNNAME_RV_ReceiptLogistics_ID)
					.isEqualTo(UNPLANNED_KEY_OFFSET + receiptSchedule.getM_ReceiptSchedule_ID());
		}
		else
		{
			// planned row: the planning it was built from, and the planning's own id as the key
			final I_M_Delivery_Planning deliveryPlanning = planningIdentifier.lookupNotNullIn(deliveryPlanningTable);
			softly.assertThat(actual.getM_Delivery_Planning_ID())
					.as("%s of a planned row", I_RV_ReceiptLogistics.COLUMNNAME_M_Delivery_Planning_ID)
					.isEqualTo(deliveryPlanning.getM_Delivery_Planning_ID());
			softly.assertThat(actual.getRV_ReceiptLogistics_ID())
					.as("%s of a planned row", I_RV_ReceiptLogistics.COLUMNNAME_RV_ReceiptLogistics_ID)
					.isEqualTo(deliveryPlanning.getM_Delivery_Planning_ID());
		}

		expected.getAsOptionalLocalDate(I_RV_ReceiptLogistics.COLUMNNAME_ETA)
				.ifPresent(eta -> softly.assertThat(asLocalDate(actual.getETA()))
						.as(I_RV_ReceiptLogistics.COLUMNNAME_ETA)
						.isEqualTo(eta));

		expected.getAsOptionalLocalDate(I_RV_ReceiptLogistics.COLUMNNAME_DatePromised_Effective)
				.ifPresent(date -> softly.assertThat(asLocalDate(actual.getDatePromised_Effective()))
						.as(I_RV_ReceiptLogistics.COLUMNNAME_DatePromised_Effective)
						.isEqualTo(date));

		expected.getAsOptionalBigDecimal(I_RV_ReceiptLogistics.COLUMNNAME_QtyOrdered)
				.ifPresent(qty -> softly.assertThat(actual.getQtyOrdered())
						.as(I_RV_ReceiptLogistics.COLUMNNAME_QtyOrdered)
						.isEqualByComparingTo(qty));

		expected.getAsOptionalString(I_RV_ReceiptLogistics.COLUMNNAME_POReference)
				.ifPresent(poReference -> softly.assertThat(actual.getPOReference())
						.as(I_RV_ReceiptLogistics.COLUMNNAME_POReference)
						.isEqualTo(poReference));

		expected.getAsOptionalIdentifier(I_RV_ReceiptLogistics.COLUMNNAME_C_BPartner_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.ifPresent(id -> {
					final I_C_BPartner bpartner = id.lookupNotNullIn(bpartnerTable);
					softly.assertThat(actual.getC_BPartner_ID())
							.as(I_RV_ReceiptLogistics.COLUMNNAME_C_BPartner_ID)
							.isEqualTo(bpartner.getC_BPartner_ID());
				});

		expected.getAsOptionalIdentifier(I_RV_ReceiptLogistics.COLUMNNAME_M_Product_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.ifPresent(id -> {
					final I_M_Product product = id.lookupNotNullIn(productTable);
					softly.assertThat(actual.getM_Product_ID())
							.as(I_RV_ReceiptLogistics.COLUMNNAME_M_Product_ID)
							.isEqualTo(product.getM_Product_ID());
				});

		expected.getAsOptionalIdentifier(I_RV_ReceiptLogistics.COLUMNNAME_M_Warehouse_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.ifPresent(id -> {
					final I_M_Warehouse warehouse = id.lookupNotNullIn(warehouseTable);
					softly.assertThat(actual.getM_Warehouse_ID())
							.as(I_RV_ReceiptLogistics.COLUMNNAME_M_Warehouse_ID)
							.isEqualTo(warehouse.getM_Warehouse_ID());
				});

		softly.assertAll();

		expected.getAsOptionalIdentifier(I_RV_ReceiptLogistics.COLUMNNAME_RV_ReceiptLogistics_ID)
				.ifPresent(identifier -> receiptLogisticsTable.putOrReplace(identifier, actual));
	}

	@Nullable
	private static LocalDate asLocalDate(@Nullable final Timestamp timestamp)
	{
		return timestamp == null ? null : TimeUtil.asLocalDate(timestamp);
	}
}
