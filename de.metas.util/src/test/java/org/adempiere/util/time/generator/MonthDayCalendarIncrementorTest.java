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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MonthDayCalendarIncrementorTest
{
	@Test
	public void test_year2014_1MonthIncrement_31DayOfMonth()
	{
		final MonthDayCalendarIncrementor incrementor = new MonthDayCalendarIncrementor(1, 31);
		final GregorianCalendar cal = new GregorianCalendar(2014, Calendar.JANUARY, 1);
		
		final List<Date> expectedDates = Arrays.asList(
				createDate(2014, 1, 31),
				createDate(2014, 2, 28),
				createDate(2014, 3, 31),
				createDate(2014, 4, 30),
				createDate(2014, 5, 31),
				createDate(2014, 6, 30),
				createDate(2014, 7, 31),
				createDate(2014, 8, 31),
				createDate(2014, 9, 30),
				createDate(2014, 10, 31),
				createDate(2014, 11, 30),
				createDate(2014, 12, 31)
				);
		
		final List<Date> actualDates = new ArrayList<>(12);
		for (int i = 1; i <= 12; i++)
		{
			incrementor.increment(cal);
			final Date date = cal.getTime();
			actualDates.add(date);
		}
		
		Assert.assertEquals(expectedDates, actualDates);
	}
	
	private static final Date createDate(final int year, final int month, final int day)
	{
		final GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}

}
