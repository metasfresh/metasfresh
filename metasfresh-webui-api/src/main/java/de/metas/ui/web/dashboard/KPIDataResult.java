package de.metas.ui.web.dashboard;

import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

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
public class KPIDataResult
{
	public static Builder builder()
	{
		return new Builder();
	}

	@JsonProperty("took")
	private final String took;

	@JsonProperty("itemId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer itemId;

	@JsonProperty("range")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final TimeRange range;

	@JsonProperty("datasets")
	private final List<KPIDataSet> datasets;

	private KPIDataResult(final Builder builder)
	{
		took = builder.took;

		range = builder.range;
		datasets = ImmutableList.copyOf(builder.datasets.values());
	}

	public List<KPIDataSet> getData()
	{
		return datasets;
	}

	public KPIDataResult setItemId(final int itemId)
	{
		this.itemId = itemId;
		return this;
	}

	public static final class Builder
	{
		private final LinkedHashMap<String, KPIDataSet> datasets = new LinkedHashMap<>();
		private TimeRange range;

		private String took;

		private Builder()
		{
			super();
		}

		public KPIDataResult build()
		{
			return new KPIDataResult(this);
		}

		public KPIDataSet dataSet(final String name)
		{
			return datasets.computeIfAbsent(name, KPIDataSet::new);
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

		public Builder setTook(final Stopwatch took)
		{
			this.took = took.toString();
			return this;
		}

		public Builder putValue(final String dataSetName, final Object dataSetValueKey, final String fieldName, final Object jsonValue)
		{
			dataSet(dataSetName)
					.putValue(dataSetValueKey, fieldName, jsonValue);
			return this;
		}
		
		public Builder putValueIfAbsent(final String dataSetName, final Object dataSetValueKey, final String fieldName, final Object jsonValue)
		{
			dataSet(dataSetName)
					.putValueIfAbsent(dataSetValueKey, fieldName, jsonValue);
			return this;
		}


	}
}
