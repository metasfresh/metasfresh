package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
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

@ApiModel(value = "lookup-value", description = "pair of { field : value}")
@SuppressWarnings("serial")
public final class JSONLookupValue implements Serializable
{
	public static final JSONLookupValue of(final String key, final String value)
	{
		return new JSONLookupValue(ImmutableMap.of(key, value));
	}

	public static final List<JSONLookupValue> ofLookupValuesList(final List<LookupValue> lookupValues)
	{
		if (lookupValues == null || lookupValues.isEmpty())
		{
			return ImmutableList.of();
		}

		return lookupValues.stream()
				.map(JSONLookupValue::ofLookupValue)
				.collect(GuavaCollectors.toImmutableList());
	}

	public static final JSONLookupValue ofLookupValue(final LookupValue lookupValue)
	{
		return JSONLookupValue.of(lookupValue.getIdAsString(), lookupValue.getDisplayName());
	}

	public static final IntegerLookupValue integerLookupValueFromJsonMap(final Map<String, String> map)
	{
		final Set<Map.Entry<String, String>> entrySet = map.entrySet();
		if (entrySet.size() != 1)
		{
			throw new IllegalArgumentException("Invalid JSON lookup value: map=" + map);
		}
		final Map.Entry<String, String> e = entrySet.iterator().next();

		String idStr = e.getKey();
		if (idStr == null)
		{
			return null;
		}
		idStr = idStr.trim();
		if (idStr.isEmpty())
		{
			return null;
		}

		final int id = Integer.parseInt(idStr);
		final String name = e.getValue();

		return IntegerLookupValue.of(id, name);
	}

	public static final StringLookupValue stringLookupValueFromJsonMap(final Map<String, String> map)
	{
		final Set<Map.Entry<String, String>> entrySet = map.entrySet();
		if (entrySet.size() != 1)
		{
			throw new IllegalArgumentException("Invalid JSON lookup value: map=" + map);
		}
		final Map.Entry<String, String> e = entrySet.iterator().next();

		final String id = e.getKey();
		final String name = e.getValue();

		return StringLookupValue.of(id, name);
	}

	private final Map<String, String> map;

	private JSONLookupValue(final ImmutableMap<String, String> map)
	{
		this.map = map;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(map)
				.toString();
	}

	@JsonAnyGetter
	public Map<String, String> getMap()
	{
		return map;
	}

}
