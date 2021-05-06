/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.dashboard.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.ui.web.dashboard.KPIDataSetValuesAggregationKey;
import de.metas.ui.web.dashboard.KPIDataSetValuesMap;
import de.metas.ui.web.dashboard.KPIDataValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.Value;

import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class JsonKPIDataSetValue
{
	@JsonProperty("_key") Object _key;
	@JsonIgnore Map<String, Object> map;

	public static JsonKPIDataSetValue of(@NonNull final KPIDataSetValuesMap dataSetValue, @NonNull final KPIJsonOptions jsonOpts)
	{
		return new JsonKPIDataSetValue(dataSetValue.getKey(), dataSetValue.getValues(), jsonOpts);
	}

	public JsonKPIDataSetValue(
			@NonNull final KPIDataSetValuesAggregationKey key,
			@NonNull final Map<String, KPIDataValue> map,
			@NonNull final KPIJsonOptions jsonOpts)
	{
		_key = key.toJsonValue(jsonOpts);
		this.map = map.entrySet()
				.stream()
				.map(e -> toJson(e, jsonOpts))
				.collect(GuavaCollectors.toImmutableMap());
	}

	private static Map.Entry<String, Object> toJson(@NonNull final Map.Entry<String, KPIDataValue> entry, @NonNull final KPIJsonOptions jsonOpts)
	{
		return GuavaCollectors.entry(
				entry.getKey(),
				entry.getValue().toJsonValue(jsonOpts));
	}

	@JsonAnyGetter
	private Map<String, Object> getMap()
	{
		return map;
	}
}
