/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.impexp.spreadsheet.excel;

import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UtilityClass
public class CellValues
{
	public static int extractDisplayTypeFromValue(final Object value)
	{
		if (value == null)
		{
			return -1;
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
		else if (value instanceof RepoIdAware)
		{
			return DisplayType.Integer;
		}
		else if (value instanceof Boolean)
		{
			return DisplayType.YesNo;
		}
		else
		{
			return DisplayType.String;
		}
	}

	/**
	 * @return a list that could contain {@code null} values
	 */
	public static ArrayList<CellValue> toCellValues(@NonNull final List<?> row)
	{
		final ArrayList<CellValue> result = new ArrayList<>();
		for (final Object value : row)
		{
			result.add(toCellValue(value));
		}
		return result;
	}

	public static CellValue toCellValue(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}

		final int displayType = extractDisplayTypeFromValue(value);
		return toCellValue(value, displayType);
	}

	public static CellValue toCellValue(final Object value, final int displayType)
	{
		if (value == null)
		{
			return null;
		}

		if (DisplayType.isDate(displayType))
		{
			final Date date = TimeUtil.asDate(value);
			return CellValue.ofDate(date);
		}
		else if (displayType == DisplayType.Integer)
		{
			final Integer valueInteger = NumberUtils.asInteger(value, null);
			return valueInteger != null
					? CellValue.ofNumber(valueInteger)
					: null;
		}
		else if (DisplayType.isNumeric(displayType))
		{
			if (value instanceof Number)
			{
				return CellValue.ofNumber((Number)value);
			}
			else
			{
				final BigDecimal number = NumberUtils.asBigDecimal(value, null);
				return number != null ? CellValue.ofNumber(number) : null;
			}
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
