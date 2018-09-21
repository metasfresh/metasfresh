package de.metas.ui.web.document.filter;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;

import de.metas.util.GuavaCollectors;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

/** How to render it when the filter is inline (i.e. frequentUsed is true) */
public enum DocumentFilterInlineRenderMode
{
	BUTTON("button"), //
	INLINE_PARAMETERS("inline-parameters") //
	;

	private final String json;

	private DocumentFilterInlineRenderMode(final String json)
	{
		this.json = json;
	}

	@JsonValue
	public String toJson()
	{
		return json;
	}

	@JsonCreator
	public static DocumentFilterInlineRenderMode fromNullableJson(final String json)
	{
		if (json == null)
		{
			return null;
		}

		final DocumentFilterInlineRenderMode type = json2type.get(json);
		if (type == null)
		{
			throw new IllegalArgumentException("No " + DocumentFilterInlineRenderMode.class + " found for json: " + json);
		}

		return type;
	}

	private static final ImmutableMap<String, DocumentFilterInlineRenderMode> json2type = Stream.of(values())
			.map(type -> GuavaCollectors.entry(type.toJson(), type))
			.collect(GuavaCollectors.toImmutableMap());
}
