package de.metas.frontend_testing.pickingslot;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

/**
 * Removes all HUs from the given picking slots and releases them (if possible).
 * A test calls it for its own slots afterwards: every non-empty picking slot is offered as an HU consolidation job to every later test,
 * so slots a test left full would make the job list of all later tests (and runs) longer.
 */
@Builder
public class EmptyPickingSlotsCommand
{
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final Set<PickingSlotId> pickingSlotIds;

	public void execute()
	{
		pickingSlotIds.forEach(this::emptyPickingSlot);
	}

	private void emptyPickingSlot(@NonNull final PickingSlotId pickingSlotId)
	{
		final Set<HuId> huIds = pickingSlotService.getPickingSlotQueue(pickingSlotId).getHuIds();
		if (!huIds.isEmpty())
		{
			pickingSlotService.removeFromPickingSlotQueue(pickingSlotId, huIds);
		}
		pickingSlotService.releasePickingSlotIfPossible(pickingSlotId);
	}
}
