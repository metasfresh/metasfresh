package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

public class ShipmentScheduleAndJobSchedulesCollection implements Iterable<ShipmentScheduleAndJobSchedules>
{
	public static final ShipmentScheduleAndJobSchedulesCollection EMPTY = new ShipmentScheduleAndJobSchedulesCollection(ImmutableList.of());

	private final ImmutableList<ShipmentScheduleAndJobSchedules> list;

	private ShipmentScheduleAndJobSchedulesCollection(final List<ShipmentScheduleAndJobSchedules> list)
	{
		this.list = list.stream()
				.sorted(comparing(ShipmentScheduleAndJobSchedules::getHeaderAggregationKey, nullsLast(naturalOrder()))
						.thenComparing(ShipmentScheduleAndJobSchedules::getShipmentScheduleId))
				.collect(ImmutableList.toImmutableList());

		Maps.uniqueIndex(this.list, ShipmentScheduleAndJobSchedules::getShipmentScheduleId); // avoid duplicates
	}

	public static ShipmentScheduleAndJobSchedulesCollection ofList(final List<ShipmentScheduleAndJobSchedules> list)
	{
		return list.isEmpty() ? EMPTY : new ShipmentScheduleAndJobSchedulesCollection(list);
	}

	public static ShipmentScheduleAndJobSchedulesCollection ofShipmentSchedules(final Collection<? extends de.metas.inoutcandidate.model.I_M_ShipmentSchedule> shipmentSchedules)
	{
		if (shipmentSchedules.isEmpty()) {return EMPTY;}

		return shipmentSchedules.stream()
				.map(ShipmentScheduleAndJobSchedules::ofShipmentSchedule)
				.collect(collect());
	}

	public static Collector<ShipmentScheduleAndJobSchedules, ?, ShipmentScheduleAndJobSchedulesCollection> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(ShipmentScheduleAndJobSchedulesCollection::ofList);
	}

	public boolean isEmpty() {return list.isEmpty();}

	@Override
	public @NotNull Iterator<ShipmentScheduleAndJobSchedules> iterator() {return list.iterator();}

	public ImmutableSet<PickingJobScheduleId> getJobScheduleIds()
	{
		return list.stream()
				.flatMap(schedule -> schedule.getJobScheduleIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIdsWithoutJobSchedules()
	{
		return getShipmentScheduleIds(schedule -> !schedule.hasJobSchedules());
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIdsWithJobSchedules()
	{
		return getShipmentScheduleIds(ShipmentScheduleAndJobSchedules::hasJobSchedules);
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds(@NonNull Predicate<ShipmentScheduleAndJobSchedules> filter)
	{
		return list.stream()
				.filter(filter)
				.map(ShipmentScheduleAndJobSchedules::getShipmentScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

}
