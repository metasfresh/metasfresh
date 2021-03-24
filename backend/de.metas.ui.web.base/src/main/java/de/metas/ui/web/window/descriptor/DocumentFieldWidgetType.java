package de.metas.ui.web.window.descriptor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Set;

import org.compiere.util.DisplayType;

import com.google.common.collect.Sets;

import de.metas.ui.web.upload.WebuiImageId;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.DateRangeValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.Password;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public enum DocumentFieldWidgetType
{
	//
	// Text
	Text(LayoutAlign.Left, String.class, DisplayType.Text) //
	, LongText(LayoutAlign.Left, String.class, DisplayType.TextLong) //
	, URL(LayoutAlign.Left, String.class, DisplayType.URL) //
	, Password(LayoutAlign.Left, Password.class, DisplayType.Text) //

	//
	// Dates
	, LocalDate(LayoutAlign.Right, LocalDate.class, DisplayType.Date) //
	, LocalTime(LayoutAlign.Right, LocalTime.class, DisplayType.Time) //
	, ZonedDateTime(LayoutAlign.Right, ZonedDateTime.class, DisplayType.DateTime) //
	, Timestamp(LayoutAlign.Right, Instant.class, DisplayType.DateTime) //
	, DateRange(LayoutAlign.Left, DateRangeValue.class, -1) //

	// Numbers, Amounts, Prices
	, Integer(LayoutAlign.Right, Integer.class, DisplayType.Integer) //
	, Number(LayoutAlign.Right, BigDecimal.class, DisplayType.Number) //
	, Amount(LayoutAlign.Right, BigDecimal.class, DisplayType.Amount) //
	, Quantity(LayoutAlign.Right, BigDecimal.class, DisplayType.Quantity) //
	, CostPrice(LayoutAlign.Right, BigDecimal.class, DisplayType.CostPrice) //

	//
	// General Lookups
	, List(LayoutAlign.Left, null, DisplayType.Search) //
	, MultiValuesList(LayoutAlign.Left, LookupValuesList.class, -1) //
	, Lookup(LayoutAlign.Left, null, DisplayType.Search) //
	, Labels(LayoutAlign.Left, LookupValuesList.class, -1) //

	//
	// Special lookups
	, Address(LayoutAlign.Left, IntegerLookupValue.class, DisplayType.Location) //
	, ProductAttributes(LayoutAlign.Left, IntegerLookupValue.class, DisplayType.PAttribute) //
	, Image(LayoutAlign.Left, WebuiImageId.class, DisplayType.Image) //
	, Color(LayoutAlign.Center, ColorValue.class, DisplayType.Color) //
	, BinaryData(LayoutAlign.Left, byte[].class, DisplayType.Binary) // TODO: not supported, search for references and see

	//
	// Checkboxes
	, YesNo(LayoutAlign.Center, Boolean.class, DisplayType.YesNo) //
	, Switch(LayoutAlign.Center, Boolean.class, DisplayType.YesNo) //

	//
	// Buttons
	, Button(LayoutAlign.Left, null, DisplayType.Button) //
	, ActionButton(LayoutAlign.Left, null, DisplayType.Button) //
	, ProcessButton(LayoutAlign.Left, String.class, DisplayType.Button) //
	, ZoomIntoButton(LayoutAlign.Left, Integer.class, DisplayType.Button) //

	//
	, InlineTab(LayoutAlign.Left, null, -1) //

	//
	;

	private static final Set<DocumentFieldWidgetType> TYPES_ALL_DATES = Sets.immutableEnumSet(LocalDate, LocalTime, ZonedDateTime, Timestamp);
	private static final Set<DocumentFieldWidgetType> TYPES_ALL_NUMERIC = Sets.immutableEnumSet(Integer, Number, Amount, Quantity, CostPrice);

	private final LayoutAlign gridAlign;
	private final Class<?> valueClass;
	private final int displayType;

	private DocumentFieldWidgetType(final LayoutAlign gridAlign, final Class<?> valueClass, final int displayType)
	{
		this.gridAlign = gridAlign;
		this.valueClass = valueClass;
		this.displayType = displayType;
	}

	public int getDisplayType()
	{
		return displayType;
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

	public final boolean isDateOrTime()
	{
		return TYPES_ALL_DATES.contains(this);
	}

	public final boolean isDateWithTime()
	{
		return this == ZonedDateTime
				|| this == Timestamp;
	}

	public final boolean isNumeric()
	{
		return TYPES_ALL_NUMERIC.contains(this);
	}

	public final boolean isStrictText()
	{
		return this == Text || this == LongText;
	}

	public final boolean isText()
	{
		return isStrictText() || this == URL || this == Password;
	}

	public final boolean isButton()
	{
		return this == Button || this == ActionButton || this == ProcessButton || this == ZoomIntoButton;
	}

	public final boolean isLookup()
	{
		return this == Lookup || this == List;
	}

	public final boolean isSupportZoomInto()
	{
		return isLookup() || this == DocumentFieldWidgetType.ZoomIntoButton;
	}

	public final boolean isBoolean()
	{
		return this == YesNo || this == Switch;
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
