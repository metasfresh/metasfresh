package org.adempiere.util.time;

/*
 * #%L
 * org.adempiere.util
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
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A {@link TimeSource} implementation which returns a preset time.
 * 
 * Mainly this class is used in testing.
 * 
 * @author tsa
 *
 */
public class FixedTimeSource implements TimeSource
{
	private final Date date;

	/**
	 * 
	 * @param year
	 * @param month 1..12
	 * @param day
	 * @param hour
	 * @param minute
	 * @param sec
	 */
	public FixedTimeSource(int year, int month, int day, int hour, int minute, int sec)
	{
		super();

		GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); // 0..11
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);
		date = new Date(cal.getTimeInMillis());
	}

	public FixedTimeSource(Date date)
	{
		super();
		this.date = date;
	}

	@Override
	public long millis()
	{
		return date.getTime();
	}
}
