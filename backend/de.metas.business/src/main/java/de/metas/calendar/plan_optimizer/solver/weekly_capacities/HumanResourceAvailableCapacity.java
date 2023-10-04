package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.resource.HumanResourceTestGroup;
import de.metas.resource.HumanResourceTestGroupId;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

	public int getOverReservedCapacityPenalty()
	{
		return (int)reservations.values()
				.stream()
				.mapToLong(Reservation::getOverReservedCapacityPenalty)
				.reduce(0, Long::sum);
	}

	private void reserveCapacity(
			@NonNull final ResourceGroupYearWeek resourceGroupYearWeek,
			@NonNull final List<StepItemRequiredCapacity> capacityItems)
	{
		getReservation(resourceGroupYearWeek).reserve(capacityItems);
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

		return new Reservation(groupWeeklyCapacity);
	}

	//
	//
	//
	//
	//

	@RequiredArgsConstructor
	private static class Reservation
	{
		@NonNull private final Duration totalCapacity;
		@NonNull private Duration reservedCapacity = Duration.ZERO;
		@Getter private long overReservedCapacityPenalty = 0;

		public void reserve(@NonNull final List<StepItemRequiredCapacity> requiredCapacityItems)
		{
			requiredCapacityItems.forEach(item -> {
				this.reservedCapacity = this.reservedCapacity.plus(item.getHumanResourceDuration());

				if (totalCapacity.compareTo(reservedCapacity) < 0)
				{
					final LocalDateTime startDate = item.getStartDate();
					final LocalDateTime nextAvailableDate = YearWeek.from(startDate).nextWeekMonday();
					this.overReservedCapacityPenalty += Plan.PLANNING_TIME_PRECISION.between(startDate, nextAvailableDate);
				}
			});
		}
	}
}
