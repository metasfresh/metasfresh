package de.metas.adempiere.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.service.IBusinessDayMatcher;

/* package */class BusinessDayMatcher implements IBusinessDayMatcher
{
	public static final Set<Integer> DEFAULT_WEEKEND_DAYS = ListUtils.asSet(Calendar.SATURDAY, Calendar.SUNDAY);

	private Set<Integer> weekendDays = DEFAULT_WEEKEND_DAYS;

	public BusinessDayMatcher()
	{
		super();
	}

	/*
	 * I Don't understand why this one was made this way. And it is never used in the code
	 * Replaced it with de.metas.adempiere.service.impl.BusinessDayMatcher.setWeekendDays(Set<Integer>)
	 *  (non-Javadoc)
	 * @see de.metas.adempiere.service.IBusinessDayMatcher#setWeekendDays(int[])
	 */
	@Override
	public void setWeekendDays(final int... daysOfWeek)
	{
		if (daysOfWeek == null || daysOfWeek.length == 0)
		{
			weekendDays = Collections.emptySet();
		}
		else
		{
			weekendDays = new HashSet<Integer>(daysOfWeek.length);
			for (final int dayOfWeek : daysOfWeek)
			{
				weekendDays.add(dayOfWeek);
			}
		}
	}
	
	@Override
	public void setWeekendDays(final Set<Integer> daysOfWeek)
	{
		if (daysOfWeek == null || daysOfWeek.size() == 0)
		{
			weekendDays = Collections.emptySet();
		}
		
		else
		{
			weekendDays = new HashSet<Integer>(daysOfWeek.size());
			
			weekendDays.addAll(daysOfWeek);
		}
	}

	@Override
	public Set<Integer> getWeekendDays()
	{
		return new HashSet<>(weekendDays);
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
