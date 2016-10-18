package org.adempiere.util;

/*
 * #%L
 * de.metas.util
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

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Number Utils
 * 
 * @author tsa
 * 
 */
public final class NumberUtils
{
	private NumberUtils()
	{
		super();
	}

	/**
	 * Remove trailing zeros after decimal separator
	 * 
	 * @param bd
	 * @return <code>bd</code> without trailing zeros after separator; if argument is NULL then NULL will be retu
	 */
	public static BigDecimal stripTrailingDecimalZeros(final BigDecimal bd)
	{
		if (bd == null)
		{
			return null;
		}

		//
		// Remove all trailing zeros
		BigDecimal result = bd.stripTrailingZeros();

		// Fix very weird java 6 bug: stripTrailingZeros doesn't work on 0 itself
		// see http://stackoverflow.com/questions/5239137/clarification-on-behavior-of-bigdecimal-striptrailingzeroes
		// see http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6480539 => fixed in 8 (b100)
		if (result.signum() == 0)
		{
			result = BigDecimal.ZERO;
		}

		//
		// If after removing our scale is negative, we can safely set the scale to ZERO because we don't want to get rid of zeros before decimal point
		if (result.scale() < 0)
		{
			result = result.setScale(0);
		}

		return result;
	}

	/**
	 * Converts given <code>bd</code> to a big decimal which has at least <code>minScale</code> decimals.
	 * 
	 * If it has more decimals (and not trailing zeros) the value will not be changed.
	 * 
	 * @param bd
	 * @param minScale
	 */
	public static final BigDecimal setMinimumScale(final BigDecimal bd, final int minScale)
	{
		BigDecimal result = bd;
		if (result.scale() < minScale)
		{
			return result.setScale(minScale, RoundingMode.UNNECESSARY);
		}

		result = stripTrailingDecimalZeros(result);
		if (result.scale() < minScale)
		{
			return result.setScale(minScale, RoundingMode.UNNECESSARY);
		}

		return result;
	}

	/**
	 * Creates the error margin absolute value for given scale.
	 * 
	 * e.g.
	 * <ul>
	 * <li>for scale=0 it will return 0
	 * <li>for scale=1 it will return 0.1
	 * <li>for scale=2 it will return 0.01
	 * <li>for scale=-1 it will return 10
	 * <li>for scale=-2 it will return 100
	 * </ul>
	 * 
	 * @param scale
	 * @return error mergin (absolute value)
	 */
	public static final BigDecimal getErrorMarginForScale(final int scale)
	{
		if (scale == 0)
		{
			return BigDecimal.ZERO;
		}
		return BigDecimal.ONE.movePointLeft(scale);
	}

	/**
	 * Converts given <code>value</code> to BigDecimal.
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 * 		<ul>
	 *         <li>{@link BigDecimal} if the value is a BigDecimal or its string representation can be converted to BigDecimal
	 *         <li><code>defaultValue</code> if value is <code>null</code> or it's string representation cannot be converted to BigDecimal.
	 *         </ul>
	 */
	public static final BigDecimal asBigDecimal(final Object value, final BigDecimal defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		else if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else
		{
			final String valueStr = value.toString();
			try
			{
				return new BigDecimal(valueStr);
			}
			catch (NumberFormatException e)
			{
				System.err.println("Cannot convert " + value + " to BigDecimal.");
				e.printStackTrace();

				return defaultValue;
			}
		}
	}

	/**
	 * Converts given <code>value</code> to integer.
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 * 		<ul>
	 *         <li>integer value if the value is a integer or its string representation can be converted to integer
	 *         <li><code>defaultValue</code> if value is <code>null</code> or it's string representation cannot be converted to integer.
	 *         </ul>
	 */
	public static final int asInt(final Object value, final int defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else
		{
			try
			{
				final BigDecimal bd = new BigDecimal(value.toString());
				return bd.intValue();
			}
			catch (NumberFormatException e)
			{
				System.err.println("Cannot convert " + value + " to integer.");
				e.printStackTrace();

				return defaultValue;
			}
		}

	}

}
