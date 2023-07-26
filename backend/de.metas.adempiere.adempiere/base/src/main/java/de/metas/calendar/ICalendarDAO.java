/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.calendar;

import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_NonBusinessDay;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

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

	I_C_Period findByCalendar(Timestamp date, @NonNull CalendarId calendarId);

	/**
	 * Period of the year for given calendar and date
	 * 
	 * @return the period from calendar that includes the given date if found, null otherwise
	 * @deprecated
	 */
	@Deprecated
	I_C_Period findByCalendar(Properties ctx, Timestamp date, int calendarId, String trxName);

	CalendarNonBusinessDays getCalendarNonBusinessDays(final CalendarId calendarId);

	void validate(I_C_NonBusinessDay record);

	@Nullable String getName(CalendarId calendarId);

	/**
	 * Get Calendar of current Org which has flag "default" to true.
	 * Fallback to Org=Any.
	 *
	 * @throws AdempiereException if no default calendar is found
	 */
	@NonNull I_C_Calendar getDefaultCalendar(@NonNull final OrgId orgId);
}
