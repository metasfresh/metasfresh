package de.metas.ui.web.view.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.ViewHeaderProperties;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class JSONViewHeaderProperties
{
	public static JSONViewHeaderProperties of(ViewHeaderProperties properties, final String adLanguage)
	{
		if (properties.getEntries().isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONViewHeaderProperty> jsonEntries = properties.getEntries()
				.stream()
				.map(entry -> JSONViewHeaderProperty.of(entry, adLanguage))
				.collect(ImmutableList.toImmutableList());

		return new JSONViewHeaderProperties(jsonEntries);
	}

	private static JSONViewHeaderProperties EMPTY = new JSONViewHeaderProperties(ImmutableList.of());

	@JsonProperty("entries")
	private ImmutableList<JSONViewHeaderProperty> entries;

	private JSONViewHeaderProperties(
			@JsonProperty("entries") final List<JSONViewHeaderProperty> entries)
	{
		this.entries = entries != null ? ImmutableList.copyOf(entries) : ImmutableList.of();
	}

}
