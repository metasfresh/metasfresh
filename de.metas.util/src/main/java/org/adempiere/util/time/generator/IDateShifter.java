package org.adempiere.util.time.generator;

import java.time.LocalDate;

public interface IDateShifter
{
	/**
	 * Shift forward (in the future) given date.
	 * 
	 * @param date
	 * @return can return
	 *         <ul>
	 *         <li>same date if there is no change
	 *         <li>shifted date if there was a shift
	 *         <li>null in case given date shall be excluded
	 *         </ul>
	 */
	public LocalDate shiftForward(LocalDate date);

	/**
	 * Shift backward (in the past) given date.
	 * 
	 * @param date
	 * @return can return
	 *         <ul>
	 *         <li>same date if there is no change
	 *         <li>shifted date if there was a shift
	 *         <li>null in case given date shall be excluded
	 *         </ul>
	 */
	public LocalDate shiftBackward(LocalDate date);
}
