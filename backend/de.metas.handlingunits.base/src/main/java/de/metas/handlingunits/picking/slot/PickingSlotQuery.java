package de.metas.handlingunits.picking.slot;

import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class PickingSlotQuery
{
	@Nullable Set<PickingSlotId> onlyPickingSlotIds;
	@Nullable Set<PickingSlotId> excludePickingSlotIds;
}
