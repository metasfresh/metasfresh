package de.metas.inoutcandidate.qty_reservation.qty_delivered_update;

import de.metas.inout.InOutId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class UpdateQtyDeliveredRequest
{
	@NonNull InOutId shipmentId;
}
