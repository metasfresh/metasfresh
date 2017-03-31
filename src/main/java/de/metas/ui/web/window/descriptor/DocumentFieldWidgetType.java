package de.metas.ui.web.window.descriptor;

import java.math.BigDecimal;
import java.util.Set;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;

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

public enum DocumentFieldWidgetType
{
	//
	// Text
	Text(LayoutAlign.Left, String.class) //
	, LongText(LayoutAlign.Left, String.class) //

	//
	// Dates
	, Date(LayoutAlign.Right, java.util.Date.class) //
	, Time(LayoutAlign.Right, java.util.Date.class) //
	, DateTime(LayoutAlign.Right, java.util.Date.class) //

	// Numbers, Amounts, Prices
	, Integer(LayoutAlign.Right, Integer.class) //
	, Number(LayoutAlign.Right, BigDecimal.class) //
	, Amount(LayoutAlign.Right, BigDecimal.class) //
	, Quantity(LayoutAlign.Right, BigDecimal.class) //
	, CostPrice(LayoutAlign.Right, BigDecimal.class) //

	//
	// General Lookups
	, List(LayoutAlign.Left, null) //
	, Lookup(LayoutAlign.Left, null) //

	//
	// Special lookups
	, Address(LayoutAlign.Left, IntegerLookupValue.class) //
	, ProductAttributes(LayoutAlign.Left, IntegerLookupValue.class) //
	, Image(LayoutAlign.Left, Integer.class) // TODO Image widgetType not yet supported

	//
	// Checkboxes
	, YesNo(LayoutAlign.Center, Boolean.class) //
	, Switch(LayoutAlign.Center, Boolean.class) //

	//
	// Buttons
	, Button(LayoutAlign.Left, null) //
	, ActionButton(LayoutAlign.Left, null) //
	, ProcessButton(LayoutAlign.Left, String.class) //

	//
	;

	private static final Set<DocumentFieldWidgetType> TYPES_Date = Sets.immutableEnumSet(Date, Time, DateTime);
	private static final Set<DocumentFieldWidgetType> TYPES_Numeric = Sets.immutableEnumSet(Integer, Number, Amount, Quantity, CostPrice);
	private static final Set<DocumentFieldWidgetType> TYPES_WithRageFilteringSupport = Sets.immutableEnumSet(Iterables.concat(TYPES_Date, TYPES_Numeric));

	private final LayoutAlign gridAlign;
	private final Class<?> valueClass;

	private DocumentFieldWidgetType(final LayoutAlign gridAlign, final Class<?> valueClass)
	{
		this.gridAlign = gridAlign;
		this.valueClass = valueClass;
	}

	public LayoutAlign getGridAlign()
	{
		return gridAlign;
	}

	public final Integer getStandardNumberPrecision()
	{
		// FIXME: hardcoded standard number precision

		switch (this)
		{
			case Integer:
				return 0;
			case CostPrice:
			case Amount:
				return 2;
			default:
				return null;
		}
	}

	public final boolean isRangeFilteringSupported()
	{
		return TYPES_WithRageFilteringSupport.contains(this);
	}

	public final boolean isDateOrTime()
	{
		return TYPES_Date.contains(this);
	}
	
	public final boolean isText()
	{
		return this == Text || this == LongText;
	}
	
	public final boolean isButton()
	{
		return this == Button || this == ActionButton || this == ProcessButton;
	}

	/**
	 * Same as {@link #getValueClassOrNull()} but it will throw exception in case there is no valueClass.
	 * 
	 * @return value class
	 */
	public Class<?> getValueClass()
	{
		if (valueClass == null)
		{
			throw new IllegalStateException("valueClass is unknown for " + this);
		}
		return valueClass;
	}

	/**
	 * Gets the standard value class to be used for this widget.
	 * In case there are multiple value classes which can be used for this widget, the method will return null.
	 * 
	 * @return value class or <code>null</code>
	 */
	public Class<?> getValueClassOrNull()
	{
		return valueClass;
	}
}
