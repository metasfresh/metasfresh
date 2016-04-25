package de.metas.commission.custom.type;

/*
 * #%L
 * de.metas.commission.base
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
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.X_C_Period;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;

public class PeriodsHelper
{
	public I_C_Calendar createCalendar(final Properties ctx, final String name)
	{
		final I_C_Calendar cal = InterfaceWrapperHelper.create(ctx, I_C_Calendar.class, Trx.TRXNAME_None);
		cal.setName(name);
		InterfaceWrapperHelper.save(cal);

		return cal;
	}

	public I_C_Year createYear(final I_C_Calendar cal, final int year)
	{
		final I_C_Year yearObj = InterfaceWrapperHelper.newInstance(I_C_Year.class, cal);
		yearObj.setC_Calendar_ID(cal.getC_Calendar_ID());
		yearObj.setFiscalYear(Integer.toString(year));
		InterfaceWrapperHelper.save(yearObj);

		createStandardPeriods(yearObj);

		return yearObj;
	}

	public I_C_Year createYear(final I_C_Calendar cal, final Date date)
	{
		final int year = TimeUtil.getYearFromTimestamp(date);
		return createYear(cal, year);
	}

	public I_C_Period createPeriod(final I_C_Year year, final Date startDate, Date endDate)
	{
		final I_C_Period period = InterfaceWrapperHelper.newInstance(I_C_Period.class, year);
		period.setC_Year_ID(year.getC_Year_ID());
		period.setStartDate(TimeUtil.asTimestamp(startDate));
		period.setEndDate(TimeUtil.asTimestamp(endDate));
		period.setPeriodType(X_C_Period.PERIODTYPE_StandardCalendarPeriod);
		// period.setPeriodNo(PeriodNo);
		// period.setName(Name);

		InterfaceWrapperHelper.save(period);
		return period;
	}

	public void createStandardPeriods(final I_C_Year year)
	{
		//
		final int yearInt = Integer.parseInt(year.getFiscalYear());

		final GregorianCalendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, yearInt);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		//
		for (int month = 0; month < 12; month++)
		{

			final Date start = new Date(cal.getTimeInMillis());

			// get last day of same month
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_YEAR, -1);
			final Date end = new Date(cal.getTimeInMillis());
			//

			createPeriod(year, start, end);

			// get first day of next month
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}

	}

}
