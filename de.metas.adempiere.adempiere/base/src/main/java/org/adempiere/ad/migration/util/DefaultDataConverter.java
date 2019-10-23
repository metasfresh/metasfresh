package org.adempiere.ad.migration.util;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Column;
import org.compiere.model.POInfoColumn;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;

public class DefaultDataConverter implements IDataConverter
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final transient DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * Convert string representation to appropriate object type for column
	 * 
	 * @param column
	 * @param valueStr
	 * @return
	 */
	@Override
	public Object stringToObject(final I_AD_Column column, final String valueStr)
	{
		if (valueStr == null)
		{
			if (column.isMandatory())
			{
				return VALUE_Unknown;
			}
			else
			{
				return null;
			}
		}

		final int displayType = column.getAD_Reference_ID();

		if (DisplayType.isText(displayType))
		{
			return valueStr;
		}
		else if (displayType == DisplayType.List)
		{
			return valueStr;
		}
		else if (DisplayType.isNumeric(displayType))
		{
			return new BigDecimal(valueStr);
		}
		else if (DisplayType.isID(displayType))
		{
			// TODO: hardcoded Table references with String Key
			if ("EntityType".equals(column.getColumnName()) || "AD_Language".equals(column.getColumnName()))
			{
				return valueStr;
			}
			return Integer.valueOf(valueStr);
		}
		else if (DisplayType.YesNo == displayType)
		{
			if ("true".equalsIgnoreCase(valueStr) || "Y".equals(valueStr))
			{
				return true;
			}
			else if ("false".equalsIgnoreCase(valueStr) || "N".equals(valueStr))
			{
				return false;
			}
			else
			{
				logger.warn("Invalid boolean value '" + valueStr + "' for column " + column + ". Returning false.");
				return false;
			}
		}
		else if (DisplayType.isDate(displayType))
		{
			try
			{
				Date value = dateTimeFormat.parse(valueStr);
				return new Timestamp(value.getTime()); // convert it to Timestamp, as required by persistence API
			}
			catch (ParseException e)
			{
				throw new AdempiereException(e);
			}
		}
		// YesNo Button - metas, 02662
		// e.g. AD_Column.IsEncrypted
		else if (DisplayType.Button == displayType
				&& column.getColumnName().startsWith("Is"))
		{
			final boolean valueBoolean = "true".equalsIgnoreCase(valueStr) || "Y".equalsIgnoreCase(valueStr);
			return valueBoolean ? "Y" : "N"; // column value expected to be string
		}
		//
		// Binary, Radio, RowID, Image not supported
		else
		{
			return null;
		}
	}

	@Override
	public String objectToString(I_AD_Column column, Object value)
	{
		final int displayType = column.getAD_Reference_ID();
		final boolean isMandatory = column.isMandatory();
		return objectToString(value, displayType, isMandatory);
	}

	@Override
	public String objectToString(POInfoColumn columnInfo, Object value)
	{
		return objectToString(value, columnInfo.getDisplayType(), columnInfo.isMandatory());
	}

	private String objectToString(final Object value, final int displayType, final boolean isMandatory)
	{
		if (value == null)
		{
			if (isMandatory)
			{
				logger.warn("Value is null even if is marked to be mandatory [Returning null]");
			}
			return null;
		}
		//
		final String valueStr;
		if (DisplayType.isDate(displayType))
		{
			valueStr = dateTimeFormat.format(value);
		}
		else if (DisplayType.YesNo == displayType)
		{
			if (value instanceof Boolean)
			{
				valueStr = ((Boolean)value) ? "true" : "false";
			}
			else
			{
				valueStr = "Y".equals(value) ? "true" : "false";
			}
			return valueStr;
		}
		else
		{
			valueStr = value.toString();
		}

		return valueStr;
	}
}
