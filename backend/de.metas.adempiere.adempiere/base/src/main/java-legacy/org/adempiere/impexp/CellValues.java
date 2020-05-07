package org.adempiere.impexp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.compiere.util.DisplayType;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@UtilityClass
public class CellValues
{
	public static final int extractDisplayTypeFromValue(final Object value)
	{
		if (value == null)
		{
			;
		}
		else if (value instanceof Timestamp)
		{
			return DisplayType.Date;
			// TODO: handle DateTime
		}
		else if (value instanceof Number)
		{
			if (value instanceof Integer)
			{
				return DisplayType.Integer;
			}
			else
			{
				return DisplayType.Number;
			}
		}
		else if (value instanceof Boolean)
		{
			return DisplayType.YesNo;
		}
		else
		{
			return DisplayType.String;
		}
		return -1;
	}

	public static final CellValue toCellValue(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		final int displayType = extractDisplayTypeFromValue(value);
		return toCellValue(value, displayType);
	}

	public static final CellValue toCellValue(final Object value, final int displayType)
	{
		if (value == null)
		{
			return null;
		}

		if (DisplayType.isDate(displayType))
		{
			final Date date = (Date)value;
			return CellValue.ofDate(date);
		}
		else if (displayType == DisplayType.Integer)
		{
			final int valueInt;
			if (value instanceof Number)
			{
				valueInt = ((Number)value).intValue();
			}
			else
			{
				valueInt = new BigDecimal(value.toString()).intValueExact();
			}
			return CellValue.ofNumber(valueInt);
		}
		else if (DisplayType.isNumeric(displayType))
		{
			final Number number;
			if (value instanceof Number)
			{
				number = (Number)value;
			}
			else
			{
				number = new BigDecimal(value.toString());
			}
			return CellValue.ofNumber(number);
		}
		else if (displayType == DisplayType.YesNo)
		{
			final boolean bool = DisplayType.toBoolean(value);
			return CellValue.ofBoolean(bool);
		}
		else
		{
			return CellValue.ofString(value.toString());
		}
	}
}
