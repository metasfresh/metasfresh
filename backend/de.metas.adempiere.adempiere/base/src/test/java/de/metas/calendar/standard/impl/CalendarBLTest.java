package de.metas.calendar.standard.impl;

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

import de.metas.calendar.standard.ICalendarBL;
import de.metas.calendar.standard.ICalendarDAO;
import de.metas.calendar.standard.YearId;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class CalendarBLTest
{
	private POJOLookupMap db;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final PlainCalendarDAO dao = (PlainCalendarDAO)Services.get(ICalendarDAO.class);
		db = dao.getDB();
	}

	private static Timestamp date(final int year, final int month, final int day)
	{
		//noinspection deprecation
		return TimeUtil.getDay(year, month, day);
	}

	private YearId createYear()
	{
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		db.save(year1);
		return YearId.ofRepoId(year1.getC_Year_ID());
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
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 12, 31));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		boolean isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		assertThat(isLengthOneYear).as("Length is not one year").isTrue();

		period1.setEndDate(date(2013, 12, 30));
		db.save(period1);

		isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		assertThat(isLengthOneYear).as("Length isone year").isFalse();

		period1.setEndDate(date(2013, 5, 5));
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(date(2013, 5, 5));
		period2.setEndDate(date(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		assertThat(isLengthOneYear).as("Length is not one year").isTrue();

		period1.setStartDate(date(2013, 1, 2));
		db.save(period1);

		isLengthOneYear = Services.get(ICalendarBL.class).isLengthOneYear(year1);

		assertThat(isLengthOneYear).as("Length isone year").isFalse();
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
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(date(2013, 5, 6));
		period2.setEndDate(date(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		// second year
		final I_C_Year year2 = db.newInstance(I_C_Year.class);
		year2.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year2);

		final I_C_Period period3 = db.newInstance(I_C_Period.class);
		period3.setStartDate(date(2014, 2, 1));
		period3.setEndDate(date(2014, 12, 31));
		period3.setC_Year_ID(year2.getC_Year_ID());
		db.save(period3);

		final boolean isCalendarNoGaps = Services.get(ICalendarBL.class).isCalendarNoGaps(calendar1);

		assertThat(isCalendarNoGaps).as("Calendar has no gaps").isFalse();
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
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(date(2013, 5, 7));
		period2.setEndDate(date(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		final boolean isCalendarNoGaps = Services.get(ICalendarBL.class).isCalendarNoGaps(calendar1);
		assertThat(isCalendarNoGaps).as("Calendar has no gaps").isFalse();
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
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(date(2013, 5, 6));
		period2.setEndDate(date(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		// second year
		final I_C_Year year2 = db.newInstance(I_C_Year.class);
		year2.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year2);

		final I_C_Period period3 = db.newInstance(I_C_Period.class);
		period3.setStartDate(date(2014, 1, 1));
		period3.setEndDate(date(2014, 12, 31));
		period3.setC_Year_ID(year2.getC_Year_ID());
		db.save(period3);

		final boolean isCalendarNoGaps = Services.get(ICalendarBL.class).isCalendarNoGaps(calendar1);
		assertThat(isCalendarNoGaps).as("Calendar has gaps").isTrue();
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
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(date(2013, 5, 6));
		period2.setEndDate(date(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		boolean isCalendarNoOverlaps = Services.get(ICalendarBL.class).isCalendarNoOverlaps(calendar1);
		assertThat(isCalendarNoOverlaps).as("Calendar has overlaps").isTrue();

		// second year
		final I_C_Year year2 = db.newInstance(I_C_Year.class);
		year2.setC_Calendar_ID(calendar1.getC_Calendar_ID());
		db.save(year2);

		final I_C_Period period3 = db.newInstance(I_C_Period.class);
		period3.setStartDate(date(2013, 4, 4));
		period3.setEndDate(date(2013, 6, 6));
		period3.setC_Year_ID(year2.getC_Year_ID());
		db.save(period3);

		isCalendarNoOverlaps = Services.get(ICalendarBL.class).isCalendarNoOverlaps(calendar1);
		assertThat(isCalendarNoOverlaps).as("Calendar has no overlaps").isFalse();
	}

	@Test
	public void testIsYearNoGaps()
	{
		// first year
		final I_C_Year year1 = db.newInstance(I_C_Year.class);
		db.save(year1);

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 5, 5));
		period1.setC_Year_ID(year1.getC_Year_ID());
		db.save(period1);

		final I_C_Period period2 = db.newInstance(I_C_Period.class);
		period2.setStartDate(date(2013, 5, 6));
		period2.setEndDate(date(2013, 12, 31));
		period2.setC_Year_ID(year1.getC_Year_ID());
		db.save(period2);

		boolean isYearNoGaps = Services.get(ICalendarBL.class).isYearNoGaps(year1);
		assertThat(isYearNoGaps).as("Year has gaps").isTrue();

		period2.setStartDate(date(2013, 5, 7));
		db.save(period2);

		isYearNoGaps = Services.get(ICalendarBL.class).isYearNoGaps(year1);
		assertThat(isYearNoGaps).as("Year has no gaps").isFalse();
	}

	@Test
	public void testGetLastDayOfYear()
	{
		// first year
		final YearId yearId1 = createYear();

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 5, 5));
		period1.setC_Year_ID(yearId1.getRepoId());
		db.save(period1);

		final Timestamp lastDayOfYear = Services.get(ICalendarBL.class).getLastDayOfYear(yearId1);
		assertThat(lastDayOfYear).as("Wrong last day of year").isEqualTo(date(2013, 5, 5));
	}

	@Test
	public void testGetFirstDayOfYear()
	{
		// first year
		final YearId yearId1 = createYear();

		final I_C_Period period1 = db.newInstance(I_C_Period.class);
		period1.setStartDate(date(2013, 1, 1));
		period1.setEndDate(date(2013, 5, 5));
		period1.setC_Year_ID(yearId1.getRepoId());
		db.save(period1);

		final Timestamp firstDayOfYear = Services.get(ICalendarBL.class).getFirstDayOfYear(yearId1);
		assertThat(firstDayOfYear).as("Wrong last day of year").isEqualTo(date(2013, 1, 1));
	}
}
