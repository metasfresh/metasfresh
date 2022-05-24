package de.metas.distribution.ddorder.movement.schedule.plan;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.model.I_DD_Order;

@Value
@Builder
public class DDOrderMovePlanCreateRequest
{
	@NonNull I_DD_Order ddOrder;
	boolean failIfNotFullAllocated;
}
