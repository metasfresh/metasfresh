package de.metas.picking.job_schedule.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class PickingJobScheduleCollection implements Iterable<PickingJobSchedule>
{
	public static final PickingJobScheduleCollection EMPTY = new PickingJobScheduleCollection(ImmutableList.of());

	@NonNull private final ImmutableList<PickingJobSchedule> list;
	@NonNull @Getter private final ImmutableSet<PickingJobScheduleId> jobScheduleIds;
	@NonNull private final ImmutableListMultimap<ShipmentScheduleId, PickingJobSchedule> byShipmentScheduleId;

	private PickingJobScheduleCollection(@NonNull final ImmutableList<PickingJobSchedule> list)
	{
		this.list = list;
		this.jobScheduleIds = list.stream().map(PickingJobSchedule::getId).collect(ImmutableSet.toImmutableSet());
		this.byShipmentScheduleId = Multimaps.index(list, PickingJobSchedule::getShipmentScheduleId);
	}

	public static PickingJobScheduleCollection ofCollection(@NonNull final Collection<PickingJobSchedule> list)
	{
		return list.isEmpty() ? EMPTY : new PickingJobScheduleCollection(ImmutableList.copyOf(list));
	}

	public static Collector<PickingJobSchedule, ?, PickingJobScheduleCollection> collect() {return GuavaCollectors.collectUsingListAccumulator(PickingJobScheduleCollection::ofCollection);}

	public static Collector<PickingJobSchedule, ?, Map<ShipmentScheduleId, PickingJobScheduleCollection>> collectGroupedByShipmentScheduleId()
	{
		return Collectors.groupingBy(PickingJobSchedule::getShipmentScheduleId, collect());
	}

	@Override
	public @NotNull Iterator<PickingJobSchedule> iterator()
	{
		return list.iterator();
	}

	public boolean isEmpty()
	{
		return list.isEmpty();
	}

	public void assertSingleShipmentScheduleId(final @NonNull ShipmentScheduleId expectedShipmentScheduleId)
	{
		final ShipmentScheduleId shipmentScheduleId = getSingleShipmentScheduleId();
		if (!ShipmentScheduleId.equals(shipmentScheduleId, expectedShipmentScheduleId))
		{
			throw new AdempiereException("Expected shipment schedule " + expectedShipmentScheduleId + " but found " + shipmentScheduleId)
					.setParameter("list", list);
		}
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return byShipmentScheduleId.keySet();
	}

	public ShipmentScheduleId getSingleShipmentScheduleId()
	{
		final Set<ShipmentScheduleId> shipmentScheduleIds = getShipmentScheduleIds();
		if (shipmentScheduleIds.size() != 1)
		{
			throw new AdempiereException("Expected only one shipment schedule").setParameter("list", list);
		}
		return shipmentScheduleIds.iterator().next();
	}

	public ShipmentScheduleAndJobScheduleIdSet toShipmentScheduleAndJobScheduleIdSet()
	{
		return list.stream()
				.map(PickingJobSchedule::getShipmentScheduleAndJobScheduleId)
				.collect(ShipmentScheduleAndJobScheduleIdSet.collect());
	}

	public Optional<Quantity> getQtyToPick()
	{
		return list.stream()
				.filter(sched -> !sched.isProcessed())
				.map(PickingJobSchedule::getQtyToPick)
				.reduce(Quantity::add);
	}

	public Optional<Quantity> getQtyToPickOfProcessed()
	{
		return list.stream()
				.filter(PickingJobSchedule::isProcessed)
				.map(PickingJobSchedule::getQtyToPick)
				.reduce(Quantity::add);
	}

	public Optional<PickingJobSchedule> getSingleScheduleByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final ImmutableList<PickingJobSchedule> schedules = byShipmentScheduleId.get(shipmentScheduleId);
		if (schedules.isEmpty())
		{
			return Optional.empty();
		}
		else if (schedules.size() == 1)
		{
			return Optional.of(schedules.get(0));
		}
		else
		{
			throw new AdempiereException("Only one schedule was expected for " + shipmentScheduleId + " but found " + schedules);
		}
	}
}
