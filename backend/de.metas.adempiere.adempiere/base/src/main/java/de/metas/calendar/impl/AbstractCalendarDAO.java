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

package de.metas.calendar.impl;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.calendar.CalendarId;
import de.metas.calendar.CalendarNonBusinessDays;
import de.metas.calendar.FixedNonBusinessDay;
import de.metas.calendar.ICalendarBL;
import de.metas.calendar.ICalendarDAO;
import de.metas.calendar.NonBusinessDay;
import de.metas.calendar.RecurrentNonBusinessDay;
import de.metas.calendar.RecurrentNonBusinessDayFrequency;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_NonBusinessDay;
import org.compiere.model.I_C_Period;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

public abstract class AbstractCalendarDAO implements ICalendarDAO
{
	private final CCache<CalendarId, CalendarNonBusinessDays> nonBusinessDaysByCalendarId = CCache.newCache(I_C_NonBusinessDay.Table_Name, 10, CCache.EXPIREMINUTES_Never);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	protected abstract List<I_C_Period> retrievePeriods(
			final Properties ctx,
			final int calendarId,
			final Timestamp begin,
			final Timestamp end,
			final String trxName);

	@Override
	public List<I_C_Period> retrievePeriods(
			final Properties ctx,
			final I_C_Calendar cal,
			final Timestamp begin,
			final Timestamp end,
			final String trxName)
	{
		Check.assume(cal != null, "Param 'cal' is not null");
		final int calendarId = cal.getC_Calendar_ID();
		return retrievePeriods(ctx, calendarId, begin, end, trxName);

	}

	@Override
	public I_C_Period findByCalendar(final Timestamp date, @NonNull final CalendarId calendarId)
	{
		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		return findByCalendar(ctx, date, calendarId.getRepoId(),  trxName);
	}

	@Override
	public I_C_Period findByCalendar(final Properties ctx, final Timestamp date, final int calendarId, final String trxName)
	{
		final List<I_C_Period> periodsAll = retrievePeriods(ctx, calendarId, date, date, trxName);
		for (final I_C_Period period : periodsAll)
		{
			if (!Services.get(ICalendarBL.class).isStandardPeriod(period))
			{
				continue;
			}

			return period;
		}

		return null;
	}

	@Override
	public CalendarNonBusinessDays getCalendarNonBusinessDays(@NonNull final CalendarId calendarId)
	{
		return nonBusinessDaysByCalendarId.getOrLoad(calendarId, this::retrieveCalendarNonBusinessDays);
	}

	private CalendarNonBusinessDays retrieveCalendarNonBusinessDays(final CalendarId calendarId)
	{
		final List<NonBusinessDay> nonBusinessDaysList = queryBL.createQueryBuilderOutOfTrx(I_C_NonBusinessDay.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_NonBusinessDay.COLUMNNAME_C_Calendar_ID, calendarId)
				.create()
				.list()
				.stream()
				.map(record -> toNonBusinessDay(record))
				.collect(ImmutableList.toImmutableList());

		return CalendarNonBusinessDays.builder()
				.nonBusinessDays(nonBusinessDaysList)
				.build();
	}

	private static NonBusinessDay toNonBusinessDay(final I_C_NonBusinessDay record)
	{
		if (record.isRepeat())
		{
			final String frequency = record.getFrequency();
			if (Check.isEmpty(frequency, true))
			{
				throw new FillMandatoryException(I_C_NonBusinessDay.COLUMNNAME_Frequency);
			}

			return RecurrentNonBusinessDay.builder()
					.frequency(RecurrentNonBusinessDayFrequency.forCode(frequency))
					.startDate(TimeUtil.asLocalDate(record.getDate1()))
					.endDate(TimeUtil.asLocalDate(record.getEndDate()))
					.name(record.getName())
					.build();
		}
		else
		{
			return FixedNonBusinessDay.builder()
					.fixedDate(TimeUtil.asLocalDate(record.getDate1()))
					.name(record.getName())
					.build();
		}
	}

	@Override
	public void validate(@NonNull final I_C_NonBusinessDay record)
	{
		if (!record.isActive())
		{
			return;
		}

		toNonBusinessDay(record);
	}


	@Override
	@NonNull
	public I_C_Calendar getDefaultCalendar(@NonNull final OrgId orgId)
	{
		final I_C_Calendar calendar = queryBL.createQueryBuilder(I_C_Calendar.class)
				.addEqualsFilter(I_C_Calendar.COLUMNNAME_IsDefault, true)
				.addInArrayFilter(I_C_Calendar.COLUMNNAME_AD_Org_ID, ImmutableList.of(orgId, OrgId.ANY))
				.orderByDescending(I_C_Calendar.COLUMNNAME_AD_Org_ID)
				.create()
				.first();

		if (calendar == null)
		{
			throw new AdempiereException(CalendarBL.MSG_SET_DEFAULT_OR_ORG_CALENDAR);
		}
		return calendar;
	}

	@Nullable
	@Override
	public String getName(final CalendarId calendarId)
	{
		final List<String> calendarNames = queryBL.createQueryBuilder(I_C_Calendar.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Calendar.COLUMNNAME_C_Calendar_ID, calendarId)
				.create()
				.listDistinct(I_C_Calendar.COLUMNNAME_Name, String.class);

		if (calendarNames.isEmpty())
		{
			return null;
		}

		return calendarNames.get(0);
	}
}
