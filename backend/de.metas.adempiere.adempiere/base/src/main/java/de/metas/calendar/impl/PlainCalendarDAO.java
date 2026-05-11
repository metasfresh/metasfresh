
package de.metas.calendar.impl;

import de.metas.calendar.YearId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.util.Env;

import java.sql.Timestamp;
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
		final List<I_C_Period> periods = db.getRecords(I_C_Period.class, pojo -> {
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
		});
		periods.sort(new AccessorComparator<>(
				new ComparableComparator<>(),
				o -> ((I_C_Period)o).getStartDate()));

		return periods;
	}

	@Override
	protected List<I_C_Period> retrievePeriods(final Properties ctx, final int calendarId, final Timestamp begin, final Timestamp end, final String trxName)
	{
		Check.assume(begin != null, "Param 'begin' is not null");
		Check.assume(end != null, "Param 'end' is not null");

		final List<I_C_Year> years = db.getRecords(I_C_Year.class, pojo -> pojo.getC_Calendar_ID() == calendarId);

		final List<I_C_Period> periods = db.getRecords(I_C_Period.class, pojo -> {
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
		});

		periods.sort(new AccessorComparator<>(
				new ComparableComparator<>(),
				o -> ((I_C_Period)o).getStartDate()));

		return periods;
	}

	@Override
	public List<I_C_Year> retrieveYearsOfCalendar(final I_C_Calendar calendar)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(calendar);

		final List<I_C_Year> years = db.getRecords(I_C_Year.class, pojo -> {
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
		});

		years.sort(new AccessorComparator<>(
				new ComparableComparator<>(),
				o -> ((I_C_Year)o).getC_Year_ID()));

		return years;
	}

	@Override
	public I_C_Period retrieveFirstPeriodOfTheYear(@NonNull final YearId yearId)
	{
		final List<I_C_Period> periods = getPeriodsOfYear(yearId);
		return periods.get(0);

	}

	@Override
	public I_C_Period retrieveLastPeriodOfTheYear(@NonNull final YearId yearId)
	{
		final List<I_C_Period> periods = getPeriodsOfYear(yearId);
		return periods.get(periods.size() - 1);
	}

	private List<I_C_Period> getPeriodsOfYear(@NonNull final YearId yearId)
	{
		final List<I_C_Period> periods = db.getRecords(I_C_Period.class, pojo -> {
			if (pojo.getC_Year_ID() != yearId.getRepoId())
			{
				return false;
			}

			if (!pojo.isActive())
			{
				return false;
			}

			if (pojo.getAD_Client_ID() != 0 && pojo.getAD_Client_ID() != Env.getAD_Client_ID())
			{
				return false;
			}

			return true;
		});

		periods.sort(new AccessorComparator<>(
				new ComparableComparator<>(),
				o -> ((I_C_Period)o).getStartDate()));

		return periods;
	}
}