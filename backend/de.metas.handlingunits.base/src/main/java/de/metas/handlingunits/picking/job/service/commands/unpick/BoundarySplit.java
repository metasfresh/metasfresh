package de.metas.handlingunits.picking.job.service.commands.unpick;

import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

@Value
class BoundarySplit
{
	@NonNull PickingJobStepPickedToHU pickedToHU;
	@NonNull Quantity qtyToCarve;
}
