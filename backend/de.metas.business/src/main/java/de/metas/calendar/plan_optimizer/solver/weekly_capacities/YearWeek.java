package de.metas.calendar.plan_optimizer.solver.weekly_capacities;

import lombok.NonNull;
import lombok.Value;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static java.time.temporal.IsoFields.WEEK_BASED_YEAR;
import static java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR;

/**
 * @author thx org.threeten.extra.YearWeek
 */
@Value
public class YearWeek
{
	/**
	 * The week-based-year.
	 */
	int year;
	/**
	 * The week-of-week-based-year
	 */
	int week;

	/**
	 * Constructor.
	 *
	 * @param weekBasedYear the week-based-year to represent, validated from MIN_YEAR to MAX_YEAR
	 * @param week          the week to represent, validated
	 */
	private YearWeek(int weekBasedYear, int week)
	{
		this.year = weekBasedYear;
		this.week = week;
	}

	/**
	 * Obtains an instance of {@code YearWeek} from a temporal object.
	 * <p>
	 * This obtains a year-week based on the specified temporal.
	 * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
	 * which this factory converts to an instance of {@code YearWeek}.
	 * <p>
	 * The conversion extracts the {@link IsoFields#WEEK_BASED_YEAR WEEK_BASED_YEAR} and
	 * {@link IsoFields#WEEK_OF_WEEK_BASED_YEAR WEEK_OF_WEEK_BASED_YEAR} fields.
	 * The extraction is only permitted if the temporal object has an ISO
	 * chronology, or can be converted to a {@code LocalDate}.
	 * <p>
	 * This method matches the signature of the functional interface {@link TemporalQuery}
	 * allowing it to be used in queries via method reference, {@code YearWeek::from}.
	 *
	 * @param temporal the temporal object to convert, not null
	 * @return the year-week, not null
	 * @throws DateTimeException if unable to convert to a {@code YearWeek}
	 */
	public static YearWeek from(@NonNull TemporalAccessor temporal)
	{
		try
		{
			if (!IsoChronology.INSTANCE.equals(Chronology.from(temporal)))
			{
				temporal = LocalDate.from(temporal);
			}
			// need to use getLong() as JDK Parsed class get() doesn't work properly
			int year = Math.toIntExact(temporal.getLong(WEEK_BASED_YEAR));
			int week = Math.toIntExact(temporal.getLong(WEEK_OF_WEEK_BASED_YEAR));
			return of(year, week);
		}
		catch (DateTimeException ex)
		{
			throw new DateTimeException("Unable to obtain YearWeek from TemporalAccessor: " +
					temporal + " of type " + temporal.getClass().getName(), ex);
		}
	}

	/**
	 * Obtains an instance of {@code YearWeek} from a week-based-year and week.
	 * <p>
	 * If the week is 53 and the year does not have 53 weeks, week one of the following
	 * year is selected.
	 *
	 * @param weekBasedYear the week-based-year to represent, from MIN_YEAR to MAX_YEAR
	 * @param week          the week-of-week-based-year to represent, from 1 to 53
	 * @return the year-week, not null
	 * @throws DateTimeException if either field is invalid
	 */
	public static YearWeek of(int weekBasedYear, int week)
	{
		WEEK_BASED_YEAR.range().checkValidValue(weekBasedYear, WEEK_BASED_YEAR);
		WEEK_OF_WEEK_BASED_YEAR.range().checkValidValue(week, WEEK_OF_WEEK_BASED_YEAR);
		if (week == 53 && weekRange(weekBasedYear) < 53)
		{
			week = 1;
			weekBasedYear++;
			WEEK_BASED_YEAR.range().checkValidValue(weekBasedYear, WEEK_BASED_YEAR);
		}
		return new YearWeek(weekBasedYear, week);
	}

	// from IsoFields in ThreeTen-Backport
	private static int weekRange(int weekBasedYear)
	{
		LocalDate date = LocalDate.of(weekBasedYear, 1, 1);
		// 53 weeks if year starts on Thursday, or Wed in a leap year
		if (date.getDayOfWeek() == THURSDAY || (date.getDayOfWeek() == WEDNESDAY && date.isLeapYear()))
		{
			return 53;
		}
		return 52;
	}
}
