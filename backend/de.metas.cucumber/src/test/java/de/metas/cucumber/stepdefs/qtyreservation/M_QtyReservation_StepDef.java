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

package de.metas.cucumber.stepdefs.qtyreservation;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.qty_reservation.CreateQtyReservationRequest;
import de.metas.inoutcandidate.qty_reservation.QtyReservationId;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.inoutcandidate.qty_reservation.SupplyType;
import de.metas.project.ProjectId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_QtyReservation;

/**
 * Step definitions for {@code M_QtyReservation}.
 *
 * <p>Allows Cucumber scenarios to create reservation records directly (bypassing the cockpit V2 UI process)
 * and validate their state after shipment completion.
 *
 * <h2>Steps</h2>
 *
 * <h3>{@code metasfresh contains M_QtyReservations:}</h3>
 * <p>Creates one or more {@code M_QtyReservation} records.
 * <pre>
 * And metasfresh contains M_QtyReservations:
 *   | Identifier       | C_OrderLine_ID | M_Product_ID | M_Warehouse_ID | Qty | C_UOM_ID.X12DE355 | QtyTU | OPT.SupplyType |
 *   | qtyReservation_1 | orderLine_1    | product_1    | warehouse_1    | 10  | PCE               | 1     | OH             |
 * </pre>
 * <ul>
 *   <li><b>Identifier</b> — (required) alias for cross-step reference</li>
 *   <li><b>C_OrderLine_ID</b> — (required, identifier-ref) the sales order line this reservation covers</li>
 *   <li><b>M_Product_ID</b> — (required, identifier-ref) product being reserved</li>
 *   <li><b>M_Warehouse_ID</b> — (required, identifier-ref) warehouse the supply comes from</li>
 *   <li><b>Qty</b> — (required) quantity (CU) to reserve</li>
 *   <li><b>C_UOM_ID.X12DE355</b> — (required) UOM of the quantity, e.g. {@code PCE}</li>
 *   <li><b>QtyTU</b> — (required) transport-unit quantity (e.g. number of pallets / boxes)</li>
 *   <li><b>SupplyType</b> — (optional, default {@code OH}) supply type code: {@code OH} = on-hand, {@code PS} = planned supply</li>
 *   <li><b>C_Project_ID</b> — (optional, identifier-ref) project to link; when set, also propagates {@code C_Project_ID} to the sales order line</li>
 * </ul>
 *
 * <h3>{@code validate M_QtyReservations:}</h3>
 * <p>Validates one or more previously created reservation records by re-loading them from the DB.
 * <pre>
 * Then validate M_QtyReservations:
 *   | Identifier       | OPT.Qty | OPT.QtyDelivered | OPT.Processed |
 *   | qtyReservation_1 | 10      | 10               | true          |
 * </pre>
 * <ul>
 *   <li><b>Identifier</b> — (required) alias of the reservation to validate</li>
 *   <li><b>Qty</b> — (optional) expected reserved quantity (CU)</li>
 *   <li><b>QtyDelivered</b> — (optional) expected delivered quantity so far</li>
 *   <li><b>Processed</b> — (optional) expected value of the {@code Processed} flag</li>
 * </ul>
 */
