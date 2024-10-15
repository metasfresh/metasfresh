package de.metas.handlingunits.picking.job.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PickingJobStepUnpickInfo
{
	@NonNull List<PickingJobStepPickedToHU> unpickedHUs;

	public static PickingJobStepUnpickInfo ofUnpickedHUs(List<PickingJobStepPickedToHU> unpickedHUs)
	{
		return builder().unpickedHUs(unpickedHUs).build();
	}
}
