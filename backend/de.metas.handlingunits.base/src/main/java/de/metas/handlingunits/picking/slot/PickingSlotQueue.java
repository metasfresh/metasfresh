package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PickingSlotQueue
{
	@NonNull PickingSlotId pickingSlotId;
	@NonNull ImmutableList<PickingSlotQueueItem> items;
}
