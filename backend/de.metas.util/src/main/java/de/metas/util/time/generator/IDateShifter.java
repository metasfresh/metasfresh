package de.metas.util.time.generator;

import java.time.LocalDateTime;

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
	public LocalDateTime shiftForward(LocalDateTime date);

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
	public LocalDateTime shiftBackward(LocalDateTime date);
}