@RequiredArgsConstructor
public class M_QtyReservation_StepDef
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final QtyReservationService qtyReservationService = SpringContextHolder.instance.getBean(QtyReservationService.class);

	@NonNull private final M_QtyReservation_StepDefData qtyReservationTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_Project_StepDefData projectTable;

	/**
	 * Creates {@code M_QtyReservation} records directly in the DB, bypassing the cockpit V2 UI process.
	 * This is the test-only path — production reservations are created via {@code MD_CockpitV2_MakeQtyReservation}.
	 */
	@Given("metasfresh contains M_QtyReservations:")
	public void metasfresh_contains_M_QtyReservations(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createReservation);
	}

	private void createReservation(@NonNull final DataTableRow row)
	{
		final ProjectId projectId = row.getAsOptionalIdentifier("C_Project_ID")
				.map(projectTable::get)
				.map(project -> ProjectId.ofRepoId(project.getC_Project_ID()))
				.orElse(null);

		final QtyReservationId qtyReservationId = qtyReservationService.makeReservation(CreateQtyReservationRequest.builder()
				.orderAndLineId(orderLineTable.getOrderAndLineId(row.getAsIdentifier("C_OrderLine_ID")))
				.productId(row.getAsIdentifier("M_Product_ID").lookupNotNullIdIn(productTable))
				.warehouseId(row.getAsIdentifier("M_Warehouse_ID").lookupNotNullIdIn(warehouseTable))
				.supplyType(row.getAsOptionalEnum("SupplyType", SupplyType.class).orElse(SupplyType.ON_HAND))
				.qtyTU(QtyTU.ofInt(row.getAsInt("QtyTU")))
				.qty(row.getAsQuantity("Qty", "C_UOM_ID", uomDAO::getByX12DE355))
				.projectId(projectId)
				.build());

		// final I_M_QtyReservation record = InterfaceWrapperHelper.newInstance(I_M_QtyReservation.class);
		// record.setC_Order_ID(orderLine.getC_Order_ID());
		// record.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		// record.setM_Product_ID(productId);
		// record.setM_Warehouse_ID(warehouseId);
		// record.setQty(qty.toBigDecimal());
		// record.setQtyDelivered(BigDecimal.ZERO);
		// record.setQtyTU(qtyTU);
		// record.setC_UOM_ID(qty.getUomId().getRepoId());
		// record.setSupplyType(supplyType);
		// InterfaceWrapperHelper.saveRecord(record);

		row.getAsOptionalIdentifier().ifPresent(id -> qtyReservationTable.putOrReplace(id, qtyReservationId));
	}

	/**
	 * Validates {@code M_QtyReservation} records by reloading them fresh from the DB and asserting their field values.
	 * Intended to be called after a shipment is completed to verify the allocation logic.
	 */
	@Then("validate M_QtyReservations:")
	public void validate_M_QtyReservations(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID)
				.forEach(this::validateReservation);
	}

	private void validateReservation(@NonNull final DataTableRow row) throws InterruptedException
	{
		StepDefUtil.tryAndWaitForData(() -> {
					final QtyReservationId qtyReservationId = qtyReservationTable.get(row.getAsIdentifier());
					return qtyReservationService.getById(qtyReservationId);
				})
				.validateUsingConsumer(qtyReservation -> {
					final SoftAssertions softly = new SoftAssertions();
					row.getAsOptionalQuantity("Qty", uomDAO::getByX12DE355)
							.ifPresent(expectedQty ->
									softly.assertThat(qtyReservation.getQty())
											.as("M_QtyReservation[%d].Qty", qtyReservation.getId().getRepoId())
											.isEqualTo(expectedQty));
					row.getAsOptionalQuantity("QtyDelivered", uomDAO::getByX12DE355)
							.ifPresent(expectedQtyDelivered ->
									softly.assertThat(qtyReservation.getQtyDelivered())
											.as("M_QtyReservation[%d].QtyDelivered", qtyReservation.getId().getRepoId())
											.isEqualTo(expectedQtyDelivered));
					row.getAsOptionalInt("QtyTU")
							.map(QtyTU::ofInt)
							.ifPresent(expectedQtyTU ->
									softly.assertThat(qtyReservation.getQtyTU())
											.as("M_QtyReservation[%d].QtyTU", qtyReservation.getId().getRepoId())
											.isEqualTo(expectedQtyTU));
					row.getAsOptionalBoolean("Processed").ifPresent(expectedProcessed ->
							softly.assertThat(qtyReservation.isFullyDelivered())
									.as("M_QtyReservation[%d].Processed", qtyReservation.getId().getRepoId())
									.isEqualTo(expectedProcessed));

					softly.assertAll();
				})
				.execute();
	}
}
