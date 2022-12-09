package de.metas.handlingunits.picking.job.model;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class PickingJobStepIdAndPickFromKey
{
	@NonNull PickingJobStepId pickingStepId;
	@NonNull PickingJobStepPickFromKey pickFromKey;
}
