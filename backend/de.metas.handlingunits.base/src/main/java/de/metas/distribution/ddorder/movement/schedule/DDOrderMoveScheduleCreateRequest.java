package de.metas.distribution.ddorder.movement.schedule;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;

@Value
@Builder
public class DDOrderMoveScheduleCreateRequest
{
	@NonNull DDOrderId ddOrderId;
	@NonNull DDOrderLineId ddOrderLineId;
	@NonNull HuId pickFromHUId;
	@NonNull Quantity qtyToPick;
	boolean isPickWholeHU;
}
