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

import de.metas.util.Check;
import de.metas.util.TypedAccessor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.util.Env;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class PlainCalendarDAO extends AbstractCalendarDAO
{
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public List<I_C_Period> retrievePeriods(final Properties ctx, final I_C_Year year, final String trxName)
	{
		final List<I_C_Period> periods = db.getRecords(I_C_Period.class, new IQueryFilter<I_C_Period>()
		{

			@Override
			public boolean accept(I_C_Period pojo)
			{
				if (!pojo.getC_Year().equals(year))
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
				{
					return false;
				}

				return true;
			}

		});
		Collections.sort(periods, new AccessorComparator<I_C_Period, Timestamp>(
				new ComparableComparator<Timestamp>(),
				new TypedAccessor<Timestamp>()
				{

					@Override
					public Timestamp getValue(Object o)
					{
						return ((I_C_Period)o).getStartDate();
					}
				}));

		return periods;
	}

	@Override
	protected List<I_C_Period> retrievePeriods(final Properties ctx, final int calendarId, final Timestamp begin, final Timestamp end, final String trxName)
	{
		Check.assume(begin != null, "Param 'begin' is not null");
		Check.assume(end != null, "Param 'end' is not null");

		final List<I_C_Year> years = db.getRecords(I_C_Year.class, new IQueryFilter<I_C_Year>()
		{

			@Override
			public boolean accept(I_C_Year pojo)
			{
				if (pojo.getC_Calendar_ID() != calendarId)
				{
					return false;
				}

				return true;
			}

		});

		final List<I_C_Period> periods = db.getRecords(I_C_Period.class, new IQueryFilter<I_C_Period>()
		{

			@Override
			public boolean accept(I_C_Period pojo)
			{
				if (!years.contains(pojo.getC_Year()))
				{
					return false;
				}

				if (pojo.getEndDate().before(begin))
				{
					return false;
				}

				if (pojo.getStartDate().after(end))
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}

				return true;
			}

		});

		Collections.sort(periods, new AccessorComparator<I_C_Period, Timestamp>(
				new ComparableComparator<Timestamp>(),
				new TypedAccessor<Timestamp>()
				{

					@Override
					public Timestamp getValue(Object o)
					{
						return ((I_C_Period)o).getStartDate();
					}
				}));

		return periods;
	}

	@Override
	public List<I_C_Year> retrieveYearsOfCalendar(final I_C_Calendar calendar)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(calendar);

		final List<I_C_Year> years = db.getRecords(I_C_Year.class, new IQueryFilter<I_C_Year>()
		{

			@Override
			public boolean accept(I_C_Year pojo)
			{
				if (!pojo.isActive())
				{
					return false;
				}

				if (pojo.getC_Calendar_ID() != calendar.getC_Calendar_ID())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
				{
					return false;
				}

				return true;
			}

		});

		Collections.sort(years, new AccessorComparator<I_C_Year, Integer>(
				new ComparableComparator<Integer>(),
				new TypedAccessor<Integer>()
				{

					@Override
					public Integer getValue(Object o)
					{
						return ((I_C_Year)o).getC_Year_ID();
					}
				}));

		return years;
	}

	@Override
	public I_C_Period retrieveFirstPeriodOfTheYear(I_C_Year year)
	{
		final List<I_C_Period> periods = getPeriodsOfYear(year);
		return periods.get(0);

	}

	@Override
	public I_C_Period retrieveLastPeriodOfTheYear(I_C_Year year)
	{
		final List<I_C_Period> periods = getPeriodsOfYear(year);
		return periods.get(periods.size() - 1);
	}

	private List<I_C_Period> getPeriodsOfYear(final I_C_Year year)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(year);

		List<I_C_Period> periods = db.getRecords(I_C_Period.class, new IQueryFilter<I_C_Period>()
		{

			@Override
			public boolean accept(I_C_Period pojo)
			{
				if (!pojo.getC_Year().equals(year))
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}

				if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID(ctx))
				{
					return false;
				}

				return true;
			}

		});

		Collections.sort(periods, new AccessorComparator<I_C_Period, Timestamp>(
				new ComparableComparator<Timestamp>(),
				new TypedAccessor<Timestamp>()
				{

					@Override
					public Timestamp getValue(Object o)
					{
						return ((I_C_Period)o).getStartDate();
					}
				}));

		return periods;
	}
}
