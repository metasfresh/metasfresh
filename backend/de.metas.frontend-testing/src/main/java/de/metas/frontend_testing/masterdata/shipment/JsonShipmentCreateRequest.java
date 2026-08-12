package de.metas.frontend_testing.masterdata.shipment;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonShipmentCreateRequest
{
	@NonNull Identifier salesOrder;

	/**
	 * When {@code true} (default), the generated {@code M_InOut} is completed (DocStatus='CO').
	 * When {@code false}, it is left as a DRAFT (DocStatus='DR', Processed='N') so that the
	 * shipment line is bound to the schedule's {@code M_ShipmentSchedule_QtyPicked} rows
	 * (M_InOutLine_ID set) without completing the document.
	 */
	@Nullable Boolean complete;

	/**
	 * Shipment-schedule quantity type to ship: {@code "D"} = quantity to deliver (default),
	 * {@code "P"} = already-picked quantity, {@code "PD"} = both. Mirrors the
	 * {@code QuantityType} parameter of the desktop "generate shipments" process.
	 */
	@Nullable String quantityType;
}
