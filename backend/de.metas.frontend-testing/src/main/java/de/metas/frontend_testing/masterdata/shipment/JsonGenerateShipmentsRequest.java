package de.metas.frontend_testing.masterdata.shipment;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Request to generate shipments from a sales order's shipment schedules via the REAL
 * {@code ShipmentService.generateShipmentsForScheduleIds} path — the faithful equivalent of the
 * desktop "Generate Shipments" process (NOT the {@code ShipmentCreateCommand} shortcut, which calls
 * {@code prepareShipmentSchedulesWithHU} directly and diverges after a reverse).
 *
 * <p>With {@code quantityType = "P"} (Picked) this ships the schedule's already-picked qty through the
 * {@code GenerateInOutFromShipmentSchedules} workpackage; when no picked qty survives (e.g. the customer
 * "can't recreate shipment after void" defect) it generates NO shipment.
 */
@Value
@Builder
@Jacksonized
public class JsonGenerateShipmentsRequest
{
	/** The {@code C_Order_ID} (sales order) whose shipment schedules shall be shipped (as a string). */
	@NonNull String salesOrderId;

	/** Quantity type to use: {@code "P"} = Picked qty (default), {@code "D"} = QtyToDeliver. */
	@Nullable String quantityType;

	/** Whether to complete the generated shipment. Defaults to {@code true}. */
	@Nullable Boolean complete;
}
