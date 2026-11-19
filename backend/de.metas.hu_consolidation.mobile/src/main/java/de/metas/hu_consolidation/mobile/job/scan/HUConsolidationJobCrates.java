package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueueQuery;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.picking.api.PickingSlotId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * The crates (HUs) queued in the picking slots of one consolidation job, each with the slot it sits in.
 */
@EqualsAndHashCode
@ToString
public final class HUConsolidationJobCrates
{
	public static final HUConsolidationJobCrates EMPTY = new HUConsolidationJobCrates(ImmutableMap.of());

	private final ImmutableMap<HuId, PickingSlotId> pickingSlotIdsByHuId;

	private HUConsolidationJobCrates(@NonNull final ImmutableMap<HuId, PickingSlotId> pickingSlotIdsByHuId)
	{
		this.pickingSlotIdsByHuId = pickingSlotIdsByHuId;
	}

	public static HUConsolidationJobCrates of(@NonNull final ImmutableMap<HuId, PickingSlotId> pickingSlotIdsByHuId)
	{
		return pickingSlotIdsByHuId.isEmpty() ? EMPTY : new HUConsolidationJobCrates(pickingSlotIdsByHuId);
	}

	/**
	 * Loads the crates of the given slots with ONE queue read.
	 * An empty slot set gives {@link #EMPTY} without any query ({@link PickingSlotQueueQuery#onlyPickingSlotIds(Set)} requires a non-empty set).
	 * An HU seen in more than one slot keeps its first slot.
	 */
	public static HUConsolidationJobCrates load(
			@NonNull final Set<PickingSlotId> pickingSlotIds,
			@NonNull final PickingSlotService pickingSlotService)
	{
		if (pickingSlotIds.isEmpty())
		{
			return EMPTY;
		}

		final LinkedHashMap<HuId, PickingSlotId> pickingSlotIdsByHuId = new LinkedHashMap<>();
		for (final PickingSlotQueue queue : pickingSlotService.getNotEmptyQueues(PickingSlotQueueQuery.onlyPickingSlotIds(pickingSlotIds)).getQueues())
		{
			for (final HuId huId : queue.getHuIds())
			{
				pickingSlotIdsByHuId.putIfAbsent(huId, queue.getPickingSlotId());
			}
		}

		return of(ImmutableMap.copyOf(pickingSlotIdsByHuId));
	}

	public boolean isEmpty() {return pickingSlotIdsByHuId.isEmpty();}

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
