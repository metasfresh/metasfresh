package org.adempiere.util.time.generator;

import java.time.LocalDate;

import org.adempiere.util.Check;

import lombok.Value;

/**
 * Increment by given a given number of Days.
 *
 * NOTE: this class is immutable and can be used to define pre-configured constants.
 *
 * @author tsa
 *
 */
@Value
final class DayCalendarIncrementor implements ICalendarIncrementor
{
	private final int daysToAdd;

	public DayCalendarIncrementor(final int daysToAdd)
	{
		Check.assume(daysToAdd > 0, "daysToAdd > 0");
		this.daysToAdd = daysToAdd;
	}

	@Override
	public LocalDate increment(final LocalDate date)
	{
		return date.plusDays(daysToAdd);
	}
}
