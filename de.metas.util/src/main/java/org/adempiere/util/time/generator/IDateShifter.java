package org.adempiere.util.time.generator;

import java.time.LocalDate;

@FunctionalInterface
public interface IDateShifter
{
	/**
	 * Shift given date.
	 * 
	 * @param date
	 * @return can return
	 *         <ul>
	 *         <li>same date if there is no change
	 *         <li>shifted date if there was a shift
	 *         <li>null in case given date shall be excluded
	 *         </ul>
	 */
	public LocalDate shift(LocalDate date);
}
