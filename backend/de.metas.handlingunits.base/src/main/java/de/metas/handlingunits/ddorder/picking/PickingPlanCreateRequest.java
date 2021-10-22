package de.metas.handlingunits.ddorder.picking;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.model.I_DD_Order;

@Value
@Builder
public class PickingPlanCreateRequest
{
	@NonNull I_DD_Order ddOrder;
	boolean failIfNotFullAllocated;
}
