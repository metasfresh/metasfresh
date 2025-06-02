package de.metas.handlingunits.picking.slot;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingSlotAllocateRequest
{
	@NonNull PickingSlotId pickingSlotId;
	@NonNull BPartnerLocationId bpartnerAndLocationId;
	@Nullable PickingJobId pickingJobId;
}
