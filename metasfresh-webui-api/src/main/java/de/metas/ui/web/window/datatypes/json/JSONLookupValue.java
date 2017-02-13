package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.compiere.util.NamePair;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
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
	public static final JSONLookupValue of(final String key, final String name)
	{
		return new JSONLookupValue(key, name);
	}

	public static final JSONLookupValue of(final int key, final String name)
	{
		final String keyStr = String.valueOf(key);
		return new JSONLookupValue(keyStr, name);
	}

	public static final JSONLookupValue ofLookupValue(final LookupValue lookupValue)
	{
		return of(lookupValue.getIdAsString(), lookupValue.getDisplayName());
	}

	public static final JSONLookupValue ofNamePair(final NamePair namePair)
	{
		return of(namePair.getID(), namePair.getName());
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

	private Map<String, String> map;
	@JsonIgnore
	private String _key;
	@JsonIgnore
	private String _name;

	/**
	 * Used to deserialize JSON string
	 *
	 * @see #set(String, String)
	 */
	@JsonCreator
	private JSONLookupValue()
	{
		super();
		map = ImmutableMap.of();
		_key = null;
		_name = null;
	}

	private JSONLookupValue(final String key, final String name)
	{
		super();
		set(key, name);
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

	@JsonIgnore
	public Map.Entry<String, String> entry()
	{
		return map.entrySet().iterator().next();
	}

	@JsonAnySetter
	public void set(final String key, final String name)
	{
		final String nameNorm = name == null ? "" : name;
		_key = key;
		_name = nameNorm;
		map = ImmutableMap.of(key, nameNorm);
	}

	@JsonIgnore
	public String getKey()
	{
		return _key;
	}

	@JsonIgnore
	public String getName()
	{
		return _name;
	}
}
