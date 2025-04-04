package de.metas.handlingunits.picking.job.model;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
@Jacksonized
public class PickingJobStepPickedToHU
{
	@NonNull HuId pickFromHUId;
	@NonNull HUInfo actualPickedHU;
	@NonNull Quantity qtyPicked;
	@Nullable Quantity catchWeight;
	@Nullable Instant createdAt;
	// @NonNull PickingCandidateId pickingCandidateId;
}
