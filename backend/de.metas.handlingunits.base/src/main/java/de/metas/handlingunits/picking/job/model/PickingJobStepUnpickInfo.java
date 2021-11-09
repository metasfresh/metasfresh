package de.metas.handlingunits.picking.job.model;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingJobStepUnpickInfo
{
	@Nullable HUInfo pickFromHU;
}
