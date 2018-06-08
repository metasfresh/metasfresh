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
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.util.Check;

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
public class DateSequenceGenerator
{
	private Date _dateFrom;
	private Date _dateTo;
	private ICalendarIncrementor incrementor = CalendarIncrementors.dayByDay();
	private IDateSequenceExploder exploder = NullDateSequenceExploder.instance;
	private IDateShifter shifter = NullDateShifter.instance;
	private boolean enforceDateToAfterShift = true;

	public DateSequenceGenerator()
	{
		super();
	}

	public SortedSet<Date> generate()
	{
		final Date dateFrom = getDateFromToUse();

		final Date dateTo = getDateToToUse();
		final long dateToMillis = dateTo.getTime();

		final SortedSet<Date> result = new TreeSet<Date>();

		final Calendar currentDateCal = new GregorianCalendar();
		currentDateCal.setTime(dateFrom);
		while (currentDateCal.getTimeInMillis() <= dateToMillis)
		{
			//
			// Get current date
			final Date date = currentDateCal.getTime();

			//
			// Explode current date using the converter and then shift each exploded date
			doExplodeAndShift(result, date);

			//
			// Increment to next date
			incrementor.increment(currentDateCal);
		}

		return result;
	}

	private final void doExplodeAndShift(final SortedSet<Date> result, final Date dateToExplode)
	{
		// Check: if current date to explode is before or equal to the last (and maximum) date we generated
		// ... then skip it
		//
		// NOTE: maybe in future we can make this configurable.
		// The reason why we have it now here is because we want to support shifters which are shifting dates to next business day, but we want to skip the days in between.
		if (!result.isEmpty() && result.last().after(dateToExplode))
		{
			return;
		}

		final Date dateTo = getDateToToUse();

		final Collection<Date> datesExploded = exploder.explode(dateToExplode);
		if (datesExploded != null && !datesExploded.isEmpty())
		{
			Date lastDateConsidered = dateToExplode;
			for (final Date dateExploded : new TreeSet<>(datesExploded))
			{
				// Skip null dates... shall not happen
				if (dateExploded == null)
				{
					continue;
				}

				// Skip dates which are before last date which we exploded
				// NOTE: this case could happen in case the date was shifted
				if (dateExploded.before(lastDateConsidered))
				{
					continue;
				}
				// Skip dates which are after our date generation interval
				if (dateExploded.after(dateTo))
				{
					continue;
				}

				// Shift exploded date
				final Date dateExplodedAndShifted = shifter.shift(dateExploded);
				// Skip dates on which shifter is telling us to exclude
				if (dateExplodedAndShifted == null)
				{
					continue;
				}

				// Skip shifted dates which are before last date which was added to our result
				if (dateExplodedAndShifted.before(lastDateConsidered))
				{
					continue;
				}

				// Skip shifted dates which are after generation interval
				if (enforceDateToAfterShift && dateExplodedAndShifted.after(dateTo))
				{
					// Even if we exclude this date, we want to consider it for next dates
					if (lastDateConsidered.before(dateExplodedAndShifted))
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

	/**
	 * 
	 * @param dateFrom from date
	 * @return this
	 */
	public DateSequenceGenerator from(final Date dateFrom)
	{
		Check.assumeNotNull(dateFrom, "dateFrom not null");
		this._dateFrom = dateFrom;

		return this;
	}

	private Date getDateFromToUse()
	{
		Check.assumeNotNull(_dateFrom, "dateFrom not null");
		return _dateFrom;
	}

	/**
	 * 
	 * @param dateTo to date
	 * @return this
	 */
	public DateSequenceGenerator to(final Date dateTo)
	{
		Check.assumeNotNull(dateTo, "dateTo not null");
		this._dateTo = dateTo;

		return this;
	}

	private Date getDateToToUse()
	{
		Check.assumeNotNull(_dateTo, "dateTo not null");
		return _dateTo;
	}

	/**
	 * Iterate using given incrementor.
	 * 
	 * @param incrementor date incrementor
	 * @return this
	 */
	public DateSequenceGenerator by(final ICalendarIncrementor incrementor)
	{
		Check.assumeNotNull(incrementor, "incrementor not null");
		this.incrementor = incrementor;

		return this;
	}

	/**
	 * Iterate day by day.
	 * 
	 * @return this
	 */
	public DateSequenceGenerator byDay()
	{
		return by(CalendarIncrementors.dayByDay());
	}

	/**
	 * Iterate each <code>day</code> days.
	 * 
	 * @param day
	 * @return this
	 */
	public DateSequenceGenerator byNthDay(final int day)
	{
		return by(CalendarIncrementors.eachNthDay(day));
	}

	public DateSequenceGenerator byWeeks(final int week, final int dayOfWeek)
	{
		return by(CalendarIncrementors.eachNthWeek(week, dayOfWeek));
	}

	public DateSequenceGenerator byMonths(final int months, final int dayOfMonth)
	{
		return by(CalendarIncrementors.eachNthMonth(months, dayOfMonth));
	}

	/**
	 * Sets exploder to be used to convert current interated date to a set of dates.
	 * 
	 * @param exploder
	 * @return this
	 */
	public DateSequenceGenerator explode(final IDateSequenceExploder exploder)
	{
		Check.assumeNotNull(exploder, "exploder not null");
		this.exploder = exploder;

		return this;
	}

	/**
	 * Sets a date shifter to be used to shift current iterated date.
	 * 
	 * This shifter will be called AFTER current iterated date is exploded (see {@link #explode(IDateSequenceExploder)}).
	 * 
	 * @param shifter
	 * @return this
	 */
	public DateSequenceGenerator shift(final IDateShifter shifter)
	{
		Check.assumeNotNull(shifter, "shifter not null");
		this.shifter = shifter;

		return this;
	}

	/**
	 * 
	 * @param enforce if true dates will not be shifted after specified end date.
	 * @return this
	 */
	public DateSequenceGenerator enforceDateToAfterShift(final boolean enforce)
	{
		this.enforceDateToAfterShift = enforce;

		return this;
	}
}
