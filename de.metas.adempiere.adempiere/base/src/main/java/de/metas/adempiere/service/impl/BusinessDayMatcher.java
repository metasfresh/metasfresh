package de.metas.adempiere.service.impl;

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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Set;

import org.adempiere.util.Check;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.IBusinessDayMatcher;
import lombok.NonNull;
import lombok.Value;

@Value
/* package */class BusinessDayMatcher implements IBusinessDayMatcher
{
	private static final ImmutableSet<Integer> DEFAULT_WEEKEND_DAYS = ImmutableSet.of(Calendar.SATURDAY, Calendar.SUNDAY);

	private final ImmutableSet<Integer> weekendDays;

	public BusinessDayMatcher()
	{
		this(DEFAULT_WEEKEND_DAYS);
	}

	private BusinessDayMatcher(@NonNull final ImmutableSet<Integer> weekendDays)
	{
		this.weekendDays = weekendDays;
	}

	@Override
	public BusinessDayMatcher changeWeekendDays(@NonNull final Set<Integer> weekendDays)
	{
		if (Objects.equals(this.weekendDays, weekendDays))
		{
			return this;
		}

		return new BusinessDayMatcher(ImmutableSet.copyOf(weekendDays));
	}

	@Override
	public Set<Integer> getWeekendDays()
	{
		return weekendDays;
	}

	@Override
	public boolean isBusinessDay(final Date date)
	{
		Check.assumeNotNull(date, "date not null");

		//
		// Exclude configured weekend days
		if (isWeekend(date))
		{
			return false;
		}

		// TODO: check C_NonBusinessDay table

		return true;
	}

	@Override
	public Date getNextBusinessDay(final Date date)
	{
		Check.assumeNotNull(date, "date not null");

		Date currentDate = date;
		while (!isBusinessDay(currentDate))
		{
			currentDate = TimeUtil.addDays(currentDate, 1);
		}
		return currentDate;
	}

	private boolean isWeekend(final Date date)
	{
		final Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return weekendDays.contains(dayOfWeek);
	}
}
