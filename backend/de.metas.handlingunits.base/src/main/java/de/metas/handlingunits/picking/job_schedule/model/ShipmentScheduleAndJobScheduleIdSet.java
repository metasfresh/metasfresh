package de.metas.handlingunits.picking.job_schedule.model;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class ShipmentScheduleAndJobScheduleIdSet implements Iterable<ShipmentScheduleAndJobScheduleId>
{
	public static final ShipmentScheduleAndJobScheduleIdSet EMPTY = new ShipmentScheduleAndJobScheduleIdSet(ImmutableSet.of());
	@NonNull private final ImmutableSet<ShipmentScheduleAndJobScheduleId> ids;

	private ShipmentScheduleAndJobScheduleIdSet(@NonNull final ImmutableSet<ShipmentScheduleAndJobScheduleId> ids)
	{
		this.ids = ids;
	}

	public static ShipmentScheduleAndJobScheduleIdSet ofSet(@NonNull final Set<ShipmentScheduleAndJobScheduleId> ids)
	{
		return ids.isEmpty() ? EMPTY : new ShipmentScheduleAndJobScheduleIdSet(ImmutableSet.copyOf(ids));
	}

	public static Collector<ShipmentScheduleAndJobScheduleId, ?, ShipmentScheduleAndJobScheduleIdSet> collect()
	{
		return GuavaCollectors.collectUsingHashSetAccumulator(ShipmentScheduleAndJobScheduleIdSet::ofSet);
	}

	public boolean isEmpty() {return ids.isEmpty();}

	@Override
	@NonNull
	public Iterator<ShipmentScheduleAndJobScheduleId> iterator() {return ids.iterator();}

	public Stream<ShipmentScheduleAndJobScheduleId> stream() {return ids.stream();}

	public Set<ShipmentScheduleAndJobScheduleId> toSet() {return ids;}

	public Set<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return ids.stream()
				.map(ShipmentScheduleAndJobScheduleId::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<PickingJobScheduleId> getJobScheduleIds()
	{
		return ids.stream()
				.map(ShipmentScheduleAndJobScheduleId::getJobScheduleId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

}
