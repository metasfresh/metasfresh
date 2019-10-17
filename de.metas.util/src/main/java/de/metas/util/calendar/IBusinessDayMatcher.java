package de.metas.util.calendar;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import de.metas.util.Check;
import lombok.NonNull;

/**
 * Implementations of this interface are responsible of:
 * <ul>
 * <li>validating if a given date is a bussiness-day
 * <li>getting next/previous business day
 * </ul>
 * 
 * @author tsa
 *
 */
@FunctionalInterface
public interface IBusinessDayMatcher
{
	/**
	 * 
	 * @param date
	 * @return true if given date is a business day
	 */
	boolean isBusinessDay(final LocalDate date);

	/**
	 * Gets next business day.
	 * 
	 * If given date is a business day then that date will be returned.
	 */
	default LocalDate getNextBusinessDay(@NonNull final LocalDate date)
	{
		LocalDate currentDate = date;
		while (!isBusinessDay(currentDate))
		{
			currentDate = currentDate.plusDays(1);
		}
		return currentDate;
	}

	/**
	 * Gets previous business day.
	 * 
	 * If given date is a business day then that date will be returned.
	 */
	default LocalDate getPreviousBusinessDay(@NonNull final LocalDate date)
	{
		final int targetWorkingDays = 0;
		return getPreviousBusinessDay(date, targetWorkingDays);
	}

	default LocalDateTime getPreviousBusinessDay(@NonNull final LocalDateTime dateTime, final int targetWorkingDays)
	{
		final LocalDate previousDate = getPreviousBusinessDay(dateTime.toLocalDate(), targetWorkingDays);
		return LocalDateTime.of(previousDate, dateTime.toLocalTime());
	}
	
	default ZonedDateTime getPreviousBusinessDay(@NonNull final ZonedDateTime dateTime, final int targetWorkingDays)
	{
		final LocalDate previousDate = getPreviousBusinessDay(dateTime.toLocalDate(), targetWorkingDays);
		return ZonedDateTime.of(previousDate, dateTime.toLocalTime(), dateTime.getZone());
	}


	default LocalDate getPreviousBusinessDay(@NonNull final LocalDate date, final int targetWorkingDays)
	{
		Check.assumeGreaterOrEqualToZero(targetWorkingDays, "targetWorkingDays");

		LocalDate currentDate = date;

		// Skip until we find the first business day
		while (!isBusinessDay(currentDate))
		{
			currentDate = currentDate.minusDays(1);
		}

		if (targetWorkingDays == 0)
		{
			return currentDate;
		}

		int workingDays = 0;
		while (true)
		{
			currentDate = currentDate.minusDays(1);
			final boolean isBusinessDay = isBusinessDay(currentDate);
			if (isBusinessDay)
			{
				workingDays++;
			}

			if (workingDays >= targetWorkingDays && isBusinessDay)
			{
				return currentDate;
			}
		}
	}
}
