package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.calendar.plan_optimizer.domain.HumanResourceCapacity;
import de.metas.calendar.plan_optimizer.domain.Plan;
import de.metas.resource.HumanResourceTestGroupId;
import de.metas.util.time.DurationUtils;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class HumanResourceAvailableCapacity
{
	private final ImmutableMap<HumanResourceTestGroupId, HumanResourceCapacity> capacitiesById;
	private final HashMap<ResourceGroupYearWeek, Reservation> reservations = new HashMap<>();

	private HumanResourceAvailableCapacity(@NonNull final Set<HumanResourceCapacity> capacities)
	{
		this.capacitiesById = Maps.uniqueIndex(capacities, HumanResourceCapacity::getId);
	}

	public static HumanResourceAvailableCapacity of(@NonNull final Set<HumanResourceCapacity> capacities)
	{
		return new HumanResourceAvailableCapacity(capacities);
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
			@NonNull final Collection<StepItemRequiredCapacity> capacityItems)
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
		final HumanResourceCapacity capacity = capacitiesById.get(resourceGroupYearWeek.getCapacity().getId()); // TODO improve this logic!
		final Duration weeklyCapacity = capacity != null ? capacity.getWeeklyCapacity() : Duration.ZERO;
		return Reservation.ofTotalCapacity(weeklyCapacity);
	}

	//
	//
	//
	//
	//

	private static class Reservation
	{
		@NonNull private final Duration totalCapacity;
		@NonNull private Duration reservedCapacity = Duration.ZERO;
		@Getter private long overReservedCapacityPenalty = 0;

		private Reservation(@NonNull final Duration totalCapacity) {this.totalCapacity = totalCapacity;}

		public static Reservation ofTotalCapacity(@NonNull final Duration totalCapacity) {return new Reservation(totalCapacity);}

		public void reserve(@NonNull final Collection<StepItemRequiredCapacity> requiredCapacityItems)
		{
			requiredCapacityItems.forEach(item -> {
				this.reservedCapacity = this.reservedCapacity.plus(item.getHumanResourceDuration());

				// if (totalCapacity.compareTo(reservedCapacity) < 0)
				// {
				// 	final LocalDateTime startDate = item.getStartDate();
				// 	final LocalDateTime nextAvailableDate = YearWeek.from(startDate).nextWeekMonday();
				// 	this.overReservedCapacityPenalty += Plan.PLANNING_TIME_PRECISION.between(startDate, nextAvailableDate);
				// }
			});

			final long availableCapacity = DurationUtils.toLong(totalCapacity.minus(reservedCapacity), Plan.PLANNING_TIME_PRECISION);
			this.overReservedCapacityPenalty = availableCapacity < 0 ? -availableCapacity : 0;
		}
	}
}
