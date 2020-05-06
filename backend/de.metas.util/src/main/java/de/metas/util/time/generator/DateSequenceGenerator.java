package de.metas.util.time.generator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Helper class used to generate a sequence of dates with using configurable frequency, date transformers etc.
 *
 * For custom incrementors, see {@link CalendarIncrementors}.
 *
 * For custom date exploders, see implementations of {@link IDateSequenceExploder}.
 *
 * @author tsa
 *
 */
@Value
public class DateSequenceGenerator
{
	private final LocalDate dateFrom;
	private final LocalDate dateTo;
	private final ICalendarIncrementor incrementor;
	private final IDateSequenceExploder exploder;
	private final IDateShifter shifter;

	@Builder(toBuilder = true)
	private DateSequenceGenerator(
			final LocalDate dateFrom,
			final LocalDate dateTo,
			final ICalendarIncrementor incrementor,
			final IDateSequenceExploder exploder,
			final IDateShifter shifter)
	{
		this.dateFrom = dateFrom != null ? dateFrom : LocalDate.MIN;
		this.dateTo = dateTo != null ? dateTo : LocalDate.MAX;
		this.incrementor = incrementor != null ? incrementor : CalendarIncrementors.dayByDay();
		this.exploder = exploder != null ? exploder : SameDateSequenceExploder.instance;
		this.shifter = shifter != null ? shifter : SameDateShifter.instance;
	}

