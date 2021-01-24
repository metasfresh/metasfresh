/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.view.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.view.ViewHeaderProperties;
import lombok.NonNull;

import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONViewHeaderProperties
{
	public static JSONViewHeaderProperties of(@NonNull final ViewHeaderProperties properties, final String adLanguage)
	{
		if (properties.getGroups().isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONViewHeaderPropertiesGroup> jsonEntries = properties.getGroups()
				.stream()
				.map(group -> JSONViewHeaderPropertiesGroup.of(group, adLanguage))
				.collect(ImmutableList.toImmutableList());

		return new JSONViewHeaderProperties(jsonEntries);
	}

	private static JSONViewHeaderProperties EMPTY = new JSONViewHeaderProperties(ImmutableList.of());

	@JsonProperty("groups")
	private ImmutableList<JSONViewHeaderPropertiesGroup> groups;

	private JSONViewHeaderProperties(
			@JsonProperty("groups") final List<JSONViewHeaderPropertiesGroup> groups)
	{
		this.groups = groups != null ? ImmutableList.copyOf(groups) : ImmutableList.of();
	}

}
