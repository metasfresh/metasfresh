package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.common.rest_api.v1.JsonErrorItem;
import de.metas.ui.web.window.datatypes.DebugProperties;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Schema(name = "lookup-values-list", description = "[ { field : value} ]")
@EqualsAndHashCode
public class JSONLookupValuesList
{
	public static JSONLookupValuesList empty() {return new JSONLookupValuesList();}

	public static JSONLookupValuesList ofLookupValuesList(
			@Nullable final LookupValuesList lookupValues,
			@NonNull final String adLanguage)
	{
		return ofLookupValuesList(lookupValues, adLanguage, false);
	}

	public static JSONLookupValuesList ofLookupValuesList(
			@Nullable final LookupValuesList lookupValues,
			@NonNull final String adLanguage,
			final boolean appendDescriptionToName)
	{
		if (lookupValues == null || lookupValues.isEmpty())
		{
			return empty();
		}

		final ImmutableList<JSONLookupValue> jsonValuesList = toListOfJSONLookupValues(lookupValues, adLanguage, appendDescriptionToName);
		final DebugProperties otherProperties = lookupValues.getDebugProperties();
		return new JSONLookupValuesList(jsonValuesList, otherProperties);
	}

	static ImmutableList<JSONLookupValue> toListOfJSONLookupValues(@NonNull final LookupValuesList lookupValues, @NonNull final String adLanguage, final boolean appendDescriptionToName)
	{
		Stream<JSONLookupValue> jsonValues = lookupValues.getValues()
				.stream()
				.map(lookupValue -> JSONLookupValue.ofLookupValue(lookupValue, adLanguage, appendDescriptionToName));

		if (!lookupValues.isOrdered())
		{
			jsonValues = jsonValues.sorted(Comparator.comparing(JSONLookupValue::getCaption));
		}

		return jsonValues.collect(ImmutableList.toImmutableList());
	}

	@JsonCreator
	private static JSONLookupValuesList ofJSONLookupValuesList(@JsonProperty("values") final List<JSONLookupValue> jsonLookupValues)
	{
		if (jsonLookupValues == null || jsonLookupValues.isEmpty())
		{
			return empty();
		}

		return new JSONLookupValuesList(ImmutableList.copyOf(jsonLookupValues), DebugProperties.EMPTY);
	}

	public static Collector<JSONLookupValue, ?, JSONLookupValuesList> collect()
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

	public static LookupValuesList lookupValuesListFromJsonMap(final Map<String, Object> map)
	{
		@SuppressWarnings("unchecked") final List<Object> values = (List<Object>)map.get("values");

		//
		// Corner case: the `map` it's just a single lookup value
		if (values == null
				&& map.get(JSONLookupValue.PROPERTY_Key) != null)
		{
			final StringLookupValue lookupValue = JSONLookupValue.stringLookupValueFromJsonMap(map);
			return LookupValuesList.fromNullable(lookupValue);
		}

		if (values == null || values.isEmpty())
		{
			return LookupValuesList.EMPTY;
		}

		return values.stream()
				.map(valueObj -> {
					@SuppressWarnings("unchecked") final Map<String, Object> valueAsMap = (Map<String, Object>)valueObj;
					return JSONLookupValue.stringLookupValueFromJsonMap(valueAsMap);
				})
				.collect(LookupValuesList.collect());
	}

	public static JSONLookupValuesList error(@NonNull JsonErrorItem error)
	{
		return new JSONLookupValuesList(error);
	}

	@JsonProperty("values")
	private final List<JSONLookupValue> values;

	@JsonProperty("defaultValue")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private String defaultId;

	@JsonProperty("error")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	@Nullable private final JsonErrorItem error;

	private LinkedHashMap<String, Object> otherProperties;

	@VisibleForTesting
	JSONLookupValuesList(final ImmutableList<JSONLookupValue> values, final DebugProperties otherProperties)
	{
		this.values = values;
		this.error = null;
		if (otherProperties != null && !otherProperties.isEmpty())
		{
			this.otherProperties = new LinkedHashMap<>(otherProperties.toMap());
		}

	}

	private JSONLookupValuesList()
	{
		this.values = ImmutableList.of();
		this.error = null;
	}

	private JSONLookupValuesList(@NonNull JsonErrorItem error)
	{
		this.values = ImmutableList.of();
		this.error = error;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("values", values)
				.add("error", error)
				.add("properties", otherProperties == null || otherProperties.isEmpty() ? null : otherProperties)
				.toString();
	}

	public List<JSONLookupValue> getValues()
	{
		return values;
	}

	@JsonAnyGetter
	public Map<String, Object> getOtherProperties()
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

	@JsonSetter
	public JSONLookupValuesList setDefaultId(final String defaultId)
	{
		this.defaultId = defaultId;
		return this;
	}

	public String getDefaultId()
	{
		return defaultId;
	}

	@Nullable
	public JsonErrorItem getError()
	{
		return error;
	}
}
