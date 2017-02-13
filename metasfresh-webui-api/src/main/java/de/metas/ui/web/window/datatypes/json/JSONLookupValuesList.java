package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
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

@ApiModel(value = "lookup-values-list", description = "[ { field : value} ]")
@SuppressWarnings("serial")
public class JSONLookupValuesList implements Serializable
{
	public static final JSONLookupValuesList ofLookupValuesList(final LookupValuesList lookupValues)
	{
		if (lookupValues == null || lookupValues.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONLookupValue> values = lookupValues.getValues()
				.stream()
				.map(JSONLookupValue::ofLookupValue)
				.collect(GuavaCollectors.toImmutableList());

		final Map<String, String> otherProperties = lookupValues.getDebugProperties();

		return new JSONLookupValuesList(values, otherProperties);
	}

	@JsonCreator
	private static final JSONLookupValuesList ofJSONLookupValuesList(@JsonProperty("values") final List<JSONLookupValue> jsonLookupValues)
	{
		if (jsonLookupValues == null || jsonLookupValues.isEmpty())
		{
			return EMPTY;
		}

		return new JSONLookupValuesList(ImmutableList.copyOf(jsonLookupValues), ImmutableMap.of());
	}

	public static final Collector<JSONLookupValue, ?, JSONLookupValuesList> collect()
	{
		final Supplier<List<JSONLookupValue>> supplier = ArrayList::new;
		final BiConsumer<List<JSONLookupValue>, JSONLookupValue> accumulator = List::add;
		final BinaryOperator<List<JSONLookupValue>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<List<JSONLookupValue>, JSONLookupValuesList> finisher = JSONLookupValuesList::ofJSONLookupValuesList;
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private static final JSONLookupValuesList EMPTY = new JSONLookupValuesList();

	@JsonProperty("values")
	private final List<JSONLookupValue> values;

	@JsonProperty("defaultValue")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private String defaultValue;

	private LinkedHashMap<String, String> otherProperties;

	private JSONLookupValuesList(final ImmutableList<JSONLookupValue> values, final Map<String, String> otherProperties)
	{
		super();
		this.values = values;
		if (otherProperties != null && !otherProperties.isEmpty())
		{
			this.otherProperties = new LinkedHashMap<>(otherProperties);
		}
	}

	private JSONLookupValuesList()
	{
		super();
		values = ImmutableList.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("values", values)
				.add("properties", otherProperties.isEmpty() ? null : otherProperties)
				.toString();
	}

	public List<JSONLookupValue> getValues()
	{
		return values;
	}

	@JsonAnyGetter
	public Map<String, String> getOtherProperties()
	{
		return otherProperties == null ? ImmutableMap.of() : otherProperties;
	}

	@JsonAnySetter
	public void putOtherProperty(final String name, final String jsonValue)
	{
		if (otherProperties == null)
		{
			otherProperties = new LinkedHashMap<>();
		}
		otherProperties.put(name, jsonValue);
	}

	public JSONLookupValuesList setDefaultValue(final JSONLookupValue defaultValue)
	{
		this.defaultValue = defaultValue == null ? null : defaultValue.getKey();
		return this;
	}

	public JSONLookupValuesList setDefaultValue(final LookupValue defaultValue)
	{
		this.defaultValue = defaultValue == null ? null : defaultValue.getIdAsString();
		return this;
	}

	@JsonSetter
	public JSONLookupValuesList setDefaultValue(final String defaultValue)
	{
		this.defaultValue = defaultValue;
		return this;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}
}
