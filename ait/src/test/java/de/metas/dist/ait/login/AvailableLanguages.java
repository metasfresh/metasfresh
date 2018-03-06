package de.metas.dist.ait.login;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.ImmutableMap;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * #%L
 * metasfresh-dist-ait
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

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
public class AvailableLanguages
{
	private final List<Map<String, String>> values;

	private final String defaultValue;

	public Map<String, String> getValuesMap()
	{
		final ImmutableMap.Builder<String, String> result = ImmutableMap.builder();

		for (final Map<String, String> value : values)
		{
			final String key = value.get("key");
			final String caption = value.get("caption");
			result.put(key, caption);
		}

		return result.build();
	}
}
