/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.kpi.descriptor;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DisplayType;

public enum KPIFieldValueType
{
	String,
	URL,
	Number,
	Date,
	DateTime;

	public static KPIFieldValueType fromDisplayType(final int displayType)
	{
		if (DisplayType.isText(displayType))
		{
			if (displayType == DisplayType.URL)
			{
				return URL;
			}
			else
			{
				return String;
			}
		}
		else if (DisplayType.isNumeric(displayType))
		{
			return Number;
		}
		else if (displayType == DisplayType.Date)
		{
			return Date;
		}
		else if (displayType == DisplayType.DateTime)
		{
			return DateTime;
		}
		else
		{
			throw new AdempiereException("Display type not supported: " + DisplayType.getDescription(displayType));
		}
	}

	public String toJson() {return name();}

	public boolean isDate() {return this == Date || this == DateTime;}

	public boolean isURL() {return this == URL;}

	public boolean isStringStrict() {return this == String;}

	public boolean isString() {return isStringStrict() || isURL();}
}
