package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
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
