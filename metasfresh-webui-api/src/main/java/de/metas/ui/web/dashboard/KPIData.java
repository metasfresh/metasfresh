package de.metas.ui.web.dashboard;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class KPIData
{
	public static Builder builder()
	{
		return new Builder();
	}
	
	@JsonProperty("itemId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer itemId;

	@JsonProperty("data")
	private final List<KPIValue> data;

	@JsonProperty("fromMillis")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long fromMillis;
	@JsonProperty("toMillis")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Long toMillis;

	private KPIData(final Builder builder)
	{
		data = builder.data.build();
		fromMillis = builder.fromMillis <= 0 ? null : builder.fromMillis;
		toMillis = builder.toMillis <= 0 ? null : builder.toMillis;
	}

	public List<KPIValue> getData()
	{
		return data;
	}
	
	public KPIData setItemId(int itemId)
	{
		this.itemId = itemId;
		return this;
	}

	public static final class Builder
	{
		private final ImmutableList.Builder<KPIValue> data = ImmutableList.builder();
		private long fromMillis;
		private long toMillis;

		private Builder()
		{
			super();
		}

		public KPIData build()
		{
			return new KPIData(this);
		}

		public Builder setTimeRange(final long fromMillis, final long toMillis)
		{
			this.fromMillis = fromMillis;
			this.toMillis = toMillis;
			return this;
		}

		public Builder addValue(final KPIValue value)
		{
			data.add(value);
			return this;
		}
	}
}
