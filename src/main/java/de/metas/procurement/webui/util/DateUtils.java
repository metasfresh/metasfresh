package de.metas.procurement.webui.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.ui.UI;

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

public final class DateUtils
{
	public static Date truncToDay(final Date date)
	{
		if (date == null)
		{
			return date;
		}

		final GregorianCalendar cal = new GregorianCalendar(getLocale());
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date truncToWeek(final Date date)
	{
		if (date == null)
		{
			return date;
		}

		final GregorianCalendar cal = new GregorianCalendar(getLocale());
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date truncToMonth(final Date date)
	{
		if (date == null)
		{
			return date;
		}

		final GregorianCalendar cal = new GregorianCalendar(getLocale());
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date toDayDate(final int year, final int month, final int day)
	{
		final GregorianCalendar cal = new GregorianCalendar(getLocale());
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date getToday()
	{
		return truncToDay(new Date());
	}

	public static boolean between(final Date date, final Date dateFrom, final Date dateTo)
	{
		Preconditions.checkNotNull(date, "date not null");

		if (dateFrom != null && dateFrom.compareTo(date) > 0)
		{
			return false;
		}
		if (dateTo != null && date.compareTo(dateTo) > 0)
		{
			return false;
		}

		return true;
	}

	public static Date addDays(final Date date, final int daysToAdd)
	{
		Preconditions.checkNotNull(date, "date not null");

		final GregorianCalendar cal = new GregorianCalendar(getLocale());
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
		return cal.getTime();
	}

	/**
	 * @param date
	 * @return week number string formated as "KWxx"
	 */
	public static String formatWeekNumberWithPrefix(final Date date)
	{
		final SimpleDateFormat df = new SimpleDateFormat("ww", getLocale());
		return "KW" + df.format(date);
	}
	
    private static final Locale getLocale()
    {
        UI currentUI = UI.getCurrent();
        Locale locale = (currentUI == null ? null : currentUI.getLocale());
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

}
