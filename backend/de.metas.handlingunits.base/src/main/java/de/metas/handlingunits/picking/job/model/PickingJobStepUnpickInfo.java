package de.metas.handlingunits.picking.job.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PickingJobStepUnpickInfo
{
	@NonNull HUInfo pickFromHU;
}
