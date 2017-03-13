package de.metas.ui.web.window.datatypes.json;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel("widget-type")
public enum JSONLayoutWidgetType
{
	Text, LongText

	, Date, Time, DateTime

	, Integer, Number, Amount, Quantity, CostPrice

	//
	, List, Lookup, Address, ProductAttributes

	, YesNo, Switch

	, Image

	, Button, ActionButton, ProcessButton
	//
	;

	public static JSONLayoutWidgetType fromNullable(DocumentFieldWidgetType widgetType)
	{
		if (widgetType == null)
		{
			return null;
		}
		final JSONLayoutWidgetType jsonWidgetType = widgetType2json.get(widgetType);
		if (jsonWidgetType == null)
		{
			throw new IllegalArgumentException("Cannot convert " + widgetType + " to " + JSONLayoutWidgetType.class);
		}
		return jsonWidgetType;
	}

	private static final Map<DocumentFieldWidgetType, JSONLayoutWidgetType> widgetType2json = ImmutableMap.<DocumentFieldWidgetType, JSONLayoutWidgetType> builder()
			.put(DocumentFieldWidgetType.Text, JSONLayoutWidgetType.Text)
			.put(DocumentFieldWidgetType.LongText, JSONLayoutWidgetType.LongText)
			.put(DocumentFieldWidgetType.Date, JSONLayoutWidgetType.Date)
			.put(DocumentFieldWidgetType.Time, JSONLayoutWidgetType.Time)
			.put(DocumentFieldWidgetType.DateTime, JSONLayoutWidgetType.DateTime)
			.put(DocumentFieldWidgetType.Integer, JSONLayoutWidgetType.Integer)
			.put(DocumentFieldWidgetType.Number, JSONLayoutWidgetType.Number)
			.put(DocumentFieldWidgetType.Amount, JSONLayoutWidgetType.Amount)
			.put(DocumentFieldWidgetType.Quantity, JSONLayoutWidgetType.Quantity)
			.put(DocumentFieldWidgetType.CostPrice, JSONLayoutWidgetType.CostPrice)
			.put(DocumentFieldWidgetType.List, JSONLayoutWidgetType.List)
			.put(DocumentFieldWidgetType.Lookup, JSONLayoutWidgetType.Lookup)
			.put(DocumentFieldWidgetType.Address, JSONLayoutWidgetType.Address)
			.put(DocumentFieldWidgetType.ProductAttributes, JSONLayoutWidgetType.ProductAttributes)
			.put(DocumentFieldWidgetType.YesNo, JSONLayoutWidgetType.YesNo)
			.put(DocumentFieldWidgetType.Switch, JSONLayoutWidgetType.Switch)
			.put(DocumentFieldWidgetType.Image, JSONLayoutWidgetType.Image)
			.put(DocumentFieldWidgetType.Button, JSONLayoutWidgetType.Button)
			.put(DocumentFieldWidgetType.ActionButton, JSONLayoutWidgetType.ActionButton)
			.put(DocumentFieldWidgetType.ProcessButton, JSONLayoutWidgetType.ProcessButton)
			.build();

}
