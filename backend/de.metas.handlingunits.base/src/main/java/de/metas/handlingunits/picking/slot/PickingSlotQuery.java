package de.metas.handlingunits.picking.slot;

import de.metas.picking.api.PickingSlotId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class PickingSlotQuery
{
	@Nullable Set<PickingSlotId> onlyPickingSlotIds;
	@Nullable Set<PickingSlotId> excludePickingSlotIds;

	public static PickingSlotQuery onlyPickingSlotIds(@NonNull final Set<PickingSlotId> onlyPickingSlotIds)
	{
		Check.assumeNotEmpty(onlyPickingSlotIds, "onlyPickingSlotIds is not empty");
		return builder().onlyPickingSlotIds(onlyPickingSlotIds).build();
	}
}
