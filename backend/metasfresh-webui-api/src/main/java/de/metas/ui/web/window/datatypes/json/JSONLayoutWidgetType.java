package de.metas.ui.web.window.datatypes.json;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableBiMap;

import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import io.swagger.annotations.ApiModel;

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

@ApiModel("widget-type")
public enum JSONLayoutWidgetType
{
	Text, LongText, Link, Password,

	Date, Time, DateTime, ZonedDateTime, DateRange, Timestamp,

	Integer, Number, Amount, Quantity, CostPrice,

	List, MultiValuesList, Lookup, Labels, Address, ProductAttributes,

	YesNo, Switch,

	Image, Color, BinaryData,

	Button, ActionButton, ProcessButton, ZoomIntoButton,

	InlineTab;

	@Nullable
	public static JSONLayoutWidgetType fromNullable(@Nullable final DocumentFieldWidgetType widgetType)
	{
		if (widgetType == null)
		{
			return null;
		}

		final JSONLayoutWidgetType jsonWidgetType = widgetType2json.get(widgetType);
		if (jsonWidgetType == null)
		{
			throw new AdempiereException("Cannot convert " + widgetType + " to " + JSONLayoutWidgetType.class);
		}

		return jsonWidgetType;
	}

	private static final ImmutableBiMap<DocumentFieldWidgetType, JSONLayoutWidgetType> widgetType2json = ImmutableBiMap.<DocumentFieldWidgetType, JSONLayoutWidgetType> builder()
			.put(DocumentFieldWidgetType.Text, JSONLayoutWidgetType.Text)
			.put(DocumentFieldWidgetType.LongText, JSONLayoutWidgetType.LongText)
			.put(DocumentFieldWidgetType.URL, JSONLayoutWidgetType.Link)
			.put(DocumentFieldWidgetType.Password, JSONLayoutWidgetType.Password)
			.put(DocumentFieldWidgetType.LocalDate, JSONLayoutWidgetType.Date)
			.put(DocumentFieldWidgetType.LocalTime, JSONLayoutWidgetType.Time)
			.put(DocumentFieldWidgetType.ZonedDateTime, JSONLayoutWidgetType.ZonedDateTime)
			.put(DocumentFieldWidgetType.Timestamp, JSONLayoutWidgetType.Timestamp)
			.put(DocumentFieldWidgetType.DateRange, JSONLayoutWidgetType.DateRange)
			.put(DocumentFieldWidgetType.Integer, JSONLayoutWidgetType.Integer)
			.put(DocumentFieldWidgetType.Number, JSONLayoutWidgetType.Number)
			.put(DocumentFieldWidgetType.Amount, JSONLayoutWidgetType.Amount)
			.put(DocumentFieldWidgetType.Quantity, JSONLayoutWidgetType.Quantity)
			.put(DocumentFieldWidgetType.CostPrice, JSONLayoutWidgetType.CostPrice)
			.put(DocumentFieldWidgetType.List, JSONLayoutWidgetType.List)
			.put(DocumentFieldWidgetType.MultiValuesList, JSONLayoutWidgetType.MultiValuesList)
			.put(DocumentFieldWidgetType.Lookup, JSONLayoutWidgetType.Lookup)
			.put(DocumentFieldWidgetType.Labels, JSONLayoutWidgetType.Labels)
			.put(DocumentFieldWidgetType.Address, JSONLayoutWidgetType.Address)
			.put(DocumentFieldWidgetType.ProductAttributes, JSONLayoutWidgetType.ProductAttributes)
			.put(DocumentFieldWidgetType.YesNo, JSONLayoutWidgetType.YesNo)
			.put(DocumentFieldWidgetType.Switch, JSONLayoutWidgetType.Switch)
			.put(DocumentFieldWidgetType.Image, JSONLayoutWidgetType.Image)
			.put(DocumentFieldWidgetType.Color, JSONLayoutWidgetType.Color)
			.put(DocumentFieldWidgetType.BinaryData, JSONLayoutWidgetType.BinaryData)
			.put(DocumentFieldWidgetType.Button, JSONLayoutWidgetType.Button)
			.put(DocumentFieldWidgetType.ActionButton, JSONLayoutWidgetType.ActionButton)
			.put(DocumentFieldWidgetType.ProcessButton, JSONLayoutWidgetType.ProcessButton)
			.put(DocumentFieldWidgetType.ZoomIntoButton, JSONLayoutWidgetType.ZoomIntoButton)
			.put(DocumentFieldWidgetType.InlineTab, JSONLayoutWidgetType.InlineTab)
			.build();

}
