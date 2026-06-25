package de.metas.handlingunits.picking.job.service.commands.unpick;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedToHU;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Value
class LifoSelection
{
	@NonNull Map<StepPickFromKey, List<PickingJobStepPickedToHU>> wholeHUsByStepPickFrom;
	@NonNull Map<StepPickFromKey, BoundarySplit> boundaryByStepPickFrom;

	Stream<StepUnpickInstructions> toUnpickInstructions()
	{
		final ImmutableSet<StepPickFromKey> allKeys = ImmutableSet.<StepPickFromKey>builder()
				.addAll(wholeHUsByStepPickFrom.keySet())
				.addAll(boundaryByStepPickFrom.keySet())
				.build();

		return allKeys.stream()
				.map(key -> {
					final BoundarySplit boundary = boundaryByStepPickFrom.get(key);
					return StepUnpickInstructions.builder()
							.stepId(key.getStepId())
							.pickFromKey(key.getPickFromKey())
							.pickedToHUsToUnpick(ImmutableList.copyOf(wholeHUsByStepPickFrom.getOrDefault(key, ImmutableList.of())))
							.boundaryHuToSplit(boundary != null ? boundary.getPickedToHU() : null)
							.boundarySplitQty(boundary != null ? boundary.getQtyToCarve() : null)
							.build();
				});
	}
}
