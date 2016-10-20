package de.metas.ui.web.window.descriptor;

import java.util.Set;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

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
	Text //
	, LongText //

	//
	// Dates
	, Date(LayoutAlign.Right) //
	, Time(LayoutAlign.Right) //
	, DateTime(LayoutAlign.Right) //

	// Numbers, Amounts, Prices
	, Integer(LayoutAlign.Right) //
	, Number(LayoutAlign.Right) //
	, Amount(LayoutAlign.Right) //
	, Quantity(LayoutAlign.Right) //
	, CostPrice(LayoutAlign.Right) //

	//
	// General Lookups
	, List //
	, Lookup //

	//
	// Special lookups
	, Address //
	, ProductAttributes //
	, Image //

	//
	// Checkboxes
	, YesNo(LayoutAlign.Center) //
	, Switch(LayoutAlign.Center) //

	//
	// Buttons
	, Button //
	, ActionButton //

	//
	;

	private static final Set<DocumentFieldWidgetType> TYPES_Date = Sets.immutableEnumSet(Date, Time, DateTime);
	private static final Set<DocumentFieldWidgetType> TYPES_Numeric = Sets.immutableEnumSet(Integer, Number, Amount, Quantity, CostPrice);
	private static final Set<DocumentFieldWidgetType> TYPES_WithRageFilteringSupport = Sets.immutableEnumSet(Iterables.concat(TYPES_Date, TYPES_Numeric));

	private final LayoutAlign gridAlign;

	private DocumentFieldWidgetType(final LayoutAlign gridAlign)
	{
		this.gridAlign = gridAlign;
	}

	/** Default constructor */
	private DocumentFieldWidgetType()
	{
		gridAlign = LayoutAlign.Left;
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
}
