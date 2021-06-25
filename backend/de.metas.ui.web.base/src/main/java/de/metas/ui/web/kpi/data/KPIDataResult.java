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
import de.metas.common.util.time.SystemTime;
import de.metas.ui.web.kpi.TimeRange;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;

@Value
public class KPIDataResult
{
	public static Builder builder()
	{
		return new Builder();
	}

	TimeRange range;
	ImmutableList<KPIDataSet> datasets;

	@NonNull Instant createdTime = SystemTime.asInstant();
	@Nullable Duration took;

	private KPIDataResult(final Builder builder)
	{
		range = builder.range;
		datasets = builder.datasets
				.values()
				.stream()
				.map(KPIDataSet.KPIDataSetBuilder::build)
				.collect(ImmutableList.toImmutableList());

		took = builder.took;
	}

	public static final class Builder
	{
		private final LinkedHashMap<String, KPIDataSet.KPIDataSetBuilder> datasets = new LinkedHashMap<>();
		private TimeRange range;

		private Duration took;

		private Builder() { }

		public KPIDataResult build()
		{
			return new KPIDataResult(this);
		}

		public KPIDataSet.KPIDataSetBuilder dataSet(final String name)
		{
			return datasets.computeIfAbsent(name, KPIDataSet::builder);
		}

		public Builder setRange(final TimeRange timeRange)
		{
			range = timeRange;
			return this;
		}

		public TimeRange getRange()
		{
			return range;
		}

		public Builder setTook(@NonNull final Duration took)
		{
			this.took = took;
			return this;
		}

		public void putValue(
				@NonNull final String dataSetName,
				@NonNull final KPIDataSetValuesAggregationKey dataSetValueKey,
				@NonNull final String fieldName,
				@NonNull final KPIDataValue value)
		{
			dataSet(dataSetName).putValue(dataSetValueKey, fieldName, value);
		}

		public void putValueIfAbsent(
				@NonNull final String dataSetName,
				@NonNull final KPIDataSetValuesAggregationKey dataSetValueKey,
				@NonNull final String fieldName,
				@NonNull final KPIDataValue value)
		{
			dataSet(dataSetName).putValueIfAbsent(dataSetValueKey, fieldName, value);
		}

	}
}
