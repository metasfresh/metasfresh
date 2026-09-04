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
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Receives a receipt-logistics grid row, the way the window's "CUs annehmen" / "CUs annehmen mit Menge" actions
 * do, and asserts what the produced receipt is linked to.
 * <p>
 * <b>Why the BL and not the WebUI process.</b> {@code de.metas.cucumber} deliberately excludes
 * {@code de.metas.ui.web.base} (its pom says why: {@code ServerBoot} component-scans {@code de.metas}, so the
 * WebUI's Spring components would be dragged into this test JVM's app-server context), so the action classes
 * themselves are not loadable here. They are thin adapters: they turn the selected row into a
 * {@link ReceiptScheduleAndDeliveryPlanningId} and call
 * {@link ReceiptFromReceiptScheduleService#receiveCUs}, which is exactly what this step-def does with the ids
 * the view actually produced. The behaviour under test - which receipt a row's two source ids yield - is
 * therefore the production one, and the adapter's own row-to-ids step is covered by
 * {@code ReceiptDispositionDeliveryPlanningViewBasedProcessTest}.
 */
@RequiredArgsConstructor
public class RV_ReceiptDisposition_DeliveryPlanning_Receive_StepDef
{
	@NonNull private final RV_ReceiptDisposition_DeliveryPlanning_StepDefData receiptDispositionDeliveryPlanningTable;
	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final M_InOut_StepDefData inOutTable;

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
	 * When the receipt-logistics row identified by rowPlanned_RL is received:
	 *   | OPT.Qty | OPT.M_InOut_ID |
	 *   | 5       | receipt_1      |
	 * </pre>
	 */
	@When("^the receipt-logistics row identified by (.*) is received:$")
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
	 * <p>
	 * <b>The data table IS the grouping assertion.</b> One line per receipt the gesture must produce: two rows
	 * that belong on one receipt yield ONE line, two rows that cannot share a header yield two. The step fails
	 * when the count differs, so a change that merged or split receipts differently cannot pass unnoticed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) alias for one produced receipt, in creation order<br>
	 * @cucumber.depends StepDefData: RV_ReceiptDisposition_DeliveryPlanning_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the receipt-logistics rows identified by row_1, row_2, row_3 are received together:
	 *   | M_InOut_ID |
	 *   | receipt_1  |
	 *   | receipt_2  |
	 * </pre>
	 */
	@When("^the receipt-logistics rows identified by (.*) are received together:$")
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
	 * Asserts which delivery planning a receipt is stamped with - the link the HU-editor receive path silently
	 * omits, and the one AC10 turns on.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_InOut_ID</b> — (required, identifier-ref) the receipt to check<br>
	 *   <b>M_Delivery_Planning_ID</b> — (required, identifier-ref) the planning it must carry, or the
	 *   {@code null} placeholder when it must carry none<br>
	 * @cucumber.depends StepDefData: M_InOut_StepDefData, M_Delivery_Planning_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate the delivery planning link of M_InOut:
	 *   | M_InOut_ID | M_Delivery_Planning_ID |
	 *   | receipt_1  | planningPlanned_RL     |
	 *   | receipt_2  | null                   |
	 * </pre>
	 */
	@Then("^validate the delivery planning link of M_InOut:$")
	public void validateDeliveryPlanningLink(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_InOut receipt = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).lookupNotNullIn(inOutTable);
			InterfaceWrapperHelper.refresh(receipt);

			final StepDefDataIdentifier expected = row.getAsIdentifier(I_M_InOut.COLUMNNAME_M_Delivery_Planning_ID);
			final int expectedRepoId = expected.isNullPlaceholder()
					? 0
					: expected.lookupNotNullIn(deliveryPlanningTable).getM_Delivery_Planning_ID();

			assertThat(receipt.getM_Delivery_Planning_ID())
					.as("%s of receipt %s", I_M_InOut.COLUMNNAME_M_Delivery_Planning_ID, receipt.getDocumentNo())
					.isEqualTo(expectedRepoId);
		});
	}
}
