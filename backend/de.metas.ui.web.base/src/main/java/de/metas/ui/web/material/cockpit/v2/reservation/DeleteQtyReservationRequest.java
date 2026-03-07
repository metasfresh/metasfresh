package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class DeleteQtyReservationRequest
{
	@NonNull OrderLineId orderLineId;
	@NonNull SupplyType supplyType;
	@Nullable Instant datePromised;
}
