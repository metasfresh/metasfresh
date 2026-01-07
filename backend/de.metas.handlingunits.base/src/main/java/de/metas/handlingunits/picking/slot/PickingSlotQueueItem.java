package de.metas.handlingunits.picking.slot;

import de.metas.handlingunits.HuId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PickingSlotQueueItem
{
	@NonNull HuId huId;
}
