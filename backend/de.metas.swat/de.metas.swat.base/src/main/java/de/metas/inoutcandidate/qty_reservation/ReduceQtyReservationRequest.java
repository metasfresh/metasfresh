package de.metas.inoutcandidate.qty_reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.OrderAndLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

/**
 * Request to partially reduce the reservation of one bucket (order line + supply type + optional date promised).
 */
@Value
@Builder
public class ReduceQtyReservationRequest
{
	@NonNull OrderAndLineId orderAndLineId;
	@NonNull SupplyType supplyType;
	@Nullable Instant datePromised;
	@NonNull QtyTU qtyToUnreserveTU;
}
