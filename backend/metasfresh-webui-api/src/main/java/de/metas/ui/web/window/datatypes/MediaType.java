package de.metas.ui.web.window.datatypes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public enum MediaType
{
	SCREEN("screen"), TABLET("tablet"), PHONE("phone");

	private final String json;

	MediaType(final String json)
	{
		this.json = json;
	}

	@JsonValue
	public String toJson()
	{
		return json;
	}

	@JsonCreator
	public static final MediaType fromJson(final String json)
	{
		final MediaType type = json2type.get(json);
		if (type == null)
		{
			throw new NoSuchElementException("Invalid media type '" + json + "'."
					+ " Available media types are: " + Stream.of(values()).map(MediaType::toJson).collect(Collectors.joining(", ")));
		}
		return type;
	}

	public static final Set<MediaType> fromNullableCommaSeparatedString(final String str)
	{
		if (str == null || str.isEmpty())
		{
			return ImmutableSet.of();
		}
		final List<String> parts = Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(str);
		return parts.stream()
				.map(MediaType::fromJson)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final ImmutableMap<String, MediaType> json2type = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(MediaType::toJson, Function.identity()));
}
