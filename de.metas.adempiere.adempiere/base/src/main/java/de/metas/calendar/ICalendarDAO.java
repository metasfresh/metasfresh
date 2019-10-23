package de.metas.calendar;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_NonBusinessDay;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;

import de.metas.util.ISingletonService;

public interface ICalendarDAO extends ISingletonService
{
	/**
	 * @return periods of the year, ordered by startDate
	 */
	List<I_C_Period> retrievePeriods(Properties ctx, I_C_Year year, String trxName);

	List<I_C_Period> retrievePeriods(Properties ctx, I_C_Calendar cal, Timestamp begin, Timestamp end, String trxName);

	List<I_C_Year> retrieveYearsOfCalendar(I_C_Calendar calendar);

	I_C_Period retrieveFirstPeriodOfTheYear(I_C_Year year);

	I_C_Period retrieveLastPeriodOfTheYear(I_C_Year year);

	/**
	 * Period of the year for given calendar and date
	 * 
	 * @param ctx
	 * @param date
	 * @param calendarId
	 * @param trxName
	 * @return the period from calendar that includes the given date if found, null otherwise
	 */
	I_C_Period findByCalendar(Properties ctx, Timestamp date, int calendarId, String trxName);

	CalendarNonBusinessDays getCalendarNonBusinessDays(final CalendarId calendarId);

	void validate(I_C_NonBusinessDay record);
}
