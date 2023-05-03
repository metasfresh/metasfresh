package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.resource.HumanResourceTestGroup;
import de.metas.resource.HumanResourceTestGroupId;
import lombok.NonNull;

import java.time.Duration;
import java.util.HashMap;

public class AvailableCapacity
{
	private final ImmutableMap<HumanResourceTestGroupId, HumanResourceTestGroup> groupsById;
	private final HashMap<ResourceGroupYearWeek, Reservation> reservations = new HashMap<>();

	private AvailableCapacity(final ImmutableList<HumanResourceTestGroup> groups)
	{
		this.groupsById = Maps.uniqueIndex(groups, HumanResourceTestGroup::getId);
	}

	public static AvailableCapacity of(final ImmutableList<HumanResourceTestGroup> groups)
	{
		return new AvailableCapacity(groups);
	}

	public void reserveCapacity(final StepRequiredCapacity requiredCapacity)
	{
		requiredCapacity.forEach(this::reserveCapacity);
	}

	public void reserveCapacity(
			@NonNull final ResourceGroupYearWeek resourceGroupYearWeek,
			@NonNull final Duration capacity)
	{
		getReservation(resourceGroupYearWeek).reserve(capacity);
	}

	private Reservation getReservation(@NonNull final ResourceGroupYearWeek resourceGroupYearWeek)
	{
		return reservations.computeIfAbsent(resourceGroupYearWeek, this::createEmptyReservation);
	}

	private Reservation createEmptyReservation(@NonNull final ResourceGroupYearWeek resourceGroupYearWeek)
	{
		final HumanResourceTestGroup group = groupsById.get(resourceGroupYearWeek.getGroupId());
		@NonNull final Duration totalCapacity = group != null ? group.getWeeklyCapacity() : Duration.ZERO;

		return new Reservation(resourceGroupYearWeek, totalCapacity);
	}

	public Duration getOverReservedCapacity()
	{
		return reservations.values()
				.stream()
				.map(Reservation::getOverReservedCapacity)
				.reduce(Duration::plus)
				.orElse(Duration.ZERO);
	}

	//
	//
	//
	//
	//

	private static class Reservation
	{
		@NonNull ResourceGroupYearWeek resourceGroupYearWeek;
		@NonNull Duration totalCapacity;
		@NonNull Duration reservedCapacity = Duration.ZERO;

		public Reservation(@NonNull final ResourceGroupYearWeek resourceGroupYearWeek, @NonNull final Duration totalCapacity)
		{
			this.resourceGroupYearWeek = resourceGroupYearWeek;
			this.totalCapacity = totalCapacity;
		}

		public void reserve(@NonNull final Duration capacityToReserve)
		{
			this.reservedCapacity = this.reservedCapacity.plus(capacityToReserve);
		}

		public Duration getOverReservedCapacity()
		{
			final Duration freeCapacity = totalCapacity.minus(reservedCapacity);
			return freeCapacity.isNegative() ? freeCapacity.negated() : Duration.ZERO;
		}
	}
}
