package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueueQuery;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.picking.api.PickingSlotId;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * The crates (HUs) queued in the picking slots of one consolidation job, each with the slot it sits in.
 */
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class HUConsolidationJobCrates
{
	public static final HUConsolidationJobCrates EMPTY = new HUConsolidationJobCrates(ImmutableMap.of(), ImmutableMap.of());

	@NonNull private final ImmutableMap<HuId, PickingSlotId> pickingSlotIdsByHuId;
	/** per slot: the size of that slot's own queue (an HU queued in two slots counts in both) */
	@NonNull private final ImmutableMap<PickingSlotId, Integer> countHUsByPickingSlotId;

	public static HUConsolidationJobCrates of(@NonNull final ImmutableMap<HuId, PickingSlotId> pickingSlotIdsByHuId)
	{
		if (pickingSlotIdsByHuId.isEmpty())
		{
			return EMPTY;
		}
		final LinkedHashMap<PickingSlotId, Integer> counts = new LinkedHashMap<>();
		pickingSlotIdsByHuId.values().forEach(slotId -> counts.merge(slotId, 1, Integer::sum));
		return new HUConsolidationJobCrates(pickingSlotIdsByHuId, ImmutableMap.copyOf(counts));
	}

	/**
	 * An HU seen in more than one queue keeps its first slot in {@link #getPickingSlotId(HuId)}, but counts in every slot whose queue holds it.
	 */
	public static HUConsolidationJobCrates ofQueues(@NonNull final Collection<PickingSlotQueue> queues)
	{
		final LinkedHashMap<HuId, PickingSlotId> pickingSlotIdsByHuId = new LinkedHashMap<>();
		final LinkedHashMap<PickingSlotId, Integer> counts = new LinkedHashMap<>();
		for (final PickingSlotQueue queue : queues)
		{
			counts.put(queue.getPickingSlotId(), queue.getCountHUs());
			for (final HuId huId : queue.getHuIds())
			{
				pickingSlotIdsByHuId.putIfAbsent(huId, queue.getPickingSlotId());
			}
		}

		return pickingSlotIdsByHuId.isEmpty()
				? EMPTY
				: new HUConsolidationJobCrates(ImmutableMap.copyOf(pickingSlotIdsByHuId), ImmutableMap.copyOf(counts));
	}

	/**
	 * Loads the crates of the given slots with ONE queue read.
	 * An empty slot set gives {@link #EMPTY} without any query ({@link PickingSlotQueueQuery#onlyPickingSlotIds(Set)} requires a non-empty set).
	 */
	public static HUConsolidationJobCrates load(
			@NonNull final Set<PickingSlotId> pickingSlotIds,
			@NonNull final PickingSlotService pickingSlotService)
	{
		if (pickingSlotIds.isEmpty())
		{
			return EMPTY;
		}

		return ofQueues(pickingSlotService.getNotEmptyQueues(PickingSlotQueueQuery.onlyPickingSlotIds(pickingSlotIds)).getQueues());
	}

	public boolean isEmpty() {return pickingSlotIdsByHuId.isEmpty();}

	public int getCountHUs(@NonNull final PickingSlotId pickingSlotId)
	{
		return countHUsByPickingSlotId.getOrDefault(pickingSlotId, 0);
	}

	public ImmutableSet<HuId> getHuIds() {return pickingSlotIdsByHuId.keySet();}

	public PickingSlotId getPickingSlotId(@NonNull final HuId huId)
	{
		final PickingSlotId pickingSlotId = pickingSlotIdsByHuId.get(huId);
		if (pickingSlotId == null)
		{
			throw new AdempiereException("HU " + huId + " is not a crate of this job");
		}
		return pickingSlotId;
	}
}
