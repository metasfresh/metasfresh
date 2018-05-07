package org.adempiere.util.time.generator;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Calendar;

import org.adempiere.util.Check;

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

	public MonthDayCalendarIncrementor(final int monthsAmount, final int dayOfMonth)
	{
		super();

		Check.assume(monthsAmount >= 0, "monthsAmount({}) >= 0", monthsAmount);
		this.monthsAmount = monthsAmount;

		Check.assume(dayOfMonth > 0, "dayOfMonth({}) > 0", dayOfMonth);
		this.dayOfMonth = dayOfMonth;
	}

	@Override
	public String toString()
	{
		return "MonthDayCalendarIncrement [monthsAmount=" + monthsAmount + ", dayOfMonth=" + dayOfMonth + "]";
	}

	private final void setDayOfMonth(final Calendar calendar)
	{
		final int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		final int dayOfMonthActual;
		if (dayOfMonth > maxDayOfMonth)
		{
			dayOfMonthActual = maxDayOfMonth;
		}
		else
		{
			dayOfMonthActual = dayOfMonth;
		}

		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthActual);
	}

	@Override
	public void increment(final Calendar calendar)
	{
		final long millisBefore = calendar.getTimeInMillis();

		// Set Day of Month
		setDayOfMonth(calendar);

		// If we actually moved back or we did not move at all, we need to increment also
		if (millisBefore >= calendar.getTimeInMillis())
		{
			calendar.add(Calendar.MONTH, monthsAmount);

			// make sure we still have the right Day Of Month set
			// e.g. If current date is 28th Feb and our target day of month is 31, and we add 1 month we will get 28th March.
			setDayOfMonth(calendar);
		}
	}

	@Override
	public void adjustToClosest(final Calendar calendar)
	{
		final long millisBefore = calendar.getTimeInMillis();

		// Set Day of Month
		setDayOfMonth(calendar);

		// If we actually moved back, we need to increment also
		if (millisBefore > calendar.getTimeInMillis())
		{
			calendar.add(Calendar.MONTH, monthsAmount);

			// make sure we still have the right Day Of Month set
			// e.g. If current date is 28th Feb and our target day of month is 31, and we add 1 month we will get 28th March.
			setDayOfMonth(calendar);
		}
	}

}
