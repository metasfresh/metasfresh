package de.metas.report.xls.engine;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * de.metas.report.jasper.server.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JexlCustomFunctionsTest
{
	private JexlCustomFunctions customFunctions;

	@Before
	public void beforeTest()
	{
		customFunctions = new JexlCustomFunctions();
	}

	/**
	 * Testing {@code de.metas.report.xls.engine.JexlCustomFunctions.monthBetween(int, int, Date, Date)}
	 */
	@Test
	public void test_MonthsBetween()
	{
		// Generic test with all parameters given
		test_MonthsBetween(
				true,
				4,
				2016,
				createDate(2016, 1, 1),
				createDate(2016, 12, 12));

		// Test no startDate
		test_MonthsBetween(
				true,
				3,
				2016,
				null,
				createDate(2016, 4, 1));

		// Test no endDate
		test_MonthsBetween(
				true,
				12,
				2016,
				createDate(2016, 1, 1),
				null);
		// Test no startDate and endDate
		test_MonthsBetween(
				true,
				10,
				2016,
				null,
				null);
		// Test year not between startDate and endDate
		test_MonthsBetween(
				false,
				11,
				2017,
				createDate(2016, 1, 1),
				createDate(2016, 12, 12));
		// Test month not between startDate and endDate
		test_MonthsBetween(
				false,
				11,
				2016,
				createDate(2016, 1, 1),
				createDate(2016, 3, 1));

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_MonthsBetween_InvalidMonth()
	{
		customFunctions.monthBetween(
				13,
				2016,
				createDate(2016, 1, 1),
				createDate(2016, 12, 12));
	}

	private void test_MonthsBetween(
			final boolean expected,     // the expected results
			final int monthOneBased,
			final int year,
			final Date dateStart,
			final Date dateEnd)
	{
		final boolean actualResult = customFunctions.monthBetween(monthOneBased, year, dateStart, dateEnd);

		final String errmsg = "Invalid result for "
				+ "\n month= " + monthOneBased
				+ "\n year= " + year
				+ "\n dateStart = " + dateStart
				+ "\n dateEnd= " + dateEnd;

		Assert.assertEquals(errmsg, expected, actualResult);
	}

	private static final Date createDate(final int year, final int month, final int day)
	{
		final GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
		return calendar.getTime();
	}
}
