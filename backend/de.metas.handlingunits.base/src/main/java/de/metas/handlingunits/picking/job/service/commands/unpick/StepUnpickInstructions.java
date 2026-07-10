package de.metas.handlingunits.picking.job.service.commands.unpick;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.job.model.PickingJobStepId;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFromKey;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
@VisibleForTesting
public class StepUnpickInstructions
{
	@NonNull PickingJobStepId stepId;
	@NonNull PickingJobStepPickFromKey pickFromKey;
	/**
	 * When present: reverse only these specific packed HUs entirely (subset path, whole-CU portion).
	 * When absent: reverse all packed HUs for this step/pickFrom (whole-step path).
	 * May be an empty list when only a boundary split happens (see {@link #boundaryHuToSplit}).
	 */
	@Nullable ImmutableList<PickingJobStepPickedToHU> pickedToHUsToUnpick;

	/**
	 * Subset path only: the boundary CU that must be physically split because only part of its qty is
	 * being removed. {@code null} when the requested qty lands exactly on whole-CU boundaries.
	 */
	@Nullable PickingJobStepPickedToHU boundaryHuToSplit;

	/** The qty to carve out of {@link #boundaryHuToSplit} (the rest stays packed). */
	@Nullable Quantity boundarySplitQty;
}
