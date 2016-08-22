package de.metas.ui.web.window.datatypes;

import java.math.BigDecimal;
import java.util.Objects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class DataTypes
{
	/**
	 * Checks if given values are both null or equal. This method works like {@link Objects#equals(Object)} with following exceptions:
	 * <ul>
	 * <li>{@link BigDecimal}s are compared excluding the scale (so "1.00" equals with "1.0")
	 * </ul>
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static final <T> boolean equals(final T value1, final T value2)
	{
		if (value1 == value2)
		{
			return true;
		}
		else if (value1 == null)
		{
			return false;
		}
		//
		// Special case: BigDecimals => we consider them equal if their value is equal, EXCLUDING the scale
		else if ((value1 instanceof BigDecimal) && (value2 instanceof BigDecimal))
		{
			return ((BigDecimal)value1).compareTo((BigDecimal)value2) == 0;
		}
		else
		{
			return value1.equals(value2);
		}
	}

	private DataTypes()
	{
		super();
	}
}
