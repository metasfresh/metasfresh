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


import java.util.Calendar;

public class FixedTimeSource implements TimeSource
{
	private long ts = -1;

	@Override
	public long millis()
	{
		if (ts < 0)
		{
			ts = System.currentTimeMillis();
		}
		return ts;
	}

	public void setMillis(final int millis)
	{
		ts = millis;
	}

	public FixedTimeSource setTime(final int year, final int month, final int day, final int hour, final int minute, final int sec)
	{
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);

		ts = cal.getTimeInMillis();
		return this;
	}
}
