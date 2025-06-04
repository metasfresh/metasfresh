package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class PickingSlotQueueRepository
{
	private final IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);

	public PickingSlotQueues getNotEmptyQueues(@NonNull final PickingSlotQuery query)
	{
		final ImmutableListMultimap<PickingSlotId, PickingSlotQueueItem> items = huPickingSlotDAO.retrieveAllPickingSlotHUs(query)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						PickingSlotQueueRepository::extractPickingSlotId,
						PickingSlotQueueRepository::fromRecord
				));

		return items.keySet()
				.stream()
				.map(pickingSlotId -> PickingSlotQueue.builder()
						.pickingSlotId(pickingSlotId)
						.items(items.get(pickingSlotId))
						.build())
				.collect(PickingSlotQueues.collect());
	}

	public PickingSlotQueue getPickingSlotQueue(@NonNull final PickingSlotId pickingSlotId)
	{
		final ImmutableList<PickingSlotQueueItem> items = huPickingSlotDAO.retrievePickingSlotHUs(ImmutableSet.of(pickingSlotId))
				.stream()
				.map(PickingSlotQueueRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return PickingSlotQueue.builder()
				.pickingSlotId(pickingSlotId)
				.items(items)
				.build();
	}

	private static @NonNull PickingSlotId extractPickingSlotId(final I_M_PickingSlot_HU queueItemRecord)
	{
		return PickingSlotId.ofRepoId(queueItemRecord.getM_PickingSlot_ID());
	}

	private static PickingSlotQueueItem fromRecord(final I_M_PickingSlot_HU queueItemRecord)
	{
		return PickingSlotQueueItem.builder()
				.huId(HuId.ofRepoId(queueItemRecord.getM_HU_ID()))
				.build();
	}

}

