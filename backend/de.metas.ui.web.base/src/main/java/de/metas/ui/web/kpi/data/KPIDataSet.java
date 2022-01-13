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

package de.metas.ui.web.kpi.data;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

@Value
public class KPIDataSet
{
	String name;
	String unit;
	ImmutableList<KPIDataSetValuesMap> values;

	public static KPIDataSetBuilder builder(@NonNull final String name)
	{
		return new KPIDataSetBuilder().name(name);
	}

	private KPIDataSet(final KPIDataSetBuilder builder)
	{
		this.name = builder.name;
		this.unit = builder.unit;
		this.values = builder.valuesByKey
				.values()
				.stream()
				.map(KPIDataSetValuesMap.KPIDataSetValuesMapBuilder::build)
				.collect(ImmutableList.toImmutableList());
	}

	//
	//
	//
	//
	//
	public static class KPIDataSetBuilder
	{
		private String name;
		@Getter
		@Nullable private String unit;
		private final Map<KPIDataSetValuesAggregationKey, KPIDataSetValuesMap.KPIDataSetValuesMapBuilder> valuesByKey = new LinkedHashMap<>();

		public KPIDataSet build()
		{
			return new KPIDataSet(this);
		}

		public KPIDataSetBuilder name(final String name)
		{
			this.name = name;
			return this;
		}

		public KPIDataSetBuilder unit(@Nullable final String unit)
		{
			this.unit = unit;
			return this;
		}

		public KPIDataSetValuesMap.KPIDataSetValuesMapBuilder dataSetValue(final KPIDataSetValuesAggregationKey dataSetValueKey)
		{
			return valuesByKey.computeIfAbsent(dataSetValueKey, KPIDataSetValuesMap::builder);
		}

		public void putValue(@NonNull final KPIDataSetValuesAggregationKey dataSetValueKey, @NonNull final String fieldName, @NonNull final KPIDataValue value)
		{
			dataSetValue(dataSetValueKey).put(fieldName, value);
		}

		public void putValueIfAbsent(@NonNull final KPIDataSetValuesAggregationKey dataSetValueKey, @NonNull final String fieldName, @NonNull final KPIDataValue value)
		{
			dataSetValue(dataSetValueKey).putIfAbsent(fieldName, value);
		}

	}
}
