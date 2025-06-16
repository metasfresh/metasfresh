package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class PickingSlotQueuesSummary
{
	public static final PickingSlotQueuesSummary EMPTY = new PickingSlotQueuesSummary(ImmutableList.of());

	private final ImmutableMap<PickingSlotId, PickingSlotQueueSummary> queuesById;

	private PickingSlotQueuesSummary(@NonNull final Collection<PickingSlotQueueSummary> queues)
	{
		this.queuesById = Maps.uniqueIndex(queues, PickingSlotQueueSummary::getPickingSlotId);
	}

	public static PickingSlotQueuesSummary ofList(@NonNull final List<PickingSlotQueueSummary> queues) {return queues.isEmpty() ? EMPTY : new PickingSlotQueuesSummary(queues);}

	public static Collector<PickingSlotQueueSummary, ?, PickingSlotQueuesSummary> collect() {return GuavaCollectors.collectUsingListAccumulator(PickingSlotQueuesSummary::ofList);}

	public boolean isEmpty() {return queuesById.isEmpty();}

	public Set<PickingSlotId> getPickingSlotIds() {return queuesById.keySet();}

	public OptionalInt getCountHUs(@NonNull final PickingSlotId pickingSlotId)
	{
		final PickingSlotQueueSummary queue = queuesById.get(pickingSlotId);
		return queue != null ? OptionalInt.of(queue.getCountHUs()) : OptionalInt.empty();
	}

	public OptionalInt getCountHUs(@NonNull final Set<PickingSlotId> pickingSlotIds)
	{
		if (pickingSlotIds.isEmpty())
		{
			return OptionalInt.of(0);
		}

		int countHUs = 0;
		for (final PickingSlotId pickingSlotId : pickingSlotIds)
		{
			final PickingSlotQueueSummary queue = queuesById.get(pickingSlotId);
			if (queue == null)
			{
				return OptionalInt.empty();
			}

			countHUs += queue.getCountHUs();
		}

		return OptionalInt.of(countHUs);
	}

}
