package de.metas.ui.web.window.descriptor;

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
}
