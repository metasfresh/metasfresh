package de.metas.ui.web.dashboard;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

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

	@JsonProperty("labels")
	private final Set<String> labels;

	@JsonProperty("datasets")
	private final List<KPIDataSet> datasets;

	@JsonProperty("fromMillis")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long fromMillis;
	@JsonProperty("toMillis")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long toMillis;
	
	
	private KPIDataResult(final Builder builder)
	{
		took = builder.took;
		
		datasets = builder.datasets.build();
		labels = builder.labels.build();

		fromMillis = builder.fromMillis <= 0 ? null : builder.fromMillis;
		toMillis = builder.toMillis <= 0 ? null : builder.toMillis;
		
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
		private final ImmutableSet.Builder<String> labels = ImmutableSet.builder();
		private final ImmutableList.Builder<KPIDataSet> datasets = ImmutableList.builder();
		private long fromMillis;
		private long toMillis;
		
		private String took;

		private Builder()
		{
			super();
		}

		public KPIDataResult build()
		{
			return new KPIDataResult(this);
		}

		public Builder addDataSet(final KPIDataSet dataset)
		{
			datasets.add(dataset);
			return this;
		}

		public Builder setTimeRange(final long fromMillis, final long toMillis)
		{
			this.fromMillis = fromMillis;
			this.toMillis = toMillis;
			return this;
		}

		public Builder setTook(final Stopwatch took)
		{
			this.took = took.toString();
			return this;
		}

	}
}
