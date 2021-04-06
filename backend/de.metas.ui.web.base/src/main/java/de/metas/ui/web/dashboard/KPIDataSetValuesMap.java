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

package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;

@Value
public class KPIDataSetValuesMap
{
	public static KPIDataSetValuesMapBuilder builder(@NonNull final KPIDataSetValuesAggregationKey key)
	{
		return new KPIDataSetValuesMapBuilder().key(key);
	}

	KPIDataSetValuesAggregationKey key;
	ImmutableMap<String, KPIDataValue> values;

	private KPIDataSetValuesMap(final KPIDataSetValuesMapBuilder builder)
	{
		this.key = builder.key;
		this.values = ImmutableMap.copyOf(builder.values);
	}

	public static class KPIDataSetValuesMapBuilder
	{
		private KPIDataSetValuesAggregationKey key;
		private final HashMap<String, KPIDataValue> values = new HashMap<>();

		public KPIDataSetValuesMap build()
		{
			return new KPIDataSetValuesMap(this);
		}

		private KPIDataSetValuesMapBuilder key(@NonNull final KPIDataSetValuesAggregationKey key)
		{
			this.key = key;
			return this;
		}

		public KPIDataSetValuesMapBuilder put(@NonNull final String name, @NonNull final KPIDataValue value)
		{
			values.put(name, value);
			return this;
		}

		public void putIfAbsent(@NonNull final String name, @NonNull final KPIDataValue value)
		{
			values.putIfAbsent(name, value);
		}

	}
}
