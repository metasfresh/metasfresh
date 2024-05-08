package de.metas.calendar.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.compiere.Adempiere;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.calendar.ICalendarBL;
import de.metas.util.Services;

public class CalendarBLTest extends CalendarTestBase
{
	@BeforeClass
	public static void configure()
	{
		Adempiere.enableUnitTestMode();
	}

	@Test
	public void testIsLengthOneYear()
	{
		final I_C_Calendar calendar1 = db.newInstance(I_C_Calendar.class);
		db.save(calendar1);

		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		year1.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		boolean isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		Assert.assertTrue("Length is not one year", isLengthOneYear == true);

		period1.setEndDate(TimeUtil.getDay(2013, 12, 30));
		db.save(period1);

		isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		Assert.assertTrue("Length isone year", isLengthOneYear == false);

		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(TimeUtil.getDay(2013, 5, 5));
		period2.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		Assert.assertTrue("Length is not one year", isLengthOneYear == true);

		period1.setStartDate(TimeUtil.getDay(2013, 1, 2));
		db.save(period1);

		isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		Assert.assertTrue("Length isone year", isLengthOneYear == false);
	}

	@Test
	public void testIsCalendarNoGaps_GapBetweenYears()
	{
		final I_C_Calendar calendar1 = db.newInstance(I_C_Calendar.class);
		db.save(calendar1);

		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		year1.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(TimeUtil.getDay(2013, 5, 6));
		period2.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		// second year
		final I_C_Year year2 = db.newInstance(I_C_Year.class);
		year2.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year2);

		final I_C_Period period3 = db.newInstance(I_C_Period.class);
		period3.setStartDate(TimeUtil.getDay(2014, 2, 1));
		period3.setEndDate(TimeUtil.getDay(2014, 12, 31));
		period3.setC_Year_ID(year2.getC_Year_ID());
		db.save(period3);

		boolean isCalendarNoGaps = Services.get(ICalendarBL.class).isCalendarNoGaps(calendar1);

		Assert.assertTrue("Calendar has no gaps", isCalendarNoGaps == false);
	}

	@Test
	public void testIsCalendarNoGaps_GapBetweenPeriods()
	{
		final I_C_Calendar calendar1 = db.newInstance(I_C_Calendar.class);
		db.save(calendar1);

		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		year1.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(TimeUtil.getDay(2013, 5, 7));
		period2.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		boolean isCalendarNoGaps = Services.get(ICalendarBL.class).isCalendarNoGaps(calendar1);
		Assert.assertTrue("Calendar has no gaps", isCalendarNoGaps == false);
	}

	@Test
	public void testIsCalendarNoGaps()
	{
		final I_C_Calendar calendar1 = db.newInstance(I_C_Calendar.class);
		db.save(calendar1);

		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		year1.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(TimeUtil.getDay(2013, 5, 6));
		period2.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		// second year
		final I_C_Year year2 = db.newInstance(I_C_Year.class);
		year2.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year2);

		final I_C_Period period3 = db.newInstance(I_C_Period.class);
		period3.setStartDate(TimeUtil.getDay(2014, 1, 1));
		period3.setEndDate(TimeUtil.getDay(2014, 12, 31));
		period3.setC_Year_ID(year2.getC_Year_ID());
		db.save(period3);

		boolean isCalendarNoGaps = Services.get(ICalendarBL.class).isCalendarNoGaps(calendar1);
		Assert.assertTrue("Calendar has gaps", isCalendarNoGaps == true);
	}

	@Test
	public void testIsCalendarNoOverlaps()
	{
		final I_C_Calendar calendar1 = db.newInstance(I_C_Calendar.class);
		db.save(calendar1);

		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		year1.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(TimeUtil.getDay(2013, 5, 6));
		period2.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		boolean isCalendarNoOverlaps = Services.get(ICalendarBL.class).isCalendarNoOverlaps(calendar1);
		Assert.assertTrue("Calendar has overlaps", isCalendarNoOverlaps == true);

		// second year
		final I_C_Year year2 = db.newInstance(I_C_Year.class);
		year2.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year2);

		final I_C_Period period3 = db.newInstance(I_C_Period.class);
		period3.setStartDate(TimeUtil.getDay(2013, 4, 4));
		period3.setEndDate(TimeUtil.getDay(2013, 6, 6));
		period3.setC_Year_ID(year2.getC_Year_ID());
		db.save(period3);

		isCalendarNoOverlaps = Services.get(ICalendarBL.class).isCalendarNoOverlaps(calendar1);
		Assert.assertTrue("Calendar has no overlaps", isCalendarNoOverlaps == false);
	}

	@Test
	public void testIsYearNoGaps()
	{
		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(TimeUtil.getDay(2013, 5, 6));
		period2.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		boolean isYearNoGaps = Services.get(ICalendarBL.class).isYearNoGaps(year1);
		Assert.assertTrue("Year has gaps", isYearNoGaps == true);

		period2.setStartDate(TimeUtil.getDay(2013, 5, 7));
		db.save(period2);

		isYearNoGaps = Services.get(ICalendarBL.class).isYearNoGaps(year1);
		Assert.assertTrue("Year has no gaps", isYearNoGaps == false);
	}

	@Test
	public void testGetLastDayOfYear()
	{
		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		Timestamp lastDayOfYear = Services.get(ICalendarBL.class).getLastDayOfYear(year1);
		Assert.assertTrue("Wrong last day of year", lastDayOfYear.compareTo(TimeUtil.getDay(2013, 5, 5)) == 0);
	}

	@Test
	public void testGetFirstDayOfYear()
	{
		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		Timestamp firstDayOfYear = Services.get(ICalendarBL.class).getFirstDayOfYear(year1);
		Assert.assertTrue("Wrong last day of year", firstDayOfYear.compareTo(TimeUtil.getDay(2013, 1, 1)) == 0);
	}
}
