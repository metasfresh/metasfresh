package de.metas.procurement.webui.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("serial")
public final class DateRange implements Serializable, Cloneable
{
	private static final int DAY_OF_WEEK_First = Calendar.MONDAY;
	private static final int DAY_OF_WEEK_Last = Calendar.SUNDAY;

	/**
	 * Creates the week {@link DateRange} which includes the given date, but makes sure that the date range shall be in same month as given date.
	 *
	 * @param date
	 * @return week range in same month as given date
	 */
	public static final DateRange createWeekInMonthForDay(final Date date)
	{
		Preconditions.checkNotNull(date, "date not null");

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(date.getTime());
		cal.setFirstDayOfWeek(DAY_OF_WEEK_First);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH);

		//
		// Move to first day of week or first day of month
		cal.set(Calendar.DAY_OF_WEEK, DAY_OF_WEEK_First);
		if (cal.get(Calendar.MONTH) != month)
		{
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
		}
		final Date dateWeekStart = cal.getTime();

		//
		// Move to last day of week
		cal.set(Calendar.DAY_OF_WEEK, DAY_OF_WEEK_Last);
		if (cal.get(Calendar.MONTH) != month)
		{
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.YEAR, year);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_YEAR, -1);
		}
		final Date dateWeekEnd = cal.getTime();

		return of(dateWeekStart, dateWeekEnd);
	}

	public static final DateRange createWeek(final Date date)
	{
		final Date dateFrom = DateUtils.truncToWeek(date);
		final Date dateTo = DateUtils.addDays(dateFrom, 6);
		return of(dateFrom, dateTo);
	}

	public static DateRange of(final Date dateFrom, final Date dateTo)
	{
		return new DateRange(dateFrom, dateTo);
	}

	private final Date dateFrom;
	private final Date dateTo;

	private Integer _daysBetween;
	private Integer _daysInCurrentMonth;

	private DateRange(Date dateFrom, Date dateTo)
	{
		super();
		Preconditions.checkNotNull(dateFrom, "dateFrom");
		Preconditions.checkNotNull(dateTo, "dateTo");

		// Invert dateFrom and dateTo if they are not in order
		if (dateFrom.compareTo(dateTo) > 0)
		{
			final Date tmp = dateFrom;
			dateFrom = dateTo;
			dateTo = tmp;
		}

		this.dateFrom = (Date)dateFrom.clone();
		this.dateTo = (Date)dateTo.clone();
	}

	private DateRange(final DateRange from)
	{
		super();
		dateFrom = (Date)from.dateFrom.clone();
		dateTo = (Date)from.dateTo.clone();
	}

	@Override
	public String toString()
	{
		return com.google.gwt.thirdparty.guava.common.base.Objects.toStringHelper(this)
				.add("from", dateFrom)
				.add("to", dateTo)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(dateFrom, dateTo);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (!(obj instanceof DateRange))
		{
			return false;
		}
		final DateRange other = (DateRange)obj;

		return Objects.equals(dateFrom, other.dateFrom)
				&& Objects.equals(dateTo, other.dateTo);
	}

	@Override
	protected DateRange clone()
	{
		return new DateRange(this);
	}

	public Date getDateFrom()
	{
		return (Date)dateFrom.clone();
	}

	public Date getDateTo()
	{
		return (Date)dateTo.clone();
	}

	public int getDaysBetween()
	{
		if (_daysBetween == null)
		{
			final long durationMillis = dateTo.getTime() - dateFrom.getTime();
			_daysBetween = 1 + (int)TimeUnit.DAYS.convert(durationMillis, TimeUnit.MILLISECONDS);
		}
		return _daysBetween;
	}

	public int getDaysInCurrentMonth()
	{
		if (_daysInCurrentMonth == null)
		{
			final Calendar cal = new GregorianCalendar();
			cal.setTime(dateTo);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_YEAR, -1);
			_daysInCurrentMonth = cal.get(Calendar.DAY_OF_MONTH);
		}
		return _daysInCurrentMonth;
	}

	public Iterable<Date> daysIterable()
	{
		return new Iterable<Date>()
		{
			@Override
			public Iterator<Date> iterator()
			{
				return new DayIterator(dateFrom, dateTo);
			}
		};
	}
	
	public boolean contains(final Date date)
	{
		Preconditions.checkNotNull(date, "date not null");

		if (dateFrom.compareTo(date) > 0)
		{
			return false;
		}
		if (date.compareTo(dateTo) > 0)
		{
			return false;
		}

		return true;
	}

	/** @return next week {@link DateRange} */
	public DateRange getNextWeek()
	{
		final Date dateFrom = DateUtils.addDays(getDateFrom(), 7);
		final Date dateTo = DateUtils.addDays(dateFrom, 6);
		return of(dateFrom, dateTo);
	}

	private static final class DayIterator implements Iterator<Date>
	{
		private final Calendar dateTo;
		private final Calendar current;

		private DayIterator(final Date dateFrom, final Date dateTo)
		{
			super();
			this.dateTo = new GregorianCalendar();
			this.dateTo.setTime(dateTo);

			current = new GregorianCalendar();
			current.setTime(dateFrom);
			current.add(Calendar.DAY_OF_MONTH, -1);
		}

		@Override
		public boolean hasNext()
		{
			return current.compareTo(dateTo) < 0;
		}

		@Override
		public Date next()
		{
			if (current.compareTo(dateTo) >= 0)
			{
				throw new NoSuchElementException();
			}
			current.add(Calendar.DAY_OF_MONTH, 1);
			return current.getTime();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
