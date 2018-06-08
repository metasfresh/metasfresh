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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

import org.adempiere.util.Check;

/**
 * Wraps a given {@link ICalendarIncrementor} and calls it until the calendar's day of the week is one that we accept.
 * 
 * @author tsa
 *
 */
public class OnlyDaysOfWeekFilter implements Predicate<Calendar>
{
	private final List<Integer> allowedDaysOfWeek;

	public OnlyDaysOfWeekFilter(final List<Integer> allowedDaysOfWeek)
	{
		super();

		Check.assumeNotEmpty(allowedDaysOfWeek, "allowedDaysOfWeek not empty");
		this.allowedDaysOfWeek = new ArrayList<>(allowedDaysOfWeek);
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + " [allowedDaysOfWeek=" + allowedDaysOfWeek + "]";
	}

	@Override
	public boolean test(final Calendar calendar)
	{
		final int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return allowedDaysOfWeek.contains(dayOfWeek);
	}
}
