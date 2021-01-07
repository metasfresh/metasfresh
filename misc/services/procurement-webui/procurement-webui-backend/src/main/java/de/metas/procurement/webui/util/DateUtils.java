package de.metas.procurement.webui.util;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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

@UtilityClass
public final class DateUtils
{

	@Nullable
	public static Date truncToDay(@Nullable final Date date)
	{
		if (date == null)
		{
			return null;
		}

		final GregorianCalendar cal = new GregorianCalendar(Locale.getDefault());
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	@SuppressWarnings("deprecation")
	@Nullable
	public static LocalDate toLocalDate(@Nullable final java.util.Date date)
	{
		return date != null
				? LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate())
				: null;
	}

	@Deprecated
	@Nullable
	public static LocalDate toLocalDate(@Nullable final LocalDate date)
	{
		return date;
	}

	@Nullable
	public static java.sql.Date toSqlDate(@Nullable final java.util.Date date)
	{
		return date != null
				? java.sql.Date.valueOf(toLocalDate(date))
				: null;
	}

	@NonNull
	public static java.sql.Date toSqlDate(@NonNull final LocalDate date)
	{
		return java.sql.Date.valueOf(date);
	}

	public static java.util.Date toDate(final LocalDate date)
	{
		return java.util.Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date truncToMonth(@NonNull final Date date)
	{
		final GregorianCalendar cal = new GregorianCalendar(Locale.getDefault());
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static boolean between(
			@NonNull final LocalDate date,
			@Nullable final Date dateFrom,
			@Nullable final Date dateTo)
	{
		return between(toDate(date), dateFrom, dateTo);
	}

	public static boolean between(
			@NonNull final Date date,
			@Nullable final Date dateFrom,
			@Nullable final Date dateTo)
	{
		if (dateFrom != null && dateFrom.compareTo(date) > 0)
		{
			return false;
		}

		return dateTo == null || date.compareTo(dateTo) <= 0;
	}

	public static boolean between(
			@NonNull final LocalDate date,
			@Nullable final LocalDate dateFrom,
			@Nullable final LocalDate dateTo)
	{
		if (dateFrom != null && dateFrom.compareTo(date) > 0)
		{
			return false;
		}

		return dateTo == null || date.compareTo(dateTo) <= 0;
	}


	public static Date addDays(final Date date, final int daysToAdd)
	{
		Preconditions.checkNotNull(date, "date not null");

		final GregorianCalendar cal = new GregorianCalendar(Locale.getDefault());
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
		return cal.getTime();
	}

	public static Date addMonths(final Date date, final int monthsToAdd)
	{
		Preconditions.checkNotNull(date, "date not null");

		final GregorianCalendar cal = new GregorianCalendar(Locale.getDefault());
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.MONTH, monthsToAdd);
		return cal.getTime();
	}

	public static String getDayName(@NonNull final LocalDate date, @NonNull final Locale locale)
	{
		return date.format(DateTimeFormatter.ofPattern("EEEE", locale));
	}

	public static List<LocalDate> getDaysList(final LocalDate startDate, final LocalDate endDate)
	{
		final ArrayList<LocalDate> result = new ArrayList<>();
		for (LocalDate date = startDate; date.compareTo(endDate) <= 0; date = date.plusDays(1))
		{
			result.add(date);
		}

		return result;
	}

}
