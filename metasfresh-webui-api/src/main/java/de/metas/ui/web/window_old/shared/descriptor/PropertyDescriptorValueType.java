package de.metas.ui.web.window_old.shared.descriptor;

import org.compiere.util.DisplayType;

import de.metas.ui.web.window_old.shared.datatype.LookupValue;

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

public enum PropertyDescriptorValueType
{
	//
	Text(DisplayType.String, java.lang.String.class) //
	, TextLong(DisplayType.TextLong, java.lang.String.class) //
	//
	, Date(DisplayType.Date, java.util.Date.class) //
	, Time(DisplayType.Time, java.util.Date.class) //
	, DateTime(DisplayType.DateTime, java.util.Date.class) //
	//
	, Integer(DisplayType.Integer, java.lang.Integer.class) //
	, Amount(DisplayType.Amount, java.math.BigDecimal.class) //
	, Number(DisplayType.Number, java.math.BigDecimal.class) //
	, CostPrice(DisplayType.CostPrice, java.math.BigDecimal.class) //
	, Quantity(DisplayType.Quantity, java.math.BigDecimal.class) //
	//
	, List(DisplayType.List, LookupValue.class) //
	, SearchLookup(DisplayType.Search, LookupValue.class) //
	, Location(DisplayType.Location, LookupValue.class) //
	, PAttribute(DisplayType.PAttribute, LookupValue.class) //
	, ResourceAssignment(DisplayType.Assignment, LookupValue.class) //
	, Account(DisplayType.Search, LookupValue.class) //
	, ID(DisplayType.ID, Integer.class) //
	//
	, YesNo(DisplayType.YesNo, java.lang.Boolean.class) //
	, Button(DisplayType.Button, java.lang.String.class) //
	, Binary(DisplayType.Binary, byte[].class) //
	, Image(DisplayType.Image, java.lang.Integer.class) //
	, ComposedValue(-1, de.metas.ui.web.window_old.shared.datatype.ComposedValue.class) //
	;

	private final int displayType;
	private final Class<?> valueClass;

	PropertyDescriptorValueType(int displayType, final Class<?> valueClass)
	{
		this.displayType = displayType;
		this.valueClass = valueClass;
	}

	@Deprecated
	public int getDisplayType()
	{
		return displayType;
	}

	@Deprecated
	public Class<?> getValueClass()
	{
		return valueClass;
	}
	
	public boolean isLookup()
	{
		return LookupValue.class.isAssignableFrom(valueClass);
	}
}
