/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.math.BigDecimal;
import java.sql.Timestamp;

import de.metas.logging.LogManager;

/**
 * Evaluator source.
 *
 * To create {@link Evaluatee} instances, please use {@link Evaluatees}.
 *
 * @author Jorg Janke
 * @version $Id: Evaluatee.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public interface Evaluatee
{
	/**
	 * Get Variable Value
	 *
	 * @param variableName name
	 * @return value
	 */
	String get_ValueAsString(String variableName);

	/**
	 * Get variable value as integer.
	 *
	 * @param variableName
	 * @param defaultValue
	 * @return
	 * 		<ul>
	 *         <li>integer value
	 *         <li>or <code>defaultValue</code> in case no value was found
	 *         <li>or <code>defaultValue</code> in case the value was not parseable as integer
	 *         </ul>
	 */
	default Integer get_ValueAsInt(final String variableName, final Integer defaultValue)
	{
		final String valueStr = get_ValueAsString(variableName);
		return convertToInteger(variableName, valueStr, defaultValue);
	}

	/**
	 * @return default value or null; never throws exception
	 */
	static Integer convertToInteger(final String variableName, String valueStr, final Integer defaultValue)
	{
		if (Env.isPropertyValueNull(variableName, valueStr))
		{
			return defaultValue;
		}

		valueStr = valueStr.trim();
		if (valueStr.isEmpty())
		{
			return defaultValue;
		}

		try
		{
			return Integer.parseInt(valueStr);
		}
		catch (final Exception e)
		{
			LogManager.getLogger(Evaluatee.class).warn("Failed converting {}={} to Integer. Returning default value: {}", variableName, valueStr, defaultValue, e);
			return defaultValue;
		}
	}

	/**
	 * Get variable value as boolean.
	 *
	 * @param variableName
	 * @param defaultValue
	 * @return
	 * 		<ul>
	 *         <li>boolean value
	 *         <li>or <code>defaultValue</code> in case no value was found
	 *         <li>or <code>defaultValue</code> in case the value was not parseable as boolean
	 *         </ul>
	 */
	default Boolean get_ValueAsBoolean(final String variableName, final Boolean defaultValue)
	{
		final String valueStr = get_ValueAsString(variableName);
		return DisplayType.toBoolean(valueStr, defaultValue);
	}

	default BigDecimal get_ValueAsBigDecimal(final String variableName, final BigDecimal defaultValue)
	{
		final String valueStr = get_ValueAsString(variableName);
		return convertToBigDecimal(variableName, valueStr, defaultValue);
	}

	/**
	 * @return default value or null; never throws exception
	 */
	static BigDecimal convertToBigDecimal(final String variableName, String valueStr, final BigDecimal defaultValue)
	{
		if (Env.isPropertyValueNull(variableName, valueStr))
		{
			return defaultValue;
		}

		valueStr = valueStr.trim();
		if (valueStr.isEmpty())
		{
			return defaultValue;
		}

		try
		{
			return new BigDecimal(valueStr);
		}
		catch (final Exception e)
		{
			LogManager.getLogger(Evaluatee.class).warn("Failed converting {}={} to BigDecimal. Returning default value: {}", variableName, valueStr, defaultValue, e);
			return defaultValue;
		}
	}

	default java.util.Date get_ValueAsDate(final String variableName, final java.util.Date defaultValue)
	{
		final String valueStr = get_ValueAsString(variableName);
		return convertToDate(variableName, valueStr, defaultValue);
	}

	static java.util.Date convertToDate(final String variableName, final String valueStr, final java.util.Date defaultValue)
	{
		if (valueStr == null || valueStr.isEmpty())
		{
			return defaultValue;
		}

		try
		{
			final Timestamp value = Env.parseTimestamp(valueStr);
			return value == null ? defaultValue : value;
		}
		catch (final Exception e)
		{
			LogManager.getLogger(Evaluatee.class).warn("Failed converting {}={} to Date. Returning default value: {}", variableName, valueStr, defaultValue, e);
			return defaultValue;
		}
	}

}	// Evaluatee
