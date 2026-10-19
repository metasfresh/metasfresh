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
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.ShipmentScheduleRecomputeRaceHarness;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.qty_reservation.CreateQtyReservationRequest;
import de.metas.inoutcandidate.qty_reservation.QtyReservationId;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.inoutcandidate.qty_reservation.ReduceQtyReservationRequest;
import de.metas.inoutcandidate.qty_reservation.SupplyType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.order.OrderAndLineId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_QtyReservation;

import javax.annotation.Nullable;

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
 *   <li><b>AttributesKey</b> — (optional) raw attributes key string (§&§-separated M_AttributeValue_IDs); mutually exclusive with {@code M_AttributeSetInstance_ID}</li>
 *   <li><b>M_AttributeSetInstance_ID</b> — (optional, identifier-ref) ASI whose storage-relevant attributes are used to compute the AttributesKey; mutually exclusive with {@code AttributesKey}</li>
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
 *
 * <h3>{@code M_QtyReservation bucket is reduced:}</h3>
 * <p>Partially reverses (reduces) the reservation bucket of an order line via {@code QtyReservationService#reduceReservation}, like the cockpit V2 "reverse reservation" overlay.
 * <pre>
 * When M_QtyReservation bucket is reduced:
 *   | C_OrderLine_ID | OPT.SupplyType | QtyTU |
 *   | orderLine_1    | OH             | 1     |
 * </pre>
 * <ul>
 *   <li><b>C_OrderLine_ID</b> — (required, identifier-ref) the sales order line whose reservation is reduced</li>
 *   <li><b>SupplyType</b> — (optional, default {@code OH}) supply type of the bucket: {@code OH} or {@code PS}</li>
 *   <li><b>QtyTU</b> — (required) number of TUs to unreserve; must not exceed the bucket's reversible TUs</li>
 * </ul>
 *
 * <h3>{@code M_QtyReservation bucket is reduced, committed only after the pending recompute ran:}</h3>
 * <p>Same columns as {@code M_QtyReservation bucket is reduced:}, but the reduction's transaction stays open while the pending shipment schedule recompute pass runs
 * and is committed afterwards. Requires {@code the shipment schedule recompute queues are paused}.
 * <pre>
 * When M_QtyReservation bucket is reduced, committed only after the pending recompute ran:
 *   | C_OrderLine_ID | SupplyType | QtyTU |
 *   | orderLine_1    | OH         | 1     |
 * </pre>
 *
 * <h3>{@code M_QtyReservation bucket reduction is rejected:}</h3>
 * <p>Same columns as {@code M_QtyReservation bucket is reduced:}, but expects the reduction to fail because more TUs are requested than are reversible.
 * <pre>
 * When M_QtyReservation bucket reduction is rejected:
 *   | C_OrderLine_ID | OPT.SupplyType | QtyTU |
 *   | orderLine_1    | OH             | 6     |
 * </pre>
 *
 * <h3>{@code validate M_QtyReservation bucket:}</h3>
 * <p>Validates the reservation bucket of an order line.
 * <pre>
 * Then validate M_QtyReservation bucket:
 *   | C_OrderLine_ID | OPT.SupplyType | OPT.ReversibleQtyTU | OPT.ReservedQtyTU |
 *   | orderLine_1    | OH             | 2                   | 3                 |
 * </pre>
 * <ul>
 *   <li><b>C_OrderLine_ID</b> — (required, identifier-ref) the sales order line</li>
 *   <li><b>SupplyType</b> — (optional, default {@code OH}) supply type of the bucket</li>
 *   <li><b>ReversibleQtyTU</b> — (optional) expected reserved-minus-delivered TUs of the bucket</li>
 *   <li><b>ReservedQtyTU</b> — (optional) expected total unprocessed reserved TUs of the order line</li>
 * </ul>
 *
 * <h3>{@code M_QtyReservations are deleted:}</h3>
 * <p>Asserts that the given reservations no longer exist in the DB (e.g. after the whole bucket was reversed).
 * <pre>
 * Then M_QtyReservations are deleted:
 *   | Identifier       |
 *   | qtyReservation_1 |
 * </pre>
 * <ul>
 *   <li><b>Identifier</b> — (required) alias of the reservation that must be gone</li>
 * </ul>
 */
@RequiredArgsConstructor
public class M_QtyReservation_StepDef
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final QtyReservationService qtyReservationService = SpringContextHolder.instance.getBean(QtyReservationService.class);

	@NonNull private final M_QtyReservation_StepDefData qtyReservationTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_Project_StepDefData projectTable;
	@NonNull private final M_AttributeSetInstance_StepDefData asiTable;
	@NonNull private final ShipmentScheduleRecomputeRaceHarness recomputeRaceHarness;

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
		final QtyReservationId qtyReservationId = qtyReservationService.makeReservation(
				CreateQtyReservationRequest.builder()
						.orderAndLineId(orderLineTable.getOrderAndLineId(row.getAsIdentifier("C_OrderLine_ID")))
						.productId(row.getAsIdentifier("M_Product_ID").lookupNotNullIdIn(productTable))
						.warehouseId(row.getAsIdentifier("M_Warehouse_ID").lookupNotNullIdIn(warehouseTable))
						.supplyType(row.getAsOptionalEnum("SupplyType", SupplyType.class).orElse(SupplyType.ON_HAND))
						.qtyTU(QtyTU.ofInt(row.getAsInt("QtyTU")))
						.qty(row.getAsQuantity("Qty", "C_UOM_ID", uomDAO::getByX12DE355))
						.projectId(row.getAsOptionalIdentifier("C_Project_ID").map(projectTable::getId).orElse(null))
						.attributesKey(extractAttributesKey(row))
						.build()
		);

		row.getAsOptionalIdentifier().ifPresent(id -> qtyReservationTable.putOrReplace(id, qtyReservationId));
	}

	@Nullable
	private AttributesKey extractAttributesKey(final @NonNull DataTableRow row)
	{
		return row.getAsOptionalString("AttributesKey")
				.map(AttributesKey::ofString)
				.orElseGet(() -> row.getAsOptionalIdentifier(I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID)
						.map(asiTable::getId)
						.flatMap(AttributesKeys::createAttributesKeyFromASIStorageAttributes)
						.orElse(null));
	}

	/**
	 * Partially reverses (reduces) the reservation bucket of an order line, like the cockpit V2 "reverse reservation" overlay.
	 * <pre>
	 * When M_QtyReservation bucket is reduced:
	 *   | C_OrderLine_ID | OPT.SupplyType | QtyTU |
	 *   | orderLine_1    | OH             | 1     |
	 * </pre>
	 *
	 * @see M_QtyReservation_StepDef class JavaDoc, section {@code M_QtyReservation bucket is reduced:}
	 */
	@When("M_QtyReservation bucket is reduced:")
	public void reduce_M_QtyReservation_bucket(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> trxManager.runInNewTrx(() -> qtyReservationService.reduceReservation(toReduceRequest(row))));
	}

	/**
	 * Reduces the reservation bucket like {@code M_QtyReservation bucket is reduced:}, but keeps the reduction's transaction open while the pending
	 * shipment schedule recompute pass runs; the transaction is committed after the pass. Requires the paused recompute queues.
	 * While the transaction is open, asserts that the reduction is not visible outside, i.e. that the bucket still has its reversible TUs.
	 * <p>
	 * Stands in for reducing a reservation from the material cockpit ({@code MD_CockpitV2_DeleteQtyReservation:82}), which calls
	 * {@code QtyReservationService.reduceReservation}; its storage-segment flag (QtyReservationService:191) is raised outside the reduction's
	 * transaction. The reduction is held open at that point because only a commit after the pass makes the race deterministic.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns Same as {@code M_QtyReservation bucket is reduced:}
	 * @cucumber.depends StepDefData: C_OrderLine_StepDefData<br>
	 *   Given the shipment schedule recompute queues are paused
	 * @cucumber.example
	 * <pre>
	 * When M_QtyReservation bucket is reduced, committed only after the pending recompute ran:
	 *   | C_OrderLine_ID | SupplyType | QtyTU |
	 *   | orderLine_1    | OH         | 1     |
	 * </pre>
	 */
	@When("M_QtyReservation bucket is reduced, committed only after the pending recompute ran:")
	public void reduce_M_QtyReservation_bucket_committedAfterRecompute(@NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final ReduceQtyReservationRequest request = toReduceRequest(row);
			final QtyTU reversibleBefore = qtyReservationService.getTotalReversibleQtyTU(request.getOrderAndLineId(), request.getSupplyType(), request.getDatePromised());

			recomputeRaceHarness.runInTransactionCommittedAfterRecompute(
					() -> {
						qtyReservationService.reduceReservation(request);
						return null;
					},
					() -> Assertions.assertThat(qtyReservationService.getTotalReversibleQtyTU(request.getOrderAndLineId(), request.getSupplyType(), request.getDatePromised()))
							.as("reversible QtyTU of %s visible outside the reduction's transaction (the reduction must not commit before the recompute pass ran)", request)
							.isEqualTo(reversibleBefore));
		});
	}

	/**
	 * Same as {@code M_QtyReservation bucket is reduced:}, but expects the request to be rejected (more TUs than reversible).
	 *
	 * @see M_QtyReservation_StepDef class JavaDoc, section {@code M_QtyReservation bucket reduction is rejected:}
	 */
	@When("M_QtyReservation bucket reduction is rejected:")
	public void reduce_M_QtyReservation_bucket_is_rejected(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> Assertions.assertThatThrownBy(
						() -> trxManager.runInNewTrx(() -> qtyReservationService.reduceReservation(toReduceRequest(row))))
				.as("reduction of %s should be rejected", row)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Cannot unreserve"));
	}

	private ReduceQtyReservationRequest toReduceRequest(@NonNull final DataTableRow row)
	{
		return ReduceQtyReservationRequest.builder()
				.orderAndLineId(orderLineTable.getOrderAndLineId(row.getAsIdentifier("C_OrderLine_ID")))
				.supplyType(row.getAsOptionalEnum("SupplyType", SupplyType.class).orElse(SupplyType.ON_HAND))
				.qtyToUnreserveTU(QtyTU.ofInt(row.getAsInt("QtyTU")))
				.build();
	}

	/**
	 * Validates the reservation bucket of an order line.
	 * <pre>
	 * Then validate M_QtyReservation bucket:
	 *   | C_OrderLine_ID | OPT.SupplyType | OPT.ReversibleQtyTU | OPT.ReservedQtyTU |
	 *   | orderLine_1    | OH             | 2                   | 3                 |
	 * </pre>
	 * {@code ReversibleQtyTU} = reserved minus delivered TUs of the bucket; {@code ReservedQtyTU} = all unprocessed reserved TUs of the order line.
	 *
	 * @see M_QtyReservation_StepDef class JavaDoc, section {@code validate M_QtyReservation bucket:}
	 */
	@Then("validate M_QtyReservation bucket:")
	public void validate_M_QtyReservation_bucket(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final OrderAndLineId orderAndLineId = orderLineTable.getOrderAndLineId(row.getAsIdentifier("C_OrderLine_ID"));
			final SupplyType supplyType = row.getAsOptionalEnum("SupplyType", SupplyType.class).orElse(SupplyType.ON_HAND);
			final SoftAssertions softly = new SoftAssertions();
			row.getAsOptionalInt("ReversibleQtyTU").ifPresent(expected ->
					softly.assertThat(qtyReservationService.getTotalReversibleQtyTU(orderAndLineId, supplyType, null).toInt())
							.as("reversible QtyTU of %s/%s", orderAndLineId, supplyType)
							.isEqualTo(expected));
			row.getAsOptionalInt("ReservedQtyTU").ifPresent(expected ->
					softly.assertThat(qtyReservationService.getReservedQtyTU(orderAndLineId).toInt())
							.as("reserved QtyTU of %s", orderAndLineId)
							.isEqualTo(expected));
			softly.assertAll();
		});
	}

	/**
	 * Asserts that the given reservations no longer exist in the DB (e.g. after the whole bucket was reversed).
	 *
	 * @see M_QtyReservation_StepDef class JavaDoc, section {@code M_QtyReservations are deleted:}
	 */
	@Then("M_QtyReservations are deleted:")
	public void M_QtyReservations_are_deleted(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final QtyReservationId id = qtyReservationTable.get(row.getAsIdentifier());
			final int count = queryBL.createQueryBuilder(I_M_QtyReservation.class)
					.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_M_QtyReservation_ID, id.getRepoId())
					.create()
					.count();
			Assertions.assertThat(count).as("M_QtyReservation[%d] still exists", id.getRepoId()).isZero();
		});
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
