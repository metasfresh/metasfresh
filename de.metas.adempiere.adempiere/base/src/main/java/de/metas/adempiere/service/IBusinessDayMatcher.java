package de.metas.adempiere.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

/**
 * Implementations of this interface are responsible of:
 * <ul>
 * <li>validating if a given date is a bussiness-day
 * <li>getting next business day
 * </ul>
 * 
 * @author tsa
 *
 */
public interface IBusinessDayMatcher
{
	/**
	 * Gets next business day.
	 * 
	 * If given date is a business day then that date will be returned.
	 * 
	 * @param date
	 * @return next business day
	 */
	LocalDate getNextBusinessDay(final LocalDate date);

	/**
	 * 
	 * @param date
	 * @return true if given date is a business day
	 */
	boolean isBusinessDay(final LocalDate date);

	/**
	 * 
	 * @return days of week which will be considered non-business day
	 */
	Set<DayOfWeek> getWeekendDays();

	/**
	 * Sets week days which shall be considered as non-business day.
	 * 
	 * @param daysOfWeek
	 */
	IBusinessDayMatcher changeWeekendDays(Set<DayOfWeek> weekendDays);

	default IBusinessDayMatcher removeWeekendDays(@NonNull final Set<DayOfWeek> weekendDaysToRemove)
	{
		return changeWeekendDays(getWeekendDays()
				.stream()
				.filter(weekendDay -> !weekendDaysToRemove.contains(weekendDay))
				.collect(ImmutableSet.toImmutableSet()));
	}

}
