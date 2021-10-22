package de.metas.handlingunits.ddorder.picking;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.DDOrderId;
import org.eevolution.api.DDOrderLineId;

@Value
@Builder
public class ScheduleToPickRequest
{
	@NonNull DDOrderId ddOrderId;
	@NonNull DDOrderLineId ddOrderLineId;
	@NonNull HuId pickFromHUId;
	@NonNull Quantity qtyToPick;
	boolean isPickWholeHU;
}
