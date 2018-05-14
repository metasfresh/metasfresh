package org.adempiere.util.time.generator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.adempiere.util.Check;

import lombok.NonNull;
import lombok.Value;

/**
 * Increment by a given amount of weeks and always just to a given week day.
 * 
 * @author tsa
 *
 */
@Value
public class WeekDayCalendarIncrementor implements ICalendarIncrementor
{
	private final int weeksToAdd;
	private final DayOfWeek dayOfWeek;

	public WeekDayCalendarIncrementor(final int weeksToAdd, @NonNull final DayOfWeek dayOfWeek)
	{
		Check.assume(weeksToAdd > 0, "weeksToAdd({}) > 0", weeksToAdd);

		this.weeksToAdd = weeksToAdd;
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public LocalDate increment(final LocalDate date)
	{
		if (date.getDayOfWeek().getValue() >= dayOfWeek.getValue())
		{
			return date.with(TemporalAdjusters.previousOrSame(dayOfWeek)).plusWeeks(weeksToAdd);
		}
		else
		{
			return date.with(TemporalAdjusters.next(dayOfWeek));
		}
	}

	@Override
	public LocalDate decrement(final LocalDate date)
	{
		if (weeksToAdd > 1)
		{
			throw new UnsupportedOperationException("Decrementing using a step greater than one is not supported");
		}
		
		if (date.getDayOfWeek().getValue() <= dayOfWeek.getValue())
		{
			return date.with(TemporalAdjusters.nextOrSame(dayOfWeek)).minusWeeks(weeksToAdd);
		}
		else
		{
			return date.with(TemporalAdjusters.previous(dayOfWeek));
		}
	}

}
