package de.metas.ui.web.window.datatypes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.google.common.collect.ImmutableMap;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
@EqualsAndHashCode
public final class DebugProperties
{
	public static final DebugProperties EMPTY = new DebugProperties();

	private final ImmutableMap<String, Object> map;

	private DebugProperties(@NonNull final ImmutableMap<String, Object> map)
	{
		this.map = map;
	}

	private DebugProperties()
	{
		this.map = ImmutableMap.of();
	}

	public static DebugProperties ofNullableMap(final Map<String, ?> map)
	{
		if (map == null || map.isEmpty())
		{
			return EMPTY;
		}

		return new DebugProperties(ImmutableMap.copyOf(map));
	}

	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	public Map<String, Object> toMap()
	{
		return map;
	}

	public DebugProperties withProperty(@NonNull final String propertyName, final Object propertyValue)
	{
		final Object existingValue = map.get(propertyName);
		if (Objects.equals(propertyValue, existingValue))
		{
			return this;
		}

		final LinkedHashMap<String, Object> newMap = new LinkedHashMap<>(map);
		if (propertyValue == null)
		{
			newMap.remove(propertyName);
		}
		else
		{
			newMap.put(propertyName, propertyValue);
		}

		return ofNullableMap(newMap);
	}
}
