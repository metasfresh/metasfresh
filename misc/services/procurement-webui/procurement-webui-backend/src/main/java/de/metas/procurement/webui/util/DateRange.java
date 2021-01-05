package de.metas.procurement.webui.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

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

public final class DateRange implements Serializable, Cloneable
{
	private static final int DAY_OF_WEEK_First = Calendar.MONDAY;
	private static final int DAY_OF_WEEK_Last = Calendar.SUNDAY;

	/**
	 * Creates the week {@link DateRange} which includes the given date, but makes sure that the date range shall be in same month as given date.
	 *
	 * @return week range in same month as given date
	 */
	public static DateRange createWeekInMonthForDay(final Date date)
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

	public static DateRange createWeek(final Date date)
	{
		final Date dateFrom = DateUtils.truncToWeek(date);
		final Date dateTo = DateUtils.addDays(dateFrom, 6);
		return of(dateFrom, dateTo);
	}

	public static DateRange createWeek(final LocalDate date)
	{
		return createWeek(DateUtils.toDate(date));
	}

	public static DateRange of(final Date dateFrom, final Date dateTo)
	{
		return of(DateUtils.toLocalDate(dateFrom), DateUtils.toLocalDate(dateTo));
	}

	public static DateRange of(final LocalDate dateFrom, final LocalDate dateTo)
	{
		return new DateRange(dateFrom, dateTo);
	}

	@Getter
	private final LocalDate dateFrom;
	@Getter
	private final LocalDate dateTo;

	private transient Integer _daysBetween;
	private transient Integer _daysInCurrentMonth;

	private DateRange(@NonNull final LocalDate dateFrom, @NonNull final LocalDate dateTo)
	{
		if (dateFrom.compareTo(dateTo) > 0)
		{
			this.dateFrom = dateTo;
			this.dateTo = dateFrom;
		}
		else
		{
			this.dateFrom = dateFrom;
			this.dateTo = dateTo;
		}

	}

	private DateRange(final DateRange from)
	{
		dateFrom = from.dateFrom;
		dateTo = from.dateTo;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
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

	public int getDaysBetween()
	{
		Integer daysBetween = _daysBetween;
		if (daysBetween == null)
		{
			daysBetween = _daysBetween = Math.toIntExact(Duration.between(dateFrom, dateTo).toDays());
		}
		return daysBetween;
	}

	public int getDaysInCurrentMonth()
	{
		Integer daysInCurrentMonth = _daysInCurrentMonth;
		if (daysInCurrentMonth == null)
		{
			daysInCurrentMonth = _daysInCurrentMonth = computeDaysInCurrentMonth();
		}
		return daysInCurrentMonth;
	}

	private int computeDaysInCurrentMonth()
	{
		final Calendar cal = new GregorianCalendar();
		cal.setTime(DateUtils.toDate(dateTo));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public Iterable<LocalDate> daysIterable()
	{
		return () -> new DayIterator(dateFrom, dateTo);
	}

	public boolean contains(@NonNull final LocalDate date)
	{
		if (dateFrom.compareTo(date) > 0)
		{
			return false;
		}
		return date.compareTo(dateTo) <= 0;
	}

	/**
	 * @return next week {@link DateRange}
	 */
	public DateRange getNextWeek()
	{
		final LocalDate dateFrom = getDateFrom().plusDays(7);
		final LocalDate dateTo = dateFrom.plusDays(6);
		return of(dateFrom, dateTo);
	}

	private static final class DayIterator implements Iterator<LocalDate>
	{
		private LocalDate current;
		private final LocalDate dateTo;

		private DayIterator(@NonNull final LocalDate dateFrom, @NonNull final LocalDate dateTo)
		{
			this.current = dateFrom.minusDays(1);
			this.dateTo = dateTo;
		}

		@Override
		public boolean hasNext()
		{
			return current.compareTo(dateTo) < 0;
		}

		@Override
		public LocalDate next()
		{
			if (current.compareTo(dateTo) >= 0)
			{
				throw new NoSuchElementException();
			}

			current = current.plusDays(1);
			return current;
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
