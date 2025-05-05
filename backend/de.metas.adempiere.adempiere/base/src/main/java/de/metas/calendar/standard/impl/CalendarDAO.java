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

package de.metas.calendar.standard.impl;

import de.metas.calendar.standard.YearId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.Query;

import java.util.List;
import java.util.Properties;

public class CalendarDAO extends AbstractCalendarDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public List<I_C_Period> retrievePeriods(
			final Properties ctx,
			final I_C_Year year,
			final String trxName)
	{
		return new Query(ctx, I_C_Period.Table_Name, I_C_Period.COLUMNNAME_C_Year_ID + "=?", trxName)
				.setParameters(year.getC_Year_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Period.COLUMNNAME_StartDate)
				.list(I_C_Period.class);
	}

	@Override
	protected List<I_C_Period> retrievePeriods(
			final Properties ctx,
			final int calendarId,
			final @NonNull LocalDateAndOrgId begin,
			final @NonNull LocalDateAndOrgId end,
			final String trxName)
	{
		final String wc =
				I_C_Period.COLUMNNAME_C_Year_ID + " IN ("
						+ " select " + I_C_Year.COLUMNNAME_C_Year_ID + " from " + I_C_Year.Table_Name + " where " + I_C_Year.COLUMNNAME_C_Calendar_ID + "=?"
						+ ") AND "
						+ I_C_Period.COLUMNNAME_EndDate + ">=? AND "
						+ I_C_Period.COLUMNNAME_StartDate + "<=?";

		return new Query(ctx, I_C_Period.Table_Name, wc, trxName)
				.setParameters(calendarId, begin.toTimestamp(orgDAO::getTimeZone), end.toTimestamp(orgDAO::getTimeZone))
				.setOnlyActiveRecords(true)
				.setClient_ID()
				// .setApplyAccessFilter(true) isn't required here and case cause problems when running from ad_scheduler
				.setOrderBy(I_C_Period.COLUMNNAME_StartDate)
				.list(I_C_Period.class);
	}

	@Override
	public List<I_C_Year> retrieveYearsOfCalendar(final I_C_Calendar calendar)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(calendar);
		final String trxName = InterfaceWrapperHelper.getTrxName(calendar);

		final String whereClause = I_C_Year.COLUMNNAME_C_Calendar_ID + "=?";

		return new Query(ctx, I_C_Year.Table_Name, whereClause, trxName)
				.setParameters(calendar.getC_Calendar_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_Year.COLUMNNAME_C_Year_ID)
				.list(I_C_Year.class);
	}

	@Override
	public I_C_Period retrieveFirstPeriodOfTheYear(@NonNull final YearId yearId)
	{
		return queryBL.createQueryBuilder(I_C_Period.class)
				.addEqualsFilter(I_C_Period.COLUMNNAME_C_Year_ID, yearId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Period.COLUMNNAME_StartDate)
				.create()
				.first(I_C_Period.class);
	}

	@Override
	public I_C_Period retrieveLastPeriodOfTheYear(@NonNull final YearId yearId)
	{
		return queryBL.createQueryBuilder(I_C_Period.class)
				.addEqualsFilter(I_C_Period.COLUMNNAME_C_Year_ID, yearId)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_C_Period.COLUMNNAME_StartDate)
				.create()
				.first(I_C_Period.class);
	}

}
