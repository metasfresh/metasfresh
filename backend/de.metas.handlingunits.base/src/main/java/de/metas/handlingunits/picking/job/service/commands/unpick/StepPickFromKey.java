package de.metas.handlingunits.picking.job.service.commands.unpick;

import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import lombok.NonNull;
import lombok.Value;

@Value
class StepPickFromKey
{
	@NonNull PickingJobStepId stepId;
	@NonNull PickingJobStepPickFromKey pickFromKey;
}
