package de.metas.handlingunits.picking.slot;

import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;

@Value
@Builder
public class PickingSlotReservation
{
	@NonNull PickingSlotId pickingSlotId;
	@NonNull @Delegate PickingSlotReservationValue reservationValue;
}
