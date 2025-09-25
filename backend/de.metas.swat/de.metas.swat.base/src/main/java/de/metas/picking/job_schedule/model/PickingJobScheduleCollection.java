package de.metas.picking.job_schedule.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
import java.util.stream.Collector;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class PickingJobScheduleCollection implements Iterable<PickingJobSchedule>
{
	public static final PickingJobScheduleCollection EMPTY = new PickingJobScheduleCollection(ImmutableList.of());

	@NonNull private final ImmutableList<PickingJobSchedule> list;
	@NonNull @Getter private final ImmutableSet<PickingJobScheduleId> jobScheduleIds;

	private PickingJobScheduleCollection(@NonNull final ImmutableList<PickingJobSchedule> list)
	{
		this.list = list;
		this.jobScheduleIds = list.stream().map(PickingJobSchedule::getId).collect(ImmutableSet.toImmutableSet());
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

	public ShipmentScheduleId getSingleShipmentScheduleId()
	{
		return list.stream()
				.map(PickingJobSchedule::getShipmentScheduleId)
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Expected only one shipment schedule").setParameter("list", list)));
	}

	public ShipmentScheduleAndJobScheduleIdSet toShipmentScheduleAndJobScheduleIdSet()
	{
		return list.stream()
				.map(PickingJobSchedule::getShipmentScheduleAndJobScheduleId)
				.collect(ShipmentScheduleAndJobScheduleIdSet.collect());
	}

	public boolean isAllProcessed()
	{
		return list.stream().allMatch(PickingJobSchedule::isProcessed);
	}

	public Optional<Quantity> getQtyToPick()
	{
		return list.stream()
				.map(PickingJobSchedule::getQtyToPick)
				.reduce(Quantity::add);
	}
}