	public Set<LocalDate> generate()
	{
		final SortedSet<LocalDateTime> result = newSortedSet(ImmutableSet.of());

		LocalDateTime currentDate = dateFrom.atStartOfDay();
		while (currentDate.toLocalDate().compareTo(dateTo) <= 0)
		{
			//
			// Explode current date using the converter and then shift each exploded date
			doExplodeAndShiftForward(result, currentDate);

			//
			// Increment to next date
			currentDate = incrementor.increment(currentDate);
		}

		return result
				.stream()
				.map(LocalDateTime::toLocalDate)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Optional<LocalDate> calculatePrevious(@NonNull final LocalDate date)
	{
		return calculatePrevious(date.atStartOfDay())
				.map(LocalDateTime::toLocalDate);
	}

	public Optional<LocalDateTime> calculatePrevious(@NonNull final LocalDateTime date)
	{
		final int MAX_ITERATIONS = 100;

		int iterationNo = 0;
		LocalDateTime currentDate = date;
		while (currentDate.toLocalDate().compareTo(dateFrom) >= 0)
		{
			iterationNo++;
			if (iterationNo > MAX_ITERATIONS)
			{
				throw new IllegalStateException("Maximum number of iterations(" + MAX_ITERATIONS + ") reached while trying to find a current/previous date for " + date + " using " + this + "."
						+ "\nCurrent date: " + currentDate);
			}

			//
			// Explode current date using the converter and then shift each exploded date
			final Set<LocalDateTime> currentDateExploded = doExplodeAndShiftBackward(currentDate);

			if (!currentDateExploded.isEmpty())
			{
				final LocalDateTime maxDate = max(currentDateExploded);
				return Optional.of(maxDate);
			}

			//
			// Decrement to previous date
			currentDate = incrementor.decrement(currentDate);
		}

		return Optional.empty();
	}

	private final void doExplodeAndShiftForward(final SortedSet<LocalDateTime> result, final LocalDateTime dateToExplode)
	{
		// Check: if current date to explode is before or equal to the last (and maximum) date we generated
		// ... then skip it
		//
		// NOTE: maybe in future we can make this configurable.
		// The reason why we have it now here is because we want to support shifters which are shifting dates to next business day, but we want to skip the days in between.
		if (!result.isEmpty() && result.last().isAfter(dateToExplode))
		{
			return;
		}

		final Collection<LocalDateTime> datesExploded = exploder.explodeForward(dateToExplode);
		if (datesExploded != null && !datesExploded.isEmpty())
		{
			LocalDateTime lastDateConsidered = dateToExplode;
			for (final LocalDateTime dateExploded : newSortedSet(datesExploded))
			{
				// Skip null dates... shall not happen
				if (dateExploded == null)
				{
					continue;
				}

				// Skip dates which are before last date which we exploded
				// NOTE: this case could happen in case the date was shifted
				if (dateExploded.isBefore(lastDateConsidered))
				{
					continue;
				}
				// Skip dates which are after our date generation interval
				if (dateExploded.toLocalDate().isAfter(dateTo))
				{
					continue;
				}

				// Shift exploded date
				final LocalDateTime dateExplodedAndShifted = shifter.shiftForward(dateExploded);
				// Skip dates on which shifter is telling us to exclude
				if (dateExplodedAndShifted == null)
				{
					continue;
				}

				// Skip shifted dates which are before last date which was added to our result
				if (dateExplodedAndShifted.isBefore(lastDateConsidered))
				{
					continue;
				}

				// Skip shifted dates which are after generation interval
				if (dateExplodedAndShifted.toLocalDate().isAfter(dateTo))
				{
					// Even if we exclude this date, we want to consider it for next dates
					if (lastDateConsidered.isBefore(dateExplodedAndShifted))
					{
						lastDateConsidered = dateExplodedAndShifted;
					}
					continue;
				}

				//
				// Add our converted and shifted date to result
				result.add(dateExplodedAndShifted);
				lastDateConsidered = dateExplodedAndShifted;
			}
		}
	}

	private final Set<LocalDateTime> doExplodeAndShiftBackward(final LocalDateTime dateToExplode)
	{
		final SortedSet<LocalDateTime> result = newReverseSortedSet(ImmutableSet.of());

		final Collection<LocalDateTime> datesExploded = exploder.explodeBackward(dateToExplode);
		if (datesExploded != null && !datesExploded.isEmpty())
		{
			LocalDateTime lastDateConsidered = dateToExplode;
			for (final LocalDateTime dateExploded : newReverseSortedSet(datesExploded))
			{
				// Skip null dates... shall not happen
				if (dateExploded == null)
				{
					continue;
				}

				// Skip dates which are after last date which we exploded
				// NOTE: this case could happen in case the date was shifted
				if (dateExploded.isAfter(lastDateConsidered))
				{
					continue;
				}
				// Skip dates which are before our date generation interval
				if (dateExploded.toLocalDate().isBefore(dateFrom))
				{
					continue;
				}

				// Shift exploded date
				final LocalDateTime dateExplodedAndShifted = shifter.shiftBackward(dateExploded);
				// Skip dates on which shifter is telling us to exclude
				if (dateExplodedAndShifted == null)
				{
					continue;
				}

				// Skip shifted dates which are after last date which was added to our result
				if (dateExplodedAndShifted.isAfter(lastDateConsidered))
				{
					continue;
				}

				// Skip shifted dates which are before generation interval
				if (dateExplodedAndShifted.toLocalDate().isBefore(dateFrom))
				{
					// Even if we exclude this date, we want to consider it for next dates
					if (lastDateConsidered.isAfter(dateExplodedAndShifted))
					{
						lastDateConsidered = dateExplodedAndShifted;
					}
					continue;
				}

				//
				// Add our converted and shifted date to result
				result.add(dateExplodedAndShifted);
				lastDateConsidered = dateExplodedAndShifted;
			}
		}
		
		return result;
	}

	private static SortedSet<LocalDateTime> newSortedSet(final Collection<LocalDateTime> values)
	{
		if (values == null || values.isEmpty())
		{
			return new TreeSet<>();
		}
		else
		{
			return new TreeSet<>(values);
		}
	}

	private static SortedSet<LocalDateTime> newReverseSortedSet(final Collection<LocalDateTime> values)
	{
		final TreeSet<LocalDateTime> sortedSet = new TreeSet<>(Comparator.<LocalDateTime> naturalOrder().reversed());
		if (values != null && !values.isEmpty())
		{
			sortedSet.addAll(values);
		}

		return sortedSet;
	}
	
	private static LocalDateTime max(final Collection<LocalDateTime> dates)
	{
		Check.assumeNotEmpty(dates, "dates is not empty");
		return dates.stream().max(Comparator.naturalOrder()).get();
	}

	//
	//
	//
	//
	//

	public static class DateSequenceGeneratorBuilder
	{
		public DateSequenceGeneratorBuilder byDay()
		{
			return incrementor(CalendarIncrementors.dayByDay());
		}

		/**
		 * Iterate each <code>day</code> days.
		 * 
		 * @param day
		 * @return this
		 */
		public DateSequenceGeneratorBuilder byNthDay(final int day)
		{
			return incrementor(CalendarIncrementors.eachNthDay(day));
		}

		public DateSequenceGeneratorBuilder byWeeks(final int week, final DayOfWeek dayOfWeek)
		{
			return incrementor(CalendarIncrementors.eachNthWeek(week, dayOfWeek));
		}

		public DateSequenceGeneratorBuilder byMonths(final int months, final int dayOfMonth)
		{
			return incrementor(CalendarIncrementors.eachNthMonth(months, dayOfMonth));
		}

		public DateSequenceGeneratorBuilder frequency(@NonNull final Frequency frequency)
		{
			incrementor(createCalendarIncrementor(frequency));
			exploder(createDateSequenceExploder(frequency));
			return this;
		}

		private static ICalendarIncrementor createCalendarIncrementor(final Frequency frequency)
		{
			if (frequency.isWeekly())
			{
				return CalendarIncrementors.eachNthWeek(frequency.getEveryNthWeek(), DayOfWeek.MONDAY);
			}
			else if (frequency.isMonthly())
			{
				return CalendarIncrementors.eachNthMonth(frequency.getEveryNthMonth(), 1); // every given month, 1st day
			}
			else
			{
				throw new IllegalArgumentException("Frequency type not supported for " + frequency);
			}
		}

		private static IDateSequenceExploder createDateSequenceExploder(final Frequency frequency)
		{
			if (frequency.isWeekly())
			{
				if (frequency.isOnlySomeDaysOfTheWeek())
				{
					return DaysOfWeekExploder.of(frequency.getOnlyDaysOfWeek());
				}
				else
				{
					return DaysOfWeekExploder.ALL_DAYS_OF_WEEK;
				}
			}
			else if (frequency.isMonthly())
			{
				return DaysOfMonthExploder.of(frequency.getOnlyDaysOfMonth());
			}
			else
			{
				throw new IllegalArgumentException("Frequency type not supported for " + frequency);
			}
		}
	}
}
