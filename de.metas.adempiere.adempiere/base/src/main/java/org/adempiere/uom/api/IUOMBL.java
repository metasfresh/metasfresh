package org.adempiere.uom.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_UOM;

public interface IUOMBL extends ISingletonService
{

	/**
	 * Second
	 *
	 * @param uom
	 * @return true if UOM is second
	 */
	boolean isSecond(I_C_UOM uom);

	/**
	 * Minute
	 * 
	 * @param uom
	 * @return true if UOM is minute
	 */
	boolean isMinute(I_C_UOM uom);

	/**
	 * Hour
	 * 
	 * @param uom
	 * @return true if UOM is hour
	 */
	boolean isHour(I_C_UOM uom);

	/**
	 * Day
	 * 
	 * @param uom
	 * @return true if UOM is Day
	 */
	boolean isDay(I_C_UOM uom);

	/**
	 * 
	 * WorkDay
	 * 
	 * @param uom
	 * @return true if UOM is work day
	 */
	boolean isWorkDay(I_C_UOM uom);

	/**
	 * Week
	 * 
	 * @param uom
	 * @return true if UOM is Week
	 */
	boolean isWeek(I_C_UOM uom);

	/**
	 * Month
	 * 
	 * @param uom
	 * @return true if UOM is Month
	 */
	boolean isMonth(I_C_UOM uom);

	/**
	 * WorkMonth
	 * 
	 * @param uom
	 * @return true if UOM is Work Month
	 */
	boolean isWorkMonth(I_C_UOM uom);

	/**
	 * Year
	 * 
	 * @param uom
	 * @return true if UOM is year
	 */
	boolean isYear(I_C_UOM uom);

}
