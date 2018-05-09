package de.metas.adempiere.service;

import java.time.LocalDate;

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
	 * 
	 * @param date
	 * @return next business day
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
	 * 
	 * @param date
	 * @return next business day
	 */
	default LocalDate getPreviousBusinessDay(@NonNull final LocalDate date)
	{
		LocalDate currentDate = date;
		while (!isBusinessDay(currentDate))
		{
			currentDate = currentDate.minusDays(1);
		}
		return currentDate;
	}
}
