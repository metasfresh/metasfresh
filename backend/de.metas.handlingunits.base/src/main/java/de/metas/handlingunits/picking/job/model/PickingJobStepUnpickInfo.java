package de.metas.handlingunits.picking.job.model;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class PickingJobStepUnpickInfo
{
	/** HUs whose full picked qty is removed from the package. */
	@NonNull List<PickingJobStepPickedToHU> unpickedHUs;

	/**
	 * Partial (split-boundary) unpick: the boundary CU whose picked qty is reduced (not fully removed)
	 * because only part of it was carved out and moved. {@code null} when no boundary split happened.
	 */
	@Nullable PickingJobStepPickedToHU huToReduce;

	/** The new (lowered) picked qty for {@link #huToReduce} — the remainder left packed. */
	@Nullable Quantity reducedQtyPicked;

	public static PickingJobStepUnpickInfo ofUnpickedHUs(List<PickingJobStepPickedToHU> unpickedHUs)
	{
		return builder().unpickedHUs(unpickedHUs).build();
	}
}
