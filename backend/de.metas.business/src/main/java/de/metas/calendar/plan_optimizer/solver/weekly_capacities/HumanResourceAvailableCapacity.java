package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.resource.HumanResourceTestGroup;
import de.metas.resource.HumanResourceTestGroupId;
import lombok.NonNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.Optional;

public class HumanResourceAvailableCapacity
{
	private final ImmutableMap<HumanResourceTestGroupId, HumanResourceTestGroup> groupsById;
	private final HashMap<ResourceGroupYearWeek, Reservation> reservations = new HashMap<>();

	private HumanResourceAvailableCapacity(final ImmutableList<HumanResourceTestGroup> groups)
	{
		this.groupsById = Maps.uniqueIndex(groups, HumanResourceTestGroup::getId);
	}

	public static HumanResourceAvailableCapacity of(final ImmutableList<HumanResourceTestGroup> groups)
	{
		return new HumanResourceAvailableCapacity(groups);
	}

	public void reserveCapacity(final StepRequiredCapacity requiredCapacity)
	{
		requiredCapacity.forEach(this::reserveCapacity);
	}

	@NonNull
	public Duration getOverReservedCapacity()
	{
		return reservations.values()
				.stream()
				.map(Reservation::getOverReservedCapacity)
				.reduce(Duration::plus)
				.orElse(Duration.ZERO);
	}

	private void reserveCapacity(
			@NonNull final ResourceGroupYearWeek resourceGroupYearWeek,
			@NonNull final Duration capacity)
	{
		getReservation(resourceGroupYearWeek).reserve(capacity);
	}

	@NonNull
	private Reservation getReservation(@NonNull final ResourceGroupYearWeek resourceGroupYearWeek)
	{
		return reservations.computeIfAbsent(resourceGroupYearWeek, this::createEmptyReservation);
	}

	@NonNull
	private Reservation createEmptyReservation(@NonNull final ResourceGroupYearWeek resourceGroupYearWeek)
	{
		final HumanResourceTestGroup group = groupsById.get(resourceGroupYearWeek.getGroupId());
		final Duration groupWeeklyCapacity = Optional.ofNullable(group)
				.map(HumanResourceTestGroup::getWeeklyCapacity)
				.orElse(Duration.ZERO);

		return new Reservation(resourceGroupYearWeek, groupWeeklyCapacity);
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

		@NonNull
		public Duration getOverReservedCapacity()
		{
			final Duration freeCapacity = totalCapacity.minus(reservedCapacity);
			return freeCapacity.isNegative() ? freeCapacity.negated() : Duration.ZERO;
		}
	}
}
