package de.metas.edi.esb.commons;

/*
 * #%L
 * de.metas.edi.esb
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


import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Code taken from the book "Test Driven", Chapter 7 ("Test-driving the unpredictable") by Lasse Koskela.
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

	private SystemTime()
	{
	}

	public static long millis()
	{
		return SystemTime.getTimeSource().millis();
	}

	public static GregorianCalendar asGregorianCalendar()
	{

		final GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeInMillis(SystemTime.millis());

		return cal;
	}

	public static Date asDate()
	{

		return new Date(SystemTime.millis());
	}

	public static Timestamp asTimestamp()
	{

		return new Timestamp(SystemTime.millis());
	}

	private static TimeSource getTimeSource()
	{
		return SystemTime.timeSource == null ? SystemTime.defaultTimeSource : SystemTime.timeSource;
	}

	/**
	 * After invocation of this method, the time returned will be the system time again.
	 */
	public static void resetTimeSource()
	{
		SystemTime.timeSource = null;
	}

	/**
	 *
	 * @param newTimeSource the given TimeSource will be used for the time returned by the methods of this class (unless it is null).
	 *
	 */
	public static void setTimeSource(final TimeSource newTimeSource)
	{
		SystemTime.timeSource = newTimeSource;
	}
}
