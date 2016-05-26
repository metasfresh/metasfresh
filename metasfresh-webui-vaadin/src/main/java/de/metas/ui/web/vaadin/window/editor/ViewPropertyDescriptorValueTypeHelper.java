package de.metas.ui.web.vaadin.window.editor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.compiere.util.DisplayType;

import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorValueType;

/*
 * #%L
 * metasfresh-webui-vaadin
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

public final class ViewPropertyDescriptorValueTypeHelper
{
	private ViewPropertyDescriptorValueTypeHelper()
	{
		super();
	}

	public static SimpleDateFormat getDateFormat(final PropertyDescriptorValueType valueType)
	{
		return DisplayType.getDateFormat(valueType.getDisplayType());
	}

	public static DecimalFormat getNumberFormat(PropertyDescriptorValueType valueType)
	{
		return DisplayType.getNumberFormat(valueType.getDisplayType());
	}
	
	public static final Class<?> getValueClass(final PropertyDescriptorValueType valueType)
	{
		return valueType == null ? null : valueType.getValueClass();
	}
	
	public static final boolean convertToBoolean(final Object value)
	{
		return DisplayType.toBoolean(value);
	}

}
