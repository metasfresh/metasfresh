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

import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.ICalendarBL;
import de.metas.calendar.standard.ICalendarDAO;
import de.metas.calendar.standard.YearId;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.TypedAccessor;
import de.metas.util.calendar.ExcludeWeekendBusinessDayMatcher;
import de.metas.util.calendar.IBusinessDayMatcher;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.X_C_Period;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class CalendarBL implements ICalendarBL
{

	public static final AdMessageKey MSG_SET_DEFAULT_OR_ORG_CALENDAR = AdMessageKey.of("de.metas.calendar.impl.CalendarBL.SetDefaultCalendarOrOrgCalendar");
	public static final AdMessageKey MSG_YEAR_HAS_NO_PERIOD = AdMessageKey.of("de.metas.calendar.standard.impl.CalendarBL.YearHasNoPeriod");
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public boolean isLengthOneYear(final I_C_Year year)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(year);
		final String trxName = InterfaceWrapperHelper.getTrxName(year);

		final List<I_C_Period> periodsOfTheYear = Services.get(ICalendarDAO.class).retrievePeriods(ctx, year, trxName);

		final int numberOfPeriods = periodsOfTheYear.size();

		if (numberOfPeriods == 0)
		{
			throw new AdempiereException(MSG_YEAR_HAS_NO_PERIOD)
					.appendParametersToMessage()
					.setParameter("C_Year_ID", year.getC_Year_ID());
		}

		final Timestamp firstDate = periodsOfTheYear.get(0).getStartDate();
		final Timestamp lastDate = periodsOfTheYear.get(numberOfPeriods - 1).getEndDate();

		if (TimeUtil.addYears(TimeUtil.asTimestamp(firstDate), 1).compareTo(TimeUtil.addDays(lastDate, 1)) == 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isCalendarNoGaps(final I_C_Calendar calendar)
	{
		final List<I_C_Period> periodsOfCalendar = getPeriodsOfCalendar(calendar);

		final int numberOfPeriods = periodsOfCalendar.size();

		for (int i = 0; i < numberOfPeriods - 1; i++)
		{
			final Timestamp lastDateOfPeriod = periodsOfCalendar.get(i).getEndDate();
			final Timestamp firstDateNextPeriod = periodsOfCalendar.get(i + 1).getStartDate();

			if (TimeUtil.addDays(lastDateOfPeriod, 1).compareTo(firstDateNextPeriod) != 0)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isYearNoGaps(final I_C_Year year)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(year);
		final String trxName = InterfaceWrapperHelper.getTrxName(year);

		final List<I_C_Period> periodsOfTheYear = Services.get(ICalendarDAO.class).retrievePeriods(ctx, year, trxName);

		final int numberOfPeriods = periodsOfTheYear.size();

		for (int i = 0; i < numberOfPeriods - 1; i++)
		{
			final Timestamp lastDateOfPeriod = periodsOfTheYear.get(i).getEndDate();
			final Timestamp firstDateNextPeriod = periodsOfTheYear.get(i + 1).getStartDate();

			if (TimeUtil.addDays(lastDateOfPeriod, 1).compareTo(firstDateNextPeriod) != 0)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean isCalendarNoOverlaps(final I_C_Calendar calendar)
	{
		final List<I_C_Period> periods = getPeriodsOfCalendar(calendar);
		final int numberOfPeriods = periods.size();

		for (int i = 0; i < numberOfPeriods - 1; i++)
		{
			final Timestamp lastDateOfPeriod = periods.get(i).getEndDate();
			final Timestamp firstDateNextPeriod = periods.get(i + 1).getStartDate();

			if (lastDateOfPeriod.compareTo(firstDateNextPeriod) > 0)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public Timestamp getLastDayOfYear(@NonNull final YearId yearId)
	{
		final I_C_Period period = Services.get(ICalendarDAO.class).retrieveLastPeriodOfTheYear(yearId);

		final Timestamp lastDay = period.getEndDate();

		return lastDay;
	}

	@Override
	public Timestamp getFirstDayOfYear(@NonNull final YearId yearId)
	{
		final I_C_Period period = Services.get(ICalendarDAO.class).retrieveFirstPeriodOfTheYear(yearId);

		final Timestamp firstDay = period.getStartDate();

		return firstDay;
	}

	@Override
	public void checkCorrectCalendar(final I_C_Calendar calendar)
	{
		Check.errorUnless(isCalendarNoOverlaps(calendar), "{} has overlaps", calendar);
		Check.errorUnless(isCalendarNoGaps(calendar), "{} has gaps", calendar);

		final List<I_C_Year> years = Services.get(ICalendarDAO.class).retrieveYearsOfCalendar(calendar);

		for (final I_C_Year year : years)
		{
			Check.errorUnless(isLengthOneYear(year), "{} doesn't have the length 1 year", year);
		}

	}

	public void checkCorrectCalendar(@NonNull final CalendarId calendarId)
	{
		final I_C_Calendar calendar = calendarDAO.getById(calendarId);

		checkCorrectCalendar(calendar);
	}

	private List<I_C_Period> getPeriodsOfCalendar(final I_C_Calendar calendar)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(calendar);
		final String trxName = InterfaceWrapperHelper.getTrxName(calendar);

		final List<I_C_Year> years = Services.get(ICalendarDAO.class).retrieveYearsOfCalendar(calendar);
		final List<I_C_Period> periodsOfCalendar = new ArrayList<>();

		for (final I_C_Year year : years)
		{
			final List<I_C_Period> periodsOfYear = Services.get(ICalendarDAO.class).retrievePeriods(ctx, year, trxName);
			periodsOfCalendar.addAll(periodsOfYear);

		}

		Collections.sort(periodsOfCalendar, new AccessorComparator<I_C_Period, Timestamp>(
				new ComparableComparator<Timestamp>(),
				new TypedAccessor<Timestamp>()
				{

					@Override
					public Timestamp getValue(final Object o)
					{
						return ((I_C_Period)o).getStartDate();
					}
				}));

		return periodsOfCalendar;
	}

	@Override
	public boolean isStandardPeriod(final I_C_Period period)
	{
		return X_C_Period.PERIODTYPE_StandardCalendarPeriod.equals(period.getPeriodType());
	}    // isStandardPeriod

	@Override
	public IBusinessDayMatcher createBusinessDayMatcherExcluding(final Set<DayOfWeek> excludeWeekendDays)
	{
		// TODO: consider I_C_NonBusinessDay and compose the matchers using CompositeBusinessDayMatcher
		return ExcludeWeekendBusinessDayMatcher.builder()
				.excludeWeekendDays(excludeWeekendDays)
				.build();
	}

	@Override
	@NonNull
	public CalendarId getOrgCalendarOrDefault(final @NonNull OrgId orgId)
	{
		final OrgInfo orgInfo = orgDAO.getOrgInfoByIdInTrx(orgId);
		if (orgInfo.getCalendarId() != null)
		{
			return orgInfo.getCalendarId();
		}

		final I_C_Calendar calendar = calendarDAO.getDefaultCalendar(orgId);

		return CalendarId.ofRepoId(calendar.getC_Calendar_ID());
	}
}
