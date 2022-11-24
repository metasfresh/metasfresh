package de.metas.distribution.ddorder.movement.schedule;

import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class DDOrderPickFromRequest
{
	@NonNull DDOrderMoveScheduleId scheduleId;

	@NonNull Quantity qtyPicked;
	@Nullable QtyRejectedReasonCode qtyNotPickedReason;
}
