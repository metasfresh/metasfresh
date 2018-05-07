package org.adempiere.util.time.generator;

import java.time.LocalDate;
import java.time.Month;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MonthDayCalendarIncrementorTest
{
	@Test
	public void test_year2014_1MonthIncrement_31DayOfMonth()
	{
		final List<LocalDate> expectedDates = Arrays.asList(
				LocalDate.of(2014, 1, 31),
				LocalDate.of(2014, 2, 28),
				LocalDate.of(2014, 3, 31),
				LocalDate.of(2014, 4, 30),
				LocalDate.of(2014, 5, 31),
				LocalDate.of(2014, 6, 30),
				LocalDate.of(2014, 7, 31),
				LocalDate.of(2014, 8, 31),
				LocalDate.of(2014, 9, 30),
				LocalDate.of(2014, 10, 31),
				LocalDate.of(2014, 11, 30),
				LocalDate.of(2014, 12, 31));

		final MonthDayCalendarIncrementor incrementor = new MonthDayCalendarIncrementor(1, 31);

		final List<LocalDate> actualDates = new ArrayList<>(12);
		LocalDate date = LocalDate.of(2014, Month.JANUARY, 1);
		for (int i = 1; i <= 12; i++)
		{
			date = incrementor.increment(date);
			actualDates.add(date);
		}

		Assert.assertEquals(expectedDates, actualDates);
	}
}
