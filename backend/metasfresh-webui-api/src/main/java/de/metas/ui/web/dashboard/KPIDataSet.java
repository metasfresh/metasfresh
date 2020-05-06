package de.metas.ui.web.dashboard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui-api
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class KPIDataSet
{
	@JsonProperty("name")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String name;

	@JsonProperty("unit")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String unit;

	@JsonProperty("values")
	private final List<KPIDataSetValue> values = new ArrayList<>();

	private final transient Map<Object, KPIDataSetValue> _valuesByKey = new LinkedHashMap<>();

	KPIDataSet(final String name)
	{
		super();
		this.name = name;
		unit = null;
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("name", name)
				.add("unit", unit)
				.add("values", values)
				.toString();
	}

	public String getName()
	{
		return name;
	}

	private final KPIDataSetValue dataSetValue(final Object dataSetValueKey)
	{
		return _valuesByKey.computeIfAbsent(dataSetValueKey, k -> {
			final KPIDataSetValue value = new KPIDataSetValue(dataSetValueKey);

			values.add(value);
			return value;
		});
	}

	public void putValue(final Object dataSetValueKey, final String fieldName, final Object jsonValue)
	{
		dataSetValue(dataSetValueKey).put(fieldName, jsonValue);
	}

	public void putValueIfAbsent(final Object dataSetValueKey, final String fieldName, final Object jsonValue)
	{
		dataSetValue(dataSetValueKey).putIfAbsent(fieldName, jsonValue);
	}

}
