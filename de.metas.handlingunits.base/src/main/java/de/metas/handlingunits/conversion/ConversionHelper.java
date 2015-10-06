package de.metas.handlingunits.conversion;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * Centralized helper for data conversion
 *
 * @author al
 */
public final class ConversionHelper
{
	private ConversionHelper()
	{
		super();
	}

	/**
	 * @param valueObj
	 * @return object converted to {@link BigDecimal}
	 * @throws IllegalArgumentException if value could not be converted to {@link BigDecimal}
	 */
	public static final BigDecimal toBigDecimal(final Object valueObj) throws IllegalArgumentException
	{
		if (valueObj == null)
		{
			return BigDecimal.ZERO;
		}
		else if (valueObj instanceof BigDecimal)
		{
			return (BigDecimal)valueObj;
		}
		else if (valueObj instanceof String)
		{
			return new BigDecimal((String)valueObj);
		}
		else if (valueObj instanceof Integer)
		{
			final int valueInt = (Integer)valueObj;
			return BigDecimal.valueOf(valueInt);
		}
		else if (valueObj instanceof Number)
		{
			final Number valueNum = (Number)valueObj;
			return new BigDecimal(valueNum.toString());
		}
		else
		{
			throw new IllegalArgumentException("Invalid BigDecimal value: " + valueObj + " (class=" + valueObj.getClass() + ")");
		}
	}
}
