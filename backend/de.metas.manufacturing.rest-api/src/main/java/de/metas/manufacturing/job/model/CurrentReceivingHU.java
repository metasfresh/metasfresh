package de.metas.manufacturing.job.model;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CurrentReceivingHU
{
	@NonNull HUPIItemProductId tuPIItemProductId;
	@NonNull HuId aggregateToLUId;
}
