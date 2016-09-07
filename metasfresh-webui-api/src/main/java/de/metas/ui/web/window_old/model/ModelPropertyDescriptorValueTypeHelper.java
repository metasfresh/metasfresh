package de.metas.ui.web.window_old.model;

import java.text.Format;

import org.compiere.util.DisplayType;

import de.metas.ui.web.window_old.shared.descriptor.PropertyDescriptorValueType;

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

@Deprecated
public final class ModelPropertyDescriptorValueTypeHelper
{
	private ModelPropertyDescriptorValueTypeHelper()
	{
		super();
	}

	public static final Format getFormat(final PropertyDescriptorValueType valueType)
	{
		if (valueType == null)
		{
			return null;
		}

		final int displayType = valueType.getDisplayType();
		if (DisplayType.isDate(displayType))
		{
			return DisplayType.getDateFormat(displayType);
		}
		else if (DisplayType.isNumeric(displayType))
		{
			return DisplayType.getNumberFormat(displayType);
		}
		else
		{
			return null;
		}
	}

	public static final Class<?> getValueClass(final PropertyDescriptorValueType valueType)
	{
		return valueType == null ? null : valueType.getValueClass();
	}

	public static final int getSqlDisplayType(final PropertyDescriptorValueType valueType)
	{
		return valueType == null ? -1 : valueType.getDisplayType();
	}
	
	public static final String convertToString(final Boolean value)
	{
		return DisplayType.toBooleanString(value);
	}
	
	public static final boolean convertToBoolean(final Object value)
	{
		return DisplayType.toBoolean(value);
	}
}
