package org.adempiere.util.time;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Code taken from the book "Test Driven", Chapter 7 ("Test-driving the
 * unpredictable") by Lasse Koskela.
 * 
 * @author ts
 * 
 */
public final class SystemTime
{

	private static final TimeSource defaultTimeSource = new TimeSource()
	{
		@Override
		public long millis()
		{
			return System.currentTimeMillis();
		}

	};

	private static TimeSource timeSource;

	public static long millis()
	{
		return getTimeSource().millis();
	}

	public static GregorianCalendar asGregorianCalendar()
	{

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(millis());

		return cal;
	}

	public static Date asDate()
	{

		return new Date(millis());
	}

	public static Timestamp asTimestamp()
	{

		return new Timestamp(millis());
	}

	public static LocalDateTime asLocalDateTime()
	{
		return Instant.ofEpochMilli(millis())
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}

	public static LocalDate asLocalDate()
	{
		return Instant.ofEpochMilli(millis())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	/**
	 * Same as {@link #asTimestamp()} but the returned date will be truncated to DAY.
	 * 
	 * @return
	 */
	public static Timestamp asDayTimestamp()
	{
		final GregorianCalendar cal = asGregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new Timestamp(cal.getTimeInMillis());
	}

	private static TimeSource getTimeSource()
	{
		return timeSource == null ? defaultTimeSource : timeSource;
	}

	/**
	 * After invocation of this method, the time returned will be the system
	 * time again.
	 */
	public static void resetTimeSource()
	{
		timeSource = null;
	}

	/**
	 * 
	 * @param newTimeSource
	 *            the given TimeSource will be used for the time returned by the
	 *            methods of this class (unless it is null).
	 * 
	 */
	public static void setTimeSource(TimeSource newTimeSource)
	{
		timeSource = newTimeSource;
	}
}
