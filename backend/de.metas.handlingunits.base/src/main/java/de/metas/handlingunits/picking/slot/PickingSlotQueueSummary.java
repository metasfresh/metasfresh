package de.metas.handlingunits.picking.slot;

import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PickingSlotQueueSummary
{
	@NonNull PickingSlotId pickingSlotId;
	int countHUs;
}
