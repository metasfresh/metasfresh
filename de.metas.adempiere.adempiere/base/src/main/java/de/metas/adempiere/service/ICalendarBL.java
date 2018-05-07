package de.metas.adempiere.service;

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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;

/**
 * @author RC
 * 
 */
public interface ICalendarBL extends ISingletonService
{

	/**
	 * DatenEnd of the C_Year's last period must be one year after DateStart of the C_Year's first period
	 * 
	 * @param year
	 * @return
	 */
	boolean isLengthOneYear(I_C_Year year);

	/**
	 * For every period A of a given calendar, there must be either another period B with B's StartDate beeing A's EndDate plus one day or no later period (within the same calendar!) at all
	 * 
	 * @param year
	 * @return
	 */
	boolean isYearNoGaps(I_C_Year year);

	/**
	 * For every point in time t, there may be at most one C_Period with StartDate <= t <= EndDate
	 * 
	 * @param calendar
	 * @return
	 */
	boolean isCalendarNoOverlaps(I_C_Calendar calendar);

	boolean isCalendarNoGaps(I_C_Calendar calendar);

	Timestamp getLastDayOfYear(I_C_Year year);

	Timestamp getFirstDayOfYear(I_C_Year year);

	/**
	 * Make all the validation for a calendar so it can be properly used in transactions: <br>
	 * Length: DatenEnd of the C_Year's last period must be one year after DateStart of the C_Year's first period No; <br>
	 * Gaps: For every period A of a given calendar, there must be either another period B with B's StartDate being A's EndDate plus one day or no later period (within the same calendar!) at all; <br>
	 * No overlap: for every C_Calendar and every point in time t, there may be at most one C_Period with StartDate <= t <= EndDate
	 * 
	 * @param calendar
	 */
	void checkCorrectCalendar(I_C_Calendar calendar);

	/**
	 * Standard Period
	 *
	 * @return true if standard calendar period
	 */
	boolean isStandardPeriod(I_C_Period period);

	IBusinessDayMatcher createBusinessDayMatcher();
}
