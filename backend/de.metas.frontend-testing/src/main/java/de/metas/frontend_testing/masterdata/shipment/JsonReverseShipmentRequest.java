package de.metas.frontend_testing.masterdata.shipment;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Request to reverse (document-engine Reverse-Correct) a previously-created, completed {@code M_InOut}
 * shipment. Mirrors the customer "void shipment" action: it restores the HU snapshot taken at completion
 * and replays the per-transport-unit HU-trx lines, which is the trigger for the duplicate
 * listener-shaped {@code M_ShipmentSchedule_QtyPicked} rows on a single aggregate VHU.
 */
@Value
@Builder
@Jacksonized
public class JsonReverseShipmentRequest
{
	/** The {@code M_InOut_ID} returned by a prior shipment-create call (as a string). */
	@NonNull String shipmentId;
}
