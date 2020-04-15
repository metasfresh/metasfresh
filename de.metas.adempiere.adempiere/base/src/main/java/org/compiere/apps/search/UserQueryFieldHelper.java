/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.compiere.apps.search;

import lombok.NonNull;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;

public class UserQueryFieldHelper
{
	private static transient final Logger logger = LoggerFactory.getLogger(UserQueryFieldHelper.class);

	@Nullable
	public static Object parseValueObjectByColumnDisplayType(@Nullable final Object valueObj, final int displayType, @NonNull final String columnName)
	{
		if (valueObj == null)
		{
			return null;
		}
		try
		{
			// Return Integer
			if (displayType == DisplayType.Integer || DisplayType.isID(displayType) && columnName.endsWith("_ID"))
			{
				if (valueObj instanceof Integer)
				{
					return valueObj;
				}
				return Integer.parseInt(valueObj.toString());
			}
			// Return BigDecimal
			else if (DisplayType.isNumeric(displayType))
			{
				if (valueObj instanceof BigDecimal)
				{
					return valueObj;
				}
				final DecimalFormat numberFormat = DisplayType.getNumberFormat(displayType);
				numberFormat.setParseBigDecimal(true);
				return numberFormat.parse(valueObj.toString());
			}
			// Return Timestamp
			else if (DisplayType.isDate(displayType))
			{
				if (valueObj instanceof java.util.Date)
				{
					return TimeUtil.asTimestamp((java.util.Date)valueObj);
				}
				long time;
				try
				{
					time = DisplayType.getDateFormat_JDBC().parse(valueObj.toString()).getTime();
					return new Timestamp(time);
				}
				catch (final Exception e)
				{
					logger.warn(valueObj + "(" + valueObj.getClass() + ")", e);
					time = DisplayType.getDateFormat(displayType).parse(valueObj.toString()).getTime();
				}
				return new Timestamp(time);
			}
			// Return Y/N for Boolean
			else if (valueObj instanceof Boolean)
			{
				return DisplayType.toBooleanString((Boolean)valueObj);
			}
		}
		catch (final Exception ex)
		{
			String error = ex.getLocalizedMessage();
			if (error == null || error.isEmpty())
			{
				error = ex.toString();
			}
			logger.warn("ValidationError: (" + columnName + " = " + valueObj + ") - " + error, ex);
			return null;
		}

		return valueObj;
	}
}
