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
	@NonNull private final IHUPickingSlotDAO dao = Services.get(IHUPickingSlotDAO.class);

	public PickingSlotQueuesSummary getNotEmptyQueuesSummary(@NonNull final PickingSlotQuery query)
	{
		// TODO: improve performance by really running the aggregates in database
		// i.e. we need to implement something like org.adempiere.ad.dao.IQueryBuilder.aggregateOnColumn(java.lang.String, java.lang.Class<TargetModelType>)
		// but which is more low-level focuses, e.g. aggregate().groupBy(col1).groupBy(col2).countDistinct("colAlias", col3, col4).streamAsMaps()...
		final PickingSlotQueues queues = getNotEmptyQueues(query);
		return queues.getQueues()
				.stream()
				.map(queue -> PickingSlotQueueSummary.builder()
						.pickingSlotId(queue.getPickingSlotId())
						.countHUs(queue.getCountHUs())
						.build())
				.collect(PickingSlotQueuesSummary.collect());
	}

	public PickingSlotQueues getNotEmptyQueues(@NonNull final PickingSlotQuery query)
	{
		final ImmutableListMultimap<PickingSlotId, PickingSlotQueueItem> items = dao.retrieveAllPickingSlotHUs(query)
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
		final ImmutableList<PickingSlotQueueItem> items = dao.retrievePickingSlotHUs(ImmutableSet.of(pickingSlotId))
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

