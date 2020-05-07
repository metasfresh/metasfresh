package de.metas.ui.web.window.datatypes;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

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

/**
 * Describes how a panel of elements shall be rendered.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public enum PanelLayoutType
{
	/** Render the elements in a regular panel (default) */
	Panel("panel"),
	/** Render the single element as an full screen overlay having displayed only that field and everything behind it's blurry */
	SingleOverlayField("singleOverlayField");

	private final String json;

	private PanelLayoutType(final String json)
	{
		this.json = json;
	}

	@JsonValue
	public String toJson()
	{
		return json;
	}

	@JsonCreator
	public static PanelLayoutType fromNullableJson(final String json)
	{
		if (json == null)
		{
			return null;
		}

		final PanelLayoutType type = json2type.get(json);
		if (type == null)
		{
			throw new IllegalArgumentException("No " + PanelLayoutType.class + " found for json: " + json);
		}

		return type;
	}

	private static final ImmutableMap<String, PanelLayoutType> json2type = Maps.uniqueIndex(Arrays.asList(values()), PanelLayoutType::toJson);
}
