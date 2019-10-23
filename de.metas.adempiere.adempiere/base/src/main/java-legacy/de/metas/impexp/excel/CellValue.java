package de.metas.impexp.excel;

import javax.annotation.concurrent.Immutable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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

/**
 * Cell value returned by {@link AbstractExcelExporter#getValueAt(int, int)}.
 *
 * For more helpers please check {@link CellValues} utility class.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Immutable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public final class CellValue
{
	public static CellValue ofDate(final Object dateObj)
	{
		if (dateObj != null && !TimeUtil.isDateOrTimeObject(dateObj))
		{
			throw new AdempiereException("Invalid date value: " + dateObj + " (" + dateObj.getClass() + ")");
		}

		return new CellValue(CellValueType.Date, dateObj);
	}

	public static CellValue ofNumber(final Number number)
	{
		return new CellValue(CellValueType.Number, number);
	}

	public static CellValue ofBoolean(final boolean bool)
	{
		return new CellValue(CellValueType.Boolean, bool);
	}

	public static CellValue ofString(final String string)
	{
		return new CellValue(CellValueType.String, string);
	}

	@NonNull
	private CellValueType type;

	@NonNull
	@Getter(AccessLevel.NONE)
	private Object valueObj;

	public boolean isDate()
	{
		return type == CellValueType.Date;
	}

	public java.util.Date dateValue()
	{
		return TimeUtil.asDate(valueObj);
	}

	public boolean isNumber()
	{
		return type == CellValueType.Number;
	}

	public double doubleValue()
	{
		return ((Number)valueObj).doubleValue();
	}

	public boolean isBoolean()
	{
		return type == CellValueType.Boolean;
	}

	public boolean booleanValue()
	{
		return (boolean)valueObj;
	}

	public String stringValue()
	{
		return valueObj.toString();
	}
}
