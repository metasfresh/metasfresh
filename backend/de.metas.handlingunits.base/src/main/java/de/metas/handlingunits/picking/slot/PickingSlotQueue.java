package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.picking.api.PickingSlotId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class PickingSlotQueue
{
	@NonNull PickingSlotId pickingSlotId;
	@NonNull ImmutableList<PickingSlotQueueItem> items;

	public boolean isEmpty() {return items.isEmpty();}

	public int getCountHUs() {return items.size();}

	public Set<HuId> getHuIds()
	{
		return items.stream()
				.map(PickingSlotQueueItem::getHuId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean containsHuId(@NonNull final HuId huId)
	{
		return items.stream().anyMatch(item -> HuId.equals(item.getHuId(), huId));
	}
}
