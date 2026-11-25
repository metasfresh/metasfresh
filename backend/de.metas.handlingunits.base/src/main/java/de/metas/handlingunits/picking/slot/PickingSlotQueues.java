package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.HuId;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class PickingSlotQueues
{
	public static final PickingSlotQueues EMPTY = new PickingSlotQueues(ImmutableList.of());

	private final ImmutableMap<PickingSlotId, PickingSlotQueue> queuesById;

	private PickingSlotQueues(@NonNull final Collection<PickingSlotQueue> queues)
	{
		this.queuesById = Maps.uniqueIndex(queues, PickingSlotQueue::getPickingSlotId);
	}

	public static PickingSlotQueues ofList(@NonNull final List<PickingSlotQueue> queues) {return queues.isEmpty() ? EMPTY : new PickingSlotQueues(queues);}

	public static Collector<PickingSlotQueue, ?, PickingSlotQueues> collect() {return GuavaCollectors.collectUsingListAccumulator(PickingSlotQueues::ofList);}

	public boolean isEmpty() {return queuesById.isEmpty();}

	public Set<PickingSlotId> getPickingSlotIds() {return queuesById.keySet();}

	public ImmutableCollection<PickingSlotQueue> getQueues()
	{
		return queuesById.values();
	}

	/**
	 * @return the HUs of all queues
	 */
	public ImmutableSet<HuId> getHuIds()
	{
		return queuesById.values().stream()
				.flatMap(queue -> queue.getHuIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return the size of that slot's own queue (an HU queued in two slots counts in both); 0 if the slot has no queue
	 */
	public int getCountHUs(@NonNull final PickingSlotId pickingSlotId)
	{
		final PickingSlotQueue queue = queuesById.get(pickingSlotId);
		return queue != null ? queue.getCountHUs() : 0;
	}

	/**
	 * @return the slot of the first queue that holds the HU (an HU queued in more than one slot keeps its first slot)
	 * @throws AdempiereException if the HU is in no queue
	 */
	public PickingSlotId getPickingSlotId(@NonNull final HuId huId)
	{
		return queuesById.values().stream()
				.filter(queue -> queue.containsHuId(huId))
				.map(PickingSlotQueue::getPickingSlotId)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("HU " + huId + " is not in any picking slot queue"));
	}

	public PickingSlotQueue getQueue(@NonNull final PickingSlotId pickingSlotId)
	{
		final PickingSlotQueue queue = queuesById.get(pickingSlotId);
		if (queue == null)
		{
			throw new AdempiereException("No queue found for " + pickingSlotId);
		}
		return queue;
	}
}
