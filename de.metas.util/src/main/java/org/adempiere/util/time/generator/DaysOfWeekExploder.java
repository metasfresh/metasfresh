package org.adempiere.util.time.generator;

/*
 * #%L
 * de.metas.util
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


import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.collections.ListUtils;

public class DaysOfWeekExploder implements IDateSequenceExploder
{
	private static final List<Integer> ALL_DAYS_OF_WEEK_LIST = Arrays.asList(
			Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY,
			Calendar.SATURDAY, Calendar.SUNDAY);

	public static final DaysOfWeekExploder ALL_DAYS_OF_WEEK = new DaysOfWeekExploder(ALL_DAYS_OF_WEEK_LIST);

	private final Set<Integer> weekDays;
	private final int firstDayOfTheWeek;

	public DaysOfWeekExploder(final Collection<Integer> weekDays)
	{
		super();
		Check.assumeNotEmpty(weekDays, "weekDays not empty");
		this.weekDays = new HashSet<>(weekDays);
		for (final Integer weekDay : this.weekDays)
		{
			Check.assume(ALL_DAYS_OF_WEEK_LIST.contains(weekDay), "Week day {} shall be valid", weekDay);
		}

		this.firstDayOfTheWeek = Calendar.MONDAY;
	}

	public DaysOfWeekExploder(final int... weekDays)
	{
		this(ListUtils.asList(weekDays));
	}

	@Override
	public Collection<Date> explode(final Date date)
	{
		final long dateMillis = date.getTime();

		final Set<Date> dates = new HashSet<Date>();
		for (final int dayOfWeek : weekDays)
		{
			final Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.setFirstDayOfWeek(firstDayOfTheWeek);
			cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

			// Skip all dates which are before our given date
			if (dateMillis > cal.getTimeInMillis())
			{
				continue;
			}

			final Date dayOfWeekDate = cal.getTime();
			dates.add(dayOfWeekDate);
		}

		return dates;
	}

}
