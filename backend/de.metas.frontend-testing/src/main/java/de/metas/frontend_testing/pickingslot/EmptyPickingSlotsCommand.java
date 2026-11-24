package de.metas.frontend_testing.pickingslot;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;

import java.util.Set;

/**
 * Removes all HUs from the queues of the given picking slots, then releases each slot if possible. Other picking slots are not changed.
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
