package de.metas.frontend_testing.masterdata.shipment;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonReverseShipmentResponse
{
	/** The {@code M_InOut_ID} of the reversed (original) shipment. */
	@NonNull String id;
	@NonNull String documentNo;
	/** The original shipment's {@code DocStatus} after the reverse (expected {@code "RE"} = Reversed). */
	@NonNull String docStatus;

	/**
	 * After the reverse, the largest number of ACTIVE, not-yet-shipped ({@code M_InOutLine_ID} IS NULL)
	 * {@code M_ShipmentSchedule_QtyPicked} rows that are identical on the partial-unique-index tuple
	 * (VHU, TU, LU, QtyLU, QtyTU, QtyPicked), across all shipment schedules of the reversed shipment's order.
	 *
	 * <p>This is the count that would collide on {@code M_ShipmentSchedule_QtyPicked_UI} once the next
	 * shipment assigns them an {@code M_InOutLine_ID}: {@code 1} means the listener consolidated the
	 * aggregate-HU snapshot replay (fix in place); {@code > 1} means duplicate rows survive (no fix).
	 */
	int maxIdenticalUnshippedQtyPickedRowsPerVhuTuple;
}
