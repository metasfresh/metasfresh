package de.metas.resource;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
public class ResourceWeeklyAvailability
{
	public static ResourceWeeklyAvailability ALWAYS_AVAILABLE = builder().availableDaysOfWeek(ImmutableSet.copyOf(DayOfWeek.values())).build();
	public static ResourceWeeklyAvailability NOT_AVAILABLE = builder().availableDaysOfWeek(ImmutableSet.of()).build();
	public static ResourceWeeklyAvailability MONDAY_TO_FRIDAY_09_TO_17 = ResourceWeeklyAvailability.builder()
			.availableDaysOfWeek(ImmutableSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY))
			.timeSlot(true).timeSlotStart(LocalTime.parse("09:00")).timeSlotEnd(LocalTime.parse("17:00"))
			.build();

	@NonNull @Getter(AccessLevel.NONE) ImmutableSet<DayOfWeek> availableDaysOfWeek;
	boolean timeSlot;
	LocalTime timeSlotStart;
	LocalTime timeSlotEnd;

	private ResourceWeeklyAvailability(
			@NonNull final ImmutableSet<DayOfWeek> availableDaysOfWeek,
			final boolean timeSlot,
			@Nullable final LocalTime timeSlotStart,
			@Nullable final LocalTime timeSlotEnd)
	{
		this.availableDaysOfWeek = availableDaysOfWeek;
		this.timeSlot = timeSlot;
		if (timeSlot)
		{
			if (timeSlotStart == null)
			{
				throw new AdempiereException("time slot start shall be provided");
			}
			if (timeSlotEnd == null)
			{
				throw new AdempiereException("time slot end shall be provided");
			}
			if (!timeSlotStart.isBefore(timeSlotEnd))
			{
				throw new AdempiereException("time slot start shall be before time slot end");
			}
			this.timeSlotStart = timeSlotStart;
			this.timeSlotEnd = timeSlotEnd;

		}
		else
		{
			this.timeSlotStart = null;
			this.timeSlotEnd = null;
		}
	}

	/**
	 * Get how many hours/day are available.
	 * Minutes, seconds and millis are discarded.
	 *
	 * @return available hours
	 */
	private int getTimeSlotInHours()
	{
		return timeSlot ? (int)Duration.between(timeSlotStart, timeSlotEnd).toHours() : 24;
	}

	private void assertAvailable() {Check.assume(isAvailable(), "resource shall be available");}

	private boolean isAvailable() {return !availableDaysOfWeek.isEmpty();}

	/**
	 * @return now many days per week the resource is available
	 */
	private int getAvailableDaysPerWeek() {return availableDaysOfWeek.size();}

	private boolean isDateTimeAvailable(@NonNull final LocalDateTime dateTime)
	{
		return isDayAvailable(dateTime.getDayOfWeek()) && isTimeAvailable(dateTime.toLocalTime());
	}

	private boolean isDayAvailable(@NonNull final DayOfWeek dayOfWeek) {return availableDaysOfWeek.contains(dayOfWeek);}

	private boolean isTimeAvailable(@NonNull final LocalTime time)
	{
		if (!timeSlot)
		{
			return true;
		}

		final LocalTime timeSlotStart = Check.assumeNotNull(this.timeSlotStart, "time slot start is set");
		final LocalTime timeSlotEnd = Check.assumeNotNull(this.timeSlotEnd, "time slot end is set");

		//noinspection RedundantCompareToJavaTime
		return time.compareTo(timeSlotStart) >= 0 && time.compareTo(timeSlotEnd) < 0;
	}

	public int computeDurationInDays(@NonNull final Duration duration)
	{
		final BigDecimal availableDayTimeInHours = BigDecimal.valueOf(getTimeSlotInHours());
		final int availableDays = getAvailableDaysPerWeek();

		final BigDecimal weeklyFactor = BigDecimal.valueOf(7).divide(BigDecimal.valueOf(availableDays), 8, RoundingMode.UP);

		return BigDecimal.valueOf(duration.toHours())
				.multiply(weeklyFactor)
				.divide(availableDayTimeInHours, 0, RoundingMode.UP)
				.intValueExact();
	}

	public Optional<ResourceAvailabilityRanges> computeAvailabilityRanges(
			@NonNull final LocalDateTime startDateTime,
			@NonNull final Duration duration)
	{
		if (duration.isZero() || duration.isNegative())
		{
			return Optional.empty();
		}

		Duration remainingDurationToAllocate = duration;
		LocalDateTime date = startDateTime;
		final ArrayList<ResourceAvailabilityRange> ranges = new ArrayList<>();
		while (remainingDurationToAllocate.toSeconds() > 0)
		{
			date = isDateTimeAvailable(date)
					? date
					: findNextSlotStart(date);
			final LocalDateTime slotStartDate = date;

			final LocalDateTime slotEnd = findNextSlotEnd(date);

			final Duration slotDuration = Duration.between(date, slotEnd);

			Duration slotDurationAllocated;
			if (remainingDurationToAllocate.compareTo(slotDuration) <= 0)
			{
				slotDurationAllocated = remainingDurationToAllocate;
			}
			else
			{
				slotDurationAllocated = slotDuration;
			}

			date = date.plus(slotDurationAllocated);
			ranges.add(ResourceAvailabilityRange.ofStartAndEndDate(slotStartDate, date));
			remainingDurationToAllocate = remainingDurationToAllocate.minus(slotDurationAllocated);
		}

		return ranges.isEmpty()
				? Optional.empty()
				: Optional.of(ResourceAvailabilityRanges.ofList(ranges));
	}

	public LocalDateTime findNextSlotStart(@NonNull final LocalDateTime dateTime)
	{
		assertAvailable();

		final LocalDate date = dateTime.toLocalDate();
		LocalTime time = dateTime.toLocalTime();

		for (int dayOffset = 0; dayOffset < 7; dayOffset++)
		{
			final LocalDate currentDate = date.plusDays(dayOffset);
			if (isDayAvailable(currentDate.getDayOfWeek()))
			{
				if (!timeSlot)
				{
					return currentDate.atTime(time);
				}
				else if (time.isBefore(timeSlotStart))
				{
					return currentDate.atTime(timeSlotStart);
				}
				else if (isTimeAvailable(time))
				{
					return currentDate.atTime(time);
				}
				else // after time slot
				{
					time = timeSlotStart;
					// go to next day
				}
			}
		}

		throw new AdempiereException("No next slot found for " + dateTime + " in " + this);
	}

	@VisibleForTesting
	LocalDateTime findNextSlotEnd(@NonNull final LocalDateTime dateTime)
	{
		if (!isDateTimeAvailable(dateTime))
		{
			throw new AdempiereException("Expected " + dateTime + " to be in an available slot");
		}

		if (timeSlot)
		{
			return dateTime.toLocalDate().atTime(timeSlotEnd);
		}
		else if (getAvailableDaysPerWeek() == 7) // available 7/7
		{
			return LocalDate.MAX.atStartOfDay();
		}
		else
		{
			LocalDate date = dateTime.toLocalDate();
			while (isDayAvailable(date.getDayOfWeek()))
			{
				date = date.plusDays(1);
			}

			return date.atStartOfDay();
		}
	}

	public ResourceWeeklyAvailability timeSlotTruncatedTo(@NonNull final ChronoUnit precision)
	{
		if (!timeSlot)
		{
			return this;
		}

		final LocalTime timeSlotStartNew = timeSlotStart.truncatedTo(precision);
		final LocalTime timeSlotEndNew = timeSlotEnd.truncatedTo(precision);
		if (Objects.equals(timeSlotStart, timeSlotStartNew)
				|| Objects.equals(timeSlotEnd, timeSlotEndNew))
		{
			return this;
		}

		return toBuilder().timeSlotStart(timeSlotStartNew).timeSlotEnd(timeSlotEndNew).build();
	}

	public ResourceWeeklyAvailability intersectWith(@NonNull final ResourceWeeklyAvailability other)
	{
		//
		// Compute the new available days
		final Set<DayOfWeek> availableDaysOfWeekNew = Sets.intersection(this.availableDaysOfWeek, other.availableDaysOfWeek);
		if (availableDaysOfWeekNew.isEmpty())
		{
			return NOT_AVAILABLE;
		}

		//
		// Compute the new time slot
		final boolean timeSlotNew;
		final LocalTime timeSlotStartNew;
		final LocalTime timeSlotEndNew;
		if (this.timeSlot)
		{
			if (other.timeSlot)
			{
				final Range<LocalTime> timeSlotRange = Range.closed(this.timeSlotStart, this.timeSlotEnd);
				final Range<LocalTime> other_timeSlotRange = Range.closed(other.timeSlotStart, other.timeSlotEnd);
				if (timeSlotRange.isConnected(other_timeSlotRange))
				{
					timeSlotNew = true;
					final Range<LocalTime> new_timeSlotRange = timeSlotRange.intersection(other_timeSlotRange);
					timeSlotStartNew = new_timeSlotRange.lowerEndpoint();
					timeSlotEndNew = new_timeSlotRange.upperEndpoint();
					if (timeSlotStartNew.equals(timeSlotEndNew))
					{
						return NOT_AVAILABLE;
					}
				}
				else
				{
					return NOT_AVAILABLE;
				}
			}
			else
			{
				timeSlotNew = this.timeSlot; // true
				timeSlotStartNew = this.timeSlotStart;
				timeSlotEndNew = this.timeSlotEnd;
			}
		}
		else // this.timeSlot == false
		{
			timeSlotNew = other.timeSlot;
			timeSlotStartNew = other.timeSlotStart;
			timeSlotEndNew = other.timeSlotEnd;
		}

		final ResourceWeeklyAvailability intersection = builder()
				.availableDaysOfWeek(ImmutableSet.copyOf(availableDaysOfWeekNew))
				.timeSlot(timeSlotNew)
				.timeSlotStart(timeSlotStartNew)
				.timeSlotEnd(timeSlotEndNew)
				.build();

		//
		// "intern" and return
		if (ALWAYS_AVAILABLE.equals(intersection))
		{
			return ALWAYS_AVAILABLE;
		}
		else if (NOT_AVAILABLE.equals(intersection))
		{
			return NOT_AVAILABLE;
		}
		else if (this.equals(intersection))
		{
			return this;
		}
		else if (other.equals(intersection))
		{
			return other;
		}
		else
		{
			return intersection;
		}
	}
}
