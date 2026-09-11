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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.ReceiptScheduleAndDeliveryPlanningId;
import de.metas.deliveryplanning.receipt.CreateReceiptFromReceiptScheduleResult;
import de.metas.deliveryplanning.receipt.ReceiptFromReceiptScheduleService;
import de.metas.inout.InOutId;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Receives a receipt-disposition delivery-planning grid row the way the window's "CUs annehmen" actions do, and
 * asserts what the produced receipt is linked to.
 * <p>
 * Through the BL, not the WebUI process: {@code de.metas.cucumber} deliberately excludes
 * {@code de.metas.ui.web.base}, so the action classes are not loadable here. They are thin adapters over
 * {@link ReceiptFromReceiptScheduleService#receiveCUs}, and their own row-to-ids step is covered by
 * {@code ReceiptDispositionDeliveryPlanningViewBasedProcessTest}.
 */
@RequiredArgsConstructor
public class RV_ReceiptDisposition_DeliveryPlanning_Receive_StepDef
{
	@NonNull private final RV_ReceiptDisposition_DeliveryPlanning_StepDefData receiptDispositionDeliveryPlanningTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final M_InOut_StepDefData inOutTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final ReceiptFromReceiptScheduleService receiptFromReceiptScheduleService =
			SpringContextHolder.instance.getBean(ReceiptFromReceiptScheduleService.class);

	/**
	 * Receives the given grid row - planned or unplanned - through the shared receive path.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>OPT.Qty</b> — (optional, number) quantity to receive; omitted means the receipt schedule's own
	 *   remaining quantity, which is what "CUs annehmen" receives<br>
	 *   <b>OPT.M_InOut_ID</b> — (optional, identifier-ref) alias to store the produced receipt under<br>
	 * @cucumber.depends StepDefData: RV_ReceiptDisposition_DeliveryPlanning_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the receipt-disposition delivery-planning row identified by rowPlanned_RL is received:
	 *   | OPT.Qty | OPT.M_InOut_ID |
	 *   | 5       | receipt_1      |
	 * </pre>
	 */
	@When("^the receipt-disposition delivery-planning row identified by (.*) is received:$")
	public void receiveRow(@NonNull final String rowIdentifier, @NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final I_RV_ReceiptDisposition_DeliveryPlanning viewRow = receiptDispositionDeliveryPlanningTable.get(rowIdentifier);

		// Exactly the pair the window's process extracts from the selected row: the schedule is always there,
		// the planning only on the planned branch.
		final ReceiptScheduleAndDeliveryPlanningId sourceIds = ReceiptScheduleAndDeliveryPlanningId.of(
				ReceiptScheduleId.ofRepoId(viewRow.getM_ReceiptSchedule_ID()),
				DeliveryPlanningId.ofRepoIdOrNull(viewRow.getM_Delivery_Planning_ID()));

		final BigDecimal qtyOverride = row.getAsOptionalBigDecimal("Qty").orElse(null);

		final CreateReceiptFromReceiptScheduleResult result =
				receiptFromReceiptScheduleService.receiveCUs(sourceIds, qtyOverride);

		row.getAsOptionalIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID)
				.ifPresent(identifier -> inOutTable.putOrReplace(
						identifier,
						InterfaceWrapperHelper.load(result.getReceiptId(), I_M_InOut.class)));
	}

	/**
	 * Receives SEVERAL grid rows in one gesture, the way the window's "Wareneingangsdispo zu Wareneingang"
	 * action does, and binds the receipts it produced - in creation order - to the identifiers in the data table.
	 * The data table IS the grouping assertion: one line per receipt the gesture must produce, so a change that
	 * merged or split receipts differently cannot pass unnoticed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) alias for one produced receipt, in creation order<br>
	 * @cucumber.depends StepDefData: RV_ReceiptDisposition_DeliveryPlanning_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the receipt-disposition delivery-planning rows identified by row_1, row_2, row_3 are received together:
	 *   | M_InOut_ID |
	 *   | receipt_1  |
	 *   | receipt_2  |
	 * </pre>
	 */
	@When("^the receipt-disposition delivery-planning rows identified by (.*) are received together:$")
	public void receiveRowsTogether(@NonNull final String rowIdentifiers, @NonNull final DataTable dataTable)
	{
		final ImmutableList<ReceiptScheduleAndDeliveryPlanningId> sourceIds = Stream.of(rowIdentifiers.split(","))
				.map(String::trim)
				.map(receiptDispositionDeliveryPlanningTable::get)
				// Exactly the pair the window's process extracts from each selected row, in the order the grid
				// hands them over: the schedule is always there, the planning only on the planned branch.
				.map(viewRow -> ReceiptScheduleAndDeliveryPlanningId.of(
						ReceiptScheduleId.ofRepoId(viewRow.getM_ReceiptSchedule_ID()),
						DeliveryPlanningId.ofRepoIdOrNull(viewRow.getM_Delivery_Planning_ID())))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<InOutId> receiptIds = receiptFromReceiptScheduleService.receiveRows(sourceIds);

		final ImmutableList<DataTableRow> expectedReceipts = DataTableRows.of(dataTable).stream()
				.collect(ImmutableList.toImmutableList());

		assertThat(receiptIds)
				.as("receipts produced by receiving %s together - one per expected data table row", rowIdentifiers)
				.hasSize(expectedReceipts.size());

		for (int i = 0; i < expectedReceipts.size(); i++)
		{
			expectedReceipts.get(i).getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID)
					.putOrReplace(inOutTable, InterfaceWrapperHelper.load(receiptIds.get(i), I_M_InOut.class));
		}
	}

	/**
	 * Asserts which delivery planning each of a receipt's LINES is stamped with - the grain the link lives at, so
	 * that one receipt aggregating several plannings can be asserted at all.
	 * <p>
	 * EXACT per receipt: for every {@code M_InOut_ID} the data table mentions, the receipt's order-line-bearing
	 * lines must be exactly the rows given for it - so a line that gained or lost a planning, and a receipt that
	 * gained or lost a line, both fail. Packing-material lines are out of scope: they carry no order line and no
	 * planning.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the receipt the line belongs to<br>
	 *   <b>C_OrderLine_ID</b> — (required, identifier-ref) the line's order line<br>
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning the line must carry, or the
	 *   {@code null} placeholder when it must carry none<br>
	 *   <b>OPT.MovementQty</b> — (optional, number) the line's quantity<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, C_OrderLine_StepDefData, M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate the delivery planning link of the material receipt lines:
	 *   | M_InOut_ID | C_OrderLine_ID | M_Delivery_Planning_ID | OPT.MovementQty | OPT.QtyTU_Calculated |
	 *   | receipt_1  | orderLine_1    | planning_1             | 4               | 1                    |
	 *   | receipt_1  | orderLine_1    | planning_2             | 3               |                             |
	 * </pre>
	 */
	@Then("^validate the delivery planning link of the material receipt lines:$")
	public void validateDeliveryPlanningLinkOfLines(@NonNull final DataTable dataTable)
	{
		final LinkedHashMap<StepDefDataIdentifier, List<DataTableRow>> rowsByReceipt = new LinkedHashMap<>();
		DataTableRows.of(dataTable).forEach(row -> rowsByReceipt
				.computeIfAbsent(row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID), k -> new ArrayList<>())
				.add(row));

		rowsByReceipt.forEach(this::validateDeliveryPlanningLinkOfOneReceipt);
	}

	private void validateDeliveryPlanningLinkOfOneReceipt(
			@NonNull final StepDefDataIdentifier receiptIdentifier,
			@NonNull final List<DataTableRow> expectedRows)
	{
		final I_M_InOut receipt = receiptIdentifier.lookupNotNullIn(inOutTable);

		final ImmutableMap<OrderLineAndDeliveryPlanning, DataTableRow> expectedByKey = expectedRows.stream()
				.collect(ImmutableMap.toImmutableMap(this::extractExpectedKey, row -> row));

		final ImmutableMap<OrderLineAndDeliveryPlanning, I_M_InOutLine> actualByKey = queryBL
				.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, receipt.getM_InOut_ID())
				.create()
				.stream()
				// Packing-material lines are out of scope: they carry neither an order line nor a planning.
				.filter(line -> line.getC_OrderLine_ID() > 0)
				.collect(ImmutableMap.toImmutableMap(
						line -> new OrderLineAndDeliveryPlanning(line.getC_OrderLine_ID(), line.getM_Delivery_Planning_ID()),
						line -> line));

		assertThat(actualByKey.keySet())
				.as("(C_OrderLine_ID, M_Delivery_Planning_ID) of the lines of receipt %s", receipt.getDocumentNo())
				.containsExactlyInAnyOrderElementsOf(expectedByKey.keySet());

		expectedByKey.forEach((key, expectedRow) -> {
			expectedRow.getAsOptionalBigDecimal(I_M_InOutLine.COLUMNNAME_MovementQty)
					.ifPresent(expectedMovementQty -> assertThat(actualByKey.get(key).getMovementQty())
							.as("MovementQty of the line %s of receipt %s", key, receipt.getDocumentNo())
							.isEqualByComparingTo(expectedMovementQty));

			// The TUs the line's goods arrived in, as the producer counted them:
			// InOutProducerFromReceiptScheduleHU#transferHandlingUnits resets HUPackingMaterialsCollector's TU
			// tally per line and writes it here, and that tally is incremented from the HU's UNIT TYPE - one per
			// TU, an aggregate's represented count for a "bag", zero for anything else. A receive that ignored
			// the configuration and produced a bare virtual HU therefore leaves this at zero.
			//
			// To assert the HUs THEMSELVES rather than this derived count, use the handling-units step below.
			//
			// Via de.metas.handlingunits.model.I_M_InOutLine - the HU columns are not on org.compiere.model's
			// generated interface, they live on the hand-written handling-units view of the same table.
			expectedRow.getAsOptionalBigDecimal(de.metas.handlingunits.model.I_M_InOutLine.COLUMNNAME_QtyTU_Calculated)
					.ifPresent(expectedQtyTU -> assertThat(InterfaceWrapperHelper.create(actualByKey.get(key), de.metas.handlingunits.model.I_M_InOutLine.class).getQtyTU_Calculated())
							.as("QtyTU_Calculated of the line %s of receipt %s", key, receipt.getDocumentNo())
							.isEqualByComparingTo(expectedQtyTU));
		});
	}

	/**
	 * Asserts the HANDLING UNITS a receipt's goods actually arrived in, rather than the TU count the producer
	 * derived onto the line.
	 * <p>
	 * Needed because {@code QtyTU_Calculated} cannot answer the question it looks like it answers. It is
	 * {@code HUPackingMaterialsCollector}'s TU tally, reset per line, and the collector skips any HU whose
	 * packing material is our own ({@code isHUPlanningReceiptOwnerPM}) and de-duplicates by {@code M_HU_ID} - so
	 * a count of one is equally consistent with "one TU" and with "several TUs, only one of them counted". The
	 * assignments are the ground truth: one row per TU that reached the receipt.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> - (required, identifier-ref) the receipt<br>
	 *   <b>TUCount</b> - (required, number) how many DISTINCT TU handling units its lines are assigned to<br>
	 *   <b>OPT.QtyCUsPerTU</b> - (optional, comma-separated numbers, ascending) each TU's stocked quantity, so a
	 *   TU filled past its packing instruction is visible<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate the handling units behind the material receipt:
	 *   | M_InOut_ID | TUCount | OPT.QtyCUsPerTU |
	 *   | receipt_1  | 2       | 5,10            |
	 * </pre>
	 */
	@Then("^validate the handling units behind the material receipt:$")
	public void validateHandlingUnitsBehindTheReceipt(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::validateHandlingUnitsOfOneReceipt);
	}

	private void validateHandlingUnitsOfOneReceipt(@NonNull final DataTableRow row)
	{
		final I_M_InOut receipt = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inOutTable);

		final ImmutableSet<Integer> lineIds = queryBL
				.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, receipt.getM_InOut_ID())
				.create()
				.stream()
				// Packing-material lines carry no goods, so no TU of their own.
				.filter(line -> line.getC_OrderLine_ID() > 0)
				.map(I_M_InOutLine::getM_InOutLine_ID)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<Integer> tuHuIds = queryBL
				.createQueryBuilder(I_M_HU_Assignment.class)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_M_InOutLine.class))
				.addInArrayFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, lineIds)
				.create()
				.stream()
				.map(I_M_HU_Assignment::getM_TU_HU_ID)
				.filter(tuHuId -> tuHuId > 0)
				.collect(ImmutableSet.toImmutableSet());

		assertThat(tuHuIds)
				.as("DISTINCT TU handling units assigned to the lines of receipt %s", receipt.getDocumentNo())
				.hasSize(row.getAsInt("TUCount"));

		row.getAsOptionalString("QtyCUsPerTU").ifPresent(expected -> {
			final List<BigDecimal> actualQtys = tuHuIds.stream()
					.map(this::stockedQtyOfHU)
					.sorted()
					.collect(ImmutableList.toImmutableList());

			final List<BigDecimal> expectedQtys = Stream.of(expected.split(","))
					.map(String::trim)
					.map(BigDecimal::new)
					.sorted()
					.collect(ImmutableList.toImmutableList());

			assertThat(actualQtys)
					.as("stocked quantity per TU of receipt %s - a TU holding more than its packing instruction allows shows up here", receipt.getDocumentNo())
					.usingElementComparator(BigDecimal::compareTo)
					.containsExactlyElementsOf(expectedQtys);
		});
	}

	private BigDecimal stockedQtyOfHU(final int huId)
	{
		return queryBL
				.createQueryBuilder(I_M_HU_Storage.class)
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, huId)
				.create()
				.stream()
				.map(I_M_HU_Storage::getQty)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private OrderLineAndDeliveryPlanning extractExpectedKey(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier expectedPlanning = row.getAsIdentifier(I_M_InOutLine.COLUMNNAME_M_Delivery_Planning_ID);
		return new OrderLineAndDeliveryPlanning(
				row.getAsIdentifier(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID).lookupNotNullIn(orderLineTable).getC_OrderLine_ID(),
				expectedPlanning.isNullPlaceholder()
						? 0
						: expectedPlanning.lookupNotNullIn(deliveryPlanningTable).getM_Delivery_Planning_ID());
	}

	@Value
	private static class OrderLineAndDeliveryPlanning
	{
		int orderLineRepoId;
		int deliveryPlanningRepoId;
	}
}
