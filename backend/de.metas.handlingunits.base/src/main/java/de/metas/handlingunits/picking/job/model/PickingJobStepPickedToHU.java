package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PickingCandidateId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class PickingJobStepPickedToHU
{
	@NonNull HuId pickFromHUId;
	@NonNull HuId actualPickedHUId;
	@NonNull Quantity qtyPicked;
	@NonNull PickingCandidateId pickingCandidateId;
}
