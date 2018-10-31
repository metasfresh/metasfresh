package de.metas.util.time.generator;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import de.metas.util.Check;
import lombok.Value;

/**
 * Increment by a given number of months and always just to a given day of month.
 *
 * @author tsa
 *
 */
@Value
/* package */class MonthDayCalendarIncrementor implements ICalendarIncrementor
{
	/**
	 * Increment by 1 month, always set 1st day of the month
	 */
	public static final MonthDayCalendarIncrementor EACH_MONTH_FIRST_DAY = new MonthDayCalendarIncrementor(1, 1);

	/**
	 * Increment by 1 month, always set last day of the month
	 */
	public static final MonthDayCalendarIncrementor EACH_MONTH_LAST_DAY = new MonthDayCalendarIncrementor(1, 31);

	private final int monthsAmount;
	private final int dayOfMonth;

	public MonthDayCalendarIncrementor(final int monthsToAdd, final int dayOfMonth)
	{
		Check.assume(monthsToAdd > 0, "monthsToAdd({}) > 0", monthsToAdd);
		Check.assume(dayOfMonth > 0, "dayOfMonth({}) > 0", dayOfMonth);

		monthsAmount = monthsToAdd;
		this.dayOfMonth = dayOfMonth;
	}

	@Override
	public LocalDateTime increment(final LocalDateTime date)
	{
		// Set Day of Month
		LocalDateTime nextDate = withDayOfMonth(date);

		// If we actually moved back or we did not move at all, we need to increment also
		if (date.compareTo(nextDate) >= 0)
		{
			nextDate = nextDate.plusMonths(monthsAmount);

			// make sure we still have the right Day Of Month set
			// e.g. If current date is 28th Feb and our target day of month is 31, and we add 1 month we will get 28th March.
			nextDate = withDayOfMonth(nextDate);
		}

		return nextDate;
	}

	@Override
	public LocalDateTime decrement(final LocalDateTime date)
	{
		// Set Day of Month
		LocalDateTime prevDate = withDayOfMonth(date);

		// If we actually moved forward or we did not move at all, we need to decrement also
		if (date.compareTo(prevDate) <= 0)
		{
			prevDate = prevDate.minusMonths(monthsAmount);

			// make sure we still have the right Day Of Month set
			// e.g. If current date is 28th Feb and our target day of month is 31, and we add 1 month we will get 28th March.
			prevDate = withDayOfMonth(prevDate);
		}

		return prevDate;
	}

	private final LocalDateTime withDayOfMonth(final LocalDateTime date)
	{
		final int lastDayOfMonth = getLastDayOfMonth(date);
		final int dayOfMonthEffective = Math.min(dayOfMonth, lastDayOfMonth);
		return date.withDayOfMonth(dayOfMonthEffective);
	}

	private static int getLastDayOfMonth(final LocalDateTime date)
	{
		return date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
	}
}
