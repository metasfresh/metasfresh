package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.order.OrderAndLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class DeleteQtyReservationRequest
{
	@NonNull OrderAndLineId orderAndLineId;
	@NonNull SupplyType supplyType;
	@Nullable Instant datePromised;
}
