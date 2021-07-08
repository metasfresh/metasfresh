package de.metas.ui.web.window.descriptor;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import de.metas.util.Check;
import org.compiere.model.X_AD_UI_Element;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import de.metas.util.GuavaCollectors;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public enum WidgetSize
{
	Small("S", X_AD_UI_Element.WIDGETSIZE_Small), //
	Medium("M", X_AD_UI_Element.WIDGETSIZE_Medium), //
	Large("L", X_AD_UI_Element.WIDGETSIZE_Large), //
	ExtraLarge("XL", X_AD_UI_Element.WIDGETSIZE_ExtraLarge), //
	XXL("XXL", X_AD_UI_Element.WIDGETSIZE_XXL), //
	Default(null, null) //
	;

	private final String json;
	private final String adRefListValue;

	private WidgetSize(final String json, final String adRefListValue)
	{
		this.json = json;
		this.adRefListValue = adRefListValue;
	}

	@JsonValue
	public String toJson()
	{
		return json;
	}

	@JsonCreator
	@Nullable
	public static WidgetSize fromNullableADRefListValue(@Nullable final String adRefListValue)
	{
		if(Check.isBlank(adRefListValue))
		{
			return null;
		}

		final WidgetSize widgetSize = adRefListValue2widgetType.get(adRefListValue);
		if (widgetSize == null)
		{
			throw new NoSuchElementException(adRefListValue);
		}
		return widgetSize;
	}

	private String getAD_Ref_List_Value()
	{
		return adRefListValue;
	}

	private static final ImmutableMap<String, WidgetSize> adRefListValue2widgetType = Stream.of(values())
			.filter(value -> !value.equals(Default))
			.collect(GuavaCollectors.toImmutableMapByKey(WidgetSize::getAD_Ref_List_Value));
}
